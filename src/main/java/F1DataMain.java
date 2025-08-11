import individualLap.CarDamageInfo;
import individualLap.CarStatusInfo;
import individualLap.CarTelemetryInfo;
import individualLap.IndividualLapInfo;
import packets.*;
import packets.enums.DriverStatusEnum;
import packets.events.ButtonsData;
import packets.events.SpeedTrapData;
import telemetry.TelemetryData;
import telemetry.TelemetryRunData;
import ui.dto.DriverDataDTO;
import ui.dto.SpeedTrapDataDTO;
import utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class F1DataMain {

    private final Map<Integer, TelemetryData> participants = new HashMap<>();
    private int playerCarIndex = -1;

    public void run(Consumer<DriverDataDTO> driverDataDTO, Consumer<SpeedTrapDataDTO> speedTrapDataDTO) {
        int port = Constants.PORT_NUM;
        byte[] buffer = new byte[2048];
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(packet);
                int length = packet.getLength();
                ByteBuffer byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(buffer, 0, length));
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                //Parse the packetheader that comes in on every packet.
                PacketHeader ph = new PacketHeader(byteBuffer);
                //Only update this on the first pass, as the value will never change once its set.
                if (playerCarIndex < 0) playerCarIndex = ph.getPlayerCarIndex();
                //Swtich to handle the correct logic based on what packet has been sent.
                switch (ph.getPacketId()) {
                    case Constants.MOTION_PACK:
                        break;
                    case Constants.EVENT_PACK:
                        handleEventPacket(byteBuffer, speedTrapDataDTO);
                        break;
                    case Constants.LAP_DATA_PACK:
                        handleLapDataPacket(byteBuffer, driverDataDTO);
                        break;
                    case Constants.CAR_SETUP_PACK:
                        handleCarSetupPacket(byteBuffer);
                        break;
                    case Constants.PARTICIPANTS_PACK:
                        handleParticipantsPacket(byteBuffer, driverDataDTO);
                        break;
                    case Constants.CAR_TELEMETRY_PACK:
                        handleCarTelemetryPacket(byteBuffer);
                        break;
                    case Constants.CAR_STATUS_PACK:
                        handleCarStatusPacket(byteBuffer);
                        break;
                    case Constants.CAR_DAMAGE_PACK:
                        handleCarDamagePacket(byteBuffer);
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Checks if the map of participants(drivers in session) contains the id we are looking for. Prevents extra ids for custom team from printing stuff when they have no data.
    private boolean validKey(int i) {
        return participants.containsKey(i);
    }

    //Handles the event packet. This one is different from the others as the packet changes based on what event has happened.
    //Currently I only care about the button event and the speed trap triggered event.
    private void handleEventPacket(ByteBuffer byteBuffer, Consumer<SpeedTrapDataDTO> speedTrapDataDTO) {
        byte[] codeArray = new byte[4];
        byteBuffer.get(codeArray, 0, 4);
        String value = new String(codeArray, StandardCharsets.US_ASCII);
        if (Constants.BUTTON_PRESSED_EVENT.equals(value)) {
            ButtonsData bd = new ButtonsData(byteBuffer);
            //These are the 2 values that are the pause buttons on the McLaren GT3 wheel.
            if (Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN == bd.getButtonsStatus()
                    || Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN2 == bd.getButtonsStatus()) {
                //On pause I am printing lap data to the console and other information for each car.
                for (Map.Entry<Integer, TelemetryData> entry : participants.entrySet()) {
                    Integer key = entry.getKey();
                    TelemetryData td = entry.getValue();
                    System.out.println();
                    System.out.println("ID " + key);
                    td.getParticipantData().printName();
                    System.out.println("Setup: " + td.getCurrentSetup().getSetupName() + " Lap #:" + td.getLastLapNum() + " Lap Time " + td.getLastLapTimeInMs());
                    for (TelemetryRunData trd : td.getTelemetryRunDataList()) {
                        System.out.println("Time " + trd.getStartTime() + " Setup " + trd.getCarSetupData().getSetupName());
                        for (Map.Entry<Integer, IndividualLapInfo> entry1 : trd.getLapData().entrySet()) {
                            IndividualLapInfo info = entry1.getValue();
                            System.out.println("Lap #:" + entry1.getKey() + " Time " + info.getLapTimeInMs());
                        }
                    }
                    System.out.println("-------------------------------------------");
                }
            }
        } else if (Constants.SPEED_TRAP_TRIGGERED_EVENT.equals(value)) {
            SpeedTrapData trap = new SpeedTrapData(byteBuffer);
            //Vehicle ID is the id of the driver based on the order they were presented for the participants' data.
            TelemetryData td = participants.get(trap.getVehicleId());
            td.setSpeedTrap(trap.getSpeed());
            //Populate the speedTrap consumer so that the panels get updated with the latest data.
            speedTrapDataDTO.accept(new SpeedTrapDataDTO(td.getParticipantData().getDriverId(), td.getParticipantData().getLastName(), trap.getSpeed(), td.getCurrentLap().getCurrentLapNum(), td.getNumActiveCars()));
        }
    }

    //Parses the lap data packet.
    private void handleLapDataPacket(ByteBuffer byteBuffer, Consumer<DriverDataDTO> driverDataDTO) {
        for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
            LapData ld = new LapData(byteBuffer);
            //Only look at this data if its a validKey, with 22 cars worth of data, but some modes only have 20 cars
            if (validKey(i)) {
                TelemetryData td = participants.get(i);
                if (td.getCurrentLap() != null) {
                    //If we have started a new lap, we need to create the info record, before we overnight the telemetry's ld object.
                    if (ld.getCurrentLapNum() > td.getCurrentLap().getCurrentLapNum()) {
                        //Calculate the fuel used this lap and tire wear this lap for use in the individual Info object.
                        //then update the start params so that next laps calculations use this laps ending values as there start values.
                        float fuelUsedThisLap = td.getStartOfLapFuelInTank() - td.getCurrentFuelInTank();
                        td.setStartOfLapFuelInTank(td.getCurrentFuelInTank());
                        float[] tireWearThisLap = new float[4];
                        for (int j = 0; j < tireWearThisLap.length; j++) {
                            tireWearThisLap[j] = td.getCurrentTireWear()[j] - td.getStartOfLapTireWear()[j];
                        }
                        td.setStartOfLapTireWear(td.getCurrentTireWear());
                        IndividualLapInfo info = new IndividualLapInfo(ld, td.getCurrentLap(), td.getSpeedTrap(), fuelUsedThisLap, tireWearThisLap);
                        td.setLastLapNum(info.getLapNum());
                        td.setLastLapTimeInMs(info.getLapTimeInMs());
                        if (td.getCurrentSetup() != null) {
                            info.setCarSetupData(td.getCurrentSetup());
                            info.setSetupChange(td.isSetupChange());
                            td.setSetupChange(false);
                        }
                        if (td.getCurrentTelemetry() != null) {
                            info.setCarTelemetryInfo(new CarTelemetryInfo(td.getCurrentTelemetry()));
                        }
                        if (td.getCurrentStatus() != null) {
                            info.setCarStatusInfo(new CarStatusInfo(td.getCurrentStatus()));
                        }
                        if (td.getCurrentDamage() != null) {
                            info.setCarDamageInfo(new CarDamageInfo(td.getCurrentDamage()));
                        }
                        if (!td.getTelemetryRunDataList().isEmpty()) {
                            TelemetryRunData trd = td.getTelemetryRunDataList().get(td.getTelemetryRunDataList().size() - 1);
                            trd.getLapData().put(info.getLapNum(), info);
                            participants.put(i, td);
                        }
                        //Print info when the lap is completed.
                        info.printInfo(td.getParticipantData().getLastName());
                        info.printStatus(td.getParticipantData().getLastName());
                        info.printDamage(td.getParticipantData().getLastName());
                        //Populate the DriverDataDTO to populate the panels.
                        driverDataDTO.accept(new DriverDataDTO(td.getParticipantData().getDriverId(), td.getParticipantData().getLastName(), info, i == playerCarIndex));
                    }
                    td.setCurrentLap(ld);
                } else {
                    td.setCurrentLap(ld);
                }
                //If on an out lap or flying lap and current setup is already established in td.
                if ((DriverStatusEnum.OUT_LAP.getValue() == ld.getDriverStatus() || DriverStatusEnum.FLYING_LAP.getValue() == ld.getDriverStatus())
                        && td.getCurrentSetup() != null) {
                    //If the TelemetryRunDataList isn't empty do A.
                    if (!td.getTelemetryRunDataList().isEmpty()) {
                        //Get the last record in the list, as its the most recently added run data object.
                        TelemetryRunData trd = td.getTelemetryRunDataList().get(td.getTelemetryRunDataList().size() - 1);
                        //Car setup logic does a full compare of the values, so object comparison 'should' be good enough here.
                        if (trd.getCarSetupData() != td.getCurrentSetup()) {
                            //If the setup has changed compared to the last setup in the run data, we need to add a new record to run data.
                            td.getTelemetryRunDataList().add(new TelemetryRunData(td.getCurrentSetup()));
                        }
                    } else {
                        //Else first time out on track, so we know we can just add this new object to the list.
                        td.getTelemetryRunDataList().add(new TelemetryRunData(td.getCurrentSetup()));
                    }
                }
            }
        }
        //Time trail params at the end of the Lap Data packet. Only there a single time, therefore they are outside of the loop.
        int timeTrailPBCarId = byteBuffer.get() & Constants.BIT_MASK_8;
        int timeTrailRivalPdCarId = byteBuffer.get() & Constants.BIT_MASK_8;
    }

    //Parses the car setup packet.
    private void handleCarSetupPacket(ByteBuffer byteBuffer) {
        for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
            CarSetupData csd = new CarSetupData(byteBuffer);
            if (validKey(i)) {
                TelemetryData td = participants.get(i);
                csd.setSetupName(td.getParticipantData().getLastName());
                if (td.getCurrentSetup() == null || !csd.equals(td.getCurrentSetup())) {
//                    System.out.println("i " + i + " Name " + csd.getSetupName() + " Inside td.getCurrentSetup == null. Current Setup Val " + td.getCurrentSetup());
                    td.setCurrentSetup(csd);
                    td.setSetupChange(true);
                }
//                System.out.println("I " + i + " Front Wing " + csd.getFrontWing() + " Rear " + csd.getRearWing());
            }
        }
        //Trailing value, must be here to ensure the packet is fully parsed.
        float nextFronWingVal = byteBuffer.getFloat();
    }

    //Parses the Car Telemetry packet.
    private void handleCarTelemetryPacket(ByteBuffer byteBuffer) {
        handlePacket(byteBuffer, CarTelemetryData::new, TelemetryData::setCurrentTelemetry);
        //Params at the end of the Telemetry packet, not associated with each car. Keep here to ensure the byteBuffer position is moved correctly.
        int mfdPanelIdx = byteBuffer.get() & Constants.BIT_MASK_8;
        int mfdPanelIdxSecondPlayer = byteBuffer.get() & Constants.BIT_MASK_8;
        int suggestedGear = byteBuffer.get();
    }

    //Parses the car status packet.
    private void handleCarStatusPacket(ByteBuffer byteBuffer) {
        handlePacket(byteBuffer, CarStatusData::new, TelemetryData::setCurrentStatus);
    }

    //Parses the car Damage Packet
    private void handleCarDamagePacket(ByteBuffer byteBuffer) {
        handlePacket(byteBuffer, CarDamageData::new, TelemetryData::setCurrentDamage);
    }

    //Parses the participant packet
    private void handleParticipantsPacket(ByteBuffer byteBuffer, Consumer<DriverDataDTO> driverDataDTO) {
        if (participants.isEmpty()) {
            //DO NOT DELETE THIS LINE, you will break the logic below it, we have to move the position with the .get() for the logic to work.
            int numActiveCars = byteBuffer.get();
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                ParticipantData pd = new ParticipantData(byteBuffer);
                if (pd.getRaceNumber() > 0) {
                    pd.printName();
                    TelemetryData td = new TelemetryData(pd, numActiveCars);
                    participants.put(i, td);
                    //Populates the initial DriverDataDTO consumer for the UI.
                    driverDataDTO.accept(new DriverDataDTO(td.getParticipantData().getDriverId(), td.getParticipantData().getLastName(), i == playerCarIndex));
                }
            }
        }
    }

    //Using Generics to condense the common logic as a decent chunk of the packets have the same logic flow
    private <T> void handlePacket(ByteBuffer byteBuffer, Function<ByteBuffer, T> packetCreator, BiConsumer<TelemetryData, T> dataSetter) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                T packet = packetCreator.apply(byteBuffer);
                if (validKey(i)) {
                    dataSetter.accept(participants.get(i), packet);
                }
            }
        }
    }
}

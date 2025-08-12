import individualLap.CarDamageInfo;
import individualLap.CarStatusInfo;
import individualLap.CarTelemetryInfo;
import individualLap.IndividualLapInfo;
import packets.*;
import packets.enums.DriverPairingsEnum;
import packets.enums.FormulaTypeEnum;
import packets.events.ButtonsData;
import packets.events.SpeedTrapData;
import telemetry.TelemetryData;
import ui.dto.DriverDataDTO;
import ui.dto.SpeedTrapDataDTO;
import utils.constants.Constants;

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
    private int gameYear = -1;
    private DriverPairingsEnum driverPairingsEnum = null;
    private FormulaTypeEnum formulaType = null;

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
                if (gameYear < 0) {
                    gameYear = ph.getGameYear();
                    driverPairingsEnum = DriverPairingsEnum.fromYear(gameYear);
                }
                //Switch to handle the correct logic based on what packet has been sent.
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
                    case Constants.TYRE_SETS_PACK:
                        handleTireSetsPacket(byteBuffer);
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
                        //If we have had a change of tire, that counts as a setup change. Let info object know and update the prevTireCompound value.
                        if (td.getFittedTireId() != td.getPrevLapFittedTireId()) {
                            info.setSetupChange(true);
                            td.setPrevLapFittedTireId(td.getFittedTireId());
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
                boolean isNullOrChanged = (td.getCurrentSetup() == null || !csd.equals(td.getCurrentSetup()));
                if (isNullOrChanged || !csd.isSameFuelLoad(td.getCurrentSetup())) {
//                    System.out.println("i " + i + " Name " + csd.getSetupName() + " Inside td.getCurrentSetup == null. Current Setup Val " + td.getCurrentSetup());
                    td.setCurrentSetup(csd);
                    if (isNullOrChanged) td.setSetupChange(true);
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

    private void handleTireSetsPacket(ByteBuffer byteBuffer) {
        int carId = byteBuffer.get() & Constants.BIT_MASK_8;
        TelemetryData td = participants.get(carId);
        TireSetsData[] tireSetsData = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];
        for (int i = 0; i < Constants.TIRE_SETS_PACKET_COUNT; i++) {
            tireSetsData[i] = new TireSetsData(byteBuffer);
        }
        td.setTireSetsData(tireSetsData);
        int fittedId = byteBuffer.get() & Constants.BIT_MASK_8;
        if (td.getFittedTireId() != fittedId) {
            td.setFittedTireId(fittedId);
        }
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
                    //this will only be updated once we found a driver that exists in a single series, so we know what driver lineups to use.
                    if (formulaType == null) {
                        //In F1 24 no drivers in F1 also exist in either of the F2 lineups. This is not true in F1 25, so would need changing.
                        if (driverPairingsEnum.getF1DriverPairs().containsKey(pd.getDriverId())) {
                            formulaType = FormulaTypeEnum.F1;
                        } else {
                            boolean f2Current = driverPairingsEnum.getF2DriverPairs().containsKey(pd.getDriverId());
                            boolean f2Prev = driverPairingsEnum.getF2PrevYearDriverPairs().containsKey(pd.getDriverId());
                            //If a driver exists in one F2 lineup but not the other then we have found what series we are using.
                            //TODO: if I ever get F1 25 and want to use this with that game, ensure this logic still works for it.
                            if (f2Current && !f2Prev) {
                                formulaType = FormulaTypeEnum.F2;
                            } else if (!f2Current && f2Prev) {
                                formulaType = FormulaTypeEnum.F2_PREV;
                            }
                        }
                    }
                }
            }
            assert formulaType != null;
            //Get the active driver pairings based on what formula we are.
            Map<Integer, Integer> driverPairing = driverPairingsEnum.getDriverPair(formulaType.getIndex());
            //Loop over each created TD object to create the DriverDataDTO to update the UI.
            //Do this outside the loop above to ensure we know what driver lineup we are using for the UI.
            for (int j = 0; j < participants.size(); j++) {
                TelemetryData td = participants.get(j);
                //Populates the initial DriverDataDTO consumer for the UI.
                driverDataDTO.accept(new DriverDataDTO(td.getParticipantData().getDriverId(), td.getParticipantData().getLastName(), j == playerCarIndex, driverPairing, formulaType));
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

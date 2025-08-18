package f1.data;

import f1.data.individualLap.CarDamageInfo;
import f1.data.individualLap.CarStatusInfo;
import f1.data.individualLap.CarTelemetryInfo;
import f1.data.individualLap.IndividualLapInfo;
import f1.data.packets.*;
import f1.data.packets.enums.DriverPairingsEnum;
import f1.data.packets.enums.DriverStatusEnum;
import f1.data.packets.enums.FormulaTypeEnum;
import f1.data.packets.events.ButtonsData;
import f1.data.packets.events.SpeedTrapData;
import f1.data.packets.events.SpeedTrapDataFactory;
import f1.data.telemetry.TelemetryData;
import f1.data.ui.dto.DriverDataDTO;
import f1.data.ui.dto.SpeedTrapDataDTO;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataMain {

    private static final Logger logger = LoggerFactory.getLogger(F1DataMain.class);

    private final Map<Integer, TelemetryData> participants = new HashMap<>();
    private int playerCarIndex = -1;
    private int packetFormat = -1;
    private DriverPairingsEnum driverPairingsEnum = null;
    private FormulaTypeEnum formulaType = null;
    private float speedTrapDistance = -50;

    private final int[][] packetCounts = new int[15][1];

    public void run(Consumer<DriverDataDTO> driverDataDTO, Consumer<SpeedTrapDataDTO> speedTrapDataDTO) {
        logger.info("In DataMain");
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
                PacketHeader ph = PacketHeaderFactory.build(byteBuffer);
                logger.info("Packet {} Length {}", ph.packetId(), length);
                //Only update this on the first pass, as the value will never change once its set.
                if (playerCarIndex < 0) playerCarIndex = ph.playerCarIndex();
                if (packetFormat < 0) {
                    //packet format is constantly the year (2020, 2024) game year changes from year to year it seems.
                    packetFormat = ph.packetFormat();
                    driverPairingsEnum = DriverPairingsEnum.fromYear(packetFormat);
                }
                //Switch to handle the correct logic based on what packet has been sent.
                switch (ph.packetId()) {
                    case Constants.MOTION_PACK:
                        handleMotionPacket(byteBuffer);
                        packetCounts[Constants.MOTION_PACK][0]++;
                        break;
                    case Constants.EVENT_PACK:
                        handleEventPacket(byteBuffer, speedTrapDataDTO);
                        packetCounts[Constants.EVENT_PACK][0]++;
                        break;
                    case Constants.LAP_DATA_PACK:
                        handleLapDataPacket(byteBuffer, driverDataDTO, speedTrapDataDTO);
                        packetCounts[Constants.LAP_DATA_PACK][0]++;
                        break;
                    case Constants.CAR_SETUP_PACK:
                        handleCarSetupPacket(byteBuffer);
                        packetCounts[Constants.CAR_SETUP_PACK][0]++;
                        break;
                    case Constants.PARTICIPANTS_PACK:
                        handleParticipantsPacket(byteBuffer, driverDataDTO);
                        packetCounts[Constants.PARTICIPANTS_PACK][0]++;
                        break;
                    case Constants.CAR_TELEMETRY_PACK:
                        handleCarTelemetryPacket(byteBuffer);
                        packetCounts[Constants.CAR_TELEMETRY_PACK][0]++;
                        break;
                    case Constants.CAR_STATUS_PACK:
                        handleCarStatusPacket(byteBuffer);
                        packetCounts[Constants.CAR_STATUS_PACK][0]++;
                        break;
                    case Constants.CAR_DAMAGE_PACK:
                        handleCarDamagePacket(byteBuffer);
                        packetCounts[Constants.CAR_DAMAGE_PACK][0]++;
                        break;
                    case Constants.TYRE_SETS_PACK:
                        handleTireSetsPacket(byteBuffer);
                        packetCounts[Constants.TYRE_SETS_PACK][0]++;
                        break;
                }
            }
        } catch (IOException e) {
            logger.error("e ", e);
            throw new RuntimeException(e);
        }
    }

    //Checks if the map of participants(drivers in session) contains the id we are looking for. Prevents extra ids for custom team from printing stuff when they have no data.
    private boolean validKey(int i) {
        return participants.containsKey(i);
    }

    private void handleMotionPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                MotionData md = new MotionData(byteBuffer);
            }
            //Params existed OUTSIDE of the main array in the struct until 2023 when they went away.
            if (packetFormat <= Constants.YEAR_2022) {
                float[] suspPosition = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float[] suspVelocity = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float[] suspAcceleration = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float[] wheelSpin = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float[] wheelSlip = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float localVelocityX = byteBuffer.getFloat();
                float localVelocityY = byteBuffer.getFloat();
                float localVelocityZ = byteBuffer.getFloat();
                float angularVelocityX = byteBuffer.getFloat();
                float angularVelocityY = byteBuffer.getFloat();
                float angularVelocityZ = byteBuffer.getFloat();
                float angularAccelerationX = byteBuffer.getFloat();
                float angularAccelerationY = byteBuffer.getFloat();
                float angularAccelerationZ = byteBuffer.getFloat();
                float frontWheelsAngle = byteBuffer.getFloat();
            }
        }
    }

    //Handles the event packet. This one is different from the others as the packet changes based on what event has happened.
    //Currently I only care about the button event and the speed trap triggered event.
    private void handleEventPacket(ByteBuffer byteBuffer, Consumer<SpeedTrapDataDTO> speedTrapDataDTO) {
        if (!participants.isEmpty()) {
            byte[] codeArray = new byte[4];
            byteBuffer.get(codeArray, 0, 4);
            String value = new String(codeArray, StandardCharsets.US_ASCII);
            if (Constants.BUTTON_PRESSED_EVENT.equals(value)) {
                ButtonsData bd = new ButtonsData(byteBuffer);
                //These are the 2 values that are the pause buttons on the McLaren GT3 wheel.
                if (Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN == bd.buttonsStatus()
                        || Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN2 == bd.buttonsStatus()
                ) {
                    //On pause I am printing lap data to the console and other information for each car.
                    for (Map.Entry<Integer, TelemetryData> entry : participants.entrySet()) {
                        Integer key = entry.getKey();
                        TelemetryData td = entry.getValue();
                        System.out.println();
                        System.out.println("ID " + key);
                        td.getParticipantData().printName();
                        System.out.println("Setup: " + td.getCurrentSetup().setupName() + " Lap #:" + td.getLastLapNum() + " Lap Time " + td.getLastLapTimeInMs());
                        System.out.println("-------------------------------------------");
                    }
                }
            } else if (Constants.SPEED_TRAP_TRIGGERED_EVENT.equals(value)) {
                SpeedTrapData trap = SpeedTrapDataFactory.build(packetFormat, byteBuffer);
                //Vehicle ID is the id of the driver based on the order they were presented for the participants' data.
                TelemetryData td = participants.get(trap.vehicleId());
                td.setSpeedTrap(trap.speed());
                if (packetFormat <= Constants.YEAR_2020 && speedTrapDistance < 0) {
                    speedTrapDistance = td.getCurrentLap().lapDistance();
                }
                //Populate the speedTrap consumer so that the panels get updated with the latest data.
                speedTrapDataDTO.accept(new SpeedTrapDataDTO(td.getParticipantData().driverId(), td.getParticipantData().lastName(), trap.speed(), td.getCurrentLap().currentLapNum(), td.getNumActiveCars()));
            }
        }
    }

    //Parses the lap data packet.
    private void handleLapDataPacket(ByteBuffer byteBuffer, Consumer<DriverDataDTO> driverDataDTO, Consumer<SpeedTrapDataDTO> speedTrapDataDTO) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                LapData ld = LapDataFactory.build(packetFormat, byteBuffer);
                //Only look at this data if its a validKey, with 22 cars worth of data, but some modes only have 20 cars
                if (validKey(i)) {
                    TelemetryData td = participants.get(i);
                    if (td.getCurrentLap() != null) {
                        //If we have started a new lap, we need to create the info record, before we overnight the telemetry's ld object.
                        if (ld.currentLapNum() > td.getCurrentLap().currentLapNum()) {
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
                            info.printInfo(td.getParticipantData().lastName());
                            info.printStatus(td.getParticipantData().lastName());
                            info.printDamage(td.getParticipantData().lastName());
                            //Populate the DriverDataDTO to populate the panels.
                            driverDataDTO.accept(new DriverDataDTO(td.getParticipantData().driverId(), td.getParticipantData().lastName(), info, i == playerCarIndex));
                            System.out.println(participants.get(i).getParticipantData().lastName() + " " + speedTrapDistance + " " + td.getSpeedTrap());
                            //Reset the speed trap value so the older games will know it needs to be reset on the next lap.
                            td.setSpeedTrap(0.0F);
                        }
                        td.setCurrentLap(ld);
                    } else {
                        td.setCurrentLap(ld);
                    }
                    //F1 2020 only sent a speed trap event when a new fastest speed was set in the session.
                    //So for that game, if the lap distance is within a certain amount of the distance when the first speed trap was registered
                    //We get the cars current speed. I have it within a certain distance each way, this should catch the majority of cars.
                    if (packetFormat <= Constants.YEAR_2020) {
                        if (td.getCurrentLap().driverStatus() == DriverStatusEnum.FLYING_LAP.getValue() &&
                                (td.getCurrentLap().lapDistance() >= (speedTrapDistance - 1.75) && td.getCurrentLap().lapDistance() <= (speedTrapDistance + 1.75))) {
                            if (td.getCurrentTelemetry() != null) td.setSpeedTrap(td.getCurrentTelemetry().speed());
                            speedTrapDataDTO.accept(new SpeedTrapDataDTO(td.getParticipantData().driverId(), td.getParticipantData().lastName(), td.getSpeedTrap(), td.getCurrentLap().currentLapNum(), td.getNumActiveCars()));
                            System.out.println(participants.get(i).getParticipantData().lastName() + " " + speedTrapDistance + " " + td.getCurrentLap().lapDistance() + " " + td.getSpeedTrap());
                        }
                    }
                }
            }
            if (packetFormat >= Constants.YEAR_2022) {
                //Time trail params at the end of the Lap Data packet. Only there a single time, therefore they are outside of the loop.
                int timeTrailPBCarId = BitMaskUtils.bitMask8(byteBuffer.get());
                int timeTrailRivalPdCarId = BitMaskUtils.bitMask8(byteBuffer.get());
            }
        }
    }

    //Parses the car setup packet.
    private void handleCarSetupPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                boolean isValidKey = validKey(i);
                //If this isn't a valid key, we still need to parse the packet to ensure the position in the parser is updated.
                //Pass an empty string as this setup isn't going to be saved anywhere, so we don't care about the value.
                String setupName = (isValidKey) ? participants.get(i).getParticipantData().lastName() : "";
                CarSetupData csd = CarSetupDataFactory.build(packetFormat, byteBuffer, setupName);
                if (isValidKey) {
                    TelemetryData td = participants.get(i);
                    boolean isNullOrChanged = (td.getCurrentSetup() == null || !csd.equals(td.getCurrentSetup()));
                    if (isNullOrChanged || !csd.isSameFuelLoad(td.getCurrentSetup())) {
                        td.setCurrentSetup(csd);
                        if (isNullOrChanged) td.setSetupChange(true);
                    }
                }
            }
            //Trailing value, must be here to ensure the packet is fully parsed.
            //nextFrontWingVal was added in the 24 data as a param AFTER the 22 car setups had been processed.
            if (packetFormat >= Constants.YEAR_2024) {
                float nextFronWingVal = byteBuffer.getFloat();
            }
        }
    }

    //Parses the Car Telemetry packet.
    private void handleCarTelemetryPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                CarTelemetryData ctd = CarTelemetryDataFactory.build(packetFormat, byteBuffer);
                if (validKey(i)) {
                    participants.get(i).setCurrentTelemetry(ctd);
                }
            }
        }
        //Params at the end of the Telemetry packet, not associated with each car. Keep here to ensure the byteBuffer position is moved correctly.
        if (packetFormat <= Constants.YEAR_2020) {
            long buttonEvent = BitMaskUtils.bitMask32(byteBuffer.getInt());
            //2020 special button press mapped to button 9 on the McLaren wheel. Used to log the # of packets recieved.
//            if (buttonEvent == 8192) {
//                for (int i = 0; i < packetCounts.length; i++) {
//                    logger.info("Packet # {} Count {}", PacketTypeEnum.findByValue(i).name(), packetCounts[i][0]);
//                }
//            }
        }
        int mfdPanelIdx = BitMaskUtils.bitMask8(byteBuffer.get());
        int mfdPanelIdxSecondPlayer = BitMaskUtils.bitMask8(byteBuffer.get());
        int suggestedGear = byteBuffer.get();
    }

    //Parses the car status packet.
    private void handleCarStatusPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                CarStatusData csd = CarStatusDataFactory.build(packetFormat, byteBuffer);
                if (validKey(i)) {
                    participants.get(i).setCurrentStatus(csd);
                    if (packetFormat <= Constants.YEAR_2020) {
                        CarDamageData cdd = CarDamageData.fromStatus(csd);
                        participants.get(i).setCurrentDamage(cdd);
                    }
                }
            }
        }
    }

    //Parses the car Damage Packet
    private void handleCarDamagePacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                CarDamageData cdd = CarDamageDataFactory.build(packetFormat, byteBuffer);
                if (validKey(i)) {
                    participants.get(i).setCurrentDamage(cdd);
                }
            }
        }
    }

    private void handleTireSetsPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int carId = BitMaskUtils.bitMask8(byteBuffer.get());
            TelemetryData td = participants.get(carId);
            TireSetsData[] tireSetsData = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];
            for (int i = 0; i < Constants.TIRE_SETS_PACKET_COUNT; i++) {
                tireSetsData[i] = new TireSetsData(byteBuffer);
            }
            td.setTireSetsData(tireSetsData);
            int fittedId = BitMaskUtils.bitMask8(byteBuffer.get());
            if (td.getFittedTireId() != fittedId) {
                td.setFittedTireId(fittedId);
            }
        }
    }

    //Parses the participant packet
    private void handleParticipantsPacket(ByteBuffer byteBuffer, Consumer<DriverDataDTO> driverDataDTO) {
        if (participants.isEmpty()) {
            //DO NOT DELETE THIS LINE, you will break the logic below it, we have to move the position with the .get() for the logic to work.
            int numActiveCars = byteBuffer.get();
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                ParticipantData pd = ParticipantDataFactory.build(packetFormat, byteBuffer);
                if (pd.raceNumber() > 0) {
                    pd.printName();
                    TelemetryData td = new TelemetryData(pd, numActiveCars);
                    participants.put(i, td);
                    //this will only be updated once we found a driver that exists in a single series, so we know what driver lineups to use.
                    if (formulaType == null) {
                        //In F1 24 no drivers in F1 also exist in either of the F2 lineups. This is not true in F1 25, so would need changing.
                        if (driverPairingsEnum.getF1DriverPairs().containsKey(pd.driverId())) {
                            formulaType = FormulaTypeEnum.F1;
                        } else {
                            boolean f2Current = driverPairingsEnum.getF2DriverPairs().containsKey(pd.driverId());
                            boolean f2Prev = driverPairingsEnum.getF2PrevYearDriverPairs().containsKey(pd.driverId());
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
            //Get the active driver pairings based on what formula we are.
            Map<Integer, Integer> driverPairing = driverPairingsEnum.getDriverPair(formulaType.getIndex());
            //Loop over each created TD object to create the DriverDataDTO to update the UI.
            //Do this outside the loop above to ensure we know what driver lineup we are using for the UI.
            for (int j = 0; j < participants.size(); j++) {
                TelemetryData td = participants.get(j);
                //Populates the initial DriverDataDTO consumer for the UI.
                driverDataDTO.accept(new DriverDataDTO(td.getParticipantData().driverId(), td.getParticipantData().lastName(), j == playerCarIndex, driverPairing, formulaType));
            }
        }
    }
}

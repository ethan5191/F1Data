package f1.data.packets.handlers;

import f1.data.individualLap.CarDamageInfo;
import f1.data.individualLap.CarStatusInfo;
import f1.data.individualLap.CarTelemetryInfo;
import f1.data.individualLap.IndividualLapInfo;
import f1.data.packets.LapData;
import f1.data.packets.LapDataFactory;
import f1.data.packets.PacketUtils;
import f1.data.packets.enums.DriverStatusEnum;
import f1.data.packets.events.SpeedTrapDistance;
import f1.data.telemetry.TelemetryData;
import f1.data.ui.dto.DriverDataDTO;
import f1.data.ui.dto.ParentConsumer;
import f1.data.ui.dto.SpeedTrapDataDTO;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.function.Consumer;

public class LapDataPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;
    private final Consumer<DriverDataDTO> driverData;
    private final Consumer<SpeedTrapDataDTO> speedTrapData;
    private final SpeedTrapDistance speedTrapDistance;

    public LapDataPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, ParentConsumer parent, SpeedTrapDistance speedTrapDistance) {
        this.packetFormat = packetFormat;
        this.participants = participants;
        this.driverData = parent.driverDataDTOConsumer();
        this.speedTrapData = parent.speedTrapDataDTOConsumer();
        this.speedTrapDistance = speedTrapDistance;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                LapData ld = LapDataFactory.build(packetFormat, byteBuffer);
                //Only look at this data if its a validKey, with 22 cars worth of data, but some modes only have 20 cars
                if (PacketUtils.validKey(participants, i)) {
                    TelemetryData td = participants.get(i);
                    if (td.getCurrentLap() != null) {
                        //If we have started a new lap, we need to create the info record, before we overnight the telemetry's ld object.
                        if (ld.currentLapNum() > td.getCurrentLap().currentLapNum()) {
                            handleNewLap(td, ld);
                        }
                        td.setCurrentLap(ld);
                    } else {
                        td.setCurrentLap(ld);
                    }
                    handle2020Logic(td);
                }
            }
            if (packetFormat >= Constants.YEAR_2022) {
                //Time trail params at the end of the Lap Data packet. Only there a single time, therefore they are outside of the loop.
                int timeTrailPBCarId = BitMaskUtils.bitMask8(byteBuffer.get());
                int timeTrailRivalPdCarId = BitMaskUtils.bitMask8(byteBuffer.get());
            }
        }
    }

    private void handleNewLap(TelemetryData td, LapData ld) {
        float fuelUsedThisLap = td.getStartOfLapFuelInTank() - td.getCurrentFuelInTank();
        td.setStartOfLapFuelInTank(td.getCurrentFuelInTank());
        float[] tireWearThisLap = {0, 0, 0, 0};
        for (int i = 0; i < tireWearThisLap.length; i++) {
            tireWearThisLap[i] = td.getCurrentTireWear()[i] - td.getStartOfLapTireWear()[i];
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
        this.driverData.accept(new DriverDataDTO(td.getParticipantData().driverId(), td.getParticipantData().lastName(), info));
        //Reset the speed trap value so the older games will know it needs to be reset on the next lap.
        td.setSpeedTrap(0.0F);
    }

    private void handle2020Logic(TelemetryData td) {
        //F1 2020 only sent a speed trap event when a new fastest speed was set in the session.
        //So for that game, if the lap distance is within a certain amount of the distance when the first speed trap was registered
        //We get the cars current speed. I have it within a certain distance each way, this should catch the majority of cars.
        if (packetFormat <= Constants.YEAR_2020) {
            if (td.getCurrentLap().driverStatus() == DriverStatusEnum.FLYING_LAP.getValue() && (td.getCurrentLap().lapDistance() >= (speedTrapDistance.getDistance() - Constants.TRAP_DISTANCE_BUFFER) && td.getCurrentLap().lapDistance() <= (speedTrapDistance.getDistance() + Constants.TRAP_DISTANCE_BUFFER))) {
                //If this car triggered a speed trap event, then it will already have a value, so don't replace it.
                //the td's speed trap values gets reset to 0.0F at the end of each lap.
                if (td.getCurrentTelemetry() != null && td.getCurrentTelemetry().speed() != 0.0F) {
                    td.setSpeedTrap(td.getCurrentTelemetry().speed());
                    this.speedTrapData.accept(new SpeedTrapDataDTO(td.getParticipantData().driverId(), td.getParticipantData().lastName(), td.getSpeedTrap(), td.getCurrentLap().currentLapNum()));
                }
            }
        }
    }
}


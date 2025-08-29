package f1.data.parse.telemetry;

import f1.data.enums.DriverStatusEnum;
import f1.data.parse.packets.*;
import f1.data.utils.constants.Constants;

import java.math.BigDecimal;

public class TelemetryData {

    public TelemetryData(ParticipantData participantData) {
        this.participantData = participantData;
    }

    private final ParticipantData participantData;
    private Integer lastLapNum;
    private BigDecimal lastLapTimeInMs;
    private float[] currentTireWear = {0, 0, 0, 0};
    private float[] startOfLapTireWear = {0, 0, 0, 0};
    private float currentFuelInTank;
    private float startOfLapFuelInTank = 0;

    private LapData currentLap;
    private CarTelemetryData currentTelemetry;
    private CarStatusData currentStatus;
    private CarDamageData currentDamage;
    private TireSetsData[] tireSetsData = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];

    //Empty object by default to prevent NPEs. The currentSetup param on that object is a driver to determine if its actually populated.
    private final CarSetupTelemetryData carSetupData = new CarSetupTelemetryData();
    //Default this to a speed of 0 on lap 0 to prevent an NPE.
    private SpeedTrapTelemetryData speedTrapData = new SpeedTrapTelemetryData(0, 0);

    public ParticipantData getParticipantData() {
        return participantData;
    }

    public void setCurrentSetup(CarSetupData currentSetup) {
        //Only do the setup logic when the car is on a flying lap. Otherwise, you can end up with a bunch of different setups saved with no data.
        if (this.currentLap != null && this.currentLap.driverStatus() == DriverStatusEnum.FLYING_LAP.getValue()) {
            if (this.carSetupData.getCurrentSetup() == null) {
                this.carSetupData.initialSetup(currentSetup);
            } else {
                this.carSetupData.updateCarSetupData(currentSetup);
            }
        }
    }

    public Integer getLastLapNum() {
        return lastLapNum;
    }

    public void setLastLapNum(Integer lastLapNum) {
        this.lastLapNum = lastLapNum;
    }

    public BigDecimal getLastLapTimeInMs() {
        return lastLapTimeInMs;
    }

    public void setLastLapTimeInMs(BigDecimal lastLapTimeInMs) {
        this.lastLapTimeInMs = lastLapTimeInMs;
    }

    public float[] getCurrentTireWear() {
        return currentTireWear;
    }

    public float[] getStartOfLapTireWear() {
        return startOfLapTireWear;
    }

    public void setStartOfLapTireWear(float[] startOfLapTireWear) {
        this.startOfLapTireWear = (startOfLapTireWear == null) ? new float[4] : startOfLapTireWear;
    }

    public float getCurrentFuelInTank() {
        return currentFuelInTank;
    }

    public float getStartOfLapFuelInTank() {
        return startOfLapFuelInTank;
    }

    public void setStartOfLapFuelInTank(float startOfLapFuelInTank) {
        this.startOfLapFuelInTank = startOfLapFuelInTank;
    }

    public LapData getCurrentLap() {
        return currentLap;
    }

    public void setCurrentLap(LapData currentLap) {
        this.currentLap = currentLap;
    }

    public CarTelemetryData getCurrentTelemetry() {
        return currentTelemetry;
    }

    public void setCurrentTelemetry(CarTelemetryData currentTelemetry) {
        this.currentTelemetry = currentTelemetry;
    }

    public CarStatusData getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CarStatusData currentStatus) {
        this.currentStatus = currentStatus;
        //Only update these values when we are on an actual flying lap.
        if (this.currentLap != null) {
            if (this.currentLap.driverStatus() == DriverStatusEnum.FLYING_LAP.getValue()) {
                this.currentFuelInTank = currentStatus.fuelInTank();
                //default value is -1, if it is that then we need to seed the initial with the current fuel in the tank.
                if (this.startOfLapFuelInTank == -1) {
                    this.startOfLapFuelInTank = this.currentFuelInTank;
                }
            } else if (this.currentLap.driverStatus() == DriverStatusEnum.IN_GARAGE.getValue() || this.currentLap.driverStatus() == DriverStatusEnum.IN_LAP.getValue()) {
                this.startOfLapFuelInTank = 0;
            } else if (this.currentLap.driverStatus() == DriverStatusEnum.OUT_LAP.getValue()) {
                //while on an out lap, theses two values should remain the same, that way the first 'official'
                //lap of the run has the proper start fuel value.
                this.startOfLapFuelInTank = currentStatus.fuelInTank();
                this.currentFuelInTank = currentStatus.fuelInTank();
            }
        }
    }

    public CarDamageData getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(CarDamageData currentDamage) {
        this.currentDamage = currentDamage;
        //Only update these values when we are on an actual flying lap.
        if (this.currentLap != null) {
            if (this.currentLap.driverStatus() == DriverStatusEnum.FLYING_LAP.getValue()) {
                this.currentTireWear = currentDamage.tyresWear();
            } else if (this.currentLap.driverStatus() == DriverStatusEnum.IN_GARAGE.getValue() || this.currentLap.driverStatus() == DriverStatusEnum.IN_LAP.getValue()) {
                this.startOfLapTireWear = new float[]{0, 0, 0, 0};
            } else if (this.currentLap.driverStatus() == DriverStatusEnum.OUT_LAP.getValue()) {
                //while on an out lap, theses two values should remain the same, that way the first 'official'
                //lap of the run has the proper start fuel value.
                this.startOfLapTireWear = currentDamage.tyresWear();
                this.currentTireWear = currentDamage.tyresWear();
            }
        }
    }

    public TireSetsData[] getTireSetsData() {
        return tireSetsData;
    }

    public void setTireSetsData(TireSetsData[] tireSetsData) {
        this.tireSetsData = tireSetsData;
    }

    public CarSetupTelemetryData getCarSetupData() {
        return carSetupData;
    }

    public SpeedTrapTelemetryData getSpeedTrapData() {
        return speedTrapData;
    }

    public void setSpeedTrapData(SpeedTrapTelemetryData speedTrapData) {
        this.speedTrapData = speedTrapData;
    }
}

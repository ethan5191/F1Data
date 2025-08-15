package telemetry;

import packets.*;
import packets.enums.DriverStatusEnum;
import utils.constants.Constants;

import java.math.BigDecimal;

public class TelemetryData {

    public TelemetryData(ParticipantData participantData, int numActiveCars) {
        this.participantData = participantData;
        this.numActiveCars = numActiveCars;
    }

    private final ParticipantData participantData;
    private final int numActiveCars;
    private CarSetupData currentSetup;
    private Integer lastLapNum;
    private BigDecimal lastLapTimeInMs;
    private float speedTrap;
    private float[] currentTireWear;
    private float[] startOfLapTireWear = {-1, -1, -1, -1};
    private float currentFuelInTank;
    private float startOfLapFuelInTank = -1;
    private int fittedTireId;
    private int prevLapFittedTireId;
    private boolean isSetupChange;

    private LapData currentLap;
    private CarTelemetryData currentTelemetry;
    private CarStatusData currentStatus;
    private CarDamageData currentDamage;
    private TireSetsData[] tireSetsData = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];

    public ParticipantData getParticipantData() {
        return participantData;
    }

    public int getNumActiveCars() {
        return numActiveCars;
    }

    public CarSetupData getCurrentSetup() {
        return currentSetup;
    }

    public void setCurrentSetup(CarSetupData currentSetup) {
        this.currentSetup = currentSetup;
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

    public float getSpeedTrap() {
        return speedTrap;
    }

    public void setSpeedTrap(float speedTrap) {
        this.speedTrap = speedTrap;
    }

    public float[] getCurrentTireWear() {
        return currentTireWear;
    }

    public float[] getStartOfLapTireWear() {
        return startOfLapTireWear;
    }

    public void setStartOfLapTireWear(float[] startOfLapTireWear) {
        this.startOfLapTireWear = startOfLapTireWear;
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

    public int getFittedTireId() {
        return fittedTireId;
    }

    public void setFittedTireId(int fittedTireId) {
        this.fittedTireId = fittedTireId;
    }

    public int getPrevLapFittedTireId() {
        return prevLapFittedTireId;
    }

    public void setPrevLapFittedTireId(int prevLapFittedTireId) {
        this.prevLapFittedTireId = prevLapFittedTireId;
    }

    public boolean isSetupChange() {
        return isSetupChange;
    }

    public void setSetupChange(boolean setupChange) {
        isSetupChange = setupChange;
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
            if (this.currentLap.getDriverStatus() == DriverStatusEnum.FLYING_LAP.getValue()) {
                this.currentFuelInTank = currentStatus.getFuelInTank();
                //default value is -1, if it is that then we need to seed the initial with the current fuel in the tank.
                if (this.startOfLapFuelInTank == -1) {
                    this.startOfLapFuelInTank = this.currentFuelInTank;
                }
            } else if (this.currentLap.getDriverStatus() == DriverStatusEnum.IN_GARAGE.getValue() || this.currentLap.getDriverStatus() == DriverStatusEnum.IN_LAP.getValue()) {
                this.startOfLapFuelInTank = -1;
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
            if (this.currentLap.getDriverStatus() == DriverStatusEnum.FLYING_LAP.getValue()) {
                this.currentTireWear = currentDamage.tyresWear();
                //Default values are -1, so if they are that then this is the first pass, so seed the current tire wear as the initial values.
                if (this.startOfLapTireWear[0] == -1) {
                    this.startOfLapTireWear = this.currentTireWear;
                }
            } else if (this.currentLap.getDriverStatus() == DriverStatusEnum.IN_GARAGE.getValue() || this.currentLap.getDriverStatus() == DriverStatusEnum.IN_LAP.getValue()) {
                this.startOfLapTireWear = new float[]{-1, -1, -1, -1};
            }
        }
    }

    public TireSetsData[] getTireSetsData() {
        return tireSetsData;
    }

    public void setTireSetsData(TireSetsData[] tireSetsData) {
        this.tireSetsData = tireSetsData;
    }
}

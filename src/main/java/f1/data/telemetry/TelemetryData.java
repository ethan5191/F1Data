package f1.data.telemetry;

import f1.data.individualLap.IndividualLapInfo;
import f1.data.packets.*;
import f1.data.packets.enums.DriverStatusEnum;
import f1.data.utils.constants.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelemetryData {

    public TelemetryData(ParticipantData participantData, String sessionName) {
        this.participantData = participantData;
        this.sessionName = sessionName;
    }

    private final ParticipantData participantData;
    private final String sessionName;
    private CarSetupData currentSetup;
    private Integer lastLapNum;
    private BigDecimal lastLapTimeInMs;
    private float speedTrap;
    private float[] currentTireWear = {0, 0, 0, 0};
    private float[] startOfLapTireWear = {0, 0, 0, 0};
    private float currentFuelInTank;
    private float startOfLapFuelInTank = 0;
    private int fittedTireId;
    private int prevLapFittedTireId;
    private boolean isSetupChange;

    private LapData currentLap;
    private CarTelemetryData currentTelemetry;
    private CarStatusData currentStatus;
    private CarDamageData currentDamage;
    private TireSetsData[] tireSetsData = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];

    private final Map<CarSetupData, List<IndividualLapInfo>> lapsPerSetup = new HashMap<>();

    public ParticipantData getParticipantData() {
        return participantData;
    }

    public String getSessionName() {
        return sessionName;
    }

    public CarSetupData getCurrentSetup() {
        return currentSetup;
    }

    public void setCurrentSetup(CarSetupData currentSetup) {
        //If this setup is already in the map, put that as the 'current setup' so we don't override its data.
        if (!this.lapsPerSetup.isEmpty()) {
            for (CarSetupData entry : this.lapsPerSetup.keySet()) {
                if (entry.equals(currentSetup)) {
                    this.currentSetup = entry;
                }
            }
        } else {
            this.currentSetup = currentSetup;
            this.lapsPerSetup.put(this.currentSetup, new ArrayList<>());
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

    public Map<CarSetupData, List<IndividualLapInfo>> getLapsPerSetup() {
        return lapsPerSetup;
    }
}

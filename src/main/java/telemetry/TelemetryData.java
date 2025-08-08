package telemetry;

import packets.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TelemetryData {

    public TelemetryData(ParticipantData participantData, int numActiveCars) {
        this.participantData = participantData;
        this.numActiveCars = numActiveCars;
    }

    private final ParticipantData participantData;
    private final List<TelemetryRunData> telemetryRunDataList = new ArrayList<>();
    private final int numActiveCars;
    private CarSetupData currentSetup;
    private Integer lastLapNum;
    private BigDecimal lastLapTimeInMs;
    private float speedTrap;

    private LapData currentLap;
    private CarTelemetryData currentTelemetry;
    private CarStatusData currentStatus;
    private CarDamageData currentDamage;

    public ParticipantData getParticipantData() {
        return participantData;
    }

    public List<TelemetryRunData> getTelemetryRunDataList() {
        return telemetryRunDataList;
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
    }

    public CarDamageData getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(CarDamageData currentDamage) {
        this.currentDamage = currentDamage;
    }
}

import packets.*;
import telemetry.TelemetryRunData;

import java.util.ArrayList;
import java.util.List;

public class TelemetryData {

    public TelemetryData(ParticipantData participantData) {
        this.participantData = participantData;
    }

    private final ParticipantData participantData;
    private final List<TelemetryRunData> telemetryRunDataList = new ArrayList<>();
    private CarSetupData currentSetup;
    private Integer lastLapNum;
    private long lastLapTimeInMs;
    private float speedTrap;

    private LapData currentLap;
    private CarTelemetryData currentTelemetry;
    private CarStatusData currentStatus;

    public ParticipantData getParticipantData() {
        return participantData;
    }

    public List<TelemetryRunData> getTelemetryRunDataList() {
        return telemetryRunDataList;
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

    public long getLastLapTimeInMs() {
        return lastLapTimeInMs;
    }

    public void setLastLapTimeInMs(long lastLapTimeInMs) {
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
}

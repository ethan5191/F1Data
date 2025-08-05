import packets.CarSetupData;
import packets.ParticipantData;

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
    private Integer lastLapTimeInMs;

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

    public Integer getLastLapTimeInMs() {
        return lastLapTimeInMs;
    }

    public void setLastLapTimeInMs(Integer lastLapTimeInMs) {
        this.lastLapTimeInMs = lastLapTimeInMs;
    }
}

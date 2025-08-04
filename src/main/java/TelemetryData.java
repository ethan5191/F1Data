import packets.CarSetupData;
import packets.LapData;
import packets.ParticipantData;

import java.util.ArrayList;
import java.util.List;

public class TelemetryData {

    private ParticipantData participantData;
    private final List<TelemetryRunData> telemetryRunDataList = new ArrayList<>();
    private CarSetupData currentSetup;
    private LapData currentLap;

    public ParticipantData getParticipantData() {
        return participantData;
    }

    public void setParticipantData(ParticipantData participantData) {
        this.participantData = participantData;
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

    public LapData getCurrentLap() {
        return currentLap;
    }

    public void setCurrentLap(LapData currentLap) {
        this.currentLap = currentLap;
    }
}

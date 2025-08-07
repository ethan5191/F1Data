package ui;

import telemetry.TelemetryData;

public class DriverDataDTO {

    public DriverDataDTO(Integer id, TelemetryData telemetryData) {
        this.id = id;
        this.telemetryData = telemetryData;
    }

    private final Integer id;
    private final TelemetryData telemetryData;

    public Integer getId() {
        return id;
    }

    public TelemetryData getTelemetryData() {
        return telemetryData;
    }

    public String getLastName() {
        if (this.telemetryData != null && this.telemetryData.getParticipantData() != null) {
            return this.telemetryData.getParticipantData().getLastName();
        }
        return null;
    }
}

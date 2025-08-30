package f1.data.save;

import f1.data.parse.telemetry.SetupTireKey;

import java.util.List;

public record RunDataMapRecord(SetupTireKey key, List<IndividualLapSessionData> laps) {
}

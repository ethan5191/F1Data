package f1.data.save;

import f1.data.parse.telemetry.SetupTireKey;

import java.util.List;
import java.util.Map;

public record RunDataSessionData(String lastName, Map<SetupTireKey, List<IndividualLapSessionData>> lapsPerSetup) {
}

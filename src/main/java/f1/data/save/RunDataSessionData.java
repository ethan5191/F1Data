package f1.data.save;

import f1.data.parse.packets.CarSetupData;
import f1.data.parse.telemetry.SetupTireKey;

import java.util.List;
import java.util.Map;

public record RunDataSessionData(String lastName, List<CarSetupData> setups, Map<SetupTireKey, List<IndividualLapSessionData>> lapsPerSetup) {
}

package f1.data.save;

import java.util.List;

public record SaveSessionDataWrapper(List<SpeedTrapSessionData> speedTraps, List<RunDataSessionData> runData) implements SaveSessionWrapper{
}

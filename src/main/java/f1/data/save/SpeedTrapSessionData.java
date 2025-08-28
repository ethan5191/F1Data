package f1.data.save;

import java.util.Map;

public record SpeedTrapSessionData(String lastName, Map<Integer, Float> speedTrapByLap) {
}

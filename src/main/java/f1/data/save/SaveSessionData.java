package f1.data.save;

import java.util.Map;

public record SaveSessionData(String lastName, Map<Integer, Float> speedTrapByLap) {
}

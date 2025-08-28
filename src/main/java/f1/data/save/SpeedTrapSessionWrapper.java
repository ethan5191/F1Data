package f1.data.save;

import java.util.List;

//Wrapper class to give the json block a proper title.
public record SpeedTrapSessionWrapper(List<SpeedTrapSessionData> speedTrapSessionData) {
}

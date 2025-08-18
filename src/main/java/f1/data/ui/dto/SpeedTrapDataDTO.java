package f1.data.ui.dto;

import java.util.List;
import java.util.Objects;

public class SpeedTrapDataDTO {

    public SpeedTrapDataDTO(int driverId, String name, float speed, int lapNum, int numActiveCars) {
        this.driverId = driverId;
        this.name = name;
        this.speed = speed;
        this.lapNum = lapNum;
        this.numActiveCars = numActiveCars;
    }

    private final int driverId;
    private final String name;
    private final float speed;
    private final int lapNum;
    private final int numActiveCars;

    public int getDriverId() {
        return driverId;
    }

    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }

    public int getLapNum() {
        return lapNum;
    }

    public int getNumActiveCars() {
        return numActiveCars;
    }

    public static void sortBySpeed(List<SpeedTrapDataDTO> speedTrapRankings) {
        speedTrapRankings.sort((car1, car2) -> Double.compare(car2.speed, car1.speed));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return (Objects.equals(this.name, ((SpeedTrapDataDTO) o).name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}

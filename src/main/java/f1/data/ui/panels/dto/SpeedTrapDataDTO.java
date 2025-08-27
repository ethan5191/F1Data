package f1.data.ui.panels.dto;

import java.util.List;
import java.util.Objects;

public record SpeedTrapDataDTO(int driverId, String name, float speed, int lapNum) {

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

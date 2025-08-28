package f1.data.mapKeys;

import java.util.Objects;

//Represents the key in a Map where you need both the driver ID and the Lap Number.
public record DriverIdLapNum(int driverId, int lapNum) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DriverIdLapNum that = (DriverIdLapNum) o;
        return lapNum == that.lapNum && driverId == that.driverId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, lapNum);
    }
}

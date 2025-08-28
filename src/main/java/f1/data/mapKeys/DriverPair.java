package f1.data.mapKeys;

//Object represents two drivers on a single team
public class DriverPair {

    private final int driverOne;
    private int driverTwo;

    public DriverPair(int driverOne) {
        this.driverOne = driverOne;
    }

    public int getDriverOne() {
        return driverOne;
    }

    public int getDriverTwo() {
        return driverTwo;
    }

    public void setDriverTwo(int driverTwo) {
        this.driverTwo = driverTwo;
    }
}

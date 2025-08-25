package f1.data.enums;

//Current means we will use the latest driver lineup based on the game year.
//Previous means we will use the earlier driver lineup.
//Example F1 2020->Current = F2 2020 drivers, Previous = F2 2019 drivers (from the F2 game itself).
public enum Formula2Enum {

    CURRENT(1),
    PREVIOUS(2);

    private final int value;

    Formula2Enum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

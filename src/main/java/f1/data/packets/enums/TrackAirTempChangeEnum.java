package f1.data.packets.enums;

public enum TrackAirTempChangeEnum {

    UP(0),
    DOWN(1),
    NO_CHANGE(2);

    private final int value;

    TrackAirTempChangeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

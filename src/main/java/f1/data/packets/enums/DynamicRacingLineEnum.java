package f1.data.packets.enums;

public enum DynamicRacingLineEnum {

    OFF(0),
    CORNERS_ONLY(1),
    FULL(2);

    private final int value;

    DynamicRacingLineEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

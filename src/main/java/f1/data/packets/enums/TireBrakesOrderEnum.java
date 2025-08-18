package f1.data.packets.enums;

public enum TireBrakesOrderEnum {
    REAR_LEFT(0),
    REAR_RIGHT(1),
    FRONT_LEFT(2),
    FRONT_RIGHT(3);

    private final int value;

    TireBrakesOrderEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

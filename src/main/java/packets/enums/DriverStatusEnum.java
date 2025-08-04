package packets.enums;

public enum DriverStatusEnum {

    IN_GARAGE(8),
    FLYING_LAP(1),
    IN_LAP(2),
    OUT_LAP(3),
    ON_TRACK(4);

    private final int value;

    DriverStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

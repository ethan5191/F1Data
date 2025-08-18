package f1.data.packets.enums;

public enum SessionLengthEnum {

    NONE(0),
    VERY_SHORT(2),
    SHORT(3),
    MEDIUM(4),
    MEDIUM_LONG(5),
    LONG(6),
    FULL(7);

    private final int value;

    SessionLengthEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

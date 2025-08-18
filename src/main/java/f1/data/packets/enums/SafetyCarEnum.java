package f1.data.packets.enums;

public enum SafetyCarEnum {

    NONE(0),
    FULL(1),
    VIRTUAL(2),
    FORMATION(3);

    private final int value;

    SafetyCarEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

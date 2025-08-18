package f1.data.packets.enums;

public enum GearBoxAssistEnum {

    MANUAL(1),
    SUGGESTED_GEAR(2),
    AUTO(3);

    private final int value;

    GearBoxAssistEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

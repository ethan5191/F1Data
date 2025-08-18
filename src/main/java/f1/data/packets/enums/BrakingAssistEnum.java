package f1.data.packets.enums;

public enum BrakingAssistEnum {

    OFF(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int value;

    BrakingAssistEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

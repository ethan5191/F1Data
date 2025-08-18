package f1.data.packets.enums;

public enum PitStatusEnum {

    NONE(0),
    PITTING(1),
    PIT_AREA(2);

    private final int value;

    PitStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

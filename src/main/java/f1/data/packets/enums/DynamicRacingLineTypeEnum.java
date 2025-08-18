package f1.data.packets.enums;

public enum DynamicRacingLineTypeEnum {

    TWO_D(0),
    THREE_D(1);

    private final int value;

    DynamicRacingLineTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

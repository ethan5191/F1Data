package f1.data.enums;

public enum MarshalZoneFlagEnum {

    NONE(0),
    GREEN(1),
    BLUE(2),
    YELLOW(3),
    RED(4);

    private final int value;

    MarshalZoneFlagEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

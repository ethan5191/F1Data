package f1.data.packets.enums;

public enum SectorEnum {

    FIRST(0),
    SECOND(1),
    THIRD(2);

    private final int value;

    SectorEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

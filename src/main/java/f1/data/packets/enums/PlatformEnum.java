package f1.data.packets.enums;

public enum PlatformEnum {
    STEAM(1),
    PLAYSTATION(3),
    XBOX(4),
    ORIGIN(6),
    UNKNOWN(255);

    private final int value;

    PlatformEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

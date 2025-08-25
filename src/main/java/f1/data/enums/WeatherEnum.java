package f1.data.enums;

public enum WeatherEnum {

    CLEAR(0),
    LIGHT_CLOUD(1),
    OVERCAST(2),
    LIGHT_RAIN(3),
    HEAVY_RAIN(4),
    STORM(5);

    private final int value;

    WeatherEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package packets.enums;

import java.util.HashMap;
import java.util.Map;

public enum DriverStatusEnum {

    IN_GARAGE(8),
    FLYING_LAP(1),
    IN_LAP(2),
    OUT_LAP(3),
    ON_TRACK(4);

    private final int value;
    private static final Map<Integer, DriverStatusEnum> LOOKUP = new HashMap<>();

    static {
        for (DriverStatusEnum elem : values()) {
            LOOKUP.put(elem.value, elem);
        }
    }

    DriverStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DriverStatusEnum fromValue(int value) {
        return LOOKUP.get(value);
    }
}

package f1.data.packets.enums;

import java.util.HashMap;
import java.util.Map;

public enum PacketTypeEnum {

    MOTION(0),
    SESSION(1),
    LAP_DATA(2),
    EVENT(3),
    PARTICIPANT(4),
    CAR_SETUP(5),
    CAR_TELEMETRY(6),
    CAR_STATUS(7),
    FINAL_CLASS(8),
    LOBBY_INFO(9),
    CAR_DAMAGE(10),
    SESSION_HIST(11),
    TIRE_SETS(12),
    MOTION_EX(13),
    TIME_TRIAL(14),
    LAP_POSITIONS(15);

    private final int value;
    private static final Map<Integer, PacketTypeEnum> LOOKUP = new HashMap<>();

    static {
        for (PacketTypeEnum e : PacketTypeEnum.values()) {
            LOOKUP.put(e.getValue(), e);
        }
    }

    PacketTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PacketTypeEnum findByValue(int value) {
        return LOOKUP.get(value);
    }
}

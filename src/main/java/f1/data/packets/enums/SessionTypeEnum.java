package f1.data.packets.enums;

import java.util.HashMap;
import java.util.Map;

public enum SessionTypeEnum {

    UNKNOWN(0),
    FP1(1),
    FP2(2),
    FP3(3),
    SHORT_PRACTICE(4),
    Q1(5),
    Q2(6),
    Q3(7),
    SHORT_QUALIFYING(8),
    ONE_SHOT_QUALIFYING(9),
    SQ1(10),
    SQ2(11),
    SQ3(12),
    SHORT_SPRINT_SHOOTOUT(13),
    ONE_SHOT_SPRINT_SHOOTOUT(14),
    RACE(15),
    RACE_2(16),
    RACE_3(17),
    TIME_TRAIL(18);

    private final int value;

    private static final Map<Integer, SessionTypeEnum> LOOKUP = new HashMap();

    static {
        for (SessionTypeEnum e : SessionTypeEnum.values()) {
            LOOKUP.put(e.getValue(), e);
        }
    }

    SessionTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SessionTypeEnum fromId(int id) {
        return LOOKUP.get(id);
    }
}

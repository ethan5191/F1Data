package f1.data.packets.enums;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public enum FormulaEnum {

    F1(0),
    F1_CLASSIC(1),
    F2(2),
    F1_GENERIC(3),
    BETA(4),
    ESPORTS(6),
    F1_WORLD(8),
    F1_ELIMINATION(9);

    private final int value;
    private static final Map<Integer, FormulaEnum> LOOKUP = new HashMap<>();

    static {
        for (FormulaEnum e : FormulaEnum.values()) {
            LOOKUP.put(e.value, e);
        }
    }

    FormulaEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FormulaEnum fromValue(int value) {
        return LOOKUP.get(value);
    }
}

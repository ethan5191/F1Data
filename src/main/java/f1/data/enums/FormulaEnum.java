package f1.data.enums;

import java.util.HashMap;
import java.util.Map;

//Enum matches to the Session values for the 'formula' param.
public enum FormulaEnum {

    F1(0),
    F1_CLASSIC(1),
    F2(2),
    F1_GENERIC(3),
    BETA(4),
    ESPORTS(6),
    F2_ALT(7),
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

    public static boolean isF1(FormulaEnum formulaEnum) {
        return FormulaEnum.F1.equals(formulaEnum);
    }
}

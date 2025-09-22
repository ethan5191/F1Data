package f1.data.enums;

import f1.data.utils.constants.Constants;

import java.util.HashMap;
import java.util.Map;

//Enum matches to the Session values for the 'formula' param.
public enum FormulaEnum {

    F1(0, Constants.F1),
    F1_CLASSIC(1, Constants.CLASSIC),
    F2(2, Constants.F2),
    F1_GENERIC(3, Constants.F1),
    BETA(4, Constants.F1),
    ESPORTS(6, Constants.F1),
    F2_ALT(7, Constants.F2),
    F1_WORLD(8, Constants.F1),
    F1_ELIMINATION(9, Constants.F1);

    private final int value;
    private final String name;
    private static final Map<Integer, FormulaEnum> LOOKUP = new HashMap<>();

    static {
        for (FormulaEnum e : FormulaEnum.values()) {
            LOOKUP.put(e.value, e);
        }
    }

    FormulaEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static FormulaEnum fromValue(int value) {
        return LOOKUP.get(value);
    }

    public static boolean isF1(FormulaEnum formulaEnum) {
        return FormulaEnum.F1.equals(formulaEnum);
    }
}

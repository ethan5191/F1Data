package f1.data.enums;

import f1.data.utils.constants.Constants;

import java.util.HashMap;
import java.util.Map;

public enum ActualTireEnum {
    INTER(7, Constants.F1, Constants.INTERMEDIATE),
    X_WET(8, Constants.F1, Constants.X_WET),
    C5(16, Constants.F1, "C5"),
    C4(17, Constants.F1, "C4"),
    C3(18, Constants.F1, "C3"),
    C2(19, Constants.F1, "C2"),
    C1(20, Constants.F1, "C1"),
    C0(21, Constants.F1, "C0"),
    DRY(9, Constants.CLASSIC, "Dry"),
    WET(10, Constants.CLASSIC, Constants.X_WET),
    SS(11, Constants.F2, Constants.SUPER),
    S(12, Constants.F2, Constants.SOFT),
    M(13, Constants.F2, Constants.MEDIUM),
    H(14, Constants.F2, Constants.HARD),
    W(15, Constants.F2, Constants.X_WET),
    H_F2_20(24, Constants.F2, Constants.HARD);

    private final int value;
    private final String series;
    private final String display;
    private static final Map<Integer, ActualTireEnum> LOOKUP = new HashMap<>();

    static {
        for (ActualTireEnum elem : values()) {
            LOOKUP.put(elem.value, elem);
        }
    }

    ActualTireEnum(int value, String series, String display) {
        this.value = value;
        this.series = series;
        this.display = display;
    }

    public int getValue() {
        return value;
    }

    public String getSeries() {
        return series;
    }

    public String getDisplay() {
        return display;
    }

    public static ActualTireEnum fromValue(int value) {
        return LOOKUP.get(value);
    }
}

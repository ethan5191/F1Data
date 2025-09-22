package f1.data.enums;

import f1.data.utils.constants.Constants;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum VisualTireEnum {

    ALL(0, Constants.F1, Constants.ALL, Color.BLACK),
    INTER(7, Constants.F1, Constants.INTERMEDIATE, Color.GREEN),
    X_WET(8,Constants.F1, Constants.X_WET, Color.BLUE),
    SOFT(16, Constants.F1, Constants.SOFT, Color.RED),
    MEDIUM(17, Constants.F1, Constants.MEDIUM, Color.YELLOW),
    HARD(18, Constants.F1, Constants.HARD, Color.WHITE),
    W(15, Constants.F2, Constants.X_WET, Color.BLUE),
    SS(19, Constants.F2, Constants.SUPER, Color.getColor("Purple")),
    S(20, Constants.F2, Constants.SOFT, Color.RED),
    M(21, Constants.F2, Constants.MEDIUM, Color.YELLOW),
    H(22, Constants.F2, Constants.HARD, Color.WHITE),
    SS_F2_20(23, Constants.F2, Constants.SUPER, Color.getColor("Purple")),
    S_F2_20(24, Constants.F2, Constants.SOFT, Color.RED),
    M_F2_20(25, Constants.F2, Constants.MEDIUM, Color.YELLOW),
    H_F2_20(26, Constants.F2, Constants.HARD, Color.WHITE),
    SS_F2_19(11, Constants.F2, Constants.SUPER, Color.RED),
    S_F2_19(12, Constants.F2, Constants.SOFT, Color.YELLOW),
    M_F2_19(13, Constants.F2, Constants.MEDIUM, Color.WHITE),
    H_F2_19(14, Constants.F2, Constants.HARD, Color.BLUE);

    private final int value;
    private final String series;
    private final String display;
    private final Color color;
    private static final Map<Integer, VisualTireEnum> LOOKUP = new HashMap<>();

    static {
        for (VisualTireEnum elem : values()) {
            LOOKUP.put(elem.value, elem);
        }
    }

    VisualTireEnum(int value, String series, String display, Color color) {
        this.value = value;
        this.series = series;
        this.display = display;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public static VisualTireEnum fromValue(int value) {
        return LOOKUP.get(value);
    }
}

package packets.enums;

import utils.Constants;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum VisualTireEnum {
    INTER(7, Constants.F1, Constants.INTER, Color.GREEN),
    X_WET(8,Constants.F1, Constants.WET, Color.BLUE),
    SOFT(16, Constants.F1, Constants.SOFT, Color.RED),
    MEDIUM(17, Constants.F1, Constants.MEDIUM, Color.YELLOW),
    HARD(18, Constants.F1, Constants.HARD, Color.WHITE),
    W(15, Constants.F2, Constants.WET, Color.BLUE),
    SS(19, Constants.F2, Constants.SUPER, Color.getColor("Purple")),
    S(20, Constants.F2, Constants.SOFT, Color.RED),
    M(21, Constants.F2, Constants.MEDIUM, Color.YELLOW),
    H(22, Constants.F2, Constants.HARD, Color.WHITE);

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

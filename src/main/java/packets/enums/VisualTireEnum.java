package packets.enums;

import utils.Constants;

public enum VisualTireEnum {
    INTER(7, Constants.F1),
    X_WET(8,Constants.F1),
    SOFT(16, Constants.F1),
    MEDIUM(17, Constants.F1),
    HARD(18, Constants.F1),
    W(15, Constants.F2),
    SS(19, Constants.F2),
    S(20, Constants.F2),
    M(21, Constants.F2),
    H(22, Constants.F2);

    private final int value;
    private final String type;

    VisualTireEnum(int value, String type) {
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}

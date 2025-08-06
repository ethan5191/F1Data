package packets.enums;

import utils.Constants;

public enum ActualTireEnum {
    INTER(7, Constants.F1),
    X_WET(8, Constants.F1),
    C5(16, Constants.F1),
    C4(17, Constants.F1),
    C3(18, Constants.F1),
    C2(19, Constants.F1),
    C1(20, Constants.F1),
    C0(21, Constants.F1),
    DRY(9, Constants.CLASSIC),
    WET(10, Constants.CLASSIC),
    SS(11, Constants.F2),
    S(12, Constants.F2),
    M(13, Constants.F2),
    H(14, Constants.F2),
    W(15, Constants.F2);

    private final int value;
    private final String type;

    ActualTireEnum(int value, String type) {
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

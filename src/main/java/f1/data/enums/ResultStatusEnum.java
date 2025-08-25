package f1.data.enums;

public enum ResultStatusEnum {

    INVALID(0),
    INACTIVE(1),
    ACTIVE(2),
    FINISHED(3),
    DID_NOT_FINISH(4),
    DQ(5),
    NOT_CLASSIFIED(6),
    RETIRED(7);

    private final int value;

    ResultStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

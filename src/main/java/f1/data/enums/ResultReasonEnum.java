package f1.data.enums;

public enum ResultReasonEnum {

    INVALID(0),
    RETIRED(1),
    FINISHED(2),
    TERMINAL_DAMAGE(3),
    INACTIVE(4),
    NOT_ENOUGH_LAPS_COMPLETED(5),
    BLACK_FLAGGED(6),
    RED_FLAGGED(7),
    MECHANICAL_FAILURE(8),
    SESSION_SKIPPED(9),
    SESSION_SIMULATED(10);

    private final int value;

    ResultReasonEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

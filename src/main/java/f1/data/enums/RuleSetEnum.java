package f1.data.enums;

public enum RuleSetEnum {

    PRACTICE_AND_QUALIFYING(0),
    RACE(1),
    TIME_TRAIL(2),
    TIME_ATTACK(4),
    CHECKPOINT_CHALLENGE(6),
    AUTOCROSS(8),
    DRIFT(9),
    AVERAGE_SPEED_ZONE(10),
    RIVAL_DUEL(11);

    private final int value;

    RuleSetEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

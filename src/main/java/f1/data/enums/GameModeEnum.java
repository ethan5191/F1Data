package f1.data.enums;

public enum GameModeEnum {

    EVENT(0),
    GRAND_PRIX(3),
    GRAND_PRIX_23(4),
    TIME_TRAIL(5),
    SPLITSCREEN(6),
    ONLINE_CUSTOM(7),
    ONLINE_LEAGUE(8),
    CAREER_INVITATIONAL(11),
    CHAMPIONSHIP_INVITATIONAL(12),
    CHAMPIONSHIP(13),
    ONLINE_CHAMPIONSHIP(14),
    ONLINE_WEEKLY_EVENT(15),
    STORY(17),
    CAREER_22(19),
    CAREER_22_ONLINE(20),
    CAREER_23(21),
    CAREER_23_ONLINE(22),
    DRIVER_CAREER_24(23),
    CAREER_24_ONLINE(24),
    MY_TEAM_CAREER_24(25),
    CURATED_CAREER_24(26),
    BENCHMARK(27);

    private final int value;

    GameModeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

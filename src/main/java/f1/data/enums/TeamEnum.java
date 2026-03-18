package f1.data.enums;

import java.util.HashMap;
import java.util.Map;

public enum TeamEnum {
    MERCEDES(0),
    FERRARI(1),
    RED_BULL(2),
    WILLIAMS(3),
    ASTON_MARTIN(4),
    ALPINE(5),
    RACING_BULLS(6),
    HAAS(7),
    MCLAREN(8),
    SAUBER(9),
    GENERIC(41),
    CUSTOM_TEAM(104),
    APX_24(142), //F1 MOVIE TEAM I BELIEVE
    APX_25(154), //F1 MOVIE TEAM I BELIEVE
    ART_23(143),
    CAMPOS_23(144),
    CARLIN_23(145),
    PHM_23(146),
    DAMS_23(147),
    HITECH_23(148),
    MP_23(149),
    PREMA_23(150),
    TRIDENT_23(151),
    VAR_23(152),
    VIRTUOSI_23(153),
    ART_24(158),
    CAMPOS_24(159),
    RODIN_24(160),
    AIX_24(161),
    DAMS_24(162),
    HITECH_24(163),
    MP_24(164),
    PREMA_24(165),
    TRIDENT_24(166),
    VAR_24(167),
    INVICTA_24(168),
    MERCEDES_24(185),
    FERRARI_24(186),
    RED_BULL_24(187),
    WILLIARS_24(188),
    ASTON_MARTIN_24(189),
    ALPINE_24(190),
    RACING_BULLS_24(191),
    HAAS_24(192),
    MCLAREN_24(193),
    SAUBER_24(194);

    private final int id;

    private static final Map<Integer, TeamEnum> LOOKUP = new HashMap<>();

    static {
        for (TeamEnum e : TeamEnum.values()) {
            LOOKUP.put(e.id, e);
        }
    }

    TeamEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TeamEnum fromId(int id) {
        return LOOKUP.get(id);
    }
}

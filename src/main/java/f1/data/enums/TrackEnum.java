package f1.data.enums;

import java.util.HashMap;
import java.util.Map;

public enum TrackEnum {

    AUSTRALIA(0, "Melbourne", DownforceLevelEnum.MEDIUM),
    FRANCE(1, "Paul Ricard", DownforceLevelEnum.UNKNOWN),
    CHINA(2, "Shanghai", DownforceLevelEnum.MEDIUM),
    BAHRAIN(3, "Sakir", DownforceLevelEnum.MEDIUM),
    SPAIN(4, "Catalunya", DownforceLevelEnum.HIGH),
    MONACO(5, "Monaco", DownforceLevelEnum.MAX),
    CANADA(6, "Montreal", DownforceLevelEnum.LOW),
    SILVERSTONE(7, "Silverstone", DownforceLevelEnum.LOW),
    GERMANY(8, "Hockenheim", DownforceLevelEnum.UNKNOWN),
    HUNGARY(9, "Hungaroring", DownforceLevelEnum.MAX),
    SPA(10, "Spa", DownforceLevelEnum.LOW),
    MONZA(11, "Monza", DownforceLevelEnum.MONZA),
    SINGAPORE(12, "Singapore", DownforceLevelEnum.MAX),
    JAPAN(13, "Suzuka", DownforceLevelEnum.MEDIUM),
    ABU_DHABI(14, "Abu Dhabi", DownforceLevelEnum.MEDIUM),
    COTA(15, "Circuit of the Americas", DownforceLevelEnum.HIGH),
    BRAZIL(16, "Brazil", DownforceLevelEnum.MEDIUM),
    AUSTRIA(17, "Red Bull Ring", DownforceLevelEnum.LOW),
    RUSSIA(18, "Sochi", DownforceLevelEnum.UNKNOWN),
    MEXICO(19, "Mexico", DownforceLevelEnum.MEDIUM),
    BAKU(20, "Azerbaijan", DownforceLevelEnum.LOW),
    BAHRAIN_SHORT(21, "Sakhir Short", DownforceLevelEnum.UNKNOWN),
    SILVERSTONE_SHORT(22, "Silverstone Short", DownforceLevelEnum.UNKNOWN),
    COTA_SHORT(23, "COTA Short", DownforceLevelEnum.UNKNOWN),
    JAPAN_SHORT(24, "Suzuka Short", DownforceLevelEnum.UNKNOWN),
    VIETNAM(25, "Hanoi", DownforceLevelEnum.UNKNOWN),
    ZANDVOORT(26, "Zandvoort", DownforceLevelEnum.HIGH),
    IMOLA(27, "Imola", DownforceLevelEnum.MEDIUM),
    PORTIMAO(28, "Portimao", DownforceLevelEnum.HIGH),
    SAUDI_ARABIA(29, "Jeddah", DownforceLevelEnum.LOW),
    MIAMI(30, "Miami", DownforceLevelEnum.LOW),
    LAS_VEGAS(31, "Las Vegas", DownforceLevelEnum.LOW),
    QATAR(32, "Losail", DownforceLevelEnum.HIGH);

    private final int id;
    private final String altName;
    private final String setupName;

    private static final Map<Integer, TrackEnum> LOOKUP = new HashMap<>();

    static {
        for (TrackEnum e : TrackEnum.values()) {
            LOOKUP.put(e.id, e);
        }
    }

    TrackEnum(int id, String altName, DownforceLevelEnum downforceLevel) {
        this.id = id;
        this.altName = altName;
        this.setupName = downforceLevel.toString();
    }

    public int getId() {
        return id;
    }

    public String getAltName() {
        return altName;
    }

    public String getSetupName() {
        return setupName;
    }

    public static TrackEnum fromId(int id) {
        return LOOKUP.get(id);
    }
}

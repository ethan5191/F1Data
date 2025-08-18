package f1.data.packets.enums;

public enum TrackEnum {

    AUSTRALIA(0, "Melbourne"),
    FRANCE(1, "Paul Ricard"),
    CHINA(2, "Shanghai"),
    BAHRAIN(3, "Sakir"),
    SPAIN(4, "Catalunya"),
    MONACO(5, "Monaco"),
    CANADA(6, "Montreal"),
    SILVERSTONE(7, "Silverstone"),
    GERMANY(8, "Hockenheim"),
    HUNGARY(9, "Hungaroring"),
    SPA(10, "Spa"),
    MONZA(11, "Monza"),
    SINGAPORE(12, "Singapore"),
    JAPAN(13, "Suzuka"),
    ABU_DHABI(14, "Abu Dhabi"),
    COTA(15, "Circuit of the Americas"),
    BRAZIL(16, "Brazil"),
    AUSTRIA(17, "Red Bull Ring"),
    RUSSIA(18, "Sochi"),
    MEXICO(19, "Mexico"),
    BAKU(20, "Azerbaijan"),
    BAHRAIN_SHORT(21, "Sakhir Short"),
    SILVERSTONE_SHORT(22, "Silverstone Short"),
    COTA_SHORT(23, "COTA Short"),
    JAPAN_SHORT(24, "Suzuka Short"),
    VIETNAM(25, "Hanoi"),
    ZANDVOORT(26, "Zandvoort"),
    IMOLA(27, "Imola"),
    PORTIMAO(28, "Portimao"),
    SAUDI_ARABIA(29, "Jeddah"),
    MIAMI(30, "Miami"),
    LAS_VEGAS(31, "Las Vegas"),
    QATAR(32, "Losail");

    private final int id;
    private final String altName;

    TrackEnum(int id, String altName) {
        this.id = id;
        this.altName = altName;
    }

    public int getId() {
        return id;
    }

    public String getAltName() {
        return altName;
    }
}

package f1.data.enums;

public enum DownforceLevelEnum {

    MONZA("MONZA"),
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    MAX("MAX"),
    UNKNOWN("UNKNOWN");

    private final String name;

    DownforceLevelEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

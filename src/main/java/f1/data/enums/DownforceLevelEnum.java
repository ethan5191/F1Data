package f1.data.enums;

//Enum is a definition of the downforce levels for AI setups based on track needs.
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

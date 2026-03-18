package f1.data.enums;

//Enum is a definition of the downforce levels for AI setups based on track needs.
public enum DownforceLevelEnum {

    MONZA("MONZA"),
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    MAX("MAX"),
    UNKNOWN("UNKNOWN"),
    F2_MONZA("F2.MONZA"),
    F2_LOW("F2_LOW"),
    F2_MEDIUM("F2_MEDIUM"),
    F2_HIGH("F2_HIGH"),
    F2_MAX("F2_MAX");

    private final String name;

    DownforceLevelEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

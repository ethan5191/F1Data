package f1.data.enums;

import f1.data.utils.constants.Constants;

//Represents the valid years that this app works with the games.
//F1 2018 sends telemetry, but it is not supported currently.
public enum SupportedYearsEnum {

    F1_2019(2019),
    F1_2020(2020),
    F1_2021(2021),
    F1_2022(2022),
    F1_2023(2023),
    F1_2024(2024),
    F1_2025(2025);

    private final int year;

    SupportedYearsEnum(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public static SupportedYearsEnum fromYear(int year) {
        return switch (year) {
            case Constants.YEAR_2019 -> F1_2019;
            case Constants.YEAR_2020 -> F1_2020;
            case Constants.YEAR_2021 -> F1_2021;
            case Constants.YEAR_2022 -> F1_2022;
            case Constants.YEAR_2023 -> F1_2023;
            case Constants.YEAR_2024 -> F1_2024;
            case Constants.YEAR_2025 -> F1_2025;
            default ->
                    throw new IllegalStateException("Games Packet Format did not match an accepted format (2019 - 2025)");
        };
    }

    public boolean is2019OrEarlier() {
        return this.compareTo(F1_2019) <= 0;
    }

    public boolean is2020OrEarlier() {
        return this.compareTo(F1_2020) <= 0;
    }

    public boolean is2021OrEarlier() {
        return this.compareTo(F1_2021) <= 0;
    }

    public boolean is2022OrEarlier() {
        return this.compareTo(F1_2022) <= 0;
    }

    public boolean is2023OrEarlier() {
        return this.compareTo(F1_2023) <= 0;
    }

    public boolean is2024OrEarlier() {
        return this.compareTo(F1_2024) <= 0;
    }

    public boolean is2025OrEarlier() {
        return this.compareTo(F1_2025) <= 0;
    }

    public boolean is2019OrLater() {
        return this.compareTo(F1_2019) >= 0;
    }

    public boolean is2020OrLater() {
        return this.compareTo(F1_2020) >= 0;
    }

    public boolean is2021OrLater() {
        return this.compareTo(F1_2021) >= 0;
    }

    public boolean is2022OrLater() {
        return this.compareTo(F1_2022) >= 0;
    }

    public boolean is2023OrLater() {
        return this.compareTo(F1_2023) >= 0;
    }

    public boolean is2024OrLater() {
        return this.compareTo(F1_2024) >= 0;
    }

    public boolean is2025OrLater() {
        return this.compareTo(F1_2025) >= 0;
    }

    public boolean hasSpeedTrapData() {
        return this.compareTo(F1_2020) >= 0;
    }
}

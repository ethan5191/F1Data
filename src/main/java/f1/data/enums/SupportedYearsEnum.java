package f1.data.enums;

import f1.data.utils.constants.Constants;

//Represents the valid years that this app works with the games.
//F1 2018 sends telemetry, but it is not supported currently.
public enum SupportedYearsEnum {

    F1_2019(Constants.YEAR_2019, Constants.F1_19_AND_EARLIER_CAR_COUNT),
    F1_2020(Constants.YEAR_2020, Constants.F1_20_TO_25_CAR_COUNT),
    F1_2021(Constants.YEAR_2021, Constants.F1_20_TO_25_CAR_COUNT),
    F1_2022(Constants.YEAR_2022, Constants.F1_20_TO_25_CAR_COUNT),
    F1_2023(Constants.YEAR_2023, Constants.F1_20_TO_25_CAR_COUNT),
    F1_2024(Constants.YEAR_2024, Constants.F1_20_TO_25_CAR_COUNT),
    F1_2025(Constants.YEAR_2025, Constants.F1_20_TO_25_CAR_COUNT),
    F1_2026(Constants.YEAR_2026, Constants.F1_26_AND_LATER_CAR_COUNT);

    private final int year;
    private final int carCount;

    public static int MIN_YEAR = -1;
    public static int MAX_YEAR = -1;

    SupportedYearsEnum(int year, int carCount) {
        this.year = year;
        this.carCount = carCount;
    }

    public int getYear() {
        return year;
    }

    public int getCarCount() {
        return carCount;
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
            case Constants.YEAR_2026 -> F1_2026;
            default -> {
                SupportedYearsEnum[] allYears = values();
                MIN_YEAR = allYears[0].getYear();
                MAX_YEAR = allYears[allYears.length - 1].getYear();
                String errorMessage = String.format("Games Packet Format did not match an accepted format (%d - %d)", MIN_YEAR, MAX_YEAR);
                throw new IllegalStateException(errorMessage);
            }
        };
    }

    public static String buildErrorMessageFromYear(int startYear) {
        SupportedYearsEnum[] allYears = values();
        int maxYear = allYears[allYears.length - 1].getYear();
        return "Games Packet Format did not match an accepted format " + String.format("(%d - %d)", startYear, maxYear);
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

    public boolean is2026OrEarlier() { return this.compareTo(F1_2026) <= 0; }

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

    public boolean is2026OrLater() { return this.compareTo(F1_2026) >= 0; }

    public boolean hasSpeedTrapData() {
        return this.compareTo(F1_2020) >= 0;
    }
}

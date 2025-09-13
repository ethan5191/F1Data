package f1.data.utils;

import f1.data.utils.constants.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

    private static boolean is2019OrEarlier(int packetFormat) {
        return packetFormat <= Constants.YEAR_2019;
    }

    private static boolean is2026OrLater(int packetFormat) {
        return packetFormat >= Constants.YEAR_2026;
    }

    public static int findArraySize(int packetFormat, int playerCarIndex) {
        int arraySize = is2019OrEarlier(packetFormat) ? Constants.F1_19_AND_EARLIER_CAR_COUNT : (is2026OrLater(packetFormat)) ? Constants.F1_26_AND_LATER_CAR_COUNT : Constants.F1_20_TO_25_CAR_COUNT;
        if (playerCarIndex > arraySize)
            arraySize = (playerCarIndex < Constants.F1_20_TO_25_CAR_COUNT) ? Constants.F1_20_TO_25_CAR_COUNT : Constants.F1_26_AND_LATER_CAR_COUNT;
        return arraySize;
    }

    public static BigDecimal roundDecimal(BigDecimal value) {
        return value.divide(new BigDecimal("1000.000"), 3, RoundingMode.HALF_UP);
    }
}

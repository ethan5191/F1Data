package f1.data.utils;

import f1.data.enums.SupportedYearsEnum;
import f1.data.utils.constants.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

    public static int findArraySize(int packetFormat, int playerCarIndex) {
        SupportedYearsEnum supportedYearsEnum = SupportedYearsEnum.fromYear(packetFormat);
        int arraySize = supportedYearsEnum.getCarCount();
        if (playerCarIndex > arraySize)
            //Update this line with the 2026 enum value once it gets created.
            arraySize = (playerCarIndex < SupportedYearsEnum.F1_2020.getCarCount()) ? SupportedYearsEnum.F1_2020.getCarCount() : Constants.F1_26_AND_LATER_CAR_COUNT;
        return arraySize;
    }

    public static BigDecimal roundDecimal(BigDecimal value) {
        return value.divide(new BigDecimal("1000.000"), 3, RoundingMode.HALF_UP);
    }
}

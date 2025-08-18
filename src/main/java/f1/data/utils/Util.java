package f1.data.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

    public static BigDecimal roundDecimal(BigDecimal value) {
        return value.divide(new BigDecimal("1000.000"), 3, RoundingMode.HALF_UP);
    }
}

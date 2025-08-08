package ui;

import java.math.BigDecimal;

public class DashboardUtils {

    protected static String buildTimeText(BigDecimal lapTimeLocal) {
        int lapTimeMinutes = 0;
        while (lapTimeLocal.doubleValue() > 60) {
            lapTimeMinutes++;
            lapTimeLocal = lapTimeLocal.subtract(new BigDecimal(60));
        }
        return (lapTimeMinutes > 0) ? lapTimeMinutes + ":" + String.format("%.3f", lapTimeLocal.doubleValue()) : String.format("%.3f", lapTimeLocal.doubleValue());
    }
}

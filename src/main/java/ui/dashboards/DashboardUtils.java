package ui.dashboards;

import java.math.BigDecimal;

public class DashboardUtils {

    private static final String LAP_TIME_FORMAT = "%06.3f";

    protected static String buildTimeText(BigDecimal lapTimeLocal) {
        int lapTimeMinutes = 0;
        while (lapTimeLocal.doubleValue() > 60) {
            lapTimeMinutes++;
            lapTimeLocal = lapTimeLocal.subtract(new BigDecimal(60));
        }
        return (lapTimeMinutes > 0) ? lapTimeMinutes + ":" + String.format(LAP_TIME_FORMAT, lapTimeLocal.doubleValue()) : String.format(LAP_TIME_FORMAT, lapTimeLocal.doubleValue());
    }
}

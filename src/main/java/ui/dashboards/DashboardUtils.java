package ui.dashboards;

import utils.Constants;

import java.math.BigDecimal;

public class DashboardUtils {

    private static final String LAP_TIME_FORMAT = "%06.3f";

    //Convenience method to ensure the different panels with time gets output correctly.
    protected static String buildTimeText(BigDecimal lapTimeLocal) {
        int lapTimeMinutes = 0;
        while (lapTimeLocal.doubleValue() > 60) {
            lapTimeMinutes++;
            lapTimeLocal = lapTimeLocal.subtract(new BigDecimal(60));
        }
        return (lapTimeMinutes > 0) ? lapTimeMinutes + ":" + String.format(LAP_TIME_FORMAT, lapTimeLocal.doubleValue()) : String.format(LAP_TIME_FORMAT, lapTimeLocal.doubleValue());
    }

    //Formats the received float value as a Two Decimal String.
    public static String formatTwoDecimals(float input) {
        return String.format(Constants.TWO_DECIMAL, input);
    }

    public static String formatOneDecimal(float input) { return String.format(Constants.ONE_DECIMAL, input);
    }
}

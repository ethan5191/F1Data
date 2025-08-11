package utils;

import java.util.Map;

public class Constants {

    public static final int BIT_MASK_8 = 0xFF;
    public static final int BIT_MASK_16 = 0xFFFF;
    public static final long BIT_MASK_32 = 0xFFFFFFFFL;

    public static final String ONE_DECIMAL = "%.1f";
    public static final String TWO_DECIMAL = "%.2f";

    public static final int PACKET_CAR_COUNT = 22;

    public static final int PORT_NUM = 20777;

    //length of the packet header, always present.
    public static final int HEADER_OFFSET = 28;

    //Individual packet IDs
    public static final int MOTION_PACK = 0;
    public static final int SESSION_PACK = 1;
    public static final int LAP_DATA_PACK = 2;
    public static final int EVENT_PACK = 3;
    public static final int PARTICIPANTS_PACK = 4;
    public static final int CAR_SETUP_PACK = 5;
    public static final int CAR_TELEMETRY_PACK = 6;
    public static final int CAR_STATUS_PACK = 7;
    public static final int FINAL_CLASS_PACK = 8;
    public static final int LOBBY_INFO_PACK = 9;
    public static final int CAR_DAMAGE_PACK = 10;
    public static final int SESSION_HIST_PACK = 11;
    public static final int TYRE_SETS_PACK = 12;
    public static final int MOTION_EX_PACK = 13;
    public static final int TIME_TRIAL_PACK = 14;

    public static final String BUTTON_PRESSED_EVENT = "BUTN";
    public static final String SPEED_TRAP_TRIGGERED_EVENT = "SPTP";

    //Value that is passed when pause is pressed on the McLaren GT3 wheel.
    public static final int MCLAREN_GT3_WHEEL_PAUSE_BTN = 256;
    public static final int MCLAREN_GT3_WHEEL_PAUSE_BTN2 = 131328;

    public static final String F1 = "F1";
    public static final String CLASSIC = "CLASSIC";
    public static final String F2 = "F2";
    public static final String INTER = "Inter";
    public static final String WET = "Wet";
    public static final String HARD = "Hard";
    public static final String MEDIUM = "Medium";
    public static final String SOFT = "Soft";
    public static final String SUPER = "Super Soft";

    public static final int SETUP_PANEL_WIDTH = 800;

    public static final String PERCENT_SIGN = "%";
    public static final String KG = "kg";

    public static final Map<Integer, Integer> DRIVER_PAIRS = Map.ofEntries(Map.entry(9, 14), Map.entry(14, 9),
            Map.entry(7, 50), Map.entry(50, 7),
            Map.entry(58, 0), Map.entry(0, 58),
            Map.entry(54, 112), Map.entry(112, 54),
            Map.entry(59, 17), Map.entry(17, 59),
            Map.entry(3, 19), Map.entry(19, 3),
            Map.entry(2, 94), Map.entry(94, 2),
            Map.entry(15, 80), Map.entry(80, 15),
            Map.entry(10, 11), Map.entry(11, 10),
            Map.entry(62, 132), Map.entry(132, 62));

    public static final Map<Integer, String> DRIVER_ID_TO_NAMES = Map.ofEntries(Map.entry(19, "STROLL"), Map.entry(17, "OCON"),
            Map.entry(58, "LECLERC"), Map.entry(50, "RUSSELL"), Map.entry(132, "SARGEANT"), Map.entry(59, "GASLY"),
            Map.entry(62, "ALBON"), Map.entry(0, "SAINZ"), Map.entry(54, "NORRIS"), Map.entry(112, "PIASTRI"),
            Map.entry(94, "TSUNODA"), Map.entry(7, "HAMILTON"), Map.entry(80, "ZHOU"), Map.entry(14, "PÉREZ"),
            Map.entry(9, "VERSTAPPEN"), Map.entry(10, "HULKENBERG"), Map.entry(3, "ALONSO"), Map.entry(11, "MAGNUSSEN"),
            Map.entry(2, "RICCIARDO"), Map.entry(15, "BOTTAS"));
}

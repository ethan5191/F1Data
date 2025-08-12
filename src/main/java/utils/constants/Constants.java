package utils.constants;

public class Constants {

    public static final int BIT_MASK_8 = 0xFF;
    public static final int BIT_MASK_16 = 0xFFFF;
    public static final long BIT_MASK_32 = 0xFFFFFFFFL;

    public static final String ONE_DECIMAL = "%.1f";
    public static final String TWO_DECIMAL = "%.2f";

    public static final int PACKET_CAR_COUNT = 22;
    public static final int TIRE_SETS_PACKET_COUNT = 20;

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
}

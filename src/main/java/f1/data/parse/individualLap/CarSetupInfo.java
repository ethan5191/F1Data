package f1.data.parse.individualLap;

import f1.data.parse.packets.CarSetupData;
import f1.data.ui.panels.dashboards.DashboardUtils;
import f1.data.utils.constants.Constants;
import javafx.scene.control.Label;

public class CarSetupInfo {

    private final int frontWing;
    private final int rearWing;
    private final int onThrottle;
    private final int offThrottle;
    private final int engineBraking;
    private final float frontCamber;
    private final float rearCamber;
    private final float frontToe;
    private final float rearToe;
    private final int frontSusp;
    private final int rearSusp;
    private final int frontARB;
    private final int rearARB;
    private final int frontHeight;
    private final int rearHeight;
    private final int brakePressure;
    private final int brakeBias;
    private final float rearLeftPressure;
    private final float rearRightPressure;
    private final float frontLeftPressure;
    private final float frontRightPressure;
    private final float fuelLoad;

    public CarSetupInfo(CarSetupData csd) {
        this.frontWing = csd.frontWing();
        this.rearWing = csd.rearWing();
        this.onThrottle = csd.onThrottle();
        this.offThrottle = csd.offThrottle();
        this.engineBraking = csd.engineBraking();
        this.frontCamber = csd.frontCamber();
        this.rearCamber = csd.rearCamber();
        this.frontToe = csd.frontToe();
        this.rearToe = csd.rearToe();
        this.frontSusp = csd.frontSusp();
        this.rearSusp = csd.rearSusp();
        this.frontARB = csd.frontARB();
        this.rearARB = csd.rearARB();
        this.frontHeight = csd.frontHeight();
        this.rearHeight = csd.rearHeight();
        this.brakePressure = csd.brakePressure();
        this.brakeBias = csd.brakeBias();
        this.rearLeftPressure = csd.rearLeftPressure();
        this.rearRightPressure = csd.rearRightPressure();
        this.frontLeftPressure = csd.frontLeftPressure();
        this.frontRightPressure = csd.rearRightPressure();
        this.fuelLoad = csd.fuelLoad();
    }

    public Label[][] getSetupDashboardData() {
        return new Label[][]{{new Label("Fuel Load ")}, {new Label(DashboardUtils.formatTwoDecimals(this.fuelLoad) + Constants.KG)},
                {new Label(" Front Wing "), new Label(" Rear Wing ")},
                {new Label(String.valueOf(this.frontWing)), new Label(String.valueOf(this.rearWing))},
                {new Label(" On Throttle "), new Label(" Off Throttle "), new Label(" Engine Braking ")},
                {new Label(String.valueOf(this.onThrottle)), new Label(String.valueOf(this.offThrottle)), new Label(String.valueOf(this.engineBraking))},
                {new Label(" Front Camber "), new Label(" Rear Camber "), new Label(" Front Toe-out "), new Label(" Rear Toe-in ")},
                {new Label(DashboardUtils.formatTwoDecimals(this.frontCamber)), new Label(DashboardUtils.formatTwoDecimals(this.rearCamber)),
                        new Label(DashboardUtils.formatTwoDecimals(this.frontToe)), new Label(DashboardUtils.formatTwoDecimals(this.rearToe))},
                {new Label(" Front Suspension "), new Label(" Rear Suspension "), new Label(" Front ARB "),
                        new Label(" Rear ARB "), new Label(" Front Height "), new Label(" Rear Height ")},
                {new Label(String.valueOf(this.frontSusp)), new Label(String.valueOf(this.rearSusp)), new Label(String.valueOf(this.frontARB)),
                        new Label(String.valueOf(this.rearARB)), new Label(String.valueOf(this.frontHeight)), new Label(String.valueOf(this.rearHeight))},
                {new Label(" Brake Pressure "), new Label(" Front Brake Bias ")},
                {new Label(String.valueOf(this.brakePressure)), new Label(String.valueOf(this.brakeBias))},
                {new Label(" FR Pressure "), new Label(" FL Pressure "), new Label(" RR Pressure "), new Label(" RL Pressure ")},
                {new Label(DashboardUtils.formatOneDecimal(this.frontRightPressure)), new Label(DashboardUtils.formatOneDecimal(this.frontLeftPressure)),
                        new Label(DashboardUtils.formatOneDecimal(this.rearRightPressure)), new Label(DashboardUtils.formatOneDecimal(this.rearLeftPressure))}
        };
    }
}

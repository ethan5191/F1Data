package packets;

import javafx.scene.control.Label;
import ui.dashboards.DashboardUtils;
import utils.constants.Constants;

/**
 * F1 24 CarSetupData Breakdown (Little Endian)
 * <p>
 * - F1 2020-2023 Length: 49 bytes
 * - F1 2024/2025 Length: 50 bytes
 * This struct is 50 bytes long and contains data about a single car's setup.
 * It is repeated for each car in the PacketCarSetupData packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * /*
 * PacketCarSetupData
 * ------------------
 * Member Name               | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------|----------------|--------------|----------------|-------------------------
 * m_header                  | PacketHeader    | ...          | 2020           | Full packet header
 * m_carSetups[22]           | CarSetupData    | ...          | 2020           | Array for each car
 * - m_frontWing            | uint8           | 1            | 2020           |
 * - m_rearWing             | uint8           | 1            | 2020           |
 * - m_onThrottle           | uint8           | 1            | 2020           |
 * - m_offThrottle          | uint8           | 1            | 2020           |
 * - m_frontCamber          | float           | 4            | 2020           |
 * - m_rearCamber           | float           | 4            | 2020           |
 * - m_frontToe             | float           | 4            | 2020           |
 * - m_rearToe              | float           | 4            | 2020           |
 * - m_frontSuspension      | uint8           | 1            | 2020           |
 * - m_rearSuspension       | uint8           | 1            | 2020           |
 * - m_frontAntiRollBar     | uint8           | 1            | 2020           |
 * - m_rearAntiRollBar      | uint8           | 1            | 2020           |
 * - m_frontSuspensionHeight| uint8           | 1            | 2020           |
 * - m_rearSuspensionHeight | uint8           | 1            | 2020           |
 * - m_brakePressure        | uint8           | 1            | 2020           |
 * - m_brakeBias            | uint8           | 1            | 2020           |
 * - m_engineBraking        | uint8           | 1            | 2024           | New in 2024
 * - m_rearLeftTyrePressure | float           | 4            | 2020           |
 * - m_rearRightTyrePressure| float           | 4            | 2020           |
 * - m_frontLeftTyrePressure| float           | 4            | 2020           |
 * - m_frontRightTyrePressure| float          | 4            | 2020           |
 * - m_ballast              | uint8           | 1            | 2020           |
 * - m_fuelLoad             | float           | 4            | 2020           |
 * m_nextFrontWingValue     | float           | 4            | 2024           | Player-only, value after next pit stop
 */

public class CarSetupData {

    public CarSetupData(Builder builder) {
        this.frontWing = builder.frontWing;
        this.rearWing = builder.rearWing;
        this.onThrottle = builder.onThrottle;
        this.offThrottle = builder.offThrottle;
        this.frontCamber = builder.frontCamber;
        this.rearCamber = builder.rearCamber;
        this.frontToe = builder.frontToe;
        this.rearToe = builder.rearToe;
        this.frontSusp = builder.frontSusp;
        this.rearSusp = builder.rearSusp;
        this.frontARB = builder.frontARB;
        this.rearARB = builder.rearARB;
        this.frontHeight = builder.frontHeight;
        this.rearHeight = builder.rearHeight;
        this.brakePressure = builder.brakePressure;
        this.brakeBias = builder.brakeBias;
        this.engineBraking = builder.engineBraking;
        this.rearLeftPressure = builder.rearLeftPressure;
        this.rearRightPressure = builder.rearRightPressure;
        this.frontLeftPressure = builder.frontLeftPressure;
        this.frontRightPressure = builder.frontRightPressure;
        this.ballast = builder.ballast;
        this.fuelLoad = builder.fuelLoad;

        this.setupName = builder.setupName;
    }

    private final int frontWing;
    private final int rearWing;
    private final int onThrottle;
    private final int offThrottle;
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
    private final int engineBraking;
    private final float rearLeftPressure;
    private final float rearRightPressure;
    private final float frontLeftPressure;
    private final float frontRightPressure;
    private final int ballast;
    private final float fuelLoad;

    private final String setupName;

    public int getFrontWing() {
        return frontWing;
    }

    public int getRearWing() {
        return rearWing;
    }

    public int getOnThrottle() {
        return onThrottle;
    }

    public int getOffThrottle() {
        return offThrottle;
    }

    public float getFrontCamber() {
        return frontCamber;
    }

    public float getRearCamber() {
        return rearCamber;
    }

    public float getFrontToe() {
        return frontToe;
    }

    public float getRearToe() {
        return rearToe;
    }

    public int getFrontSusp() {
        return frontSusp;
    }

    public int getRearSusp() {
        return rearSusp;
    }

    public int getFrontARB() {
        return frontARB;
    }

    public int getRearARB() {
        return rearARB;
    }

    public int getFrontHeight() {
        return frontHeight;
    }

    public int getRearHeight() {
        return rearHeight;
    }

    public int getBrakePressure() {
        return brakePressure;
    }

    public int getBrakeBias() {
        return brakeBias;
    }

    public int getEngineBraking() {
        return engineBraking;
    }

    public float getRearLeftPressure() {
        return rearLeftPressure;
    }

    public float getRearRightPressure() {
        return rearRightPressure;
    }

    public float getFrontLeftPressure() {
        return frontLeftPressure;
    }

    public float getFrontRightPressure() {
        return frontRightPressure;
    }

    public int getBallast() {
        return ballast;
    }

    public float getFuelLoad() {
        return fuelLoad;
    }

    public String getSetupName() {
        return setupName;
    }

    public boolean isSameFuelLoad(CarSetupData csd) {
        return csd.fuelLoad == this.fuelLoad;
    }

    public Label[][] getSetupDashboardData() {
        return new Label[][]{{new Label(" Fuel Load ")}, {new Label(DashboardUtils.formatTwoDecimals(this.fuelLoad) + Constants.KG)},
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

    public boolean equals(CarSetupData csd) {
        return (csd.getFrontWing() == this.frontWing && csd.getRearWing() == this.rearWing
                && csd.getOnThrottle() == this.onThrottle && csd.getOffThrottle() == this.offThrottle
                && csd.getFrontCamber() == this.frontCamber && csd.getRearCamber() == this.rearCamber
                && csd.getFrontToe() == this.frontToe && csd.getRearToe() == this.rearToe
                && csd.getFrontSusp() == this.frontSusp && csd.getRearSusp() == this.rearSusp
                && csd.getFrontARB() == this.frontARB && csd.getRearARB() == this.rearARB
                && csd.getFrontHeight() == this.frontHeight && csd.getRearHeight() == this.rearHeight
                && csd.getFrontRightPressure() == this.frontRightPressure && csd.getFrontLeftPressure() == this.frontLeftPressure
                && csd.getRearRightPressure() == this.rearRightPressure && csd.getRearLeftPressure() == this.rearLeftPressure
        );
    }

    public static class Builder {
        private int frontWing;
        private int rearWing;
        private int onThrottle;
        private int offThrottle;
        private float frontCamber;
        private float rearCamber;
        private float frontToe;
        private float rearToe;
        private int frontSusp;
        private int rearSusp;
        private int frontARB;
        private int rearARB;
        private int frontHeight;
        private int rearHeight;
        private int brakePressure;
        private int brakeBias;
        private int engineBraking = 0;
        private float rearLeftPressure;
        private float rearRightPressure;
        private float frontLeftPressure;
        private float frontRightPressure;
        private int ballast;
        private float fuelLoad;
        private String setupName;

        public Builder setFrontWing(int frontWing) {
            this.frontWing = frontWing;
            return this;
        }

        public Builder setRearWing(int rearWing) {
            this.rearWing = rearWing;
            return this;
        }

        public Builder setOnThrottle(int onThrottle) {
            this.onThrottle = onThrottle;
            return this;
        }

        public Builder setOffThrottle(int offThrottle) {
            this.offThrottle = offThrottle;
            return this;
        }

        public Builder setFrontCamber(float frontCamber) {
            this.frontCamber = frontCamber;
            return this;
        }

        public Builder setRearCamber(float rearCamber) {
            this.rearCamber = rearCamber;
            return this;
        }

        public Builder setFrontToe(float frontToe) {
            this.frontToe = frontToe;
            return this;
        }

        public Builder setRearToe(float rearToe) {
            this.rearToe = rearToe;
            return this;
        }

        public Builder setFrontSusp(int frontSusp) {
            this.frontSusp = frontSusp;
            return this;
        }

        public Builder setRearSusp(int rearSusp) {
            this.rearSusp = rearSusp;
            return this;
        }

        public Builder setFrontARB(int frontARB) {
            this.frontARB = frontARB;
            return this;
        }

        public Builder setRearARB(int rearARB) {
            this.rearARB = rearARB;
            return this;
        }

        public Builder setFrontHeight(int frontHeight) {
            this.frontHeight = frontHeight;
            return this;
        }

        public Builder setRearHeight(int rearHeight) {
            this.rearHeight = rearHeight;
            return this;
        }

        public Builder setBrakePressure(int brakePressure) {
            this.brakePressure = brakePressure;
            return this;
        }

        public Builder setBrakeBias(int brakeBias) {
            this.brakeBias = brakeBias;
            return this;
        }

        public Builder setEngineBraking(int engineBraking) {
            this.engineBraking = engineBraking;
            return this;
        }

        public Builder setRearLeftPressure(float rearLeftPressure) {
            this.rearLeftPressure = rearLeftPressure;
            return this;
        }

        public Builder setRearRightPressure(float rearRightPressure) {
            this.rearRightPressure = rearRightPressure;
            return this;
        }

        public Builder setFrontLeftPressure(float frontLeftPressure) {
            this.frontLeftPressure = frontLeftPressure;
            return this;
        }

        public Builder setFrontRightPressure(float frontRightPressure) {
            this.frontRightPressure = frontRightPressure;
            return this;
        }

        public Builder setBallast(int ballast) {
            this.ballast = ballast;
            return this;
        }

        public Builder setFuelLoad(float fuelLoad) {
            this.fuelLoad = fuelLoad;
            return this;
        }

        public Builder setSetupName(String setupName) {
            this.setupName = setupName;
            return this;
        }

        public CarSetupData build() {
            return new CarSetupData(this);
        }
    }
}

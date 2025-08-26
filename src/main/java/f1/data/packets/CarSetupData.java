package f1.data.packets;

import javafx.scene.control.Label;
import f1.data.ui.dashboards.DashboardUtils;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

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

public record CarSetupData(int frontWing, int rearWing, int onThrottle, int offThrottle, float frontCamber,
                           float rearCamber, float frontToe, float rearToe, int frontSusp, int rearSusp, int frontARB,
                           int rearARB, int frontHeight, int rearHeight, int brakePressure, int brakeBias,
                           float rearLeftPressure, float rearRightPressure, float frontLeftPressure,
                           float frontRightPressure, int ballast, float fuelLoad, int engineBraking, String setupName) {

    record CarSetupData20(int frontWing, int rearWing, int onThrottle, int offThrottle, float frontCamber,
                          float rearCamber, float frontToe, float rearToe, int frontSusp, int rearSusp, int frontARB,
                          int rearARB, int frontHeight, int rearHeight, int brakePressure, int brakeBias,
                          float rearLeftPressure, float rearRightPressure, float frontLeftPressure,
                          float frontRightPressure, int ballast, float fuelLoad) {
        public CarSetupData20(ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat(),
                    byteBuffer.getFloat(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.getFloat());
        }
    }

    record CarSetupData24(int frontWing, int rearWing, int onThrottle, int offThrottle, float frontCamber,
                          float rearCamber, float frontToe, float rearToe, int frontSusp, int rearSusp, int frontARB,
                          int rearARB, int frontHeight, int rearHeight, int brakePressure, int brakeBias,
                          int engineBraking, float rearLeftPressure, float rearRightPressure, float frontLeftPressure,
                          float frontRightPressure, int ballast, float fuelLoad) {
        public CarSetupData24(ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat(),
                    byteBuffer.getFloat(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat(),
                    byteBuffer.getFloat(), BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.getFloat()
            );
        }
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

    public boolean equals(CarSetupData csd) {
        return (csd.frontWing() == this.frontWing && csd.rearWing() == this.rearWing
                && csd.onThrottle() == this.onThrottle && csd.offThrottle() == this.offThrottle
                && csd.frontCamber() == this.frontCamber && csd.rearCamber() == this.rearCamber
                && csd.frontToe() == this.frontToe && csd.rearToe() == this.rearToe
                && csd.frontSusp() == this.frontSusp && csd.rearSusp() == this.rearSusp
                && csd.frontARB() == this.frontARB && csd.rearARB() == this.rearARB
                && csd.frontHeight() == this.frontHeight && csd.rearHeight() == this.rearHeight
                && csd.frontRightPressure() == this.frontRightPressure && csd.frontLeftPressure() == this.frontLeftPressure
                && csd.rearRightPressure() == this.rearRightPressure && csd.rearLeftPressure() == this.rearLeftPressure
        );
    }

    public boolean isSameFuelLoad(CarSetupData csd) {
        return csd.fuelLoad() == this.fuelLoad;
    }
}

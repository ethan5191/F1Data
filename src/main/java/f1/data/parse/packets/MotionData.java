package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 CarMotionData Breakdown (Little Endian)
 * - F1 2020-2022 Length: 60 bytes (had 9 other elements AFTER the array)
 * - F1 2023-2025 Length: 60 bytes
 * This struct is 60 bytes long and represents the motion data for a single car.
 * It is repeated 22 times within the PacketMotionData struct.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * -------------------------------
 * Member Name                     | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------------|----------------|--------------|----------------|-------------------------
 * m_header                        | PacketHeader    | ...          | 2020           | Full packet header
 * m_carMotionData[22]             | CarMotionData   | ...          | 2020           | Array for each car
 * - m_worldPositionX              | float           | 4            | 2020           | World space X position - metres
 * - m_worldPositionY              | float           | 4            | 2020           | World space Y position
 * - m_worldPositionZ              | float           | 4            | 2020           | World space Z position
 * - m_worldVelocityX              | float           | 4            | 2020           | Velocity in world space X – metres/s
 * - m_worldVelocityY              | float           | 4            | 2020           | Velocity in world space Y
 * - m_worldVelocityZ              | float           | 4            | 2020           | Velocity in world space Z
 * - m_worldForwardDirX            | int16           | 2            | 2020           | World space forward X direction (normalised)
 * - m_worldForwardDirY            | int16           | 2            | 2020           | World space forward Y direction (normalised)
 * - m_worldForwardDirZ            | int16           | 2            | 2020           | World space forward Z direction (normalised)
 * - m_worldRightDirX              | int16           | 2            | 2020           | World space right X direction (normalised)
 * - m_worldRightDirY              | int16           | 2            | 2020           | World space right Y direction (normalised)
 * - m_worldRightDirZ              | int16           | 2            | 2020           | World space right Z direction (normalised)
 * - m_gForceLateral               | float           | 4            | 2020           | Lateral G-Force component
 * - m_gForceLongitudinal          | float           | 4            | 2020           | Longitudinal G-Force component
 * - m_gForceVertical              | float           | 4            | 2020           | Vertical G-Force component
 * - m_yaw                         | float           | 4            | 2020           | Yaw angle in radians
 * - m_pitch                       | float           | 4            | 2020           | Pitch angle in radians
 * - m_roll                        | float           | 4            | 2020           | Roll angle in radians
 */

public record MotionData(
        float worldPositionX,
        float worldPositionY,
        float worldPositionZ,
        float worldVelocityX,
        float worldVelocityY,
        float worldVelocityZ,
        int worldForwardDirX,
        int worldForwardDirY,
        int worldForwardDirZ,
        int worldRightDirX,
        int worldRightDirY,
        int worldRightDirZ,
        float gForceLat,
        float gForceLon,
        float gForceVer,
        float yaw,
        float pitch,
        float roll
) {

    public MotionData(ByteBuffer byteBuffer) {
        this(
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                BitMaskUtils.bitMask16(byteBuffer.getShort()),
                BitMaskUtils.bitMask16(byteBuffer.getShort()),
                BitMaskUtils.bitMask16(byteBuffer.getShort()),
                BitMaskUtils.bitMask16(byteBuffer.getShort()),
                BitMaskUtils.bitMask16(byteBuffer.getShort()),
                BitMaskUtils.bitMask16(byteBuffer.getShort()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat()),
                determineFloatValue(byteBuffer.getFloat())
        );
    }

    private static float determineFloatValue(float val) {
        return val / Constants.DIVISOR;
    }
}

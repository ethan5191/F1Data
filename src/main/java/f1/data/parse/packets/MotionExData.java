package f1.data.parse.packets;

import f1.data.utils.ParseUtils;

import java.nio.ByteBuffer;

/**
 * F1 2023 PacketMotionExData Breakdown (Little Endian)
 * - F1 2023 Length: 204 bytes
 * - F1 2024 Length: 224 bytes
 * - F1 2025 Length: 260 bytes
 * This struct contains extra motion data for the player's car only.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * PacketMotionExData
 * -------------------
 * Member Name              | Data Type  | Size (bytes) | First Appeared | Notes
 * -------------------------|------------|--------------|-----------------|------------------
 * m_header                 | PacketHeader | 24         |                 | Full packet header
 * m_suspensionPosition[4]  | float[4]   | 16           | 2023            | All wheel arrays are RL, RR, FL, FR
 * m_suspensionVelocity[4]  | float[4]   | 16           | 2023            |
 * m_suspensionAcceleration[4]| float[4]   | 16         | 2023            |
 * m_wheelSpeed[4]          | float[4]   | 16           | 2023            |
 * m_wheelSlipRatio[4]      | float[4]   | 16           | 2023            |
 * m_wheelSlipAngle[4]      | float[4]   | 16           | 2023            |
 * m_wheelLatForce[4]       | float[4]   | 16           | 2023            |
 * m_wheelLongForce[4]      | float[4]   | 16           | 2023            |
 * m_heightOfCOGAboveGround | float      | 4            | 2023            |
 * m_localVelocityX         | float      | 4            | 2023            |
 * m_localVelocityY         | float      | 4            | 2023            |
 * m_localVelocityZ         | float      | 4            | 2023            |
 * m_angularVelocityX       | float      | 4            | 2023            |
 * m_angularVelocityY       | float      | 4            | 2023            |
 * m_angularVelocityZ       | float      | 4            | 2023            |
 * m_angularAccelerationX   | float      | 4            | 2023            |
 * m_angularAccelerationY   | float      | 4            | 2023            |
 * m_angularAccelerationZ   | float      | 4            | 2023            |
 * m_frontWheelsAngle       | float      | 4            | 2023            |
 * m_wheelVertForce[4]      | float[4]   | 16           | 2023            |
 * m_frontAeroHeight        | float      | 4            | 2024            |
 * m_rearAeroHeight         | float      | 4            | 2024            |
 * m_frontRollAngle         | float      | 4            | 2024            |
 * m_rearRollAngle          | float      | 4            | 2024            |
 * m_chassisYaw             | float      | 4            | 2024            |
 * m_chassisPitch           | float      | 4            | 2025            |
 * m_wheelCamber[4]         | float[4]   | 16           | 2025            |
 * m_wheelCamberGain[4]     | float[4]   | 16           | 2025            |
 * <p>
 * Note:
 * - float maps directly to a Java 'float'.
 * - Arrays must be read in a loop for proper data conversion.
 */

public record MotionExData(float[] suspensionPosition, float[] suspensionVelocity, float[] suspensionAcceleration,
                           float[] wheelSpeed, float[] wheelSlipRatio, float[] wheelSlipAngle, float[] wheelLatForce,
                           float[] wheelLongForce, float heightOfCOGAboveGround, float localVelocityX,
                           float localVelocityY, float localVelocityZ, float angularVelocityX, float angularVelocityY,
                           float angularVelocityZ, float angularAccelerationX, float angularAccelerationY,
                           float angularAccelerationZ, float frontWheelsAngle, float[] wheelVertForce,
                           float frontAeroHeight, float rearAeroHeight, float frontRollAngle, float rearRollAngle,
                           float chassisYaw, float chassisPitch, float[] wheelCamber, float[] wheelCamberGain) {

    record MotionExData23(float[] suspensionPosition, float[] suspensionVelocity, float[] suspensionAcceleration,
                          float[] wheelSpeed, float[] wheelSlipRatio, float[] wheelSlipAngle, float[] wheelLatForce,
                          float[] wheelLongForce, float heightOfCOGAboveGround, float localVelocityX,
                          float localVelocityY, float localVelocityZ, float angularVelocityX, float angularVelocityY,
                          float angularVelocityZ, float angularAccelerationX, float angularAccelerationY,
                          float angularAccelerationZ, float frontWheelsAngle, float[] wheelVertForce) {
        public MotionExData23(ByteBuffer byteBuffer) {
            this(ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    ParseUtils.parseFloatArray(byteBuffer, 4)
            );
        }
    }

    record MotionExData24(float[] suspensionPosition, float[] suspensionVelocity, float[] suspensionAcceleration,
                          float[] wheelSpeed, float[] wheelSlipRatio, float[] wheelSlipAngle, float[] wheelLatForce,
                          float[] wheelLongForce, float heightOfCOGAboveGround, float localVelocityX,
                          float localVelocityY, float localVelocityZ, float angularVelocityX, float angularVelocityY,
                          float angularVelocityZ, float angularAccelerationX, float angularAccelerationY,
                          float angularAccelerationZ, float frontWheelsAngle, float[] wheelVertForce,
                          float frontAeroHeight, float rearAeroHeight, float frontRollAngle, float rearRollAngle,
                          float chassisYaw) {
        public MotionExData24(ByteBuffer byteBuffer) {
            this(ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat()
            );
        }
    }

    record MotionExData25(float[] suspensionPosition, float[] suspensionVelocity, float[] suspensionAcceleration,
                          float[] wheelSpeed, float[] wheelSlipRatio, float[] wheelSlipAngle, float[] wheelLatForce,
                          float[] wheelLongForce, float heightOfCOGAboveGround, float localVelocityX,
                          float localVelocityY, float localVelocityZ, float angularVelocityX, float angularVelocityY,
                          float angularVelocityZ, float angularAccelerationX, float angularAccelerationY,
                          float angularAccelerationZ, float frontWheelsAngle, float[] wheelVertForce,
                          float frontAeroHeight, float rearAeroHeight, float frontRollAngle, float rearRollAngle,
                          float chassisYaw, float chassisPitch, float[] wheelCamber, float[] wheelCamberGain) {
        public MotionExData25(ByteBuffer byteBuffer) {
            this(ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseFloatArray(byteBuffer, 4)
            );
        }
    }
}

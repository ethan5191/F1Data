package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 2024 TimeTrialDataSet Breakdown (Little Endian)
 * - F1 2024/2025 Length: 23 bytes
 * This struct is 23 bytes long and contains time trial data for a single car,
 * including lap times and assist settings.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * TimeTrialDataSet
 * -------------------
 * Member Name           | Data Type | Size (bytes) | First Appeared | Notes
 * ----------------------|-----------|--------------|----------------|-------------------
 * m_carIdx              | uint8     | 1            | 2024           | Index of the car
 * m_teamId              | uint8     | 1            | 2024           | Team ID
 * m_lapTimeInMS         | uint32    | 4            | 2024           | Lap time in milliseconds
 * m_sector1TimeInMS     | uint32    | 4            | 2024           | Sector 1 time
 * m_sector2TimeInMS     | uint32    | 4            | 2024           | Sector 2 time
 * m_sector3TimeInMS     | uint32    | 4            | 2024           | Sector 3 time
 * m_tractionControl     | uint8     | 1            | 2024           | Traction control setting
 * m_gearboxAssist       | uint8     | 1            | 2024           | Gearbox assist setting
 * m_antiLockBrakes      | uint8     | 1            | 2024           | Anti-lock brakes setting
 * m_equalCarPerformance | uint8     | 1            | 2024           | Equal car performance flag
 * m_customSetup         | uint8     | 1            | 2024           | 0 = No, 1 = Yes
 * m_valid               | uint8     | 1            | 2024           | 0 = invalid, 1 = valid
 * <p>
 * Note:
 * - uint32 maps to a Java 'long'.
 * - uint8 maps to a Java 'int' (byte cast to int).
 */

public record TimeTrialData(int carIndex, int teamId, long lapTimeInMS, long sector1TimeInMS, long sector2TimeInMS,
                            long sector3TimeInMS, int tractionControl, int gearboxAssist, int antiLockBrakes,
                            int equalCarPerformance, int customSetup, int valid) {

    public TimeTrialData(ByteBuffer byteBuffer) {
        this(BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask32(byteBuffer.getInt()),
                BitMaskUtils.bitMask32(byteBuffer.getInt()),
                BitMaskUtils.bitMask32(byteBuffer.getInt()),
                BitMaskUtils.bitMask32(byteBuffer.getInt()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get())
        );
    }
}

package f1.data.parse.packets.history;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 2021 LapHistoryData Breakdown (Little Endian)
 * - F1 2021/2022 Length: 11 bytes per lap
 * - F1 2023-2025 Length: 14 bytes per lap
 * This struct is 11 bytes long and contains details of a single lap's history,
 * including lap time, sector times, and validity flags. This data is sent as an array.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * LapHistoryData
 * -----------------------------
 * Member Name             | Data Type      | Size (bytes) | First Appeared | Notes
 * ------------------------|----------------|--------------|----------------|-------------------
 * m_lapTimeInMS           | uint32         | 4            | 2021           | Lap time in milliseconds
 * m_sector1TimeInMS       | uint16         | 2            | 2021           | Sector 1 time in milliseconds
 * m_sector1TimeMinutes    | uint8          | 1            | 2023           | Sector 1 time Minutes Part
 * m_sector2TimeInMS       | uint16         | 2            | 2021           | Sector 2 time in milliseconds
 * m_sector2TimeMinutes    | uint8          | 1            | 2023           | Sector 2 time Minutes Part
 * m_sector3TimeInMS       | uint16         | 2            | 2021           | Sector 3 time in milliseconds
 * m_sector3TimeMinutes    | uint8          | 1            | 2023           | Sector 3 time Minutes Part
 * m_lapValidBitFlags      | uint8          | 1            | 2021           | Validity flags
 * <p>
 * Note:
 * - uint32 maps to a Java 'long'.
 * - uint16 maps to a Java 'int'.
 * - uint8 maps to a Java 'int' (byte cast to int).
 * - The validity flags must be read using bitmasking.
 */

public record LapHistoryData(long lapTimeInMS, int sector1TimeInMS, int sector2TimeInMS, int sector3TimeInMS,
                             int lapValidBitFlags, int sector1TimeMinutesPart, int sector2TimeMinutesPart,
                             int sector3TimeMinutesPart) {

    record LapHistoryData21(long lapTimeInMS, int sector1TimeInMS, int sector2TimeInMS, int sector3TimeInMS,
                            int lapValidBitFlags) {
        public LapHistoryData21(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record LapHistoryData23(long lapTimeInMS, int sector1TimeInMS, int sector1TimeMinutesPart, int sector2TimeInMS,
                            int sector2TimeMinutesPart, int sector3TimeInMS, int sector3TimeMinutesPart,
                            int lapValidBitFlags) {
        public LapHistoryData23(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }
}

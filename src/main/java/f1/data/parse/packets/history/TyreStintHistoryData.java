package f1.data.parse.packets.history;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 2021 TyreStintHistoryData Breakdown (Little Endian)
 * - F1 2021-2025 Length: 3 bytes per stint
 * This struct is 3 bytes long and contains details about a single tyre stint in a race.
 * This data is sent as an array for each tyre stint used by a driver.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * TyreStintHistoryData
 * --------------------
 * Member Name          | Data Type | Size (bytes) | First Appeared | Notes
 * ---------------------|-----------|--------------|----------------|-------------------
 * m_endLap             | uint8     | 1            | 2021           | Lap the stint ends on
 * m_tyreActualCompound | uint8     | 1            | 2021           | Actual tyre compound used
 * m_tyreVisualCompound | uint8     | 1            | 2021           | Visual tyre compound used
 * <p>
 * Note:
 * - uint8 maps to a Java 'int' (byte cast to int).
 */

public record TyreStintHistoryData(int endLap, int tyreActualCompound, int tyreVisualCompound) {

    public TyreStintHistoryData(ByteBuffer byteBuffer) {
        this(
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get())
        );
    }
}

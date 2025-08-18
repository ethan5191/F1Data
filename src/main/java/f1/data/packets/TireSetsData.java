package f1.data.packets;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 24 TyreSetData Breakdown (Little Endian)
 * Packet didn't exist prior to 2023's version of the game.
 * - F1 2023-2025 Length: 10 bytes
 * This struct is 10 bytes long and contains details about a single tyre set.
 * This data is sent for all tyre sets in the session.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * -------------------------------
 * Member Name                     | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------------|----------------|--------------|----------------|-------------------------
 * m_header                        | PacketHeader    | ...          | 2024           | Full packet header
 * m_carIdx                        | uint8           | 1            | 2024           | Index of the car this data relates to
 * m_tyreSetData[20]               | TyreSetData     | ...          | 2024           | Array for 20 tyre sets (13 dry, 7 wet)
 * - m_actualTyreCompound          | uint8           | 1            | 2024           | Actual tyre compound used
 * - m_visualTyreCompound          | uint8           | 1            | 2024           | Visual tyre compound used
 * - m_wear                        | uint8           | 1            | 2024           | Tyre wear (percentage)
 * - m_available                   | uint8           | 1            | 2024           | Whether this set is currently available
 * - m_recommendedSession          | uint8           | 1            | 2024           | Recommended session for tyre set
 * - m_lifeSpan                    | uint8           | 1            | 2024           | Laps left in this tyre set
 * - m_usableLife                  | uint8           | 1            | 2024           | Max number of laps recommended
 * - m_lapDeltaTime                | int16           | 2            | 2024           | Lap delta time in ms vs. fitted set
 * - m_fitted                      | uint8           | 1            | 2024           | Whether the set is fitted or not
 * m_fittedIdx                     | uint8           | 1            | 2024           | Index into array of the fitted tyre set
 * Note:
 * - The uint8 type must be read with bitmasking (e.g., byteBuffer.get() & Constants.BIT_MASK_8).
 * - The int16 type maps directly to a Java 'short'.
 */
public record TireSetsData(int actualTireCompound,
                           int visualTireCompound,
                           int wear,
                           int available,
                           int recommendedSession,
                           int lifeSpan,
                           int usableLife,
                           short lapDeltaTime,
                           int fitted) {
    public TireSetsData(ByteBuffer byteBuffer) {
        this(BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                byteBuffer.getShort(),
                BitMaskUtils.bitMask8(byteBuffer.get()));
    }
}

package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * F1 24 packets.PacketHeader Breakdown (Little Endian)
 * <p>
 * This header is present at the beginning of every telemetry packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * * **Note:** The header length and some fields vary by game year.
 *  - F1 2020 - 2022 Header Length: 24 bytes
 *  - F1 2023 - 2025 Header Length: 29 bytes
 * Member Name                    | Data Type | Size (bytes) | Starting Offset | First Appeared
 * -------------------------------|-----------|--------------|-----------------|---------------
 * m_packetFormat                 | uint16    | 2            | 0               | 2020
 * m_gameYear                     | uint8     | 1            | 2               | 2023
 * m_gameMajorVersion             | uint8     | 1            | 3               | 2020
 * m_gameMinorVersion             | uint8     | 1            | 4               | 2020
 * m_packetVersion                | uint8     | 1            | 5               | 2020
 * m_packetId                     | uint8     | 1            | 6               | 2020
 * m_sessionUID                   | uint64    | 8            | 7               | 2020
 * m_sessionTime                  | float     | 4            | 15              | 2020
 * m_frameIdentifier              | uint32    | 4            | 19              | 2020
 * m_overallFrameIdentifier       | uint32    | 4            | 23              | 2023
 * m_playerCarIndex               | uint8     | 1            | 27              | 2020
 * m_secondaryPlayerCarIndex      | uint8     | 1            | 28              | 2020
 */

public record PacketHeader(int packetFormat, int majorVersion, int minorVersion, int packetVersion, int packetId,
                           BigInteger sessionUID, float sessionTime, long frameID, int playerCarIndex,
                           int secondaryPlayerCarIndex, int gameYear, long overallFrameID) {

    record PacketHeader20(int packetFormat,
                          int majorVersion,
                          int minorVersion,
                          int packetVersion,
                          int packetId,
                          BigInteger sessionUID,
                          float sessionTime,
                          long frameID,
                          int playerCarIndex,
                          int secondaryPlayerCarIndex
    ) {
        public PacketHeader20(int packetFormat, ByteBuffer byteBuffer) {
            this(
                    packetFormat,
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask64(byteBuffer.getLong()),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record PacketHeader23(int packetFormat,
                          int gameYear,
                          int majorVersion,
                          int minorVersion,
                          int packetVersion,
                          int packetId,
                          BigInteger sessionUID,
                          float sessionTime,
                          long frameID,
                          long overallFrameID,
                          int playerCarIndex,
                          int secondaryPlayerCarIndex

    ) {
        public PacketHeader23(int packetFormat, ByteBuffer byteBuffer) {
            this(
                    packetFormat,
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask64(byteBuffer.getLong()),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }
}

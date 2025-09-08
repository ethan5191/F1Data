package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 2020 LobbyInfoData Breakdown (Little Endian)
 * - F1 2020 Length: 52 bytes per player
 * - F1 2021/2022 Length: 53 bytes per player
 * This struct is 52 bytes long and contains details about a single player in the lobby,
 * including their name, team, and ready status. This data is sent as an array for each player.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * PacketLobbyInfoData
 * -------------------
 * Member Name             | Data Type      | Size (bytes) | First Appeared | Notes
 * ------------------------|----------------|--------------|----------------|-------------------------
 * m_header                | PacketHeader   | 24           | 2018           | Full packet header
 * m_numPlayers            | uint8          | 1            | 2018           | Number of players in this packet
 * m_lobbyPlayers[22]      | LobbyInfoData  | 1650         | 2020           | Array for each player
 * - m_aiControlled        | uint8          | 1            | 2020           |
 * - m_teamId              | uint8          | 1            | 2020           |
 * - m_nationality         | uint8          | 1            | 2020           |
 * - m_name                | char[48]       | 48           | 2020           | UTF-8 name, null-terminated
 * - m_carNumber           | uint8          | 1            | 2021           | car number
 * - m_readyStatus         | uint8          | 1            | 2020           |
 *
 * Note:
 * - The uint8 type must be read as a byte and converted to an int to get the unsigned value.
 * - The char array must be read with a loop or a direct buffer get for the specified length.
 * - Arrays must be read in a loop for proper data conversion.
 */

public record LobbyInfoData(int aiControlled, int teamId, int nationality, byte[] name, int readyStatus, int carNumber) {

    private static byte[] formatName(ByteBuffer byteBuffer, int nameLength) {
        byte[] tempName = new byte[nameLength];
        byteBuffer.get(tempName, 0, nameLength);
        return tempName;
    }

    record LobbyInfoData20(int aiControlled, int teamId, int nationality, byte[] name, int readyStatus) {
        public LobbyInfoData20(ByteBuffer byteBuffer, int nameLength) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    formatName(byteBuffer, nameLength),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record LobbyInfoData21(int aiControlled, int teamId, int nationality, byte[] name, int readyStatus, int carNumber) {
        public LobbyInfoData21(ByteBuffer byteBuffer, int nameLength) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    formatName(byteBuffer, nameLength),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }
}

package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 24 ParticipantData Breakdown (Little Endian)
 * <p>
 * This struct contains details for a single participant (driver).
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * * **Note:** The header length and some fields vary by game year.
 * - F1 2020 Length: 54 bytes
 * - F1 2021/2022 Length: 56 bytes
 * - F1 2023 Length: 58 bytes
 * - F1 2024 Length: 60 bytes
 *  TODO: Look at how this is sent in 2025.
 * /*
 * PacketParticipantsData
 * ----------------------
 * Member Name               | Data Type          | Size (bytes) | First Appeared | Notes
 * --------------------------|------------------|--------------|----------------|-------------------------
 * m_header                  | PacketHeader      | ...          | 2020           | Full packet header
 * m_numActiveCars            | uint8             | 1            | 2020           | Number of active cars on HUD
 * m_participants[22]         | ParticipantData   | ...          | 2020           | Array for each participant
 * - m_aiControlled         | uint8             | 1            | 2020           | AI or human
 * - m_driverId             | uint8             | 1            | 2020           | 255 if network human
 * - m_networkId            | uint8             | 1            | 2021           | Unique network ID
 * - m_teamId               | uint8             | 1            | 2020           |
 * - m_myTeam               | uint8             | 1            | 2021           | 1 = My Team, 0 = otherwise
 * - m_raceNumber           | uint8             | 1            | 2020           |
 * - m_nationality          | uint8             | 1            | 2020           |
 * - m_name[48]             | char[48]          | 48           | 2020           | UTF-8, null-terminated
 * - m_yourTelemetry        | uint8             | 1            | 2020           | 0 = restricted, 1 = public
 * - m_showOnlineNames      | uint8             | 1            | 2023           | 0 = off, 1 = on
 * - m_techLevel            | uint16            | 2            | 2024           | F1 World tech level
 * - m_platform             | uint8             | 1            | 2023           | 1=Steam,3=PS,4=Xbox,6=Origin,255=unknown
 * * Note:
 * - uint8 and uint16 types should be read as unsigned integers.
 * - m_name is a fixed-size char array that should be read into a String.
 */

public record ParticipantData(int aiControlled, int driverId, int teamId, int raceNumber, int nationality, byte[] name,
                              int yourTelemetry, int networkId, int myTeam, int showOnlineNames, int platform,
                              int techLevel, String lastName) {

    private static byte[] formatName(ByteBuffer byteBuffer, int nameLength) {
        byte[] tempName = new byte[nameLength];
        byteBuffer.get(tempName, 0, nameLength);
        return tempName;
    }

    record ParticipantData20(int aiControlled, int driverId, int teamId, int raceNumber, int nationality, byte[] name,
                             int yourTelemetry) {
        public ParticipantData20(int nameLength, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    formatName(byteBuffer, nameLength),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record ParticipantData21(int aiControlled, int driverId, int networkId, int teamId, int myTeam, int raceNumber,
                             int nationality, byte[] name, int yourTelemetry) {
        public ParticipantData21(int nameLength, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    formatName(byteBuffer, nameLength),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record ParticipantData23(int aiControlled, int driverId, int networkId, int teamId, int myTeam, int raceNumber,
                             int nationality, byte[] name, int yourTelemetry, int showOnlineNames, int platform) {
        public ParticipantData23(int nameLength, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    formatName(byteBuffer, nameLength),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record ParticipantData24(int aiControlled, int driverId, int networkId, int teamId, int myTeam, int raceNumber,
                             int nationality, byte[] name, int yourTelemetry, int showOnlineNames, int techLevel,
                             int platform) {
        public ParticipantData24(int nameLength, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    formatName(byteBuffer, nameLength),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record ParticipantData25(int aiControlled, int driverId, int networkId, int teamId, int myTeam, int raceNumber,
                             int nationality, byte[] name, int yourTelemetry, int showOnlineNames, int techLevel,
                             int platform) {
        public ParticipantData25(int nameLength, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    formatName(byteBuffer, nameLength),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
                    //TODO add new params for 2025 participantData
            );
        }
    }
}

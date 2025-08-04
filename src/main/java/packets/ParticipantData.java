package packets;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * F1 24 ParticipantData Breakdown (Little Endian)
 *
 * This struct is 60 bytes long and contains data about a single participant.
 * It is repeated for each participant in the PacketParticipantsData packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name              | Data Type      | Size (bytes) | Starting Offset
 * -------------------------|----------------|--------------|-----------------
 * m_aiControlled           | uint8          | 1            | 0
 * m_driverId               | uint8          | 1            | 1
 * m_networkId              | uint8          | 1            | 2
 * m_teamId                 | uint8          | 1            | 3
 * m_myTeam                 | uint8          | 1            | 4
 * m_raceNumber             | uint8          | 1            | 5
 * m_nationality            | uint8          | 1            | 6
 * m_name                   | char[48]       | 48           | 7
 * m_yourTelemetry          | uint8          | 1            | 55
 * m_showOnlineNames        | uint8          | 1            | 56
 * m_techLevel              | uint16         | 2            | 57
 * m_platform               | uint8          | 1            | 59
 */

public class ParticipantData extends Data {

    public ParticipantData(ByteBuffer byteBuffer) {
//        printMessage("Participant packets.Data ", byteBuffer.array().length);
        this.aiControlled = byteBuffer.get();
        this.driverId = byteBuffer.get();
        this.networkId = byteBuffer.get();
        this.teamId = byteBuffer.get();
        this.myTeam = byteBuffer.get();
        this.raceNumber = byteBuffer.get();
        this.nationality = byteBuffer.get();
        byte[] tempName = new byte[48];
        byteBuffer.get(tempName, 0, 48);
        this.name = tempName;
        this.yourTelemetry = byteBuffer.get();
        this.showOnlineNames = byteBuffer.get();
        this.techLevel = byteBuffer.getShort();
        this.platform = byteBuffer.get();
    }

    private final byte aiControlled;
    private final byte driverId;
    private final byte networkId;
    private final byte teamId;
    private final byte myTeam;
    private final byte raceNumber;
    private final byte nationality;
    private final byte[] name;
    private final byte yourTelemetry;
    private final byte showOnlineNames;
    private final short techLevel;
    private final byte platform;

    public byte getAiControlled() {
        return aiControlled;
    }

    public byte getDriverId() {
        return driverId;
    }

    public byte getNetworkId() {
        return networkId;
    }

    public byte getTeamId() {
        return teamId;
    }

    public byte getMyTeam() {
        return myTeam;
    }

    public byte getRaceNumber() {
        return raceNumber;
    }

    public byte getNationality() {
        return nationality;
    }

    public byte[] getName() {
        return name;
    }

    public byte getYourTelemetry() {
        return yourTelemetry;
    }

    public byte getShowOnlineNames() {
        return showOnlineNames;
    }

    public short getTechLevel() {
        return techLevel;
    }

    public byte getPlatform() {
        return platform;
    }

    public void printName() {
        int length = 0;
        while (length < this.name.length && name[length] != 0) {
            length++;
        }
        String name = new String(this.name, 0, length, StandardCharsets.UTF_8);
        if (this.aiControlled == 1) {
            System.out.println(name);
        } else {
            System.out.println(name + " (Human Player)" );
        }
    }
}

package packets;

import utils.constants.Constants;

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
        this.aiControlled = byteBuffer.get() & Constants.BIT_MASK_8;
        this.driverId = byteBuffer.get() & Constants.BIT_MASK_8;
        this.networkId = byteBuffer.get() & Constants.BIT_MASK_8;
        this.teamId = byteBuffer.get() & Constants.BIT_MASK_8;
        this.myTeam = byteBuffer.get() & Constants.BIT_MASK_8;
        this.raceNumber = byteBuffer.get() & Constants.BIT_MASK_8;
        this.nationality = byteBuffer.get() & Constants.BIT_MASK_8;
        byte[] tempName = new byte[48];
        byteBuffer.get(tempName, 0, 48);
        this.name = tempName;
        this.yourTelemetry = byteBuffer.get() & Constants.BIT_MASK_8;
        this.showOnlineNames = byteBuffer.get() & Constants.BIT_MASK_8;
        this.techLevel = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.platform = byteBuffer.get() & Constants.BIT_MASK_8;
        int length = 0;
        while (length < this.name.length && name[length] != 0) {
            length++;
        }
        this.lastName = new String(this.name, 0, length, StandardCharsets.UTF_8);

    }

    private final int aiControlled;
    private final int driverId;
    private final int networkId;
    private final int teamId;
    private final int myTeam;
    private final int raceNumber;
    private final int nationality;
    private final byte[] name;
    private final int yourTelemetry;
    private final int showOnlineNames;
    private final int techLevel;
    private final int platform;

    private final String lastName;

    public int getAiControlled() {
        return aiControlled;
    }

    public int getDriverId() {
        return driverId;
    }

    public int getNetworkId() {
        return networkId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getMyTeam() {
        return myTeam;
    }

    public int getRaceNumber() {
        return raceNumber;
    }

    public int getNationality() {
        return nationality;
    }

    public byte[] getName() {
        return name;
    }

    public int getYourTelemetry() {
        return yourTelemetry;
    }

    public int getShowOnlineNames() {
        return showOnlineNames;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public int getPlatform() {
        return platform;
    }

    public String getLastName() {
        return lastName;
    }

    public void printName() {
        if (this.aiControlled == 1) {
            System.out.println(this.lastName);
        } else {
            System.out.println(this.lastName + " (Human Player)" );
        }
    }
}

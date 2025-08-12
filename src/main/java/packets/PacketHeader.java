package packets;

import utils.constants.Constants;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * F1 24 packets.PacketHeader Breakdown (Little Endian)
 * <p>
 * This header is 29 bytes long and is present at the beginning of every telemetry packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * Member Name                    | packets.Data Type | Size (bytes) | Starting Offset
 * -------------------------------|-----------|--------------|-----------------
 * m_packetFormat                 | uint16    | 2            | 0
 * m_gameYear                     | uint8     | 1            | 2
 * m_gameMajorVersion             | uint8     | 1            | 3
 * m_gameMinorVersion             | uint8     | 1            | 4
 * m_packetVersion                | uint8     | 1            | 5
 * m_packetId                     | uint8     | 1            | 6
 * m_sessionUID                   | uint64    | 8            | 7
 * m_sessionTime                  | float     | 4            | 15
 * m_frameIdentifier              | uint32    | 4            | 19
 * m_overallFrameIdentifier       | uint32    | 4            | 23
 * m_playerCarIndex               | uint8     | 1            | 27
 * m_secondaryPlayerCarIndex      | uint8     | 1            | 28
 */

public class PacketHeader {

    public PacketHeader(ByteBuffer byteBuffer) {
        this.packetFormat = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.gameYear = byteBuffer.get() & Constants.BIT_MASK_8;
        this.majorVersion = byteBuffer.get() & Constants.BIT_MASK_8;
        this.minorVersion = byteBuffer.get() & Constants.BIT_MASK_8;
        this.packetVersion = byteBuffer.get() & Constants.BIT_MASK_8;
        this.packetId = byteBuffer.get() & Constants.BIT_MASK_8;
        this.sessionUID = BigInteger.valueOf(byteBuffer.getLong()).and(BigInteger.ONE.shiftLeft(64).subtract(BigInteger.ONE));
        this.sessionTime = byteBuffer.getFloat();
        this.frameID = byteBuffer.getInt() & Constants.BIT_MASK_32;
        this.overallFrameID = byteBuffer.getInt() & Constants.BIT_MASK_32;
        this.playerCarIndex = byteBuffer.get() & Constants.BIT_MASK_8;
        this.secondaryPlayerCarIndex = byteBuffer.get() & Constants.BIT_MASK_8;
    }

    private final int packetFormat;
    private final int gameYear;
    private final int majorVersion;
    private final int minorVersion;
    private final int packetVersion;
    private final int packetId;
    private final BigInteger sessionUID;
    private final float sessionTime;
    private final long frameID;
    private final long overallFrameID;
    private final int playerCarIndex;
    private final int secondaryPlayerCarIndex;

    public int getPacketFormat() {
        return packetFormat;
    }

    public int getGameYear() {
        return gameYear;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getPacketVersion() {
        return packetVersion;
    }

    public int getPacketId() {
        return packetId;
    }

    public BigInteger getSessionUID() {
        return sessionUID;
    }

    public float getSessionTime() {
        return sessionTime;
    }

    public long getFrameID() {
        return frameID;
    }

    public long getOverallFrameID() {
        return overallFrameID;
    }

    public int getPlayerCarIndex() {
        return playerCarIndex;
    }

    public int getSecondaryPlayerCarIndex() {
        return secondaryPlayerCarIndex;
    }

    protected void print() {
        System.out.println("Packed ID " + this.packetId + " Version " + this.packetVersion + " Frame ID " + this.frameID +
                " Overall Frame ID " + this.overallFrameID + " Player Car " + this.playerCarIndex);
    }
}

package packets;

import java.nio.ByteBuffer;

public class PacketHeader {

    public PacketHeader(ByteBuffer byteBuffer) {
        this.packetFormat = byteBuffer.getShort();
        this.gameYear = byteBuffer.get();
        this.majorVersion = byteBuffer.get();
        this.minorVersion = byteBuffer.get();
        this.packetVersion = byteBuffer.get();
        this.packetId = byteBuffer.get();
        this.sessionUID = byteBuffer.getLong();
        this.sessionTime = byteBuffer.getFloat();
        this.frameID = byteBuffer.getInt();
        this.overallFrameID = byteBuffer.getInt();
        this.playerCarIndex = byteBuffer.get();
        this.secondaryPlayerCarIndex = byteBuffer.get();
    }


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


    private final short packetFormat;
    private final byte gameYear;
    private final byte majorVersion;
    private final byte minorVersion;
    private final byte packetVersion;
    private final byte packetId;
    private final long sessionUID;
    private final float sessionTime;
    private final int frameID;
    private final int overallFrameID;
    private final byte playerCarIndex;
    private final byte secondaryPlayerCarIndex;

    public short getPacketFormat() {
        return packetFormat;
    }

    public byte getGameYear() {
        return gameYear;
    }

    public byte getMajorVersion() {
        return majorVersion;
    }

    public byte getMinorVersion() {
        return minorVersion;
    }

    public byte getPacketVersion() {
        return packetVersion;
    }

    public byte getPacketId() {
        return packetId;
    }

    public long getSessionUID() {
        return sessionUID;
    }

    public float getSessionTime() {
        return sessionTime;
    }

    public int getFrameID() {
        return frameID;
    }

    public int getOverallFrameID() {
        return overallFrameID;
    }

    public byte getPlayerCarIndex() {
        return playerCarIndex;
    }

    public byte getSecondaryPlayerCarIndex() {
        return secondaryPlayerCarIndex;
    }

    protected void print() {
        System.out.println("Packed ID " + this.packetId + " Version " + this.packetVersion + " Frame ID " + this.frameID +
                " Overall Frame ID " + this.overallFrameID + " Player Car " + this.playerCarIndex);
    }
}

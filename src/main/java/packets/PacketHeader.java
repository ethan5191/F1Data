package packets;

import java.math.BigInteger;

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

public class PacketHeader {

    public PacketHeader(Builder builder) {
        this.packetFormat = builder.packetFormat;
        this.gameYear = builder.gameYear;
        this.majorVersion = builder.majorVersion;
        this.minorVersion = builder.minorVersion;
        this.packetVersion = builder.packetVersion;
        this.packetId = builder.packetId;
        this.sessionUID = builder.sessionUID;
        this.sessionTime = builder.sessionTime;
        this.frameID = builder.frameID;
        this.overallFrameID = builder.overallFrameID;
        this.playerCarIndex = builder.playerCarIndex;
        this.secondaryPlayerCarIndex = builder.secondaryPlayerCarIndex;
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

    public static class Builder {
        private int packetFormat;
        private int gameYear;
        private int majorVersion;
        private int minorVersion;
        private int packetVersion;
        private int packetId;
        private BigInteger sessionUID;
        private float sessionTime;
        private long frameID;
        private long overallFrameID;
        private int playerCarIndex;
        private int secondaryPlayerCarIndex;

        public Builder setPacketFormat(int packetFormat) {
            this.packetFormat = packetFormat;
            return this;
        }

        public Builder setGameYear(int gameYear) {
            this.gameYear = gameYear;
            return this;
        }

        public Builder setMajorVersion(int majorVersion) {
            this.majorVersion = majorVersion;
            return this;
        }

        public Builder setMinorVersion(int minorVersion) {
            this.minorVersion = minorVersion;
            return this;
        }

        public Builder setPacketVersion(int packetVersion) {
            this.packetVersion = packetVersion;
            return this;
        }

        public Builder setPacketId(int packetId) {
            this.packetId = packetId;
            return this;
        }

        public Builder setSessionUID(BigInteger sessionUID) {
            this.sessionUID = sessionUID;
            return this;
        }

        public Builder setSessionTime(float sessionTime) {
            this.sessionTime = sessionTime;
            return this;
        }

        public Builder setFrameID(long frameID) {
            this.frameID = frameID;
            return this;
        }

        public Builder setOverallFrameID(long overallFrameID) {
            this.overallFrameID = overallFrameID;
            return this;
        }

        public Builder setPlayerCarIndex(int playerCarIndex) {
            this.playerCarIndex = playerCarIndex;
            return this;
        }

        public Builder setSecondaryPlayerCarIndex(int secondaryPlayerCarIndex) {
            this.secondaryPlayerCarIndex = secondaryPlayerCarIndex;
            return this;
        }

        public PacketHeader build() {
            return new PacketHeader(this);
        }
    }
}

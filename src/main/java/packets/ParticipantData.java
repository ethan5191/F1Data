package packets;

import java.nio.charset.StandardCharsets;

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

public class ParticipantData {

    public ParticipantData(Builder builder) {
        this.aiControlled = builder.aiControlled;
        this.driverId = builder.driverId;
        this.networkId = builder.networkId;
        this.teamId = builder.teamId;
        this.myTeam = builder.myTeam;
        this.raceNumber = builder.raceNumber;
        this.nationality = builder.nationality;
        this.name = builder.name;
        this.yourTelemetry = builder.yourTelemetry;
        this.showOnlineNames = builder.showOnlineNames;
        this.techLevel = builder.techLevel;
        this.platform = builder.platform;

        int length = 0;
        while (length < this.name.length && name[length] != 0) {
            length++;
        }
        this.lastName = new String(this.name, 0, length, StandardCharsets.UTF_8);
    }

    private final int aiControlled;
    private final int driverId;
    private final Integer networkId;
    private final int teamId;
    private final Integer myTeam;
    private final int raceNumber;
    private final int nationality;
    private final byte[] name;
    private final int yourTelemetry;
    private final Integer showOnlineNames;
    private final Integer techLevel;
    private final Integer platform;

    private final String lastName;

    public int getAiControlled() {
        return aiControlled;
    }

    public int getDriverId() {
        return driverId;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public int getTeamId() {
        return teamId;
    }

    public Integer getMyTeam() {
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

    public Integer getShowOnlineNames() {
        return showOnlineNames;
    }

    public Integer getTechLevel() {
        return techLevel;
    }

    public Integer getPlatform() {
        return platform;
    }

    public String getLastName() {
        return lastName;
    }

    public void printName() {
        if (this.aiControlled == 1) {
            System.out.println(this.lastName);
        } else {
            System.out.println(this.lastName + " (Human Player)");
        }
    }

    public static class Builder {
        private int aiControlled;
        private int driverId;
        private Integer networkId;
        private int teamId;
        private Integer myTeam;
        private int raceNumber;
        private int nationality;
        private byte[] name;
        private int yourTelemetry;
        private Integer showOnlineNames;
        private Integer techLevel;
        private Integer platform;

        public Builder setAiControlled(int aiControlled) {
            this.aiControlled = aiControlled;
            return this;
        }

        public Builder setDriverId(int driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder setNetworkId(Integer networkId) {
            this.networkId = networkId;
            return this;
        }

        public Builder setTeamId(int teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder setMyTeam(Integer myTeam) {
            this.myTeam = myTeam;
            return this;
        }

        public Builder setRaceNumber(int raceNumber) {
            this.raceNumber = raceNumber;
            return this;
        }

        public Builder setNationality(int nationality) {
            this.nationality = nationality;
            return this;
        }

        public Builder setName(byte[] name) {
            this.name = name;
            return this;
        }

        public Builder setYourTelemetry(int yourTelemetry) {
            this.yourTelemetry = yourTelemetry;
            return this;
        }

        public Builder setShowOnlineNames(Integer showOnlineNames) {
            this.showOnlineNames = showOnlineNames;
            return this;
        }

        public Builder setTechLevel(Integer techLevel) {
            this.techLevel = techLevel;
            return this;
        }

        public Builder setPlatform(Integer platform) {
            this.platform = platform;
            return this;
        }

        public ParticipantData build() {
            return new ParticipantData(this);
        }
    }
}

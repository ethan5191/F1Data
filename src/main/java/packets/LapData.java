package packets;

/**
 * F1 24 LapData Breakdown (Little Endian)
 * 2021 removed 11 fields related to bestLap info, lastLapTime and currentLapTime changed from float to uint32
 * 2021 added 7 fields related to pitStop info.
 * - F1 2020 Length: 53 bytes
 * - F1 2021/2022 Length: 43 bytes
 * - F1 2023 Length: 55 bytes Removed an uint 8 m_warnings that was after m_penalties and replaced it with 2 params.
 * - F1 2024/2025 Length: 57 bytes
 * This struct is 57 bytes long and contains data about a single car's lap.
 * It is repeated for each car in the PacketLapData packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * /*
 * -------------------------------|-----------------|--------------|----------------|------------------------------------------------------
 * Member Name                     | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------------|-----------------|--------------|----------------|------------------------------------------------------
 * m_header                        | PacketHeader    | 24           | 2020           | Full packet header
 * m_lapData[22]                   | LapData[22]     | 57 * 22 = 1254 | 2020         | Lap data for all cars on track
 * - m_lastLapTimeInMS              | uint32          | 4            | 2021           | Last lap time in milliseconds
 * - m_currentLapTimeInMS           | uint32          | 4            | 2021           | Current lap time in milliseconds
 * - m_sector1TimeMSPart            | uint16          | 2            | 2020           | Sector 1 milliseconds part
 * - m_sector1TimeMinutesPart       | uint8           | 1            | 2023           | Sector 1 minutes part
 * - m_sector2TimeMSPart            | uint16          | 2            | 2020           | Sector 2 milliseconds part
 * - m_sector2TimeMinutesPart       | uint8           | 1            | 2023           | Sector 2 minutes part
 * - m_deltaToCarInFrontMSPart      | uint16          | 2            | 2023           | Delta to car ahead (ms part)
 * - m_deltaToCarInFrontMinutesPart | uint8           | 1            | 2024           | Delta to car ahead (minutes part)
 * - m_deltaToRaceLeaderMSPart      | uint16          | 2            | 2023           | Delta to race leader (ms part)
 * - m_deltaToRaceLeaderMinutesPart | uint8           | 1            | 2024           | Delta to race leader (minutes part)
 * - m_lapDistance                  | float           | 4            | 2020           | Distance around current lap in metres (negative if not crossed line yet)
 * - m_totalDistance                | float           | 4            | 2020           | Total distance in session in metres (negative if not crossed line yet)
 * - m_safetyCarDelta               | float           | 4            | 2020           | Safety car delta in seconds
 * - m_carPosition                  | uint8           | 1            | 2020           | Race position of car
 * - m_currentLapNum                | uint8           | 1            | 2020           | Current lap number
 * - m_pitStatus                    | uint8           | 1            | 2020           | 0 = none, 1 = pitting, 2 = in pit area
 * - m_numPitStops                  | uint8           | 1            | 2021           | Number of pit stops taken
 * - m_sector                       | uint8           | 1            | 2020           | 0 = sector1, 1 = sector2, 2 = sector3
 * - m_currentLapInvalid            | uint8           | 1            | 2020           | 0 = valid, 1 = invalid
 * - m_penalties                    | uint8           | 1            | 2020           | Total time penalties (seconds)
 * - m_totalWarnings                | uint8           | 1            | 2023           | Total number of warnings
 * - m_cornerCuttingWarnings        | uint8           | 1            | 2023           | Total number of corner cutting warnings
 * - m_numUnservedDriveThroughPens  | uint8           | 1            | 2021           | Drive-through penalties left to serve
 * - m_numUnservedStopGoPens        | uint8           | 1            | 2021           | Stop-go penalties left to serve
 * - m_gridPosition                 | uint8           | 1            | 2020           | Grid start position
 * - m_driverStatus                 | uint8           | 1            | 2020           | 0 = in garage, 1 = flying lap, 2 = in lap, 3 = out lap, 4 = on track
 * - m_resultStatus                 | uint8           | 1            | 2020           | 0 = invalid, 1 = inactive, 2 = active, 3 = finished, 4 = DNF, 5 = DSQ, 6 = not classified, 7 = retired
 * - m_pitLaneTimerActive           | uint8           | 1            | 2021           | Pit lane timer active (0 = no, 1 = yes)
 * - m_pitLaneTimeInLaneInMS        | uint16          | 2            | 2021           | Time spent in pit lane (ms)
 * - m_pitStopTimerInMS             | uint16          | 2            | 2021           | Time of actual pit stop (ms)
 * - m_pitStopShouldServePen        | uint8           | 1            | 2021           | Serve penalty at pit stop (0 = no, 1 = yes)
 * - m_speedTrapFastestSpeed        | float           | 4            | 2023           | Fastest speed in speed trap (km/h)
 * - m_speedTrapFastestLap          | uint8           | 1            | 2023           | Lap number of fastest trap speed (255 = not set)
 * m_timeTrialPBCarIdx              | uint8           | 1            | 2022           | PB car index in time trial (255 = invalid)
 * m_timeTrialRivalCarIdx           | uint8           | 1            | 2022           | Rival car index in time trial (255 = invalid)
 */

public class LapData {

    public LapData(Builder builder) {
        this.lastLapTimeMs = builder.lastLapTimeMs;
        this.currentLapTimeMs = builder.currentLapTimeMs;
        this.sector1TimeMsPart = builder.sector1TimeMsPart;
        this.sector1TimeMinutesPart = builder.sector1TimeMinutesPart;
        this.sector2TimeMsPart = builder.sector2TimeMsPart;
        this.sector2TimeMinutesPart = builder.sector2TimeMinutesPart;
        this.deltaCarInFrontMsPart = builder.deltaCarInFrontMsPart;
        this.deltaCarInFrontMinutesPart = builder.deltaCarInFrontMinutesPart;
        this.deltaRaceLeaderMsPart = builder.deltaRaceLeaderMsPart;
        this.deltaRaceLeaderMinutesPart = builder.deltaRaceLeaderMinutesPart;
        this.lapDistance = builder.lapDistance;
        this.totalDistance = builder.totalDistance;
        this.safetyCarDelta = builder.safetyCarDelta;
        this.carPosition = builder.carPosition;
        this.currentLapNum = builder.currentLapNum;
        this.pitStatus = builder.pitStatus;
        this.numPitStops = builder.numPitStops;
        this.sector = builder.sector;
        this.currentLapInvalid = builder.currentLapInvalid;
        this.penalties = builder.penalties;
        this.totalWarnings = builder.totalWarnings;
        this.cornerCuttingWarnings = builder.cornerCuttingWarnings;
        this.numUnservedDriveThroughPens = builder.numUnservedDriveThroughPens;
        this.numUnservedStopGoPens = builder.numUnservedStopGoPens;
        this.gridPosition = builder.gridPosition;
        this.driverStatus = builder.driverStatus;
        this.resultStatus = builder.resultStatus;
        this.pitLaneTimeActive = builder.pitLaneTimeActive;
        this.pitLaneTimerInLaneInMs = builder.pitLaneTimerInLaneInMs;
        this.pitStopTimerInMS = builder.pitStopTimerInMS;
        this.pitStopShouldServePen = builder.pitStopShouldServePen;
        this.speedTrapFastestSpeed = builder.speedTrapFastestSpeed;
        this.speedTrapFastestLap = builder.speedTrapFastestLap;

        //Contains all legacy data that has either had its data type updated or has been removed.
        this.legacyLapData = builder.legacyLapData;
    }

    private final long lastLapTimeMs;
    private final long currentLapTimeMs;
    private final int sector1TimeMsPart;
    private final int sector1TimeMinutesPart;
    private final int sector2TimeMsPart;
    private final int sector2TimeMinutesPart;
    private final int deltaCarInFrontMsPart;
    private final int deltaCarInFrontMinutesPart;
    private final int deltaRaceLeaderMsPart;
    private final int deltaRaceLeaderMinutesPart;
    private final float lapDistance;
    private final float totalDistance;
    private final float safetyCarDelta;
    private final int carPosition;
    private final int currentLapNum;
    private final int pitStatus;
    private final int numPitStops;
    private final int sector;
    private final int currentLapInvalid;
    private final int penalties;
    private final int totalWarnings;
    private final int cornerCuttingWarnings;
    private final int numUnservedDriveThroughPens;
    private final int numUnservedStopGoPens;
    private final int gridPosition;
    private final int driverStatus;
    private final int resultStatus;
    private final int pitLaneTimeActive;
    private final int pitLaneTimerInLaneInMs;
    private final int pitStopTimerInMS;
    private final int pitStopShouldServePen;
    private final float speedTrapFastestSpeed;
    private final int speedTrapFastestLap;

    //Houses params that used to exist and have either been removed or had there datatype updated.
    private final LegacyLapData legacyLapData;

    public long getLastLapTimeMs() {
        return lastLapTimeMs;
    }

    public long getCurrentLapTimeMs() {
        return currentLapTimeMs;
    }

    public int getSector1TimeMsPart() {
        return sector1TimeMsPart;
    }

    public int getSector1TimeMinutesPart() {
        return sector1TimeMinutesPart;
    }

    public int getSector2TimeMsPart() {
        return sector2TimeMsPart;
    }

    public int getSector2TimeMinutesPart() {
        return sector2TimeMinutesPart;
    }

    public int getDeltaCarInFrontMsPart() {
        return deltaCarInFrontMsPart;
    }

    public int getDeltaCarInFrontMinutesPart() {
        return deltaCarInFrontMinutesPart;
    }

    public int getDeltaRaceLeaderMsPart() {
        return deltaRaceLeaderMsPart;
    }

    public int getDeltaRaceLeaderMinutesPart() {
        return deltaRaceLeaderMinutesPart;
    }

    public float getLapDistance() {
        return lapDistance;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public float getSafetyCarDelta() {
        return safetyCarDelta;
    }

    public int getCarPosition() {
        return carPosition;
    }

    public int getCurrentLapNum() {
        return currentLapNum;
    }

    public int getPitStatus() {
        return pitStatus;
    }

    public int getNumPitStops() {
        return numPitStops;
    }

    public int getSector() {
        return sector;
    }

    public int getCurrentLapInvalid() {
        return currentLapInvalid;
    }

    public int getPenalties() {
        return penalties;
    }

    public int getTotalWarnings() {
        return totalWarnings;
    }

    public int getCornerCuttingWarnings() {
        return cornerCuttingWarnings;
    }

    public int getNumUnservedDriveThroughPens() {
        return numUnservedDriveThroughPens;
    }

    public int getNumUnservedStopGoPens() {
        return numUnservedStopGoPens;
    }

    public int getGridPosition() {
        return gridPosition;
    }

    public int getDriverStatus() {
        return driverStatus;
    }

    public int getResultStatus() {
        return resultStatus;
    }

    public int getPitLaneTimeActive() {
        return pitLaneTimeActive;
    }

    public int getPitLaneTimerInLaneInMs() {
        return pitLaneTimerInLaneInMs;
    }

    public int getPitStopTimerInMS() {
        return pitStopTimerInMS;
    }

    public int getPitStopShouldServePen() {
        return pitStopShouldServePen;
    }

    public float getSpeedTrapFastestSpeed() {
        return speedTrapFastestSpeed;
    }

    public int getSpeedTrapFastestLap() {
        return speedTrapFastestLap;
    }

    public LegacyLapData getLegacyLapData() {
        return legacyLapData;
    }

    public static class LegacyLapData {

        public LegacyLapData(float lastLapTime20, float currentLapTime20, float bestLapTime, int bestLapNum, int bestLapSector1InMS,
                             int bestLapSector2InMS, int bestLapSector3InMS, int bestOverallSector1InMS, int bestOverallSector1LapNum,
                             int bestOverallSector2InMS, int bestOverallSector2LapNum, int bestOverallSector3InMS,
                             int bestOverallSector3LapNum, int warnings) {
            this.lastLapTime20 = lastLapTime20;
            this.currentLapTime20 = currentLapTime20;
            this.bestLapTime = bestLapTime;
            this.bestLapNum = bestLapNum;
            this.bestLapSector1InMS = bestLapSector1InMS;
            this.bestLapSector2InMS = bestLapSector2InMS;
            this.bestLapSector3InMS = bestLapSector3InMS;
            this.bestOverallSector1InMS = bestOverallSector1InMS;
            this.bestOverallSector1LapNum = bestOverallSector1LapNum;
            this.bestOverallSector2InMS = bestOverallSector2InMS;
            this.bestOverallSector2LapNum = bestOverallSector2LapNum;
            this.bestOverallSector3InMS = bestOverallSector3InMS;
            this.bestOverallSector3LapNum = bestOverallSector3LapNum;
            this.warnings = warnings;
        }

        //Params that changed datatype in 2021 packet updates.
        private final float lastLapTime20;
        private final float currentLapTime20;
        //Params that where removed in the 2021 packet updates.
        private final float bestLapTime;
        private final int bestLapNum;
        private final int bestLapSector1InMS;
        private final int bestLapSector2InMS;
        private final int bestLapSector3InMS;
        private final int bestOverallSector1InMS;
        private final int bestOverallSector1LapNum;
        private final int bestOverallSector2InMS;
        private final int bestOverallSector2LapNum;
        private final int bestOverallSector3InMS;
        private final int bestOverallSector3LapNum;
        //Replaced in 2023 by the totalWarnings and cornerCuttingWarnings params.
        private final int warnings;

        public float getLastLapTime20() {
            return lastLapTime20;
        }

        public float getCurrentLapTime20() {
            return currentLapTime20;
        }

        public float getBestLapTime() {
            return bestLapTime;
        }

        public int getBestLapNum() {
            return bestLapNum;
        }

        public int getBestLapSector1InMS() {
            return bestLapSector1InMS;
        }

        public int getBestLapSector2InMS() {
            return bestLapSector2InMS;
        }

        public int getBestLapSector3InMS() {
            return bestLapSector3InMS;
        }

        public int getBestOverallSector1InMS() {
            return bestOverallSector1InMS;
        }

        public int getBestOverallSector1LapNum() {
            return bestOverallSector1LapNum;
        }

        public int getBestOverallSector2InMS() {
            return bestOverallSector2InMS;
        }

        public int getBestOverallSector2LapNum() {
            return bestOverallSector2LapNum;
        }

        public int getBestOverallSector3InMS() {
            return bestOverallSector3InMS;
        }

        public int getBestOverallSector3LapNum() {
            return bestOverallSector3LapNum;
        }

        public int getWarnings() {
            return warnings;
        }
    }

    public static class Builder {

        private long lastLapTimeMs;
        private long currentLapTimeMs;
        private int sector1TimeMsPart;
        private int sector1TimeMinutesPart;
        private int sector2TimeMsPart;
        private int sector2TimeMinutesPart;
        private int deltaCarInFrontMsPart;
        private int deltaCarInFrontMinutesPart;
        private int deltaRaceLeaderMsPart;
        private int deltaRaceLeaderMinutesPart;
        private float lapDistance;
        private float totalDistance;
        private float safetyCarDelta;
        private int carPosition;
        private int currentLapNum;
        private int pitStatus;
        private int numPitStops;
        private int sector;
        private int currentLapInvalid;
        private int penalties;
        private int totalWarnings;
        private int cornerCuttingWarnings;
        private int numUnservedDriveThroughPens;
        private int numUnservedStopGoPens;
        private int gridPosition;
        private int driverStatus;
        private int resultStatus;
        private int pitLaneTimeActive;
        private int pitLaneTimerInLaneInMs;
        private int pitStopTimerInMS;
        private int pitStopShouldServePen;
        private float speedTrapFastestSpeed;
        private int speedTrapFastestLap;

        private LegacyLapData legacyLapData;

        public Builder setLastLapTimeMs(long lastLapTimeMs) {
            this.lastLapTimeMs = lastLapTimeMs;
            return this;
        }

        public Builder setCurrentLapTimeMs(long currentLapTimeMs) {
            this.currentLapTimeMs = currentLapTimeMs;
            return this;
        }

        public Builder setSector1TimeMsPart(int sector1TimeMsPart) {
            this.sector1TimeMsPart = sector1TimeMsPart;
            return this;
        }

        public Builder setSector1TimeMinutesPart(int sector1TimeMinutesPart) {
            this.sector1TimeMinutesPart = sector1TimeMinutesPart;
            return this;
        }

        public Builder setSector2TimeMsPart(int sector2TimeMsPart) {
            this.sector2TimeMsPart = sector2TimeMsPart;
            return this;
        }

        public Builder setSector2TimeMinutesPart(int sector2TimeMinutesPart) {
            this.sector2TimeMinutesPart = sector2TimeMinutesPart;
            return this;
        }

        public Builder setDeltaCarInFrontMsPart(int deltaCarInFrontMsPart) {
            this.deltaCarInFrontMsPart = deltaCarInFrontMsPart;
            return this;
        }

        public Builder setDeltaCarInFrontMinutesPart(int deltaCarInFrontMinutesPart) {
            this.deltaCarInFrontMinutesPart = deltaCarInFrontMinutesPart;
            return this;
        }

        public Builder setDeltaRaceLeaderMsPart(int deltaRaceLeaderMsPart) {
            this.deltaRaceLeaderMsPart = deltaRaceLeaderMsPart;
            return this;
        }

        public Builder setDeltaRaceLeaderMinutesPart(int deltaRaceLeaderMinutesPart) {
            this.deltaRaceLeaderMinutesPart = deltaRaceLeaderMinutesPart;
            return this;
        }

        public Builder setLapDistance(float lapDistance) {
            this.lapDistance = lapDistance;
            return this;
        }

        public Builder setTotalDistance(float totalDistance) {
            this.totalDistance = totalDistance;
            return this;
        }

        public Builder setSafetyCarDelta(float safetyCarDelta) {
            this.safetyCarDelta = safetyCarDelta;
            return this;
        }

        public Builder setCarPosition(int carPosition) {
            this.carPosition = carPosition;
            return this;
        }

        public Builder setCurrentLapNum(int currentLapNum) {
            this.currentLapNum = currentLapNum;
            return this;
        }

        public Builder setPitStatus(int pitStatus) {
            this.pitStatus = pitStatus;
            return this;
        }

        public Builder setNumPitStops(int numPitStops) {
            this.numPitStops = numPitStops;
            return this;
        }

        public Builder setSector(int sector) {
            this.sector = sector;
            return this;
        }

        public Builder setCurrentLapInvalid(int currentLapInvalid) {
            this.currentLapInvalid = currentLapInvalid;
            return this;
        }

        public Builder setPenalties(int penalties) {
            this.penalties = penalties;
            return this;
        }

        public Builder setTotalWarnings(int totalWarnings) {
            this.totalWarnings = totalWarnings;
            return this;
        }

        public Builder setCornerCuttingWarnings(int cornerCuttingWarnings) {
            this.cornerCuttingWarnings = cornerCuttingWarnings;
            return this;
        }

        public Builder setNumUnservedDriveThroughPens(int numUnservedDriveThroughPens) {
            this.numUnservedDriveThroughPens = numUnservedDriveThroughPens;
            return this;
        }

        public Builder setNumUnservedStopGoPens(int numUnservedStopGoPens) {
            this.numUnservedStopGoPens = numUnservedStopGoPens;
            return this;
        }

        public Builder setGridPosition(int gridPosition) {
            this.gridPosition = gridPosition;
            return this;
        }

        public Builder setDriverStatus(int driverStatus) {
            this.driverStatus = driverStatus;
            return this;
        }

        public Builder setResultStatus(int resultStatus) {
            this.resultStatus = resultStatus;
            return this;
        }

        public Builder setPitLaneTimeActive(int pitLaneTimeActive) {
            this.pitLaneTimeActive = pitLaneTimeActive;
            return this;
        }

        public Builder setPitLaneTimerInLaneInMs(int pitLaneTimerInLaneInMs) {
            this.pitLaneTimerInLaneInMs = pitLaneTimerInLaneInMs;
            return this;
        }

        public Builder setPitStopTimerInMS(int pitStopTimerInMS) {
            this.pitStopTimerInMS = pitStopTimerInMS;
            return this;
        }

        public Builder setPitStopShouldServePen(int pitStopShouldServePen) {
            this.pitStopShouldServePen = pitStopShouldServePen;
            return this;
        }

        public Builder setSpeedTrapFastestSpeed(float speedTrapFastestSpeed) {
            this.speedTrapFastestSpeed = speedTrapFastestSpeed;
            return this;
        }

        public Builder setSpeedTrapFastestLap(int speedTrapFastestLap) {
            this.speedTrapFastestLap = speedTrapFastestLap;
            return this;
        }

        public Builder setLegacyLapData(LegacyLapData legacyLapData) {
            this.legacyLapData = legacyLapData;
            return this;
        }

        public LapData build() {
            return new LapData(this);
        }
    }
}

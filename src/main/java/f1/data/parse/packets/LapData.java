package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 24 LapData Breakdown (Little Endian)
 * 2021 removed 11 fields related to bestLap info, lastLapTime and currentLapTime changed from float to uint32
 * 2021 added 7 fields related to pitStop info.
 * - F1 2019 Length: 41 bytes
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
 * - m_speedTrapFastestSpeed        | float           | 4            | 2024           | Fastest speed in speed trap (km/h)
 * - m_speedTrapFastestLap          | uint8           | 1            | 2024           | Lap number of fastest trap speed (255 = not set)
 * m_timeTrialPBCarIdx              | uint8           | 1            | 2022           | PB car index in time trial (255 = invalid)
 * m_timeTrialRivalCarIdx           | uint8           | 1            | 2022           | Rival car index in time trial (255 = invalid)
 *
 * @param lastLapTime20 Params that changed datatype in 2021 packet updates.
 * @param bestLapTime   Params that where removed in the 2021 packet updates.
 * @param warnings      Replaced in 2023 by the totalWarnings and cornerCuttingWarnings params.
 * @param sector1Time
 * @param sector2Time
 */

public record LapData(long lastLapTimeMs, long currentLapTimeMs, int sector1TimeMsPart, int sector1TimeMinutesPart,
                      int sector2TimeMsPart, int sector2TimeMinutesPart, int deltaCarInFrontMsPart,
                      int deltaCarInFrontMinutesPart, int deltaRaceLeaderMsPart, int deltaRaceLeaderMinutesPart,
                      float lapDistance, float totalDistance, float safetyCarDelta, int carPosition, int currentLapNum,
                      int pitStatus, int numPitStops, int sector, int currentLapInvalid, int penalties,
                      int totalWarnings, int cornerCuttingWarnings, int numUnservedDriveThroughPens,
                      int numUnservedStopGoPens, int gridPosition, int driverStatus, int resultStatus,
                      int pitLaneTimeActive, int pitLaneTimerInLaneInMs, int pitStopTimerInMS,
                      int pitStopShouldServePen, float speedTrapFastestSpeed, int speedTrapFastestLap,
                      float lastLapTime20, float currentLapTime20, float bestLapTime, int bestLapNum,
                      int bestLapSector1InMS, int bestLapSector2InMS, int bestLapSector3InMS,
                      int bestOverallSector1InMS, int bestOverallSector1LapNum, int bestOverallSector2InMS,
                      int bestOverallSector2LapNum, int bestOverallSector3InMS, int bestOverallSector3LapNum,
                      int warnings, float sector1Time, float sector2Time) {

    record LapData19(float lastLapTime, float currentLapTime, float bestLapTime, float sector1Time, float sector2Time,
                     float lapDistance, float totalDistance, float safetyCarDelta, int carPosition, int currentLapNum,
                     int pitStatus, int sector, int currentLapInvalid, int penalties, int gridPosition,
                     int driverStatus, int resultStatus) {
        public LapData19(ByteBuffer byteBuffer) {
            this(
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record LapData20(float lastLapTime20, float currentLapTime20, int sector1TimeMsPart, int sector2TimeMsPart,
                     float bestLapTime, int bestLapNum, int bestLapSector1InMS, int bestLapSector2InMS,
                     int bestLapSector3InMS, int bestOverallSector1InMS, int bestOverallSector1LapNum,
                     int bestOverallSector2InMS, int bestOverallSector2LapNum, int bestOverallSector3InMS,
                     int bestOverallSector3LapNum, float lapDistance, float totalDistance, float safetyCarDelta,
                     int carPosition, int currentLapNum, int pitStatus, int sector, int currentLapInvalid,
                     int penalties, int gridPosition, int driverStatus, int resultStatus) {
        public LapData20(ByteBuffer byteBuffer) {
            this(
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record LapData21(long lastLapTimeMs, long currentLapTimeMs, int sector1TimeMsPart, int sector2TimeMsPart,
                     float lapDistance, float totalDistance, float safetyCarDelta, int carPosition, int currentLapNum,
                     int pitStatus, int numPitStops, int sector, int currentLapInvalid, int penalties, int warnings,
                     int numUnservedDriveThroughPens, int numUnservedStopGoPens, int gridPosition, int driverStatus,
                     int resultStatus, int pitLaneTimeActive, int pitLaneTimerInLaneInMs, int pitStopTimerInMS,
                     int pitStopShouldServePen) {
        public LapData21(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record LapData23(long lastLapTimeMs, long currentLapTimeMs, int sector1TimeMsPart, int sector1TimeMinutesPart,
                     int sector2TimeMsPart, int sector2TimeMinutesPart, int deltaCarInFrontMsPart,
                     int deltaRaceLeaderMsPart, float lapDistance, float totalDistance, float safetyCarDelta,
                     int carPosition, int currentLapNum, int pitStatus, int numPitStops, int sector,
                     int currentLapInvalid, int penalties, int totalWarnings, int cornerCuttingWarnings,
                     int numUnservedDriveThroughPens, int numUnservedStopGoPens, int gridPosition, int driverStatus,
                     int resultStatus, int pitLaneTimeActive, int pitLaneTimerInLaneInMs, int pitStopTimerInMS,
                     int pitStopShouldServePen) {
        public LapData23(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record LapData24(long lastLapTimeMs, long currentLapTimeMs, int sector1TimeMsPart, int sector1TimeMinutesPart,
                     int sector2TimeMsPart, int sector2TimeMinutesPart, int deltaCarInFrontMsPart,
                     int deltaCarInFrontMinutesPart, int deltaRaceLeaderMsPart, int deltaRaceLeaderMinutesPart,
                     float lapDistance, float totalDistance, float safetyCarDelta, int carPosition,
                     int currentLapNum, int pitStatus, int numPitStops, int sector, int currentLapInvalid,
                     int penalties, int totalWarnings, int cornerCuttingWarnings, int numUnservedDriveThroughPens,
                     int numUnservedStopGoPens, int gridPosition, int driverStatus, int resultStatus,
                     int pitLaneTimeActive, int pitLaneTimerInLaneInMs, int pitStopTimerInMS,
                     int pitStopShouldServePen, float speedTrapFastestSpeed, int speedTrapFastestLap) {
        public LapData24(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask32(byteBuffer.getInt()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }
}

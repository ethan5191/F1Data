package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 LapData Breakdown (Little Endian)
 *
 * This struct is 57 bytes long and contains data about a single car's lap.
 * It is repeated for each car in the PacketLapData packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name                             | Data Type | Size (bytes) | Starting Offset
 * ----------------------------------------|-----------|--------------|-----------------
 * m_lastLapTimeInMS                       | uint32    | 4            | 0
 * m_currentLapTimeInMS                    | uint32    | 4            | 4
 * m_sector1TimeMSPart                     | uint16    | 2            | 8
 * m_sector1TimeMinutesPart                | uint8     | 1            | 10
 * m_sector2TimeMSPart                     | uint16    | 2            | 11
 * m_sector2TimeMinutesPart                | uint8     | 1            | 13
 * m_deltaToCarInFrontMSPart               | uint16    | 2            | 14
 * m_deltaToCarInFrontMinutesPart          | uint8     | 1            | 16
 * m_deltaToRaceLeaderMSPart               | uint16    | 2            | 17
 * m_deltaToRaceLeaderMinutesPart          | uint8     | 1            | 19
 * m_lapDistance                           | float     | 4            | 20
 * m_totalDistance                         | float     | 4            | 24
 * m_safetyCarDelta                        | float     | 4            | 28
 * m_carPosition                           | uint8     | 1            | 32
 * m_currentLapNum                         | uint8     | 1            | 33
 * m_pitStatus                             | uint8     | 1            | 34
 * m_numPitStops                           | uint8     | 1            | 35
 * m_sector                                | uint8     | 1            | 36
 * m_currentLapInvalid                     | uint8     | 1            | 37
 * m_penalties                             | uint8     | 1            | 38
 * m_totalWarnings                         | uint8     | 1            | 39
 * m_cornerCuttingWarnings                 | uint8     | 1            | 40
 * m_numUnservedDriveThroughPens           | uint8     | 1            | 41
 * m_numUnservedStopGoPens                 | uint8     | 1            | 42
 * m_gridPosition                          | uint8     | 1            | 43
 * m_driverStatus                          | uint8     | 1            | 44
 * m_resultStatus                          | uint8     | 1            | 45
 * m_pitLaneTimerActive                    | uint8     | 1            | 46
 * m_pitLaneTimeInLaneInMS                 | uint16    | 2            | 47
 * m_pitStopTimerInMS                      | uint16    | 2            | 49
 * m_pitStopShouldServePen                 | uint8     | 1            | 51
 * m_speedTrapFastestSpeed                 | float     | 4            | 52
 * m_speedTrapFastestLap                   | uint8     | 1            | 56
 */

public class LapData extends PacketData {

    public LapData(ByteBuffer byteBuffer) {
//        printMessage("Lap Data ", byteBuffer.array().length);
        this.lastLapTimeMs = byteBuffer.getInt() & Constants.BIT_MASK_32;
        this.currentLapTimeMs = byteBuffer.getInt() & Constants.BIT_MASK_32;
        this.sector1TimeMsPart = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.sector1TimeMinutesPart = byteBuffer.get() & Constants.BIT_MASK_8;
        this.sector2TimeMsPart = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.sector2TimeMinutesPart = byteBuffer.get() & Constants.BIT_MASK_8;
        this.deltaCarInFrontMsPart = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.deltaCarInFrontMinutesPart = byteBuffer.get() & Constants.BIT_MASK_8;
        this.deltaRaceLeaderMsPart = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.deltaRaceLeaderMinutesPart = byteBuffer.get() & Constants.BIT_MASK_8;
        this.lapDistance = byteBuffer.getFloat();
        this.totalDistance = byteBuffer.getFloat();
        this.safetyCarDelta = byteBuffer.getFloat();
        this.carPosition = byteBuffer.get() & Constants.BIT_MASK_8;
        this.currentLapNum = byteBuffer.get() & Constants.BIT_MASK_8;
        this.pitStatus = byteBuffer.get() & Constants.BIT_MASK_8;
        this.numPitStops = byteBuffer.get() & Constants.BIT_MASK_8;
        this.sector = byteBuffer.get() & Constants.BIT_MASK_8;
        this.currentLapInvalid = byteBuffer.get() & Constants.BIT_MASK_8;
        this.penalties = byteBuffer.get() & Constants.BIT_MASK_8;
        this.totalWarnings = byteBuffer.get() & Constants.BIT_MASK_8;
        this.cornerCuttingWarnings = byteBuffer.get() & Constants.BIT_MASK_8;
        this.numUnservedDriveThroughPens = byteBuffer.get() & Constants.BIT_MASK_8;
        this.numUnservedStopGoPens = byteBuffer.get() & Constants.BIT_MASK_8;
        this.gridPosition = byteBuffer.get() & Constants.BIT_MASK_8;
        this.driverStatus = byteBuffer.get() & Constants.BIT_MASK_8;
        this.resultStatus = byteBuffer.get() & Constants.BIT_MASK_8;
        this.pitLaneTimeActive = byteBuffer.get() & Constants.BIT_MASK_8;
        this.pitLaneTimerInLaneInMs = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.pitStopTimerInMS = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.pitStopShouldServePen = byteBuffer.get() & Constants.BIT_MASK_8;
        this.speedTrapFastestSpeed = byteBuffer.getFloat();
        this.speedTrapFastestLap = byteBuffer.get() & Constants.BIT_MASK_8;
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
}

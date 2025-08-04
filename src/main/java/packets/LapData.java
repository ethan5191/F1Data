package packets;

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

public class LapData extends Data {

    public LapData(ByteBuffer byteBuffer) {
        printMessage("Lap Data ", byteBuffer.array().length);
        this.lastLapTimeMs = byteBuffer.getInt();
        this.currentLapTimeMs = byteBuffer.getInt();
        this.sector1TimeMsPart = byteBuffer.getShort();
        this.sector1TimeMinutesPart = byteBuffer.get();
        this.sector2TimeMsPart = byteBuffer.getShort();
        this.sector2TimeMinutesPart = byteBuffer.get();
        this.deltaCarInFrontMsPart = byteBuffer.getShort();
        this.deltaCarInFrontMinutesPart = byteBuffer.get();
        this.deltaRaceLeaderMsPart = byteBuffer.getShort();
        this.deltaRaceLeaderMinutesPart = byteBuffer.get();
        this.lapDistance = byteBuffer.getFloat();
        this.totalDistance = byteBuffer.getFloat();
        this.safetyCarDelta = byteBuffer.getFloat();
        this.carPosition = byteBuffer.get();
        this.currentLapNum = byteBuffer.get();
        this.pitStatus = byteBuffer.get();
        this.numPitStops = byteBuffer.get();
        this.sector = byteBuffer.get();
        this.currentLapInvalid = byteBuffer.get();
        this.penalties = byteBuffer.get();
        this.totalWarnings = byteBuffer.get();
        this.cornerCuttingWarnings = byteBuffer.get();
        this.numUnservedDriveThroughPens = byteBuffer.get();
        this.numUnservedStopGoPens = byteBuffer.get();
        this.gridPosition = byteBuffer.get();
        this.driverStatus = byteBuffer.get();
        this.resultStatus = byteBuffer.get();
        this.pitLaneTimeActive = byteBuffer.get();
        this.pitLaneTimerInLaneInMs = byteBuffer.getShort();
        this.pitStopTimerInMS = byteBuffer.getShort();
        this.pitStopShouldServePen = byteBuffer.get();
        this.speedTrapFastestSpeed = byteBuffer.getFloat();
        this.speedTrapFastestLap = byteBuffer.get();
    }

    private final int lastLapTimeMs;
    private final int currentLapTimeMs;
    private final short sector1TimeMsPart;
    private final byte sector1TimeMinutesPart;
    private final short sector2TimeMsPart;
    private final byte sector2TimeMinutesPart;
    private final short deltaCarInFrontMsPart;
    private final byte deltaCarInFrontMinutesPart;
    private final short deltaRaceLeaderMsPart;
    private final byte deltaRaceLeaderMinutesPart;
    private final float lapDistance;
    private final float totalDistance;
    private final float safetyCarDelta;
    private final byte carPosition;
    private final byte currentLapNum;
    private final byte pitStatus;
    private final byte numPitStops;
    private final byte sector;
    private final byte currentLapInvalid;
    private final byte penalties;
    private final byte totalWarnings;
    private final byte cornerCuttingWarnings;
    private final byte numUnservedDriveThroughPens;
    private final byte numUnservedStopGoPens;
    private final byte gridPosition;
    private final byte driverStatus;
    private final byte resultStatus;
    private final byte pitLaneTimeActive;
    private final short pitLaneTimerInLaneInMs;
    private final short pitStopTimerInMS;
    private final byte pitStopShouldServePen;
    private final float speedTrapFastestSpeed;
    private final byte speedTrapFastestLap;

    public int getLastLapTimeMs() {
        return lastLapTimeMs;
    }

    public int getCurrentLapTimeMs() {
        return currentLapTimeMs;
    }

    public short getSector1TimeMsPart() {
        return sector1TimeMsPart;
    }

    public byte getSector1TimeMinutesPart() {
        return sector1TimeMinutesPart;
    }

    public short getSector2TimeMsPart() {
        return sector2TimeMsPart;
    }

    public byte getSector2TimeMinutesPart() {
        return sector2TimeMinutesPart;
    }

    public short getDeltaCarInFrontMsPart() {
        return deltaCarInFrontMsPart;
    }

    public byte getDeltaCarInFrontMinutesPart() {
        return deltaCarInFrontMinutesPart;
    }

    public short getDeltaRaceLeaderMsPart() {
        return deltaRaceLeaderMsPart;
    }

    public byte getDeltaRaceLeaderMinutesPart() {
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

    public byte getCarPosition() {
        return carPosition;
    }

    public byte getCurrentLapNum() {
        return currentLapNum;
    }

    public byte getPitStatus() {
        return pitStatus;
    }

    public byte getNumPitStops() {
        return numPitStops;
    }

    public byte getSector() {
        return sector;
    }

    public byte getCurrentLapInvalid() {
        return currentLapInvalid;
    }

    public byte getPenalties() {
        return penalties;
    }

    public byte getTotalWarnings() {
        return totalWarnings;
    }

    public byte getCornerCuttingWarnings() {
        return cornerCuttingWarnings;
    }

    public byte getNumUnservedDriveThroughPens() {
        return numUnservedDriveThroughPens;
    }

    public byte getNumUnservedStopGoPens() {
        return numUnservedStopGoPens;
    }

    public byte getGridPosition() {
        return gridPosition;
    }

    public byte getDriverStatus() {
        return driverStatus;
    }

    public byte getResultStatus() {
        return resultStatus;
    }

    public byte getPitLaneTimeActive() {
        return pitLaneTimeActive;
    }

    public short getPitLaneTimerInLaneInMs() {
        return pitLaneTimerInLaneInMs;
    }

    public short getPitStopTimerInMS() {
        return pitStopTimerInMS;
    }

    public byte getPitStopShouldServePen() {
        return pitStopShouldServePen;
    }

    public float getSpeedTrapFastestSpeed() {
        return speedTrapFastestSpeed;
    }

    public byte getSpeedTrapFastestLap() {
        return speedTrapFastestLap;
    }
}

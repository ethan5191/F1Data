package individualLap;

import packets.LapData;

//Used to represent an individual laps data for an individual car. Idea is this will be populated at the end of the lap.
public class IndividualLapInfo {

    //ld is the current lap, which should be a newly started lap.
    //prevLap is the last LapData from the telemetry object, which has the sector 1 and 2 times in it.
    public IndividualLapInfo(LapData ld, LapData prevLap) {
        this.lapNum = prevLap.getCurrentLapNum();
        this.lapTimeInMs = ld.getLastLapTimeMs();
        int sector1MinPart = prevLap.getSector1TimeMinutesPart() * 60;
        this.sector1InMs = prevLap.getSector1TimeMsPart() + sector1MinPart;
        int sector2MinPart = prevLap.getSector2TimeMinutesPart() * 60;
        this.sector2InMs = prevLap.getSector2TimeMsPart() + sector2MinPart;
        this.sector3InMs = this.lapTimeInMs - (this.sector1InMs + this.sector2InMs);
    }

    //From LapData
    private final int lapNum;
    private final long lapTimeInMs;
    private final long sector1InMs;
    private final long sector2InMs;
    //Calculated from lapTimeMs - (sector2InMs + sector1InMs)
    private final long sector3InMs;

    //From SpeedTrap event
    private float speedTrap;

    private CarTelemetryInfo carTelemetryInfo;

    private CarStatusInfo carStatusInfo;

    private CarDamageInfo carDamageInfo;

    public int getLapNum() {
        return lapNum;
    }

    public long getLapTimeInMs() {
        return lapTimeInMs;
    }

    public long getSector1InMs() {
        return sector1InMs;
    }

    public long getSector2InMs() {
        return sector2InMs;
    }

    public long getSector3InMs() {
        return sector3InMs;
    }
}

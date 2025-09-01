package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class LapDataFactory {

    public static LapData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2019:
                LapData.LapData19 l19 = new LapData.LapData19(byteBuffer);
                yield new LapData(0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, l19.lapDistance(),
                        l19.totalDistance(), l19.safetyCarDelta(), l19.carPosition(), l19.currentLapNum(), l19.pitStatus(),
                        0, l19.sector(), l19.currentLapInvalid(), l19.penalties(), 0, 0, 0,
                        0, l19.gridPosition(), l19.driverStatus(), l19.resultStatus(), 0,
                        0, 0, 0, 0, 0,
                        l19.lastLapTime(), l19.currentLapTime(), l19.bestLapTime(), 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0, 0, l19.sector1Time(), l19.sector2Time());
            case Constants.YEAR_2020:
                LapData.LapData20 l20 = new LapData.LapData20(byteBuffer);
                yield new LapData(0, 0, l20.sector1TimeMsPart(), 0, l20.sector2TimeMsPart(), 0,
                        0, 0, 0, 0, l20.lapDistance(),
                        l20.totalDistance(), l20.safetyCarDelta(), l20.carPosition(), l20.currentLapNum(), l20.pitStatus(),
                        0, l20.sector(), l20.currentLapInvalid(), l20.penalties(), 0, 0, 0,
                        0, l20.gridPosition(), l20.driverStatus(), l20.resultStatus(), 0,
                        0, 0, 0, 0, 0,
                        l20.lastLapTime20(), l20.currentLapTime20(), l20.bestLapTime(), l20.bestLapNum(), l20.bestLapSector1InMS(),
                        l20.bestLapSector2InMS(), l20.bestLapSector3InMS(), l20.bestOverallSector1InMS(), l20.bestOverallSector1LapNum(),
                        l20.bestOverallSector2InMS(), l20.bestOverallSector2LapNum(), l20.bestOverallSector3InMS(), l20.bestOverallSector3LapNum(), 0, 0, 0);
            case Constants.YEAR_2021, Constants.YEAR_2022:
                LapData.LapData21 l21 = new LapData.LapData21(byteBuffer);
                yield new LapData(l21.lastLapTimeMs(), l21.currentLapTimeMs(), l21.sector1TimeMsPart(), 0, l21.sector2TimeMsPart(), 0,
                        0, 0, 0, 0, l21.lapDistance(),
                        l21.totalDistance(), l21.safetyCarDelta(), l21.carPosition(), l21.currentLapNum(), l21.pitStatus(),
                        l21.numPitStops(), l21.sector(), l21.currentLapInvalid(), l21.penalties(), 0, 0, l21.numUnservedDriveThroughPens(),
                        l21.numUnservedStopGoPens(), l21.gridPosition(), l21.driverStatus(), l21.resultStatus(), l21.pitLaneTimeActive(),
                        l21.pitLaneTimerInLaneInMs(), l21.pitStopTimerInMS(), l21.pitStopShouldServePen(), 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, l21.warnings(), 0, 0);
            case Constants.YEAR_2023:
                LapData.LapData23 l23 = new LapData.LapData23(byteBuffer);
                yield new LapData(l23.lastLapTimeMs(), l23.currentLapTimeMs(), l23.sector1TimeMsPart(), l23.sector1TimeMinutesPart(), l23.sector2TimeMsPart(), l23.sector2TimeMinutesPart(),
                        l23.deltaCarInFrontMsPart(), 0, l23.deltaRaceLeaderMsPart(), 0, l23.lapDistance(),
                        l23.totalDistance(), l23.safetyCarDelta(), l23.carPosition(), l23.currentLapNum(), l23.pitStatus(),
                        l23.numPitStops(), l23.sector(), l23.currentLapInvalid(), l23.penalties(), l23.totalWarnings(), l23.cornerCuttingWarnings(), l23.numUnservedDriveThroughPens(),
                        l23.numUnservedStopGoPens(), l23.gridPosition(), l23.driverStatus(), l23.resultStatus(), l23.pitLaneTimeActive(),
                        l23.pitLaneTimerInLaneInMs(), l23.pitStopTimerInMS(), l23.pitStopShouldServePen(), 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            case Constants.YEAR_2024, Constants.YEAR_2025:
                LapData.LapData24 l24 = new LapData.LapData24(byteBuffer);
                yield new LapData(l24.lastLapTimeMs(), l24.currentLapTimeMs(), l24.sector1TimeMsPart(), l24.sector1TimeMinutesPart(), l24.sector2TimeMsPart(), l24.sector2TimeMinutesPart(),
                        l24.deltaCarInFrontMsPart(), l24.deltaCarInFrontMinutesPart(), l24.deltaRaceLeaderMsPart(), l24.deltaRaceLeaderMinutesPart(), l24.lapDistance(),
                        l24.totalDistance(), l24.safetyCarDelta(), l24.carPosition(), l24.currentLapNum(), l24.pitStatus(),
                        l24.numPitStops(), l24.sector(), l24.currentLapInvalid(), l24.penalties(), l24.totalWarnings(), l24.cornerCuttingWarnings(), l24.numUnservedDriveThroughPens(),
                        l24.numUnservedStopGoPens(), l24.gridPosition(), l24.driverStatus(), l24.resultStatus(), l24.pitLaneTimeActive(),
                        l24.pitLaneTimerInLaneInMs(), l24.pitStopTimerInMS(), l24.pitStopShouldServePen(), l24.speedTrapFastestSpeed(), l24.speedTrapFastestLap(),
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

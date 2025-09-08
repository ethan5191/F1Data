package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class FinalClassificationDataFactory {

    public static FinalClassificationData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2020:
                FinalClassificationData.FinalClassificationData20 fcd20 = new FinalClassificationData.FinalClassificationData20(byteBuffer);
                yield new FinalClassificationData(fcd20.position(), fcd20.numLaps(), fcd20.gridPosition(), fcd20.points(), fcd20.numPitsStops(),
                        fcd20.resultStatus(), fcd20.bestLapTime20(), fcd20.totalRaceTime(), fcd20.penaltiesTime(), fcd20.numPenalties(),
                        fcd20.numTyreStints(), fcd20.tyreStintsActual(), fcd20.tyreStintsVisual(), 0, new int[8], 0);
            case Constants.YEAR_2021:
                FinalClassificationData.FinalClassificationData21 fcd21 = new FinalClassificationData.FinalClassificationData21(byteBuffer);
                yield new FinalClassificationData(fcd21.position(), fcd21.numLaps(), fcd21.gridPosition(), fcd21.points(), fcd21.numPitsStops(),
                        fcd21.resultStatus(), 0, fcd21.totalRaceTime(), fcd21.penaltiesTime(), fcd21.numPenalties(),
                        fcd21.numTyreStints(), fcd21.tyreStintsActual(), fcd21.tyreStintsVisual(), fcd21.bestLapTime(), new int[8], 0);
            case Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024:
                FinalClassificationData.FinalClassificationData22 fcd22 = new FinalClassificationData.FinalClassificationData22(byteBuffer);
                yield new FinalClassificationData(fcd22.position(), fcd22.numLaps(), fcd22.gridPosition(), fcd22.points(), fcd22.numPitsStops(),
                        fcd22.resultStatus(), 0, fcd22.totalRaceTime(), fcd22.penaltiesTime(), fcd22.numPenalties(),
                        fcd22.numTyreStints(), fcd22.tyreStintsActual(), fcd22.tyreStintsVisual(), fcd22.bestLapTime(), fcd22.tyreStintsEndLaps(), 0);
            case Constants.YEAR_2025:
                FinalClassificationData.FinalClassificationData25 fcd25 = new FinalClassificationData.FinalClassificationData25(byteBuffer);
                yield new FinalClassificationData(fcd25.position(), fcd25.numLaps(), fcd25.gridPosition(), fcd25.points(), fcd25.numPitsStops(),
                        fcd25.resultStatus(), 0, fcd25.totalRaceTime(), fcd25.penaltiesTime(), fcd25.numPenalties(),
                        fcd25.numTyreStints(), fcd25.tyreStintsActual(), fcd25.tyreStintsVisual(), fcd25.bestLapTime(), fcd25.tyreStintsEndLaps(), fcd25.resultReason());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

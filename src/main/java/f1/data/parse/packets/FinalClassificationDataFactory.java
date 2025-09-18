package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class FinalClassificationDataFactory implements DataFactory<FinalClassificationData>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public FinalClassificationDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public FinalClassificationData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2020 -> buildData(new FinalClassificationData.FinalClassificationData20(byteBuffer));
            case F1_2021 -> buildData(new FinalClassificationData.FinalClassificationData21(byteBuffer));
            case F1_2022, F1_2023, F1_2024 ->
                    buildData(new FinalClassificationData.FinalClassificationData22(byteBuffer));
            case F1_2025 -> buildData(new FinalClassificationData.FinalClassificationData25(byteBuffer));
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    private FinalClassificationData buildData(FinalClassificationData.FinalClassificationData20 fcd20) {
        return new FinalClassificationData(fcd20.position(), fcd20.numLaps(), fcd20.gridPosition(), fcd20.points(), fcd20.numPitsStops(),
                fcd20.resultStatus(), fcd20.bestLapTime20(), fcd20.totalRaceTime(), fcd20.penaltiesTime(), fcd20.numPenalties(),
                fcd20.numTyreStints(), fcd20.tyreStintsActual(), fcd20.tyreStintsVisual(), 0, new int[8], 0);
    }

    private FinalClassificationData buildData(FinalClassificationData.FinalClassificationData21 fcd21) {
        return new FinalClassificationData(fcd21.position(), fcd21.numLaps(), fcd21.gridPosition(), fcd21.points(), fcd21.numPitsStops(),
                fcd21.resultStatus(), 0, fcd21.totalRaceTime(), fcd21.penaltiesTime(), fcd21.numPenalties(),
                fcd21.numTyreStints(), fcd21.tyreStintsActual(), fcd21.tyreStintsVisual(), fcd21.bestLapTime(), new int[8], 0);
    }

    private FinalClassificationData buildData(FinalClassificationData.FinalClassificationData22 fcd22) {
        return new FinalClassificationData(fcd22.position(), fcd22.numLaps(), fcd22.gridPosition(), fcd22.points(), fcd22.numPitsStops(),
                fcd22.resultStatus(), 0, fcd22.totalRaceTime(), fcd22.penaltiesTime(), fcd22.numPenalties(),
                fcd22.numTyreStints(), fcd22.tyreStintsActual(), fcd22.tyreStintsVisual(), fcd22.bestLapTime(), fcd22.tyreStintsEndLaps(), 0);
    }

    private FinalClassificationData buildData(FinalClassificationData.FinalClassificationData25 fcd25) {
        return new FinalClassificationData(fcd25.position(), fcd25.numLaps(), fcd25.gridPosition(), fcd25.points(), fcd25.numPitsStops(),
                fcd25.resultStatus(), 0, fcd25.totalRaceTime(), fcd25.penaltiesTime(), fcd25.numPenalties(),
                fcd25.numTyreStints(), fcd25.tyreStintsActual(), fcd25.tyreStintsVisual(), fcd25.bestLapTime(), fcd25.tyreStintsEndLaps(), fcd25.resultReason());
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2020.getYear();
    }
}

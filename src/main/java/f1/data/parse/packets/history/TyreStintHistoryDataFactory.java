package f1.data.parse.packets.history;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;
import f1.data.parse.packets.FirstYearProvided;

import java.nio.ByteBuffer;

public class TyreStintHistoryDataFactory implements DataFactory<TyreStintHistoryData[]>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public static final int TYRE_STINT_HISTORY_SIZE = 8;

    public TyreStintHistoryDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public TyreStintHistoryData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2021, F1_2022, F1_2023, F1_2024, F1_2025 -> buildData(byteBuffer);
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    private TyreStintHistoryData[] buildData(ByteBuffer byteBuffer) {
        TyreStintHistoryData[] results = new TyreStintHistoryData[TYRE_STINT_HISTORY_SIZE];
        for (int i = 0; i < TYRE_STINT_HISTORY_SIZE; i++) {
            results[i] = new TyreStintHistoryData(byteBuffer);
        }
        return results;
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2021.getYear();
    }
}

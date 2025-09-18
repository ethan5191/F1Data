package f1.data.parse.packets.history;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;
import f1.data.parse.packets.FirstYearProvided;

import java.nio.ByteBuffer;

public class LapHistoryDataFactory implements DataFactory<LapHistoryData[]>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public static final int LAP_HISTORY_SIZE = 100;

    public LapHistoryDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public LapHistoryData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2021, F1_2022 -> buildData21(byteBuffer);
            case F1_2023, F1_2024, F1_2025 -> buildData23(byteBuffer);
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    private LapHistoryData[] buildData21(ByteBuffer byteBuffer) {
        LapHistoryData[] results = new LapHistoryData[LAP_HISTORY_SIZE];
        for (int i = 0; i < LAP_HISTORY_SIZE; i++) {
            LapHistoryData.LapHistoryData21 lhd21 = new LapHistoryData.LapHistoryData21(byteBuffer);
            results[i] = new LapHistoryData(lhd21.lapTimeInMS(), lhd21.sector1TimeInMS(), lhd21.sector2TimeInMS(), lhd21.sector3TimeInMS(), lhd21.lapValidBitFlags(), 0, 0, 0);
        }
        return results;
    }

    private LapHistoryData[] buildData23(ByteBuffer byteBuffer) {
        LapHistoryData[] results = new LapHistoryData[LAP_HISTORY_SIZE];
        for (int i = 0; i < LAP_HISTORY_SIZE; i++) {
            LapHistoryData.LapHistoryData23 lhd23 = new LapHistoryData.LapHistoryData23(byteBuffer);
            results[i] = new LapHistoryData(lhd23.lapTimeInMS(), lhd23.sector1TimeInMS(), lhd23.sector2TimeInMS(), lhd23.sector3TimeInMS(), lhd23.lapValidBitFlags(), lhd23.sector1TimeMinutesPart(), lhd23.sector2TimeMinutesPart(), lhd23.sector3TimeMinutesPart());
        }
        return results;
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2021.getYear();
    }
}

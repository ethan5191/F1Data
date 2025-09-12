package f1.data.parse.packets.history;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;

import java.nio.ByteBuffer;

public class TyreStintHistoryDataFactory implements DataFactory<TyreStintHistoryData[]> {

    private final SupportedYearsEnum packetFormat;

    public static final int TYRE_STINT_HISTORY_SIZE = 8;

    public TyreStintHistoryDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public TyreStintHistoryData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2021, F1_2022, F1_2023, F1_2024, F1_2025 -> buildData(byteBuffer);
            default ->
                    throw new IllegalStateException("Games Packet Format did not match an accepted format (2021 - 2025)");
        };
    }

    private TyreStintHistoryData[] buildData(ByteBuffer byteBuffer) {
        TyreStintHistoryData[] results = new TyreStintHistoryData[TYRE_STINT_HISTORY_SIZE];
        for (int i = 0; i < TYRE_STINT_HISTORY_SIZE; i++) {
            results[i] = new TyreStintHistoryData(byteBuffer);
        }
        return results;
    }
}

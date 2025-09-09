package f1.data.parse.packets.history;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class LapHistoryDataFactory {

    public static LapHistoryData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2021, Constants.YEAR_2022:
                LapHistoryData.LapHistoryData21 lhd21 = new LapHistoryData.LapHistoryData21(byteBuffer);
                yield new LapHistoryData(lhd21.lapTimeInMS(), lhd21.sector1TimeInMS(), lhd21.sector2TimeInMS(), lhd21.sector3TimeInMS(), lhd21.lapValidBitFlags(), 0, 0, 0);
            case Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025:
                LapHistoryData.LapHistoryData23 lhd23 = new LapHistoryData.LapHistoryData23(byteBuffer);
                yield new LapHistoryData(lhd23.lapTimeInMS(), lhd23.sector1TimeInMS(), lhd23.sector2TimeInMS(), lhd23.sector3TimeInMS(), lhd23.lapValidBitFlags(), lhd23.sector1TimeMinutesPart(), lhd23.sector2TimeMinutesPart(), lhd23.sector3TimeMinutesPart());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2021 - 2025)");
        };
    }
}

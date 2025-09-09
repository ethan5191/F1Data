package f1.data.parse.packets.history;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class SessionHistoryDataFactory {

    public static SessionHistoryData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025:
                SessionHistoryData.SessionHistoryData21 shd21 = new SessionHistoryData.SessionHistoryData21(packetFormat, byteBuffer);
                yield new SessionHistoryData(shd21.carIndex(), shd21.numLaps(), shd21.numTyreStints(), shd21.bestLapTimeLapNum(),
                        shd21.bestSector1LapNum(), shd21.bestSector2LapNum(), shd21.bestSector3LapNum(), shd21.lapHistoryData(), shd21.tyreStintHistoryData());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2021 - 2025)");
        };
    }
}

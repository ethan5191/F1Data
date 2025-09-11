package f1.data.parse.packets.history;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;

import java.nio.ByteBuffer;

public class SessionHistoryDataFactory implements DataFactory<SessionHistoryData> {

    private final SupportedYearsEnum packetFormat;

    public SessionHistoryDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public SessionHistoryData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2021, F1_2022, F1_2023, F1_2024, F1_2025 ->
                    buildData(new SessionHistoryData.SessionHistoryData21(this.packetFormat.getYear(), byteBuffer));
            default ->
                    throw new IllegalStateException("Games Packet Format did not match an accepted format (2021 - 2025)");
        };
    }

    private SessionHistoryData buildData(SessionHistoryData.SessionHistoryData21 shd21) {
        return new SessionHistoryData(shd21.carIndex(), shd21.numLaps(), shd21.numTyreStints(), shd21.bestLapTimeLapNum(),
                shd21.bestSector1LapNum(), shd21.bestSector2LapNum(), shd21.bestSector3LapNum(), shd21.lapHistoryData(), shd21.tyreStintHistoryData());
    }
}

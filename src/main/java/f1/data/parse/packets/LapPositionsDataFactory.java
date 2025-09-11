package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class LapPositionsDataFactory implements DataFactory<LapPositionsData> {

    private final SupportedYearsEnum packetFormat;

    public LapPositionsDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public LapPositionsData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2025 -> new LapPositionsData(this.packetFormat.getYear(), byteBuffer);
            default -> throw new IllegalStateException("Games Packet Format did not match an accepted format (2025)");
        };
    }
}

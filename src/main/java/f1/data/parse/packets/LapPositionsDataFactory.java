package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class LapPositionsDataFactory implements DataFactory<LapPositionsData>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public LapPositionsDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public LapPositionsData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2025 -> new LapPositionsData(this.packetFormat, byteBuffer);
            default -> throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2025.getYear();
    }
}

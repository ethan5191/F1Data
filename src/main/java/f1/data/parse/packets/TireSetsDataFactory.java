package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class TireSetsDataFactory implements DataFactory<TireSetsData[]>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public TireSetsDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public TireSetsData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2023, F1_2024, F1_2025 -> buildData(byteBuffer);
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    private TireSetsData[] buildData(ByteBuffer byteBuffer) {
        TireSetsData[] results = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];
        for (int i = 0; i < Constants.TIRE_SETS_PACKET_COUNT; i++) {
            results[i] = new TireSetsData(byteBuffer);
        }
        return results;
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2023.getYear();
    }
}

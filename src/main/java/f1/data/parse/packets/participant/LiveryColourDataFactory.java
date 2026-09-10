package f1.data.parse.packets.participant;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;
import f1.data.parse.packets.FirstYearProvided;

import java.nio.ByteBuffer;

public class LiveryColourDataFactory implements DataFactory<LiveryColourData[]>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public LiveryColourDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public LiveryColourData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2025 -> buildData(byteBuffer);
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    public LiveryColourData[] buildData(ByteBuffer byteBuffer) {
        LiveryColourData[] results = new LiveryColourData[ParticipantData.LIVERY_COLOUR_DATA_25_SIZE];
        for (int i = 0; i < ParticipantData.LIVERY_COLOUR_DATA_25_SIZE; i++) {
            results[i] = new LiveryColourData(byteBuffer);
        }
        return results;
    }

    public int getFirstYear() {
        return SupportedYearsEnum.F1_2025.getYear();
    }
}

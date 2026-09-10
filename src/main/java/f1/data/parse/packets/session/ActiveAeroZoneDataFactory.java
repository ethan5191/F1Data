package f1.data.parse.packets.session;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;
import f1.data.parse.packets.FirstYearProvided;

import java.nio.ByteBuffer;

public class ActiveAeroZoneDataFactory implements DataFactory<ActiveAeroZoneData[]>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public ActiveAeroZoneDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public ActiveAeroZoneData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2025 -> buildData26(byteBuffer);
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    public ActiveAeroZoneData[] buildData26(ByteBuffer byteBuffer) {
        ActiveAeroZoneData[] results = new ActiveAeroZoneData[SessionData.ACTIVE_AERO_ZONE_SIZE];
        for (int i = 0; i < results.length; i++) {
            results[i] = new ActiveAeroZoneData(byteBuffer);
        }
        return results;
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2025.getYear();
    }
}

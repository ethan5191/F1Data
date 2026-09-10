package f1.data.parse.packets.session;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;
import f1.data.parse.packets.FirstYearProvided;

import java.nio.ByteBuffer;

public class DrsZoneDataFactory implements DataFactory<DrsZoneData[]>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public DrsZoneDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public DrsZoneData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2025 -> buildData26(byteBuffer);
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    public DrsZoneData[] buildData26(ByteBuffer byteBuffer) {
        DrsZoneData[] results = new DrsZoneData[SessionData.DRS_ZONE_SIZE];
        for (int i = 0; i < results.length; i++) {
            results[i] = new DrsZoneData(byteBuffer);
        }
        return results;
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2025.getYear();
    }
}

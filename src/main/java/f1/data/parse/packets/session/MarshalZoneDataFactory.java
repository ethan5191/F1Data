package f1.data.parse.packets.session;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;

import java.nio.ByteBuffer;

public class MarshalZoneDataFactory implements DataFactory<MarshalZoneData[]> {

    private final SupportedYearsEnum packetFormat;

    public static final int MARSHAL_ZONE_SIZE = 21;

    public MarshalZoneDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public MarshalZoneData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2019, F1_2020, F1_2021, F1_2022, F1_2023, F1_2024, F1_2025 -> buildData(byteBuffer);
        };
    }

    private MarshalZoneData[] buildData(ByteBuffer byteBuffer) {
        MarshalZoneData[] result = new MarshalZoneData[MARSHAL_ZONE_SIZE];
        for (int i = 0; i < result.length; i++) {
            result[i] = new MarshalZoneData(byteBuffer);
        }
        return result;
    }
}

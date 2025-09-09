package f1.data.parse.packets.session;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class MarshalZoneDataFactory {

    private static final int MARSHAL_ZONE_SIZE = 21;

    public static MarshalZoneData[] build(int packetFormat, ByteBuffer byteBuffer) {
        MarshalZoneData[] result = new MarshalZoneData[MARSHAL_ZONE_SIZE];
        return switch (packetFormat) {
            case Constants.YEAR_2019, Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022,
                 Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025:
                for (int i = 0; i < result.length; i++) {
                    result[i] = new MarshalZoneData(byteBuffer);
                }
                yield result;
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2019 - 2025)");
        };
    }
}

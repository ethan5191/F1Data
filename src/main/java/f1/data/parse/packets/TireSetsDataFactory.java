package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class TireSetsDataFactory {

    public static TireSetsData[] build(int packetFormat, ByteBuffer byteBuffer) {
        TireSetsData[] tireSetsData = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];
        switch (packetFormat) {
            case Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025:
                for (int i = 0; i < Constants.TIRE_SETS_PACKET_COUNT; i++) {
                    tireSetsData[i] = new TireSetsData(byteBuffer);
                }
                break;
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2023 - 2025)");
        }
        return tireSetsData;
    }
}

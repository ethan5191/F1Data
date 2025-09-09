package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class LapPositionsDataFactory {

    public static LapPositionsData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2025:
                yield new LapPositionsData(packetFormat, byteBuffer);
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2025)");
        };
    }
}

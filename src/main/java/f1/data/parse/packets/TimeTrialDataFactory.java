package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class TimeTrialDataFactory {

    public static TimeTrialData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2024, Constants.YEAR_2025:
                yield new TimeTrialData(byteBuffer);
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2024 - 2025)");
        };
    }
}

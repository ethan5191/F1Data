package packets.parsers;

import packets.events.SpeedTrapData;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class SpeedTrapEventParser {

    public static SpeedTrapData parseEventPacket(int packetFormat, ByteBuffer byteBuffer) {
        SpeedTrapData.Builder builder = new SpeedTrapData.Builder()
                .setVehicleId(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setSpeed(byteBuffer.getFloat());
        if (packetFormat >= Constants.YEAR_2021) {
            builder.setIsOverallFastest(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setIsDriverFastest(BitMaskUtils.bitMask8(byteBuffer.get()));
        }
        if (packetFormat >= Constants.YEAR_2022) {
            builder.setFastestVehicleId(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setFastestSpeed(byteBuffer.getFloat());
        }
        return builder.build();
    }
}

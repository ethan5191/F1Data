package packets.parsers;

import packets.TireSetsData;
import utils.BitMaskUtils;

import java.nio.ByteBuffer;

public class TireSetsPacketParser {

    public static TireSetsData parsePacket(ByteBuffer byteBuffer) {
        TireSetsData.Builder builder = new TireSetsData.Builder()
                .setActualTireCompound(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setVisualTireCompound(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setWear(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setAvailable(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setRecommendedSession(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setLifeSpan(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setUsableLife(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setLapDeltaTime(byteBuffer.getShort())
                .setFitted(BitMaskUtils.bitMask8(byteBuffer.get()));
        return builder.build();
    }
}

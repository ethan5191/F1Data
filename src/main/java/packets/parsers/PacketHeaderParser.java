package packets.parsers;

import packets.PacketHeader;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class PacketHeaderParser {

    public static PacketHeader parsePacket(ByteBuffer byteBuffer) {
        int packetFormat = BitMaskUtils.bitMask16(byteBuffer.getShort());
        PacketHeader.Builder builder = new PacketHeader.Builder().setPacketFormat(packetFormat);
        if (packetFormat >= Constants.YEAR_2023) builder.setGameYear(BitMaskUtils.bitMask8(byteBuffer.get()));
        builder.setMajorVersion(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setMinorVersion(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setPacketVersion(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setPacketId(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setSessionUID(BitMaskUtils.bitMask64(byteBuffer.getLong()))
                .setSessionTime(byteBuffer.getFloat())
                .setFrameID(BitMaskUtils.bitMask32(byteBuffer.getInt()));
        if (packetFormat >= Constants.YEAR_2023) builder.setOverallFrameID(BitMaskUtils.bitMask32(byteBuffer.getInt()));
        builder.setPlayerCarIndex(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setSecondaryPlayerCarIndex(BitMaskUtils.bitMask8(byteBuffer.get()));

        return builder.build();
    }
}

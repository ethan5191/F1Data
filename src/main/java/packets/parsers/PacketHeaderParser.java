package packets.parsers;

import packets.PacketHeader;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class PacketHeaderParser {

    public static PacketHeader parsePacket(ByteBuffer byteBuffer) {
        int packetFormat = ParserUtils.bitMask16(byteBuffer.getShort());
        PacketHeader.Builder builder = new PacketHeader.Builder().setPacketFormat(packetFormat);
        if (packetFormat >= Constants.YEAR_2023) builder.setGameYear(ParserUtils.bitMask8(byteBuffer.get()));
        builder.setMajorVersion(ParserUtils.bitMask8(byteBuffer.get()))
                .setMinorVersion(ParserUtils.bitMask8(byteBuffer.get()))
                .setPacketVersion(ParserUtils.bitMask8(byteBuffer.get()))
                .setPacketId(ParserUtils.bitMask8(byteBuffer.get()))
                .setSessionUID(ParserUtils.bitMask64(byteBuffer.getLong()))
                .setSessionTime(byteBuffer.getFloat())
                .setFrameID(ParserUtils.bitMask32(byteBuffer.getInt()));
        if (packetFormat >= Constants.YEAR_2023) builder.setOverallFrameID(ParserUtils.bitMask32(byteBuffer.getInt()));
        builder.setPlayerCarIndex(ParserUtils.bitMask8(byteBuffer.get()))
                .setSecondaryPlayerCarIndex(ParserUtils.bitMask8(byteBuffer.get()));

        return builder.build();
    }
}

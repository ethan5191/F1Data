package packets.parsers;

import packets.PacketHeader;
import utils.constants.Constants;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class PacketHeaderParser {

    public static PacketHeader parsePacket(ByteBuffer byteBuffer) {
        PacketHeader.Builder builder = new PacketHeader.Builder();
        int packetFormat = byteBuffer.getShort() & Constants.BIT_MASK_16;
        builder.setPacketFormat(packetFormat);
        if (packetFormat >= 2024) builder.setGameYear(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setMajorVersion(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setMinorVersion(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setPacketVersion(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setPacketId(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setSessionUID(BigInteger.valueOf(byteBuffer.getLong()).and(BigInteger.ONE.shiftLeft(64).subtract(BigInteger.ONE)));
        builder.setSessionTime(byteBuffer.getFloat());
        builder.setFrameID(byteBuffer.getInt() & Constants.BIT_MASK_32);
        if (packetFormat >= 2024) builder.setOverallFrameID(byteBuffer.getInt() & Constants.BIT_MASK_32);
        builder.setPlayerCarIndex(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setSecondaryPlayerCarIndex(byteBuffer.get() & Constants.BIT_MASK_8);

        return builder.build();
    }
}

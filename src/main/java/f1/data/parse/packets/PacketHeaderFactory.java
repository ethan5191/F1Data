package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class PacketHeaderFactory {

    public static PacketHeader build(ByteBuffer byteBuffer) {
        int packetFormat = BitMaskUtils.bitMask16(byteBuffer.getShort());
        return switch (packetFormat) {
            case Constants.YEAR_2019:
                PacketHeader.PacketHeader19 p19 = new PacketHeader.PacketHeader19(packetFormat, byteBuffer);
                yield new PacketHeader(packetFormat, p19.majorVersion(), p19.minorVersion(), p19.packetVersion(), p19.packetId(),
                        p19.sessionUID(), p19.sessionTime(), p19.frameID(), p19.playerCarIndex(), 0, 0, 0L);
            case Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022:
                PacketHeader.PacketHeader20 p20 = new PacketHeader.PacketHeader20(packetFormat, byteBuffer);
                yield new PacketHeader(packetFormat, p20.majorVersion(), p20.minorVersion(), p20.packetVersion(), p20.packetId(),
                        p20.sessionUID(), p20.sessionTime(), p20.frameID(), p20.playerCarIndex(), p20.secondaryPlayerCarIndex(), 0, 0L);
            case Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025:
                PacketHeader.PacketHeader23 p23 = new PacketHeader.PacketHeader23(packetFormat, byteBuffer);
                yield new PacketHeader(packetFormat, p23.majorVersion(), p23.minorVersion(), p23.packetVersion(), p23.packetId(),
                        p23.sessionUID(), p23.sessionTime(), p23.frameID(), p23.playerCarIndex(), p23.secondaryPlayerCarIndex(),
                        p23.gameYear(), p23.overallFrameID());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2019 - 2025)");
        };
    }
}

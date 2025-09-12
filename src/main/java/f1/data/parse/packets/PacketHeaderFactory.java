package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;
import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

public class PacketHeaderFactory {

    public static PacketHeader build(ByteBuffer byteBuffer) {
        int packetFormat = BitMaskUtils.bitMask16(byteBuffer.getShort());
        SupportedYearsEnum packet = SupportedYearsEnum.fromYear(packetFormat);
        return switch (packet) {
            case F1_2019 -> buildData(packetFormat, new PacketHeader.PacketHeader19(packetFormat, byteBuffer));
            case F1_2020, F1_2021, F1_2022 ->
                    buildData(packetFormat, new PacketHeader.PacketHeader20(packetFormat, byteBuffer));
            case F1_2023, F1_2024, F1_2025 ->
                    buildData(packetFormat, new PacketHeader.PacketHeader23(packetFormat, byteBuffer));
        };
    }

    private static PacketHeader buildData(int packetFormat, PacketHeader.PacketHeader19 p19) {
        return new PacketHeader(packetFormat, p19.majorVersion(), p19.minorVersion(), p19.packetVersion(), p19.packetId(),
                p19.sessionUID(), p19.sessionTime(), p19.frameID(), p19.playerCarIndex(), 0, 0, 0L);
    }

    private static PacketHeader buildData(int packetFormat, PacketHeader.PacketHeader20 p20) {
        return new PacketHeader(packetFormat, p20.majorVersion(), p20.minorVersion(), p20.packetVersion(), p20.packetId(),
                p20.sessionUID(), p20.sessionTime(), p20.frameID(), p20.playerCarIndex(), p20.secondaryPlayerCarIndex(), 0, 0L);
    }

    private static PacketHeader buildData(int packetFormat, PacketHeader.PacketHeader23 p23) {
        return new PacketHeader(packetFormat, p23.majorVersion(), p23.minorVersion(), p23.packetVersion(), p23.packetId(),
                p23.sessionUID(), p23.sessionTime(), p23.frameID(), p23.playerCarIndex(), p23.secondaryPlayerCarIndex(),
                p23.gameYear(), p23.overallFrameID());
    }
}

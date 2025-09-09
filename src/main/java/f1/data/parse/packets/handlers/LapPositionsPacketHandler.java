package f1.data.parse.packets.handlers;

import f1.data.parse.packets.LapPositionsData;
import f1.data.parse.packets.LapPositionsDataFactory;

import java.nio.ByteBuffer;

public class LapPositionsPacketHandler implements PacketHandler {

    private final int packetFormat;
    private LapPositionsData lapPositionsData;

    public LapPositionsPacketHandler(int packetFormat) {
        this.packetFormat = packetFormat;
    }

    public void processPacket(ByteBuffer byteBuffer) {
        lapPositionsData = LapPositionsDataFactory.build(this.packetFormat, byteBuffer);
    }
}

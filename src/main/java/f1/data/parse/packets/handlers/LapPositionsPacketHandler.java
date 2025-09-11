package f1.data.parse.packets.handlers;

import f1.data.parse.packets.LapPositionsData;
import f1.data.parse.packets.LapPositionsDataFactory;

import java.nio.ByteBuffer;

public class LapPositionsPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final LapPositionsDataFactory factory;
    private LapPositionsData lapPositionsData;

    public LapPositionsPacketHandler(int packetFormat) {
        this.packetFormat = packetFormat;
        this.factory = new LapPositionsDataFactory(this.packetFormat);
    }

    public void processPacket(ByteBuffer byteBuffer) {
        lapPositionsData = factory.build(byteBuffer);
    }
}

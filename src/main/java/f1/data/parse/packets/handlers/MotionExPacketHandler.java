package f1.data.parse.packets.handlers;

import f1.data.parse.packets.MotionExData;
import f1.data.parse.packets.MotionExDataFactory;

import java.nio.ByteBuffer;

public class MotionExPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final MotionExDataFactory factory;

    public MotionExPacketHandler(int packetFormat) {
        this.packetFormat = packetFormat;
        this.factory = new MotionExDataFactory(this.packetFormat);
    }

    public void processPacket(ByteBuffer byteBuffer) {
        MotionExData med = factory.build(byteBuffer);
    }
}

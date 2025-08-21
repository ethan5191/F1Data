package f1.data.packets.handlers;

import java.nio.ByteBuffer;

public interface PacketHandler {

    void processPacket(ByteBuffer byteBuffer);
}

package f1.data.parse.packets.handlers;

import java.nio.ByteBuffer;

public interface PacketHandler {

    void processPacket(ByteBuffer byteBuffer);
}

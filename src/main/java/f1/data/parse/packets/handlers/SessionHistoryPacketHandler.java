package f1.data.parse.packets.handlers;

import f1.data.parse.packets.history.SessionHistoryData;
import f1.data.parse.packets.history.SessionHistoryDataFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SessionHistoryPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final List<SessionHistoryData> sessionHistory = new ArrayList<>();

    public SessionHistoryPacketHandler(int packetFormat) {
        this.packetFormat = packetFormat;
    }

    public void processPacket(ByteBuffer byteBuffer) {
        sessionHistory.add(SessionHistoryDataFactory.build(this.packetFormat, byteBuffer));
    }
}

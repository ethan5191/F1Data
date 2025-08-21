package f1.data.packets.handlers;

import f1.data.packets.session.SessionData;
import f1.data.packets.session.SessionDataFactory;
import f1.data.telemetry.TelemetryData;

import java.nio.ByteBuffer;
import java.util.Map;

public class SessionPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;

    public SessionPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.participants = participants;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sessionData = SessionDataFactory.build(packetFormat, byteBuffer);
            //TODO add logic for session check. Will hav eto clear the panels in the UI, and do a save process so the data can be viewed after the session.
        }
    }
}

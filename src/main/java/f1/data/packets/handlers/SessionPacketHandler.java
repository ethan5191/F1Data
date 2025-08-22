package f1.data.packets.handlers;

import f1.data.packets.session.SessionData;
import f1.data.packets.session.SessionDataFactory;
import f1.data.telemetry.TelemetryData;
import f1.data.ui.dto.SessionResetDTO;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.function.Consumer;

public class SessionPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;
    private final Consumer<SessionResetDTO> sessionDataConsumer;

    public SessionPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, Consumer<SessionResetDTO> sessionDataConsumer) {
        this.packetFormat = packetFormat;
        this.participants = participants;
        this.sessionDataConsumer = sessionDataConsumer;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sessionData = SessionDataFactory.build(packetFormat, byteBuffer);
            TelemetryData td = participants.get(0);
            if (!td.getSessionName().equals(sessionData.buildSessionName())) {
                this.sessionDataConsumer.accept(new SessionResetDTO(true));
            }
        }
    }
}

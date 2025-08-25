package f1.data.packets.handlers;

import f1.data.packets.session.SessionData;
import f1.data.packets.session.SessionDataFactory;
import f1.data.packets.session.SessionName;
import f1.data.ui.dto.SessionResetDTO;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class SessionPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Consumer<SessionResetDTO> sessionDataConsumer;
    private final SessionName sessionName;

    public SessionPacketHandler(int packetFormat, Consumer<SessionResetDTO> sessionDataConsumer, SessionName sessionName) {
        this.packetFormat = packetFormat;
        this.sessionDataConsumer = sessionDataConsumer;
        this.sessionName = sessionName;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sd = SessionDataFactory.build(packetFormat, byteBuffer);
            if (!this.sessionName.buildSessionName().equals(sd.buildSessionName())) {
                this.sessionName.setSessionType(sd.sessionType());
                this.sessionName.setFormula(sd.formula());
                this.sessionName.setTrackId(sd.trackId());
                this.sessionDataConsumer.accept(new SessionResetDTO(true, sd.buildSessionName()));
            }
        }
    }
}

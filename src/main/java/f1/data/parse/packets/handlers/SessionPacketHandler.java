package f1.data.parse.packets.handlers;

import f1.data.enums.FormulaEnum;
import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.SessionDataFactory;
import f1.data.parse.packets.session.SessionName;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.save.*;
import f1.data.ui.panels.dto.SessionResetDTO;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.function.Consumer;

public class SessionPacketHandler implements PacketHandler {

    private final Map<Integer, TelemetryData> participants;
    private final int packetFormat;
    private final Consumer<SessionResetDTO> sessionDataConsumer;
    private final SessionName sessionName;
    private final SessionDataFactory factory;

    public SessionPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, Consumer<SessionResetDTO> sessionDataConsumer, SessionName sessionName) {
        this.participants = participants;
        this.packetFormat = packetFormat;
        this.sessionDataConsumer = sessionDataConsumer;
        this.sessionName = sessionName;
        this.factory = new SessionDataFactory(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sd = factory.build(byteBuffer);
            if (!this.sessionName.buildSessionName().equals(sd.buildSessionName())) {
                //Builds the save data, if enabled and calls the method to actually create the save file.
                SaveSessionDataHandler.buildSaveData(this.packetFormat, sessionName.buildSessionName(), this.participants, true);
                //Clear the participants map, so the participants packet logic knows to rebuild it.
                this.participants.clear();
                //build out the new session name object
                this.sessionName.setSessionType(sd.sessionType());
                this.sessionName.setFormula(sd.formula());
                this.sessionName.setTrackId(sd.trackId());
                //Send a notification to the consumer so it knows to reset the UI.
                this.sessionDataConsumer.accept(new SessionResetDTO(true, sd.buildSessionName(), sd.formula() == FormulaEnum.F1.getValue()));
            }
        }
    }
}

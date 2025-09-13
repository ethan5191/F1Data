package f1.data.parse.packets.handlers;

import f1.data.enums.FormulaEnum;
import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.SessionDataFactory;
import f1.data.parse.packets.session.SessionInformation;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.save.SaveSessionDataHandler;
import f1.data.ui.panels.dto.SessionResetDTO;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.function.Consumer;

public class SessionPacketHandler implements PacketHandler {

    private final Map<Integer, TelemetryData> participants;
    private final int packetFormat;
    private final Consumer<SessionResetDTO> sessionDataConsumer;
    private final SessionInformation sessionInformation;
    private final SessionDataFactory factory;

    public SessionPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, Consumer<SessionResetDTO> sessionDataConsumer, SessionInformation sessionInformation) {
        this.participants = participants;
        this.packetFormat = packetFormat;
        this.sessionDataConsumer = sessionDataConsumer;
        this.sessionInformation = sessionInformation;
        this.factory = new SessionDataFactory(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sd = factory.build(byteBuffer);
            SessionInformation temp = new SessionInformation(sd.sessionType(), sd.trackId(), sd.formula(), this.sessionInformation.getPlayerDriverId(), this.sessionInformation.getTeamMateDriverId(), this.sessionInformation.getTeamId());
            if (!this.sessionInformation.equals(temp)) {
                //Builds the save data, if enabled and calls the method to actually create the save file.
                SaveSessionDataHandler.buildSaveData(this.packetFormat, sessionInformation.getName(), this.participants, true);
                //Clear the participants map, so the participants packet logic knows to rebuild it.
                this.participants.clear();
                //build out the new session name object
                this.sessionInformation.updateSessionName(sd.sessionType(), sd.trackId(), sd.formula());
                //Send a notification to the consumer so it knows to reset the UI.
                this.sessionDataConsumer.accept(new SessionResetDTO(true, this.sessionInformation.getName(), sd.formula() == FormulaEnum.F1.getValue()));
            }
        }
    }
}

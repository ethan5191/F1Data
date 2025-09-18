package f1.data.parse.packets.handlers;

import f1.data.enums.FormulaEnum;
import f1.data.enums.SupportedYearsEnum;
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
    private final SupportedYearsEnum supportedYearsEnum;

    public SessionPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, Consumer<SessionResetDTO> sessionDataConsumer, SessionInformation sessionInformation) {
        this.participants = participants;
        this.packetFormat = packetFormat;
        this.sessionDataConsumer = sessionDataConsumer;
        this.sessionInformation = sessionInformation;
        this.factory = new SessionDataFactory(this.packetFormat);
        this.supportedYearsEnum = SupportedYearsEnum.fromYear(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sd = factory.build(byteBuffer);
            SessionInformation temp = new SessionInformation(sd, this.sessionInformation.getPlayerDriverId(), this.sessionInformation.getTeamMateDriverId(), this.sessionInformation.getTeamId());
            boolean isSameSessionRestart = sd.sessionTimeLeft() > this.sessionInformation.getSessionTimeLeft();
            //If the sessionInformation objects are not equal OR the session time left on the latest session data packet is greater than the session information's time left param.
            //The only way the OR in this can get hit is if you leave or restart a session without changing either the track, session, or player car.
            if (!this.sessionInformation.equals(temp) || isSameSessionRestart) {
                //Builds the save data, if enabled and calls the method to actually create the save file.
                //F1 2019 and earlier did not have the final classification packet, so they don't save data then, so save it now.
                //I don't want F1 2019 and earlier to save data just because the user restarted the session.
                if (this.supportedYearsEnum.is2019OrEarlier() && !isSameSessionRestart) {
                    SaveSessionDataHandler.buildSaveData(this.packetFormat, sessionInformation.getName(), this.participants);
                }
                //Clear the participants map, so the participants packet logic knows to rebuild it.
                this.participants.clear();
                //build out the new session name object
                this.sessionInformation.updateSessionName(sd);
                //Send a notification to the consumer so it knows to reset the UI.
                this.sessionDataConsumer.accept(new SessionResetDTO(true, this.sessionInformation.getName(), sd.formula() == FormulaEnum.F1.getValue()));
            }
            //Always update this value to latest value from the session packet.
            this.sessionInformation.setSessionTimeLeft(sd.sessionTimeLeft());
        }
    }
}

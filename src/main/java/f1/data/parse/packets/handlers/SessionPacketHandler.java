package f1.data.parse.packets.handlers;

import f1.data.enums.FormulaEnum;
import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.SessionDataFactory;
import f1.data.parse.packets.session.SessionInformationWrapper;
import f1.data.parse.packets.session.SessionTimeBuffer;
import f1.data.save.SaveSessionDataHandler;
import f1.data.ui.panels.dto.SessionResetDTO;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class SessionPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Consumer<SessionResetDTO> sessionDataConsumer;
    private final SessionInformationWrapper sessionInformationWrapper;
    private final SessionDataFactory factory;
    private final SupportedYearsEnum supportedYearsEnum;
    private final SessionTimeBuffer sessionTimeBuffer;

    public SessionPacketHandler(int packetFormat, Consumer<SessionResetDTO> sessionDataConsumer, SessionInformationWrapper sessionInformationWrapper) {
        this.packetFormat = packetFormat;
        this.sessionDataConsumer = sessionDataConsumer;
        this.sessionInformationWrapper = sessionInformationWrapper;
        this.factory = new SessionDataFactory(this.packetFormat);
        this.supportedYearsEnum = SupportedYearsEnum.fromYear(this.packetFormat);
        this.sessionTimeBuffer = new SessionTimeBuffer();
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sd = factory.build(byteBuffer);
            SessionInformationWrapper temp = new SessionInformationWrapper(sd, this.sessionInformationWrapper.getPlayerDriverId(), this.sessionInformationWrapper.getTeamMateDriverId(), this.sessionInformationWrapper.getTeamId());
            //If the session time left is greater than the saved session time left and the value does not exist in the session time buffer.
            boolean isSameSessionRestart = (sd.sessionTimeLeft() > this.sessionInformationWrapper.getSessionTimeLeft() && !this.sessionTimeBuffer.contains(sd.sessionTimeLeft()));
            //If the sessionInformation objects are not equal OR the session time left on the latest session data packet is greater than the session information's time left param.
            //The only way the OR in this can get hit is if you leave or restart a session without changing either the track, session, or player car.
            if (!this.sessionInformationWrapper.equals(temp) || isSameSessionRestart) {
                //Builds the save data, if enabled and calls the method to actually create the save file.
                //F1 2019 and earlier did not have the final classification packet, so they don't save data then, so save it now.
                //I don't want F1 2019 and earlier to save data just because the user restarted the session.
                if (this.supportedYearsEnum.is2019OrEarlier() && !isSameSessionRestart) {
                    SaveSessionDataHandler.buildSaveData(this.packetFormat, sessionInformationWrapper.getName(), this.sessionInformationWrapper.getParticipants());
                }
                //As this is a new gaming session clear these collections so the participants packet will rebuild them properly.
                this.sessionInformationWrapper.clearCollection();
                this.sessionTimeBuffer.reset();
                //build out the new session name object
                this.sessionInformationWrapper.updateSessionName(sd);
                //Send a notification to the consumer so it knows to reset the UI.
                this.sessionDataConsumer.accept(new SessionResetDTO(true, this.sessionInformationWrapper.getName(), sd.formula() == FormulaEnum.F1.getValue()));
            }
            //Always update this value to latest value from the session packet.
            this.sessionInformationWrapper.setSessionTimeLeft(sd.sessionTimeLeft());
            this.sessionTimeBuffer.add(sd.sessionTimeLeft());
        }
    }
}

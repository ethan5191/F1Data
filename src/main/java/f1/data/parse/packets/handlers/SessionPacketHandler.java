package f1.data.parse.packets.handlers;

import f1.data.parse.packets.ParticipantData;
import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.SessionDataFactory;
import f1.data.parse.packets.session.SessionName;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.save.SaveSessionData;
import f1.data.save.SaveSessionDataHandler;
import f1.data.ui.panels.dto.SessionResetDTO;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SessionPacketHandler implements PacketHandler {

    private final Map<Integer, TelemetryData> participantsMap;
    private final int packetFormat;
    private final Consumer<SessionResetDTO> sessionDataConsumer;
    private final SessionName sessionName;

    public SessionPacketHandler(int packetFormat, Map<Integer, TelemetryData> participantsMap, Consumer<SessionResetDTO> sessionDataConsumer, SessionName sessionName) {
        this.participantsMap = participantsMap;
        this.packetFormat = packetFormat;
        this.sessionDataConsumer = sessionDataConsumer;
        this.sessionName = sessionName;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sd = SessionDataFactory.build(packetFormat, byteBuffer);
            if (!this.sessionName.buildSessionName().equals(sd.buildSessionName())) {
                List<SaveSessionData> saveSessionDataList = new ArrayList<>(this.participantsMap.size());
                //Loop over the participant map and create new telemetry data to reset the data on the backend.
                for (Integer i : this.participantsMap.keySet()) {
                    TelemetryData td = this.participantsMap.get(i);
                    ParticipantData pd = td.getParticipantData();
                    saveSessionDataList.add(new SaveSessionData(pd.lastName(), td.getSpeedTrapData().getSpeedTrapByLap()));
                    this.participantsMap.put(i, new TelemetryData(pd));
                }
                SaveSessionDataHandler.saveSessionData(this.sessionName.buildSessionName(), saveSessionDataList);
                //build out the new session name object
                this.sessionName.setSessionType(sd.sessionType());
                this.sessionName.setFormula(sd.formula());
                this.sessionName.setTrackId(sd.trackId());
                //Send a notification to the consumer so it knows to reset the UI.
                this.sessionDataConsumer.accept(new SessionResetDTO(true, sd.buildSessionName()));
            }
        }
    }
}

package f1.data.parse.packets.handlers;

import f1.data.parse.packets.ParticipantData;
import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.SessionDataFactory;
import f1.data.parse.packets.session.SessionName;
import f1.data.parse.telemetry.SetupTireKey;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.save.*;
import f1.data.ui.panels.dto.SessionResetDTO;
import f1.data.ui.panels.home.AppState;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SessionPacketHandler implements PacketHandler {

    private final Map<Integer, TelemetryData> participants;
    private final int packetFormat;
    private final Consumer<SessionResetDTO> sessionDataConsumer;
    private final SessionName sessionName;

    public SessionPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, Consumer<SessionResetDTO> sessionDataConsumer, SessionName sessionName) {
        this.participants = participants;
        this.packetFormat = packetFormat;
        this.sessionDataConsumer = sessionDataConsumer;
        this.sessionName = sessionName;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (packetFormat > 0) {
            SessionData sd = SessionDataFactory.build(packetFormat, byteBuffer);
            if (!this.sessionName.buildSessionName().equals(sd.buildSessionName())) {
                List<SpeedTrapSessionData> speedTrapSessionDataList = new ArrayList<>(this.participants.size());
                List<RunDataSessionData> runDataSessionData = new ArrayList<>(this.participants.size());
                //Loop over the participant map and create new telemetry data to reset the data on the backend.
                for (Integer i : this.participants.keySet()) {
                    TelemetryData td = this.participants.get(i);
                    ParticipantData pd = td.getParticipantData();
                    List<RunDataMapRecord> records = new ArrayList<>(td.getCarSetupData().getLapsPerSetup().size());
                    //If a driver hasn't set a speed trap yet in the session, it will show as 0 as that is the default object for SpeedTrapData on the td object.
                    speedTrapSessionDataList.add(new SpeedTrapSessionData(pd.lastName(), td.getSpeedTrapData().getSpeedTrapByLap()));
                    for (SetupTireKey key : td.getCarSetupData().getLapsPerSetup().keySet()) {
                        records.add(new RunDataMapRecord(key, td.getCarSetupData().getLapsPerSetup().get(key)));
                    }
                    runDataSessionData.add(new RunDataSessionData(pd.lastName(), td.getCarSetupData().getSetups(), records));
                    this.participants.put(i, new TelemetryData(pd));
                }
                if (AppState.saveSessionData.get()) SaveSessionDataHandler.saveSessionData(this.packetFormat, this.sessionName.buildSessionName(), speedTrapSessionDataList, runDataSessionData);
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

package f1.data.parse.packets.session;

import f1.data.enums.FormulaEnum;
import f1.data.enums.SessionTypeEnum;
import f1.data.enums.TrackEnum;
import f1.data.mapKeys.DriverPair;
import f1.data.parse.packets.participant.ParticipantData;
import f1.data.parse.packets.participant.ParticipantKey;
import f1.data.parse.telemetry.TelemetryData;

import java.util.*;

public class SessionInformationWrapper {

    private int sessionType;
    private int trackId;
    private int formula;
    private int sessionTimeLeft;
    private int sessionDuration;
    private String name;
    private int playerDriverId;
    private int teamMateDriverId;
    private int teamId;

    private final Map<Integer, TelemetryData> participants;
    private final List<ParticipantData> participantDataList = new ArrayList<>();
    private final Map<Integer, DriverPair> driverPairPerTeam = new TreeMap<>();
    private final Set<ParticipantKey> distinctParticipants = new HashSet<>();

    public SessionInformationWrapper(Map<Integer, TelemetryData> participants, SessionData sessionData, int playerDriverId, int teamMateDriverId, int teamId) {
        this.participants = participants;
        this.sessionType = sessionData.sessionType();
        this.trackId = sessionData.trackId();
        this.formula = sessionData.formula();
        this.sessionTimeLeft = sessionData.sessionTimeLeft();
        this.sessionDuration = sessionData.sessionDuration();
        this.playerDriverId = playerDriverId;
        this.teamMateDriverId = teamMateDriverId;
        this.teamId = teamId;
        this.name = buildSessionName();
    }

    public SessionInformationWrapper(SessionData sessionData, int playerDriverId, int teamMateDriverId, int teamId) {
        this.participants = null;
        this.sessionType = sessionData.sessionType();
        this.trackId = sessionData.trackId();
        this.formula = sessionData.formula();
        this.sessionTimeLeft = sessionData.sessionTimeLeft();
        this.sessionDuration = sessionData.sessionDuration();
        this.playerDriverId = playerDriverId;
        this.teamMateDriverId = teamMateDriverId;
        this.teamId = teamId;
        this.name = buildSessionName();
    }

    public Map<Integer, TelemetryData> getParticipants() {
        return participants;
    }

    public List<ParticipantData> getParticipantDataList() {
        return participantDataList;
    }

    public Map<Integer, DriverPair> getDriverPairPerTeam() {
        return driverPairPerTeam;
    }

    public Set<ParticipantKey> getDistinctParticipants() {
        return distinctParticipants;
    }

    public String getName() {
        return name;
    }

    public int getPlayerDriverId() {
        return playerDriverId;
    }

    public int getTeamMateDriverId() {
        return teamMateDriverId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getSessionTimeLeft() {
        return sessionTimeLeft;
    }

    public void setSessionTimeLeft(int sessionTimeLeft) {
        this.sessionTimeLeft = sessionTimeLeft;
    }

    //Method used to update the given object. Using a single method vice setters as these are all updated at the same time.
    public void updateSessionName(SessionData sessionData) {
        this.sessionType = sessionData.sessionType();
        this.trackId = sessionData.trackId();
        this.formula = sessionData.formula();
        this.sessionDuration = sessionData.sessionDuration();
        this.name = buildSessionName();
    }

    public void updateDriverInfo(int playerDriverId, int teamMateDriverId, int teamId) {
        this.playerDriverId = playerDriverId;
        this.teamMateDriverId = teamMateDriverId;
        this.teamId = teamId;
    }

    //Clear the collections due to a session change
    public void clearCollection() {
        this.participants.clear();
        this.driverPairPerTeam.clear();
        this.distinctParticipants.clear();
        this.participantDataList.clear();
    }

    private String buildSessionName() {
        String formula = FormulaEnum.fromValue(this.formula).name();
        String track = TrackEnum.fromId(this.trackId).name();
        String session = SessionTypeEnum.fromId(this.sessionType).name();
        return formula + " " + session + " at " + track;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SessionInformationWrapper that = (SessionInformationWrapper) o;
        return sessionType == that.sessionType && trackId == that.trackId && formula == that.formula && playerDriverId == that.playerDriverId && teamMateDriverId == that.teamMateDriverId && teamId == that.teamId && sessionDuration == that.sessionDuration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionType, trackId, formula, sessionDuration, playerDriverId, teamMateDriverId, teamId);
    }
}

package f1.data.parse.packets.session;

import f1.data.enums.FormulaEnum;
import f1.data.enums.SessionTypeEnum;
import f1.data.enums.TrackEnum;

import java.util.Objects;

public class SessionInformation {

    private int sessionType;
    private int trackId;
    private int formula;
    private int sessionTimeLeft;
    private int sessionDuration;
    private String name;
    private int playerDriverId;
    private int teamMateDriverId;
    private int teamId;

    public SessionInformation(SessionData sessionData, int playerDriverId, int teamMateDriverId, int teamId) {
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

    public int getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(int sessionDuration) {
        this.sessionDuration = sessionDuration;
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

    private String buildSessionName() {
        String formula = FormulaEnum.fromValue(this.formula).name();
        String track = TrackEnum.fromId(this.trackId).name();
        String session = SessionTypeEnum.fromId(this.sessionType).name();
        return formula + " " + session + " at " + track;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SessionInformation that = (SessionInformation) o;
        return sessionType == that.sessionType && trackId == that.trackId && formula == that.formula && playerDriverId == that.playerDriverId && teamMateDriverId == that.teamMateDriverId && teamId == that.teamId && sessionDuration == that.sessionDuration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionType, trackId, formula, sessionDuration, playerDriverId, teamMateDriverId, teamId);
    }
}

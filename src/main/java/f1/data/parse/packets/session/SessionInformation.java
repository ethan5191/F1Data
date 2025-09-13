package f1.data.parse.packets.session;

import f1.data.enums.FormulaEnum;
import f1.data.enums.SessionTypeEnum;
import f1.data.enums.TrackEnum;

import java.util.Objects;

public class SessionInformation {

    private int sessionType;
    private int trackId;
    private int formula;
    private String name;
    private int playerDriverId;
    private int teamMateDriverId;
    private int teamId;

    public SessionInformation(int sessionType, int trackId, int formula, int playerDriverId, int teamMateDriverId, int teamId) {
        this.sessionType = sessionType;
        this.trackId = trackId;
        this.formula = formula;
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

    //Method used to update the given object. Using a single method vice setters as these are all updated at the same time.
    public void updateSessionName(int sessionType, int trackId, int formula) {
        this.sessionType = sessionType;
        this.trackId = trackId;
        this.formula = formula;
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
        return sessionType == that.sessionType && trackId == that.trackId && formula == that.formula && playerDriverId == that.playerDriverId && teamMateDriverId == that.teamMateDriverId && teamId == that.teamId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionType, trackId, formula, playerDriverId, teamMateDriverId, teamId);
    }
}

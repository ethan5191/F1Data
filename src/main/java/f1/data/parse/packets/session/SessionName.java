package f1.data.parse.packets.session;

import f1.data.enums.FormulaEnum;
import f1.data.enums.SessionTypeEnum;
import f1.data.enums.TrackEnum;

public class SessionName {

    private int sessionType;
    private int trackId;
    private int formula;

    public SessionName(int sessionType, int trackId, int formula) {
        this.sessionType = sessionType;
        this.trackId = trackId;
        this.formula = formula;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public void setFormula(int formula) {
        this.formula = formula;
    }

    public String buildSessionName() {
        String formula = FormulaEnum.fromValue(this.formula).name();
        String track = TrackEnum.fromId(this.trackId).name();
        String session = SessionTypeEnum.fromId(this.sessionType).name();
        return formula + " " + session + " at " + track;
    }
}

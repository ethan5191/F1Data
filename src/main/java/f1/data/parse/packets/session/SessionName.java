package f1.data.parse.packets.session;

import f1.data.enums.FormulaEnum;
import f1.data.enums.SessionTypeEnum;
import f1.data.enums.TrackEnum;

import java.util.Objects;

public class SessionName {

    private int sessionType;
    private int trackId;
    private int formula;
    private String name;

    public SessionName(int sessionType, int trackId, int formula) {
        this.sessionType = sessionType;
        this.trackId = trackId;
        this.formula = formula;
        this.name = buildSessionName();
    }

    public String getName() {
        return name;
    }

    //Method used to update the given object. Using a single method vice setters as these are all updated at the same time.
    public void updateSessionName(int sessionType, int trackId, int formula) {
        this.sessionType = sessionType;
        this.trackId = trackId;
        this.formula = formula;
        this.name = buildSessionName();
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
        SessionName that = (SessionName) o;
        return sessionType == that.sessionType && trackId == that.trackId && formula == that.formula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionType, trackId, formula);
    }
}

package f1.data;

import f1.data.packets.ParticipantData;
import f1.data.packets.enums.FormulaEnum;
import f1.data.packets.session.SessionData;

import java.util.List;

public class SessionInitializationResult {

    private final SessionData sessionData;
    private final List<ParticipantData> participantData;
    private final Integer numActiveCars;

    public SessionInitializationResult(SessionData sessionData, List<ParticipantData> participantData, Integer numActiveCars) {
        this.sessionData = sessionData;
        this.participantData = participantData;
        this.numActiveCars = numActiveCars;
    }

    public boolean isF1() {
        return this.sessionData.formula() == FormulaEnum.F1.getValue();
    }
}

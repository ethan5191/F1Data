package f1.data;

import f1.data.packets.ParticipantData;
import f1.data.packets.enums.DriverPairingsEnum;
import f1.data.packets.enums.Formula2Enum;
import f1.data.packets.enums.FormulaEnum;
import f1.data.packets.session.SessionData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionInitializationResult {

    private final SessionData sessionData;
    private final List<ParticipantData> participantData;
    private final DriverPairingsEnum driverPairingsEnum;
    private final Integer numActiveCars;
    private final Integer packetFormat;
    private final Integer playerCarIndex;
    private final Integer playerDriverId;
    private final Integer teamMateDriverId;
    private final Map<Integer, Integer> driverPairings;

    public SessionInitializationResult(SessionData sessionData, List<ParticipantData> participantData, DriverPairingsEnum driverPairingsEnum, Integer numActiveCars, Integer playerCarIndex, Integer packetFormat) {
        this.sessionData = sessionData;
        this.participantData = participantData;
        this.driverPairingsEnum = driverPairingsEnum;
        this.packetFormat = packetFormat;
        this.numActiveCars = numActiveCars;
        this.playerCarIndex = playerCarIndex;
        this.driverPairings = findDriverPairings(determineF2Enum());
        this.playerDriverId = this.participantData.get(this.playerCarIndex).driverId();
        this.teamMateDriverId = this.driverPairings.get(this.playerDriverId);
    }

    public List<ParticipantData> getParticipantData() {
        return participantData;
    }

    public Integer getNumActiveCars() {
        return numActiveCars;
    }

    public Integer getPacketFormat() {
        return packetFormat;
    }

    public Integer getPlayerCarIndex() {
        return playerCarIndex;
    }

    public Integer getPlayerDriverId() {
        return playerDriverId;
    }

    public Integer getTeamMateDriverId() {
        return teamMateDriverId;
    }

    public Map<Integer, Integer> getDriverPairings() {
        return driverPairings;
    }

    public boolean isF1() {
        return this.sessionData.formula() == FormulaEnum.F1.getValue();
    }

    public boolean isF2() {
        return this.sessionData.formula() == FormulaEnum.F2.getValue();
    }

    //F2 enum is current/previous, tells which F2 driver lineup to use for that specific game.
    private Map<Integer, Integer> findDriverPairings(Formula2Enum formula2Enum) {
        //I haven't created any driver pairings for non-F1 and non-F2 lineups. Not sure if I ever will, so return an empty Map.
        if (!isF1() && !isF2()) {
            return new HashMap<>(0);
        } else if (isF1()) {
            return driverPairingsEnum.getF1DriverPairs();
        } else {
            //If F2 enum is current get the current F2 drivers, else get previous.
            if (formula2Enum.equals(Formula2Enum.CURRENT)) {
                return driverPairingsEnum.getF2DriverPairs();
            } else {
                return driverPairingsEnum.getF2PrevYearDriverPairs();
            }
        }
    }

    //Logic to determine what F2 class to use. If it's not F2 formula then return null.
    //As of 2025, no 2 seasons of F2 have had the same lineup. If that ever happens, this logic will no longer work.
    private Formula2Enum determineF2Enum() {
        if (!isF2()) return null;
        Formula2Enum formula2Enum = null;
        for (ParticipantData pd : this.participantData) {
            boolean f2Current = driverPairingsEnum.getF2DriverPairs().containsKey(pd.driverId());
            boolean f2Prev = driverPairingsEnum.getF2PrevYearDriverPairs().containsKey(pd.driverId());
            if (f2Current && !f2Prev) {
                formula2Enum = Formula2Enum.CURRENT;
                break;
            } else if (!f2Current && f2Prev) {
                formula2Enum = Formula2Enum.PREVIOUS;
                break;
            }
        }
        return formula2Enum;
    }
}

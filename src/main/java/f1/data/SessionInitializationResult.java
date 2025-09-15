package f1.data;

import f1.data.enums.FormulaEnum;
import f1.data.mapKeys.DriverPair;
import f1.data.parse.packets.participant.ParticipantData;
import f1.data.parse.packets.session.SessionData;

import java.util.List;
import java.util.Map;

public class SessionInitializationResult {

    private final SessionData sessionData;
    private final List<ParticipantData> participantData;
    private final Map<Integer, DriverPair> driverPairPerTeam;
    private final Integer numActiveCars;
    private final Integer packetFormat;
    private final Integer playerCarIndex;
    private final Integer playerDriverId;
    private final Integer teamMateDriverId;
    private final Integer playerTeamId;

    public SessionInitializationResult(Integer playerCarIndex, Integer packetFormat, Integer numActiveCars, Map<Integer,
            DriverPair> driverPairPerTeam, List<ParticipantData> participantData, SessionData sessionData) {
        this.playerCarIndex = playerCarIndex;
        this.packetFormat = packetFormat;
        this.numActiveCars = numActiveCars;
        this.driverPairPerTeam = driverPairPerTeam;
        this.participantData = participantData;

        this.sessionData = sessionData;
        //Get the player driver based on the player car index value.
        ParticipantData playerDriver = this.participantData.get(this.playerCarIndex);
        this.playerDriverId = playerDriver.driverId();
        this.playerTeamId = playerDriver.teamId();
        //Use the player driver's team param to determine what team to look at for the teammate id.
        DriverPair driverPair = driverPairPerTeam.get(playerDriver.teamId());
        //Teammate driver ID will be whatever id on the driver pair isn't the players driver id.
        this.teamMateDriverId = (this.playerDriverId == driverPair.getDriverOne()) ? driverPair.getDriverTwo() : driverPair.getDriverOne();
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public List<ParticipantData> getParticipantData() {
        return participantData;
    }

    public Map<Integer, DriverPair> getDriverPairPerTeam() {
        return driverPairPerTeam;
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

    public Integer getPlayerTeamId() {
        return playerTeamId;
    }

    public boolean isF1() {
        return this.sessionData.formula() == FormulaEnum.F1.getValue();
    }

    public boolean isF2() {
        return this.sessionData.formula() == FormulaEnum.F2.getValue();
    }
}

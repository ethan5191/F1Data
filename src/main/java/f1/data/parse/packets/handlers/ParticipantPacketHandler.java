package f1.data.parse.packets.handlers;

import f1.data.mapKeys.DriverPair;
import f1.data.parse.packets.ParticipantData;
import f1.data.parse.packets.ParticipantDataFactory;
import f1.data.parse.packets.session.SessionInformation;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.ui.panels.dto.DriverDataDTO;
import f1.data.ui.panels.dto.SessionChangeDTO;
import f1.data.utils.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public class ParticipantPacketHandler implements PacketHandler {

    private final int packetFormat;
    private int playerCarIndex;
    private final Map<Integer, TelemetryData> participants;
    private final Consumer<DriverDataDTO> driverDataDTO;
    private final Consumer<SessionChangeDTO> sessionChangeDTO;
    private final SessionInformation sessionInformation;
    private final ParticipantDataFactory factory;

    private int numActiveCars;
    private final List<ParticipantData> participantDataList = new ArrayList<>();
    private final Map<Integer, DriverPair> driverPairPerTeam = new TreeMap<>();

    public ParticipantPacketHandler(int packetFormat, int playerCarIndex, Map<Integer, TelemetryData> participants, Consumer<DriverDataDTO> driverDataDTO, Consumer<SessionChangeDTO> sessionChangeDTO, SessionInformation sessionInformation) {
        this.playerCarIndex = playerCarIndex;
        this.packetFormat = packetFormat;
        this.participants = participants;
        this.driverDataDTO = driverDataDTO;
        this.sessionChangeDTO = sessionChangeDTO;
        this.sessionInformation = sessionInformation;
        this.factory = new ParticipantDataFactory(this.packetFormat);
    }

    public ParticipantPacketHandler(int packetFormat, int playerCarIndex) {
        this.packetFormat = packetFormat;
        this.playerCarIndex = playerCarIndex;
        this.factory = new ParticipantDataFactory(this.packetFormat);
        this.participants = new TreeMap<>();
        this.sessionInformation = null;
        this.driverDataDTO = null;
        this.sessionChangeDTO = null;
    }

    public void processPacket(ByteBuffer byteBuffer) {
        List<ParticipantData> localList = new ArrayList<>();
        //Must process this first as its always above the actual packet content, at least from 2020 onwards.
        numActiveCars = byteBuffer.get();
        //Loop over the packet and create objects for each record in the array.
        int arraySize = Util.findArraySize(this.packetFormat);
        for (int i = 0; i < arraySize; i++) {
            ParticipantData pd = factory.build(byteBuffer);
            //A race number of 0 means this is just a placeholder element, and shouldn't be added to the driver list.
            if (pd.raceNumber() > 0) {
                localList.add(pd);
            }
        }
        //If these two lists are different then we need to rebuild the objects, as we have had some kind of change to the participants.
        if (!localList.equals(this.participantDataList)) {
            //Clear the maps and list.
            this.driverPairPerTeam.clear();
            this.participants.clear();
            this.participantDataList.clear();
            //Update the list to be the newly created list from the packet information
            this.participantDataList.addAll(localList);
            for (int i = 0; i < this.participantDataList.size(); i++) {
                ParticipantData pd = this.participantDataList.get(i);
                //Add a new record to the participants map with a new telemetry data object.
                this.participants.put(i, new TelemetryData(pd));
                //Rebuild the driverPairPerTeam map.
                if (driverPairPerTeam.containsKey(pd.teamId())) {
                    driverPairPerTeam.get(pd.teamId()).setDriverTwo(pd.driverId());
                } else {
                    driverPairPerTeam.put(pd.teamId(), new DriverPair(pd.driverId()));
                }
                //If this dto exists, then send an update to the UI.
                if (this.driverDataDTO != null)
                    driverDataDTO.accept(new DriverDataDTO(pd.driverId(), pd.lastName()));
            }
            //If this objects exists then we need to repopulate these params for the UI.
            if (this.sessionChangeDTO != null) {
                ParticipantData playerDriver = this.participantDataList.get(this.playerCarIndex);
                final int playerDriverId = playerDriver.driverId();
                //Use the player driver's team param to determine what team to look at for the teammate id.
                DriverPair driverPair = driverPairPerTeam.get(playerDriver.teamId());
                //Teammate driver ID will be whatever id on the driver pair isn't the players driver id.
                final int teamMateDriverId = (playerDriverId == driverPair.getDriverOne()) ? driverPair.getDriverTwo() : driverPair.getDriverOne();
                this.sessionChangeDTO.accept(new SessionChangeDTO(playerDriverId, teamMateDriverId, this.numActiveCars, this.participantDataList));
                //Update this object with participant data, so the session logic will trigger a new session.
                if (this.sessionInformation != null) {
                    this.sessionInformation.updateDriverInfo(playerDriverId, teamMateDriverId, playerDriver.teamId());
                }
            }
        }
    }

    public int getNumActiveCars() {
        return numActiveCars;
    }

    public List<ParticipantData> getParticipantDataList() {
        return participantDataList;
    }

    public Map<Integer, DriverPair> getDriverPairPerTeam() {
        return driverPairPerTeam;
    }

    public void setPlayerCarIndex(int playerCarIndex) {
        this.playerCarIndex = playerCarIndex;
    }
}

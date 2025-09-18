package f1.data.parse.packets.handlers;

import f1.data.mapKeys.DriverPair;
import f1.data.parse.packets.participant.ParticipantData;
import f1.data.parse.packets.participant.ParticipantDataFactory;
import f1.data.parse.packets.participant.ParticipantKey;
import f1.data.parse.packets.session.SessionInformationWrapper;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.ui.panels.dto.DriverDataDTO;
import f1.data.ui.panels.dto.SessionChangeDTO;
import f1.data.utils.Util;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Consumer;

public class ParticipantPacketHandler implements PacketHandler {

    private final int packetFormat;
    private int playerCarIndex;
    private final Consumer<DriverDataDTO> driverDataDTO;
    private final Consumer<SessionChangeDTO> sessionChangeDTO;
    private final SessionInformationWrapper sessionInformationWrapper;
    private final ParticipantDataFactory factory;

    public ParticipantPacketHandler(int packetFormat, int playerCarIndex, Consumer<DriverDataDTO> driverDataDTO, Consumer<SessionChangeDTO> sessionChangeDTO, SessionInformationWrapper sessionInformationWrapper) {
        this.playerCarIndex = playerCarIndex;
        this.packetFormat = packetFormat;
        this.driverDataDTO = driverDataDTO;
        this.sessionChangeDTO = sessionChangeDTO;
        this.sessionInformationWrapper = sessionInformationWrapper;
        this.factory = new ParticipantDataFactory(this.packetFormat);
    }

    public void processPacket(ByteBuffer byteBuffer) {
        List<ParticipantData> localList = new ArrayList<>();
        //Must process this first as its always above the actual packet content, at least from 2019 onwards.
        int numActiveCars = byteBuffer.get();
        //Loop over the packet and create objects for each record in the array.
        int arraySize = Util.findArraySize(this.packetFormat, this.playerCarIndex);
        for (int i = 0; i < arraySize; i++) {
            ParticipantData pd = factory.build(byteBuffer);
            ParticipantKey pk = new ParticipantKey(pd.driverId(), pd.raceNumber(), pd.networkId(), pd.lastName());
            //A race number of 0 means this is just a placeholder element, and shouldn't be added to the driver list.
            if (pd.raceNumber() > 0 && !this.sessionInformationWrapper.getDistinctParticipants().contains(pk)) {
                localList.add(pd);
                this.sessionInformationWrapper.getDistinctParticipants().add(pk);
            }
        }
        //If these two lists are different then we need to rebuild the objects, as we have had some kind of change to the participants.
        if (!localList.equals(this.sessionInformationWrapper.getParticipantDataList()) && !localList.isEmpty()) {
            this.sessionInformationWrapper.clearCollection();
            //Update the list to be the newly created list from the packet information
            this.sessionInformationWrapper.getParticipantDataList().addAll(localList);
            for (int i = 0; i < this.sessionInformationWrapper.getParticipantDataList().size(); i++) {
                ParticipantData pd = this.sessionInformationWrapper.getParticipantDataList().get(i);
                //Add a new record to the participants map with a new telemetry data object.
                this.sessionInformationWrapper.getParticipants().put(i, new TelemetryData(pd));
                //Rebuild the driverPairPerTeam map.
                Map<Integer, DriverPair> driverPairs = this.sessionInformationWrapper.getDriverPairPerTeam();
                if (driverPairs.containsKey(pd.teamId())) {
                    driverPairs.get(pd.teamId()).setDriverTwo(pd.driverId());
                } else {
                    driverPairs.put(pd.teamId(), new DriverPair(pd.driverId()));
                }
                //If this dto exists, then send an update to the UI.
                if (this.driverDataDTO != null)
                    driverDataDTO.accept(new DriverDataDTO(pd.driverId(), pd.lastName()));
            }
            //If this objects exists then we need to repopulate these params for the UI.
            if (this.sessionChangeDTO != null) {
                ParticipantData playerDriver = this.sessionInformationWrapper.getParticipantDataList().get(this.playerCarIndex);
                final int playerDriverId = playerDriver.driverId();
                //Use the player driver's team param to determine what team to look at for the teammate id.
                DriverPair driverPair = this.sessionInformationWrapper.getDriverPairPerTeam().get(playerDriver.teamId());
                //Teammate driver ID will be whatever id on the driver pair isn't the players driver id.
                final int teamMateDriverId = (playerDriverId == driverPair.getDriverOne()) ? driverPair.getDriverTwo() : driverPair.getDriverOne();
                this.sessionChangeDTO.accept(new SessionChangeDTO(playerDriverId, teamMateDriverId, numActiveCars, this.sessionInformationWrapper.getParticipantDataList()));
                //Update this object with participant data, so the session logic will trigger a new session.
                this.sessionInformationWrapper.updateDriverInfo(playerDriverId, teamMateDriverId, playerDriver.teamId());
            }
        }
    }

    public void setPlayerCarIndex(int playerCarIndex) {
        this.playerCarIndex = playerCarIndex;
    }
}

package f1.data.parse.packets.handlers;

import f1.data.mapKeys.DriverPair;
import f1.data.parse.packets.ParticipantData;
import f1.data.parse.packets.ParticipantDataFactory;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.ui.panels.dto.DriverDataDTO;
import f1.data.utils.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public class ParticipantPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;
    private final Consumer<DriverDataDTO> driverDataDTO;

    private int numActiveCars;
    private final List<ParticipantData> participantDataList = new ArrayList<>();
    private final Map<Integer, DriverPair> driverPairPerTeam = new TreeMap<>();

    public ParticipantPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, Consumer<DriverDataDTO> driverDataDTO) {
        this.packetFormat = packetFormat;
        this.participants = participants;
        this.driverDataDTO = driverDataDTO;
    }

    public ParticipantPacketHandler(int packetFormat) {
        this.packetFormat = packetFormat;
        this.participants = new TreeMap<>();
        this.driverDataDTO = null;
    }

    public void processPacket(ByteBuffer byteBuffer) {
        if (this.participants.isEmpty()) {
            //Must process this first as its always above the actual packet content, at least from 2020 onwards.
            numActiveCars = byteBuffer.get();
            //Loop over the packet and create objects for each record in the array.
            int arraySize = Util.findArraySize(this.packetFormat);
            for (int i = 0; i < arraySize; i++) {
                ParticipantData pd = ParticipantDataFactory.build(this.packetFormat, byteBuffer);
                this.participants.put(i, new TelemetryData(pd));
                //If race number isn't greater than 0 then its not an actual participant but a placeholder, so don't add to the list.
                if (pd.raceNumber() > 0) {
                    if (driverPairPerTeam.containsKey(pd.teamId())) {
                        driverPairPerTeam.get(pd.teamId()).setDriverTwo(pd.driverId());
                    } else {
                        driverPairPerTeam.put(pd.teamId(), new DriverPair(pd.driverId()));
                    }
                    this.participantDataList.add(pd);
                    if (this.driverDataDTO != null)
                        driverDataDTO.accept(new DriverDataDTO(pd.driverId(), pd.lastName()));
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
}

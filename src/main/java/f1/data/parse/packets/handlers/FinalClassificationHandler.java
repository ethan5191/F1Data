package f1.data.parse.packets.handlers;

import f1.data.parse.packets.FinalClassificationData;
import f1.data.parse.packets.FinalClassificationDataFactory;
import f1.data.parse.packets.session.SessionInformation;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.save.SaveSessionDataHandler;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinalClassificationHandler implements PacketHandler {

    private final int packetFormat;
    private final int playerCarIndex;
    private final Map<Integer, TelemetryData> participants;
    private final SessionInformation sessionInformation;
    private final FinalClassificationDataFactory factory;

    private int numCars;
    private final List<FinalClassificationData> fcdList = new ArrayList<>();

    public FinalClassificationHandler(int packetFormat, int playerCarIndex, Map<Integer, TelemetryData> participants, SessionInformation sessionInformation) {
        this.packetFormat = packetFormat;
        this.playerCarIndex = playerCarIndex;
        this.participants = participants;
        this.sessionInformation = sessionInformation;
        this.factory = new FinalClassificationDataFactory(this.packetFormat);
    }

    public void processPacket(ByteBuffer byteBuffer) {
        this.numCars = BitMaskUtils.bitMask8(byteBuffer.get());
        int arraySize = Util.findArraySize(this.packetFormat, this.playerCarIndex);
        for (int i = 0; i < arraySize; i++) {
            FinalClassificationData fcd = factory.build(byteBuffer);
            this.fcdList.add(fcd);
        }
        SaveSessionDataHandler.buildSaveData(this.packetFormat, sessionInformation.getName(), this.participants, true);
        //Clear the participants map, so the participants packet logic knows to rebuild it.
        this.participants.clear();
    }
}

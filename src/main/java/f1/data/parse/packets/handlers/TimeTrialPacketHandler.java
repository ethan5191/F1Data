package f1.data.parse.packets.handlers;

import f1.data.parse.packets.TimeTrialData;
import f1.data.parse.packets.TimeTrialDataFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TimeTrialPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final TimeTrialDataFactory factory;
    private final List<TimeTrialData> timeTrialDataList = new ArrayList<>();

    public TimeTrialPacketHandler(int packetFormat) {
        this.packetFormat = packetFormat;
        this.factory = new TimeTrialDataFactory(this.packetFormat);
    }

    public void processPacket(ByteBuffer byteBuffer) {
        final int numTimeTrialRecs = 3;
        for (int i = 0; i < numTimeTrialRecs; i++) {
            timeTrialDataList.add(factory.build(byteBuffer));
        }
    }
}

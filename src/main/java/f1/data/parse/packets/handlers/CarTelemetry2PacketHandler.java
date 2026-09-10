package f1.data.parse.packets.handlers;

import f1.data.parse.packets.CarTelemetryData2;
import f1.data.parse.packets.CarTelemetryData2Factory;
import f1.data.parse.packets.PacketUtils;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.Util;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarTelemetry2PacketHandler implements PacketHandler {

    private final int packetFormat;
    private final int playerCarIndex;
    private final Map<Integer, TelemetryData> participants;
    private final CarTelemetryData2Factory factory;

    public CarTelemetry2PacketHandler(int packetFormat, int playerCarIndex, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.playerCarIndex = playerCarIndex;
        this.participants = participants;
        this.factory = new CarTelemetryData2Factory(this.packetFormat);
    }

    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int arraySize = Util.findArraySize(this.packetFormat, this.playerCarIndex);
            for (int i = 0; i < arraySize; i++) {
                CarTelemetryData2 ctd = factory.build(byteBuffer);
                if (PacketUtils.validKey(participants, i)) {
                    participants.get(i).setCurrentTelemetry2(ctd);
                }
            }
        }
    }
}

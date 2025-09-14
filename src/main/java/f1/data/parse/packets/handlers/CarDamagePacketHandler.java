package f1.data.parse.packets.handlers;

import f1.data.parse.packets.CarDamageData;
import f1.data.parse.packets.CarDamageDataFactory;
import f1.data.parse.packets.PacketUtils;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.Util;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarDamagePacketHandler implements PacketHandler {

    private final int packetFormat;
    private final int playerCarIndex;
    private final Map<Integer, TelemetryData> participants;
    private final CarDamageDataFactory factory;

    public CarDamagePacketHandler(int packetFormat, int playerCarIndex, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.playerCarIndex = playerCarIndex;
        this.participants = participants;
        factory = new CarDamageDataFactory(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int arraySize = Util.findArraySize(this.packetFormat, this.playerCarIndex);
            for (int i = 0; i < arraySize; i++) {
                CarDamageData cdd = factory.build(byteBuffer);
                if (PacketUtils.validKey(participants, i)) {
                    participants.get(i).setCurrentDamage(cdd);
                }
            }
        }
    }
}

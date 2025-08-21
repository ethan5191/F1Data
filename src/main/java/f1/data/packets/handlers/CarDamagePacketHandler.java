package f1.data.packets.handlers;

import f1.data.packets.CarDamageData;
import f1.data.packets.CarDamageDataFactory;
import f1.data.packets.PacketUtils;
import f1.data.telemetry.TelemetryData;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarDamagePacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;

    public CarDamagePacketHandler(int packetFormat, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.participants = participants;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                CarDamageData cdd = CarDamageDataFactory.build(packetFormat, byteBuffer);
                if (PacketUtils.validKey(participants, i)) {
                    participants.get(i).setCurrentDamage(cdd);
                }
            }
        }
    }
}

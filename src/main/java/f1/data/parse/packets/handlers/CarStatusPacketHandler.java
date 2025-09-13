package f1.data.parse.packets.handlers;

import f1.data.parse.packets.CarDamageData;
import f1.data.parse.packets.CarStatusData;
import f1.data.parse.packets.CarStatusDataFactory;
import f1.data.parse.packets.PacketUtils;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.Util;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarStatusPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final int playerCarIndex;
    private final Map<Integer, TelemetryData> participants;
    private final CarStatusDataFactory factory;

    public CarStatusPacketHandler(int packetFormat, int playerCarIndex, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.playerCarIndex = playerCarIndex;
        this.participants = participants;
        this.factory = new CarStatusDataFactory(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int arraySize = Util.findArraySize(this.packetFormat, this.playerCarIndex);
            for (int i = 0; i < arraySize; i++) {
                CarStatusData csd = factory.build(byteBuffer);
                if (PacketUtils.validKey(participants, i)) {
                    participants.get(i).setCurrentStatus(csd);
                    if (packetFormat <= Constants.YEAR_2020) {
                        CarDamageData cdd = CarDamageData.fromStatus(csd);
                        participants.get(i).setCurrentDamage(cdd);
                    }
                }
            }
        }
    }
}

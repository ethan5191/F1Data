package f1.data.parse.packets.handlers;

import f1.data.parse.packets.TireSetsData;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class TireSetsPacketHandler implements PacketHandler {

    private final Map<Integer, TelemetryData> participants;

    public TireSetsPacketHandler(Map<Integer, TelemetryData> participants) {
        this.participants = participants;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int carId = BitMaskUtils.bitMask8(byteBuffer.get());
            TelemetryData td = participants.get(carId);
            TireSetsData[] tireSetsData = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];
            for (int i = 0; i < Constants.TIRE_SETS_PACKET_COUNT; i++) {
                tireSetsData[i] = new TireSetsData(byteBuffer);
            }
            td.setTireSetsData(tireSetsData);
            int fittedId = BitMaskUtils.bitMask8(byteBuffer.get());
            if (td.getFittedTireId() != fittedId) {
                td.setFittedTireId(fittedId);
            }
        }
    }
}

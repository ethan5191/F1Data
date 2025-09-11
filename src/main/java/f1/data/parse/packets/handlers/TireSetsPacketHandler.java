package f1.data.parse.packets.handlers;

import f1.data.parse.packets.TireSetsDataFactory;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;
import java.util.Map;

public class TireSetsPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;
    private final TireSetsDataFactory factory;

    public TireSetsPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.participants = participants;
        this.factory = new TireSetsDataFactory(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int carId = BitMaskUtils.bitMask8(byteBuffer.get());
            TelemetryData td = participants.get(carId);
            td.setTireSetsData(factory.build(byteBuffer));
            int fittedId = BitMaskUtils.bitMask8(byteBuffer.get());
            if (td.getCarSetupData().getFittedTireId() != fittedId) {
                td.getCarSetupData().setFittedTireId(fittedId);
            }
        }
    }
}

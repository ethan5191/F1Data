package f1.data.parse.packets.handlers;

import f1.data.parse.packets.CarTelemetryData;
import f1.data.parse.packets.CarTelemetryDataFactory;
import f1.data.parse.packets.PacketUtils;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.Util;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarTelemetryPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;
    private final CarTelemetryDataFactory factory;

    public CarTelemetryPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.participants = participants;
        this.factory = new CarTelemetryDataFactory(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int arraySize = Util.findArraySize(this.packetFormat);
            for (int i = 0; i < arraySize; i++) {
                CarTelemetryData ctd = factory.build(byteBuffer);
                if (PacketUtils.validKey(participants, i)) {
                    participants.get(i).setCurrentTelemetry(ctd);
                }
            }
        }
        //Params at the end of the Telemetry packet, not associated with each car. Keep here to ensure the byteBuffer position is moved correctly.
        //For 2020 the button event was attached at the end of the telemetry packet, not the event packet.
        if (packetFormat <= Constants.YEAR_2020) {
            EventPacketHandler.handle2020ButtonEvent(packetFormat, byteBuffer, participants);
        }
        if (packetFormat >= Constants.YEAR_2020) {
            int mfdPanelIdx = BitMaskUtils.bitMask8(byteBuffer.get());
            int mfdPanelIdxSecondPlayer = BitMaskUtils.bitMask8(byteBuffer.get());
            int suggestedGear = byteBuffer.get();
        }
    }
}

package f1.data.parse.packets.handlers;

import f1.data.parse.packets.CarTelemetryData;
import f1.data.parse.packets.CarTelemetryDataFactory;
import f1.data.parse.packets.PacketUtils;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarTelemetryPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;

    public CarTelemetryPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.participants = participants;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                CarTelemetryData ctd = CarTelemetryDataFactory.build(packetFormat, byteBuffer);
                if (PacketUtils.validKey(participants, i)) {
                    participants.get(i).setCurrentTelemetry(ctd);
                }
            }
        }
        //Params at the end of the Telemetry packet, not associated with each car. Keep here to ensure the byteBuffer position is moved correctly.
        if (packetFormat <= Constants.YEAR_2020) {
            long buttonEvent = BitMaskUtils.bitMask32(byteBuffer.getInt());
            //2020 special button press mapped to button 9 on the McLaren wheel. Used to log the # of packets recieved.
//            if (buttonEvent == 8192) {
//                for (int i = 0; i < packetCounts.length; i++) {
//                    logger.info("Packet # {} Count {}", PacketTypeEnum.findByValue(i).name(), packetCounts[i][0]);
//                }
//            }
        }
        int mfdPanelIdx = BitMaskUtils.bitMask8(byteBuffer.get());
        int mfdPanelIdxSecondPlayer = BitMaskUtils.bitMask8(byteBuffer.get());
        int suggestedGear = byteBuffer.get();
    }
}

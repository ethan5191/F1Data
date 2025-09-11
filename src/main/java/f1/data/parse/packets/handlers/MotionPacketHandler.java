package f1.data.parse.packets.handlers;

import f1.data.parse.packets.MotionData;
import f1.data.parse.packets.MotionDataFactory;
import f1.data.parse.packets.MotionExData;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.Util;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class MotionPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;

    public MotionPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.participants = participants;
    }

    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int arraySize = Util.findArraySize(this.packetFormat);
            for (int i = 0; i < arraySize; i++) {
                MotionData md = MotionDataFactory.build(this.packetFormat, byteBuffer);
            }
            //Params existed OUTSIDE of the main array in the struct until 2023 when they went away.
            if (packetFormat <= Constants.YEAR_2022) {
                MotionExData med = MotionDataFactory.buildLegacy(byteBuffer);
            }
        }
    }
}

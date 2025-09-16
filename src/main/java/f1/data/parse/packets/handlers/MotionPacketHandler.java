package f1.data.parse.packets.handlers;

import f1.data.enums.SupportedYearsEnum;
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
    private final int playerCarIndex;
    private final Map<Integer, TelemetryData> participants;
    private final MotionDataFactory factory;
    private final SupportedYearsEnum supportedYearsEnum;

    public MotionPacketHandler(int packetFormat, int playerCarIndex, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.playerCarIndex = playerCarIndex;
        this.participants = participants;
        this.factory = new MotionDataFactory(this.packetFormat);
        this.supportedYearsEnum = SupportedYearsEnum.fromYear(this.packetFormat);
    }

    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int arraySize = Util.findArraySize(this.packetFormat, this.playerCarIndex);
            for (int i = 0; i < arraySize; i++) {
                MotionData md = factory.build(byteBuffer);
            }
            //Params existed OUTSIDE the main array in the struct until 2023 when they went away.
            if (this.supportedYearsEnum.is2022OrEarlier()) {
                MotionExData med = factory.buildLegacy(byteBuffer);
            }
        }
    }
}

package f1.data.parse.packets.handlers;

import f1.data.parse.packets.MotionData;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.ParseUtils;
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
            for (int i = 0; i < Constants.F1_25_AND_EARLIER_CAR_COUNT; i++) {
                MotionData md = new MotionData(byteBuffer);
            }
            //Params existed OUTSIDE of the main array in the struct until 2023 when they went away.
            if (packetFormat <= Constants.YEAR_2022) {
                float[] suspPosition = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float[] suspVelocity = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float[] suspAcceleration = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float[] wheelSpin = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float[] wheelSlip = ParseUtils.parseFloatArray(byteBuffer, new float[4]);
                float localVelocityX = byteBuffer.getFloat();
                float localVelocityY = byteBuffer.getFloat();
                float localVelocityZ = byteBuffer.getFloat();
                float angularVelocityX = byteBuffer.getFloat();
                float angularVelocityY = byteBuffer.getFloat();
                float angularVelocityZ = byteBuffer.getFloat();
                float angularAccelerationX = byteBuffer.getFloat();
                float angularAccelerationY = byteBuffer.getFloat();
                float angularAccelerationZ = byteBuffer.getFloat();
                float frontWheelsAngle = byteBuffer.getFloat();
            }
        }
    }
}

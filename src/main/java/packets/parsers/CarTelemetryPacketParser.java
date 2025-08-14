package packets.parsers;

import packets.CarTelemetryData;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarTelemetryPacketParser {

    public static CarTelemetryData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        CarTelemetryData.Builder builder = new CarTelemetryData.Builder()
                .setSpeed(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setThrottle(byteBuffer.getFloat())
                .setSteer(byteBuffer.getFloat())
                .setBrake(byteBuffer.getFloat())
                .setClutch(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setGear(byteBuffer.get())
                .setEngineRPM(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setDrs(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setRevLightPercent(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2021)
            builder.setRevLightBitVal(BitMaskUtils.bitMask16(byteBuffer.getShort()));
        builder.setBrakeTemps(populateArray(byteBuffer))
                .setTireSurfaceTemps(populateArray2(byteBuffer))
                .setTireInnerTemps(populateArray2(byteBuffer))
                .setEngineTemp(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setTirePressure(populateArray3(byteBuffer))
                .setSurfaceType(populateArray2(byteBuffer));
        return builder.build();
    }

    private static int[] populateArray(ByteBuffer byteBuffer) {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = BitMaskUtils.bitMask16(byteBuffer.getShort());
        }
        return result;
    }

    private static int[] populateArray2(ByteBuffer byteBuffer) {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = BitMaskUtils.bitMask8(byteBuffer.get());
        }
        return result;
    }

    private static float[] populateArray3(ByteBuffer byteBuffer) {
        float[] result = new float[4];
        for (int i = 0; i < 4; i++) {
            result[i] = byteBuffer.getFloat();
        }
        return result;
    }
}

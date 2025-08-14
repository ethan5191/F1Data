package packets.parsers;

import packets.MotionData;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class MotionDataPacketParser {

    public static MotionData parsePacket(ByteBuffer byteBuffer) {
        MotionData.Builder builder = new MotionData.Builder()
                .setWorldPositionX(determineFloatValue(byteBuffer.getFloat()))
                .setWorldPositionY(determineFloatValue(byteBuffer.getFloat()))
                .setWorldPositionZ(determineFloatValue(byteBuffer.getFloat()))
                .setWorldVelocityX(determineFloatValue(byteBuffer.getFloat()))
                .setWorldVelocityY(determineFloatValue(byteBuffer.getFloat()))
                .setWorldVelocityZ(determineFloatValue(byteBuffer.getFloat()))
                .setWorldForwardDirX(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setWorldForwardDirY(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setWorldForwardDirZ(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setWorldRightDirX(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setWorldRightDirY(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setWorldRightDirZ(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setgForceLat(determineFloatValue(byteBuffer.getFloat()))
                .setgForceLon(determineFloatValue(byteBuffer.getFloat()))
                .setgForceVer(determineFloatValue(byteBuffer.getFloat()))
                .setYaw(determineFloatValue(byteBuffer.getFloat()))
                .setPitch(determineFloatValue(byteBuffer.getFloat()))
                .setRoll(determineFloatValue(byteBuffer.getFloat()));

        return builder.build();
    }

    public static float[] parseFloatArray(ByteBuffer byteBuffer, float[] original) {
        for (int j = 0; j < original.length; j++) {
            original[j] = byteBuffer.getFloat();
        }
        return original;
    }

    private static float determineFloatValue(float val) {
        return val / Constants.DIVISOR;
    }
}

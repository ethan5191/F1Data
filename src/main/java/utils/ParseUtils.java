package utils;

import java.nio.ByteBuffer;

public class ParseUtils {

    public static float[] parseFloatArray(ByteBuffer byteBuffer, float[] original) {
        for (int j = 0; j < original.length; j++) {
            original[j] = byteBuffer.getFloat();
        }
        return original;
    }

    public static float[] parseFloatArray(ByteBuffer byteBuffer, int length) {
        float[] floatArray = new float[length];
        for (int i = 0; i < length; i++) floatArray[i] = byteBuffer.getFloat();
        return floatArray;
    }

    public static int[] parseIntArray(ByteBuffer byteBuffer, int length) {
        int[] intArray = new int[length];
        for (int i = 0; i < length; i++) intArray[i] = BitMaskUtils.bitMask8(byteBuffer.get());
        return intArray;
    }
}

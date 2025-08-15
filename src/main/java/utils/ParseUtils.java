package utils;

import java.nio.ByteBuffer;

public class ParseUtils {

    public static float[] parseFloatArray(ByteBuffer byteBuffer, float[] original) {
        for (int j = 0; j < original.length; j++) {
            original[j] = byteBuffer.getFloat();
        }
        return original;
    }
}

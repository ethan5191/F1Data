package utils;

import utils.constants.Constants;

import java.math.BigInteger;

public class BitMaskUtils {

    public static Integer bitMask8(byte value) {
        return value & Constants.BIT_MASK_8;
    }

    public static Integer bitMask16(short value) {
        return value & Constants.BIT_MASK_16;
    }

    public static Long bitMask32(int value) {
        return value & Constants.BIT_MASK_32;
    }

    public static BigInteger bitMask64(long value) {
        return BigInteger.valueOf(value).and(BigInteger.ONE.shiftLeft(64).subtract(BigInteger.ONE));
    }
}

package packets.parsers;

import utils.constants.Constants;

import java.math.BigInteger;

public class ParserUtils {

    protected static Integer bitMask8(byte value) {
        return value & Constants.BIT_MASK_8;
    }

    protected static Integer bitMask16(short value) {
        return value & Constants.BIT_MASK_16;
    }

    protected static Long bitMask32(int value) {
        return value & Constants.BIT_MASK_32;
    }

    protected static BigInteger bitMask64(long value) {
        return BigInteger.valueOf(value).and(BigInteger.ONE.shiftLeft(64).subtract(BigInteger.ONE));
    }
}

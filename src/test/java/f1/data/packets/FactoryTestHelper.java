package f1.data.packets;

import f1.data.utils.ParseUtils;
import org.mockito.MockedStatic;

import java.nio.ByteBuffer;

public class FactoryTestHelper {

    protected static void parseFloatArray(ByteBuffer mockByteBuffer, MockedStatic<ParseUtils> parseUtils) {
        parseUtils.when(() -> ParseUtils.parseFloatArray(mockByteBuffer, 4)).thenReturn(new float[4]);
    }

    protected static void parseIntArray(ByteBuffer mockByteBuffer,MockedStatic<ParseUtils> parseUtils) {
        parseUtils.when(() -> ParseUtils.parseIntArray(mockByteBuffer, 4)).thenReturn(new int[4]);
    }

    protected static void parseShortArray(ByteBuffer mockByteBuffer, MockedStatic<ParseUtils> parseUtils) {
        parseUtils.when(() -> ParseUtils.parseShortArray(mockByteBuffer, 4)).thenReturn(new int[4]);
    }
}

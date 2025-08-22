package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import org.mockito.MockedStatic;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.mockito.AdditionalAnswers.returnsElementsOf;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class FactoryTestHelper {

    protected static void mockBitMask8(MockedStatic<BitMaskUtils> bitMaskUtils, int count) {
        int bit8Start = 1;
        List<Integer> integerValues = IntStream.rangeClosed(bit8Start, count)
                .boxed()
                .toList();
        bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte()))
                .thenAnswer(returnsElementsOf(integerValues));
    }

    protected static void mockBitMask16(MockedStatic<BitMaskUtils> bitMaskUtils, int count) {
        int bit16Start = 50;
        List<Integer> integerValues = IntStream.rangeClosed(bit16Start, (bit16Start + count) - 1)
                .boxed()
                .toList();
        bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort()))
                .thenAnswer(returnsElementsOf(integerValues));
    }

    protected static void mockBitMask32(MockedStatic<BitMaskUtils> bitMaskUtils, int count) {
        int bit32Start = 200;
        List<Long> longValues = LongStream.rangeClosed(bit32Start, (bit32Start + count) - 1).boxed().toList();
        bitMaskUtils.when(() -> BitMaskUtils.bitMask32(anyInt())).thenAnswer(returnsElementsOf(longValues));
    }

    protected static void mockFloatValues(ByteBuffer mockByteBuffer, int count) {
        int floatStart = 100;
        List<Float> floatValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            floatValues.add((float) floatStart + i);
        }
        when(mockByteBuffer.getFloat()).thenAnswer(returnsElementsOf(floatValues));
    }

    protected static void mockSingleGetValue(ByteBuffer mockByteBuffer, int count) {
        when(mockByteBuffer.get()).thenReturn((byte) ((byte) count + 1));
    }

    protected static void parseFloatArray(ByteBuffer mockByteBuffer, MockedStatic<ParseUtils> parseUtils) {
        parseUtils.when(() -> ParseUtils.parseFloatArray(mockByteBuffer, 4)).thenReturn(new float[4]);
    }

    protected static void parseIntArray(ByteBuffer mockByteBuffer, MockedStatic<ParseUtils> parseUtils, int length) {
        parseUtils.when(() -> ParseUtils.parseIntArray(mockByteBuffer, 4)).thenReturn(new int[4]);
    }

    protected static void parseShortArray(ByteBuffer mockByteBuffer, MockedStatic<ParseUtils> parseUtils) {
        parseUtils.when(() -> ParseUtils.parseShortArray(mockByteBuffer, 4)).thenReturn(new int[4]);
    }
}

package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsElementsOf;
import static org.mockito.Mockito.*;

public abstract class AbstractFactoryTest {

    protected ByteBuffer mockByteBuffer;
    private final int bit8Start = 1;
    private final int bit16Start = 50;

    @BeforeEach
    void setUp() {
        mockByteBuffer = mock(ByteBuffer.class);
    }

    protected MockedStatic<BitMaskUtils> mockBitMask8(int count) {
        List<Integer> integerValues = IntStream.rangeClosed(bit8Start, count)
                .boxed()
                .toList();
        MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
        bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte()))
                .thenAnswer(returnsElementsOf(integerValues));
        return bitMaskUtils;
    }

    protected MockedStatic<BitMaskUtils> mockBitMask8And16(int bit8Count, int bit16Count) {
        List<Integer> bit8Values = IntStream.rangeClosed(bit8Start, bit8Count)
                .boxed()
                .toList();
        List<Integer> bit16Values = IntStream.rangeClosed(bit16Start, (bit16Start + bit16Count) - 1)
                .boxed()
                .toList();
        MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
        bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte()))
                .thenAnswer(returnsElementsOf(bit8Values));
        bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort()))
                .thenAnswer(returnsElementsOf(bit16Values));
        return bitMaskUtils;
    }

    @Test
    @DisplayName("Should throw IllegalStateException for unsupported packet format")
    void testBuild_UnsupportedPacketFormat() {
        int unsupportedFormat = 9999;
        short shortValue = (short) unsupportedFormat;
        when(mockByteBuffer.getShort()).thenReturn(shortValue);

        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(shortValue)).thenReturn(unsupportedFormat);

            // Act & Assert
            IllegalStateException exception = assertThrows(IllegalStateException.class,
                    () -> PacketHeaderFactory.build(mockByteBuffer));

            assertEquals("Games Packet Format did not match an accepted format (2020 - 2025)",
                    exception.getMessage());
        }
    }
}

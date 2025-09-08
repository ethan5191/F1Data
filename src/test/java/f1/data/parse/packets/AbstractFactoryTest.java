package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public abstract class AbstractFactoryTest {

    protected ByteBuffer mockByteBuffer;
    protected final int BIT_8_START = 1;
    protected final int BIT_16_START = 50;
    protected final int BIT_32_START = 200;
    protected final int FLOAT_START = 100;
    protected final float DOUBLE_START = 300;

    @BeforeEach
    void setUp() {
        mockByteBuffer = mock(ByteBuffer.class);
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

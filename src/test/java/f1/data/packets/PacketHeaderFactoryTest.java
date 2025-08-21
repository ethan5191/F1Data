package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacketHeaderFactoryTest {

    private ByteBuffer mockByteBuffer;

    @BeforeEach
    void setUp() {
        mockByteBuffer = mock(ByteBuffer.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds Packet Header for 2020 - 2022.")
    void testBuild_header2020To2022(int packetFormat) {
        short packetShort = (short) packetFormat;

        when(mockByteBuffer.getShort()).thenReturn(packetShort, (short) 1, (short) 2, (short) 3, (short) 4);
        when(mockByteBuffer.getLong()).thenReturn(12345L, 123456L);
        when(mockByteBuffer.getFloat()).thenReturn(1234F);
        when(mockByteBuffer.get()).thenReturn((byte) 5, (byte) 6);

        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(packetFormat);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3, 4, 5, 6);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask32(anyInt())).thenReturn(123456L);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask64(anyLong())).thenReturn(BigInteger.valueOf(12345L));

            PacketHeader result = PacketHeaderFactory.build(mockByteBuffer);

            assertNotNull(result);
            assertEquals(packetFormat, result.packetFormat());
            assertEquals(1, result.majorVersion());
            assertEquals(2, result.minorVersion());
            assertEquals(3, result.packetVersion());
            assertEquals(4, result.packetId());
            assertEquals(BigInteger.valueOf(12345L), result.sessionUID());
            assertEquals(1234F, result.sessionTime());
            assertEquals(123456L, result.frameID());
            assertEquals(5, result.playerCarIndex());
            assertEquals(6, result.secondaryPlayerCarIndex());
            assertEquals(0, result.gameYear());
            assertEquals(0L, result.overallFrameID());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds Packet Header for 2023 - 2025.")
    void testBuild_header2023To2025(int packetFormat) {
        short packetShort = (short) packetFormat;

        when(mockByteBuffer.getShort()).thenReturn(packetShort, (short) 1, (short) 2, (short) 3, (short) 4, (short) 5);
        when(mockByteBuffer.getLong()).thenReturn(12345L, 123456L, 1234567L);
        when(mockByteBuffer.getFloat()).thenReturn(1234F);
        when(mockByteBuffer.get()).thenReturn((byte) 6, (byte) 7);

        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(packetFormat);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3, 4, 5, 6, 7);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask32(anyInt())).thenReturn(123456L, 1234567L);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask64(anyLong())).thenReturn(BigInteger.valueOf(12345L));

            PacketHeader result = PacketHeaderFactory.build(mockByteBuffer);

            assertNotNull(result);
            assertEquals(packetFormat, result.packetFormat());
            assertEquals(2, result.majorVersion());
            assertEquals(3, result.minorVersion());
            assertEquals(4, result.packetVersion());
            assertEquals(5, result.packetId());
            assertEquals(BigInteger.valueOf(12345L), result.sessionUID());
            assertEquals(1234F, result.sessionTime());
            assertEquals(123456L, result.frameID());
            assertEquals(6, result.playerCarIndex());
            assertEquals(7, result.secondaryPlayerCarIndex());
            assertEquals(1, result.gameYear());
            assertEquals(1234567L, result.overallFrameID());
        }
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

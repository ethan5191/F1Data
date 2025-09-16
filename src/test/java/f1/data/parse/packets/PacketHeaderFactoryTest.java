package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PacketHeaderFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("supportedYears2019")
    @DisplayName("Builds Packet Header for 2019.")
    void testBuild_header2019(int packetFormat) {
        int bitMask8Count = 5;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(packetFormat);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask32(anyInt())).thenReturn(123456L);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask64(anyLong())).thenReturn(BigInteger.valueOf(12345L));
            when(mockByteBuffer.getFloat()).thenReturn(1234F);

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

            assertEquals(0, result.secondaryPlayerCarIndex());
            assertEquals(0, result.gameYear());
            assertEquals(0L, result.overallFrameID());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds Packet Header for 2020 - 2022.")
    void testBuild_header2020To2022(int packetFormat) {
        int bitMask8Count = 6;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(packetFormat);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask32(anyInt())).thenReturn(123456L);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask64(anyLong())).thenReturn(BigInteger.valueOf(12345L));
            when(mockByteBuffer.getFloat()).thenReturn(1234F);

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
    @DisplayName("Builds Packet Header for 2023 to Present.")
    void testBuild_header2023ToPresent(int packetFormat) {
        int bitMask8Count = 7;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(packetFormat);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask32(anyInt())).thenReturn(123456L, 1234567L);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask64(anyLong())).thenReturn(BigInteger.valueOf(12345L));
            when(mockByteBuffer.getFloat()).thenReturn(1234F);

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
}

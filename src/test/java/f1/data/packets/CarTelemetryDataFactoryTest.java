package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class CarTelemetryDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2020)
    @DisplayName("Builds the Car Telemetry Data for 2020.")
    void testBuild_carTelemetry2020(int packetFormat) {
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            parseUtils.when(() -> ParseUtils.parseFloatArray(mockByteBuffer, 4)).thenReturn(new float[4]);
            parseUtils.when(() -> ParseUtils.parseIntArray(mockByteBuffer, 4)).thenReturn(new int[4]);
            parseUtils.when(() -> ParseUtils.parseShortArray(mockByteBuffer, 4)).thenReturn(new int[4]);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(1, 2, 3);
            when(mockByteBuffer.get()).thenReturn((byte) 4);
            when(mockByteBuffer.getFloat()).thenReturn((float) 1, (float) 2, (float) 3);

            CarTelemetryData result = CarTelemetryDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(1, result.speed());
            assertEquals(1, result.throttle());
            assertEquals(2, result.steer());
            assertEquals(3, result.brake());
            assertEquals(1, result.clutch());
            assertEquals(4, result.gear());
            assertEquals(2, result.engineRPM());
            assertEquals(2, result.drs());
            assertEquals(3, result.revLightPercent());
            assertArrayEquals(new int[4], result.brakeTemps());
            assertArrayEquals(new int[4], result.tireSurfaceTemps());
            assertArrayEquals(new int[4], result.tireInnerTemps());
            assertEquals(3, result.engineTemp());
            assertArrayEquals(new float[4], result.tirePressure());
            assertArrayEquals(new int[4], result.surfaceType());
            assertEquals(0, result.revLightBitVal());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Car Telemetry Data for 2021 to Present.")
    void testBuild_carTelemetry2021ToPresent(int packetFormat) {
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            parseUtils.when(() -> ParseUtils.parseFloatArray(mockByteBuffer, 4)).thenReturn(new float[4]);
            parseUtils.when(() -> ParseUtils.parseIntArray(mockByteBuffer, 4)).thenReturn(new int[4]);
            parseUtils.when(() -> ParseUtils.parseShortArray(mockByteBuffer, 4)).thenReturn(new int[4]);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(1, 2, 3, 4);
            when(mockByteBuffer.get()).thenReturn((byte) 4);
            when(mockByteBuffer.getFloat()).thenReturn((float) 1, (float) 2, (float) 3);

            CarTelemetryData result = CarTelemetryDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(1, result.speed());
            assertEquals(1, result.throttle());
            assertEquals(2, result.steer());
            assertEquals(3, result.brake());
            assertEquals(1, result.clutch());
            assertEquals(4, result.gear());
            assertEquals(2, result.engineRPM());
            assertEquals(2, result.drs());
            assertEquals(3, result.revLightPercent());
            assertEquals(3, result.revLightBitVal());
            assertArrayEquals(new int[4], result.brakeTemps());
            assertArrayEquals(new int[4], result.tireSurfaceTemps());
            assertArrayEquals(new int[4], result.tireInnerTemps());
            assertEquals(4, result.engineTemp());
            assertArrayEquals(new float[4], result.tirePressure());
            assertArrayEquals(new int[4], result.surfaceType());
        }
    }
}

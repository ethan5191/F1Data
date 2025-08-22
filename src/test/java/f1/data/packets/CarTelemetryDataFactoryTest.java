package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class CarTelemetryDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2020)
    @DisplayName("Builds the Car Telemetry Data for 2020.")
    void testBuild_carTelemetry2020(int packetFormat) {
        int bitMask8Count = 3;
        int bitMask16Count = 3;
        int floatCount = 3;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            FactoryTestHelper.parseShortArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarTelemetryData result = CarTelemetryDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(50, result.speed());
            assertEquals(100, result.throttle());
            assertEquals(101, result.steer());
            assertEquals(102, result.brake());
            assertEquals(1, result.clutch());
            assertEquals(4, result.gear());
            assertEquals(51, result.engineRPM());
            assertEquals(2, result.drs());
            assertEquals(3, result.revLightPercent());
            assertArrayEquals(new int[4], result.brakeTemps());
            assertArrayEquals(new int[4], result.tireSurfaceTemps());
            assertArrayEquals(new int[4], result.tireInnerTemps());
            assertEquals(52, result.engineTemp());
            assertArrayEquals(new float[4], result.tirePressure());
            assertArrayEquals(new int[4], result.surfaceType());
            assertEquals(0, result.revLightBitVal());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Car Telemetry Data for 2021 to Present.")
    void testBuild_carTelemetry2021ToPresent(int packetFormat) {
        int bitMask8Count = 3;
        int bitMask16Count = 4;
        int floatCount = 3;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            FactoryTestHelper.parseShortArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarTelemetryData result = CarTelemetryDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(50, result.speed());
            assertEquals(100, result.throttle());
            assertEquals(101, result.steer());
            assertEquals(102, result.brake());
            assertEquals(1, result.clutch());
            assertEquals(4, result.gear());
            assertEquals(51, result.engineRPM());
            assertEquals(2, result.drs());
            assertEquals(3, result.revLightPercent());
            assertEquals(52, result.revLightBitVal());
            assertArrayEquals(new int[4], result.brakeTemps());
            assertArrayEquals(new int[4], result.tireSurfaceTemps());
            assertArrayEquals(new int[4], result.tireInnerTemps());
            assertEquals(53, result.engineTemp());
            assertArrayEquals(new float[4], result.tirePressure());
            assertArrayEquals(new int[4], result.surfaceType());
        }
    }
}

package f1.data.parse.packets;

import f1.data.parse.packets.events.ButtonsData;
import f1.data.parse.packets.events.ButtonsDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class CarTelemetryDataFactoryTest extends AbstractFactoryTest {

    private final int[] intArray = new int[4];
    private final float[] floatArray = new float[4];

    @ParameterizedTest
    @MethodSource("supportedYears2019")
    @DisplayName("Builds the Car Telemetry Data for 2019.")
    void testBuild_carTelemetry2019(int packetFormat) {
        int bitMask8Count = 3;
        int bitMask16Count = 3;
        int bitMask32Count = 1;
        int floatCount = 3;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            FactoryTestHelper.parseShortArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarTelemetryData result = new CarTelemetryDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask16Value++, result.speed());
            assertEquals(floatValue++, result.throttle());
            assertEquals(floatValue++, result.steer());
            assertEquals(floatValue++, result.brake());
            assertEquals(bitMask8Value++, result.clutch());
            assertEquals(bitMask8Count + 1, result.gear());
            assertEquals(bitMask16Value++, result.engineRPM());
            assertEquals(bitMask8Value++, result.drs());
            assertEquals(bitMask8Value++, result.revLightPercent());
            assertArrayEquals(intArray, result.brakeTemps());
            assertArrayEquals(intArray, result.tireSurfaceTemps());
            assertArrayEquals(intArray, result.tireInnerTemps());
            assertEquals(bitMask16Value++, result.engineTemp());
            assertArrayEquals(floatArray, result.tirePressure());
            assertArrayEquals(intArray, result.surfaceType());
            assertEquals(0, result.revLightBitVal());

            legacyButtonEvent(packetFormat);
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2020")
    @DisplayName("Builds the Car Telemetry Data for 2020.")
    void testBuild_carTelemetry2020(int packetFormat) {
        int bitMask8Count = 3;
        int bitMask16Count = 3;
        int bitMask32Count = 1;
        int floatCount = 3;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            FactoryTestHelper.parseShortArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarTelemetryData result = new CarTelemetryDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask16Value++, result.speed());
            assertEquals(floatValue++, result.throttle());
            assertEquals(floatValue++, result.steer());
            assertEquals(floatValue++, result.brake());
            assertEquals(bitMask8Value++, result.clutch());
            assertEquals(bitMask8Count + 1, result.gear());
            assertEquals(bitMask16Value++, result.engineRPM());
            assertEquals(bitMask8Value++, result.drs());
            assertEquals(bitMask8Value++, result.revLightPercent());
            assertArrayEquals(intArray, result.brakeTemps());
            assertArrayEquals(intArray, result.tireSurfaceTemps());
            assertArrayEquals(intArray, result.tireInnerTemps());
            assertEquals(bitMask16Value++, result.engineTemp());
            assertArrayEquals(floatArray, result.tirePressure());
            assertArrayEquals(intArray, result.surfaceType());
            assertEquals(0, result.revLightBitVal());

            legacyButtonEvent(packetFormat);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Car Telemetry Data for 2021 to Present.")
    void testBuild_carTelemetry2021ToPresent(int packetFormat) {
        int bitMask8Count = 3;
        int bitMask16Count = 4;
        int floatCount = 3;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            FactoryTestHelper.parseShortArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarTelemetryData result = new CarTelemetryDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask16Value++, result.speed());
            assertEquals(floatValue++, result.throttle());
            assertEquals(floatValue++, result.steer());
            assertEquals(floatValue++, result.brake());
            assertEquals(bitMask8Value++, result.clutch());
            assertEquals(bitMask8Count + 1, result.gear());
            assertEquals(bitMask16Value++, result.engineRPM());
            assertEquals(bitMask8Value++, result.drs());
            assertEquals(bitMask8Value++, result.revLightPercent());
            assertEquals(bitMask16Value++, result.revLightBitVal());
            assertArrayEquals(intArray, result.brakeTemps());
            assertArrayEquals(intArray, result.tireSurfaceTemps());
            assertArrayEquals(intArray, result.tireInnerTemps());
            assertEquals(bitMask16Value++, result.engineTemp());
            assertArrayEquals(floatArray, result.tirePressure());
            assertArrayEquals(intArray, result.surfaceType());
        }
    }

    private void legacyButtonEvent(int packetFormat) {
        ButtonsData button = new ButtonsDataFactory(packetFormat).build(mockByteBuffer);
        assertNotNull(button);
        assertEquals(BIT_32_START, button.buttonsStatus());
    }
}

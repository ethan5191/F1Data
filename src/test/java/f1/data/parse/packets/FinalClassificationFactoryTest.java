package f1.data.parse.packets;

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

public class FinalClassificationFactoryTest extends AbstractFactoryTest {

    private final int[] intArray = new int[8];

    @ParameterizedTest
    @MethodSource("supportedYears2020")
    @DisplayName("Builds the Final Classification for 2020.")
    void testBuild_finalClassificationData2020(int packetFormat) {
        int bitMask8Count = 11;
        int floatCount = 1;
        int doubleCount = 1;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.mockDoubleValues(mockByteBuffer, doubleCount);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 8);
            FinalClassificationData result = new FinalClassificationDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertEquals(bitMask8Value++, result.position());
            assertEquals(bitMask8Value++, result.numLaps());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.points());
            assertEquals(bitMask8Value++, result.numPitsStops());
            assertEquals(bitMask8Value++, result.resultStatus());
            assertEquals(FLOAT_START, result.bestLapTime20());
            assertEquals(DOUBLE_START, result.totalRaceTime());
            assertEquals(bitMask8Value++, result.penaltiesTime());
            assertEquals(bitMask8Value++, result.numPenalties());
            assertEquals(bitMask8Value++, result.numTyreStints());
            assertArrayEquals(intArray, result.tyreStintsActual());
            assertArrayEquals(intArray, result.tyreStintsActual());

            assertEquals(0, result.bestLapTime());
            assertArrayEquals(intArray, result.tyreStintsEndLaps());
            assertEquals(0, result.resultReason());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2021")
    @DisplayName("Builds the Final Classification for 2021.")
    void testBuild_finalClassificationData2021(int packetFormat) {
        int bitMask8Count = 11;
        int bitMask32Count = 1;
        int doubleCount = 1;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockDoubleValues(mockByteBuffer, doubleCount);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 8);
            FinalClassificationData result = new FinalClassificationDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertEquals(bitMask8Value++, result.position());
            assertEquals(bitMask8Value++, result.numLaps());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.points());
            assertEquals(bitMask8Value++, result.numPitsStops());
            assertEquals(bitMask8Value++, result.resultStatus());
            assertEquals(BIT_32_START, result.bestLapTime());
            assertEquals(DOUBLE_START, result.totalRaceTime());
            assertEquals(bitMask8Value++, result.penaltiesTime());
            assertEquals(bitMask8Value++, result.numPenalties());
            assertEquals(bitMask8Value++, result.numTyreStints());
            assertArrayEquals(intArray, result.tyreStintsActual());
            assertArrayEquals(intArray, result.tyreStintsActual());

            assertEquals(0, result.bestLapTime20());
            assertArrayEquals(intArray, result.tyreStintsEndLaps());
            assertEquals(0, result.resultReason());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024})
    @DisplayName("Builds the Final Classification for 2022 to 2024.")
    void testBuild_finalClassificationData2022To2024(int packetFormat) {
        int bitMask8Count = 11;
        int bitMask32Count = 1;
        int doubleCount = 1;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockDoubleValues(mockByteBuffer, doubleCount);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 8);
            FinalClassificationData result = new FinalClassificationDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertEquals(bitMask8Value++, result.position());
            assertEquals(bitMask8Value++, result.numLaps());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.points());
            assertEquals(bitMask8Value++, result.numPitsStops());
            assertEquals(bitMask8Value++, result.resultStatus());
            assertEquals(BIT_32_START, result.bestLapTime());
            assertEquals(DOUBLE_START, result.totalRaceTime());
            assertEquals(bitMask8Value++, result.penaltiesTime());
            assertEquals(bitMask8Value++, result.numPenalties());
            assertEquals(bitMask8Value++, result.numTyreStints());
            assertArrayEquals(intArray, result.tyreStintsActual());
            assertArrayEquals(intArray, result.tyreStintsActual());
            assertArrayEquals(intArray, result.tyreStintsEndLaps());

            assertEquals(0, result.bestLapTime20());
            assertEquals(0, result.resultReason());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2025})
    @DisplayName("Builds the Final Classification for 2025 to Present.")
    void testBuild_finalClassificationData2025ToPresent(int packetFormat) {
        int bitMask8Count = 12;
        int bitMask32Count = 1;
        int doubleCount = 1;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockDoubleValues(mockByteBuffer, doubleCount);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 8);
            FinalClassificationData result = new FinalClassificationDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertEquals(bitMask8Value++, result.position());
            assertEquals(bitMask8Value++, result.numLaps());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.points());
            assertEquals(bitMask8Value++, result.numPitsStops());
            assertEquals(bitMask8Value++, result.resultStatus());
            assertEquals(BIT_32_START, result.bestLapTime());
            assertEquals(DOUBLE_START, result.totalRaceTime());
            assertEquals(bitMask8Value++, result.penaltiesTime());
            assertEquals(bitMask8Value++, result.numPenalties());
            assertEquals(bitMask8Value++, result.numTyreStints());
            assertArrayEquals(intArray, result.tyreStintsActual());
            assertArrayEquals(intArray, result.tyreStintsActual());
            assertArrayEquals(intArray, result.tyreStintsEndLaps());
            assertEquals(bitMask8Value++, result.resultReason());

            assertEquals(0, result.bestLapTime20());
        }
    }
}

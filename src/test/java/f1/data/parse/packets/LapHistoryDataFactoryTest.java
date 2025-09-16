package f1.data.parse.packets;

import f1.data.parse.packets.history.LapHistoryData;
import f1.data.parse.packets.history.LapHistoryDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class LapHistoryDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("supportedYears2021To2022")
    @DisplayName("Builds the Lap History Data from 2021 to 2022.")
    void testBuild_lapHistoryData2021To2022(int packetFormat) {
        int bitMask8Count = 1;
        int bitMask16Count = 3;
        int bitMask32Count = 1;
        int bitMask16Value = BIT_16_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            LapHistoryData[] result = new LapHistoryDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            Assertions.assertEquals(LapHistoryDataFactory.LAP_HISTORY_SIZE, result.length);
            LapHistoryData data = result[0];
            assertEquals(BIT_32_START, data.lapTimeInMS());
            assertEquals(bitMask16Value++, data.sector1TimeInMS());
            assertEquals(bitMask16Value++, data.sector2TimeInMS());
            assertEquals(bitMask16Value++, data.sector3TimeInMS());
            assertEquals(BIT_8_START, data.lapValidBitFlags());

            assertEquals(0, data.sector1TimeMinutesPart());
            assertEquals(0, data.sector2TimeMinutesPart());
            assertEquals(0, data.sector3TimeMinutesPart());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Lap History Data from 2023 to Present.")
    void testBuild_lapHistoryData2023ToPresent(int packetFormat) {
        int bitMask8Count = 4;
        int bitMask16Count = 3;
        int bitMask32Count = 1;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            LapHistoryData[] result = new LapHistoryDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            Assertions.assertEquals(LapHistoryDataFactory.LAP_HISTORY_SIZE, result.length);
            LapHistoryData data = result[0];
            assertEquals(BIT_32_START, data.lapTimeInMS());
            assertEquals(bitMask16Value++, data.sector1TimeInMS());
            assertEquals(bitMask8Value++, data.sector1TimeMinutesPart());
            assertEquals(bitMask16Value++, data.sector2TimeInMS());
            assertEquals(bitMask8Value++, data.sector2TimeMinutesPart());
            assertEquals(bitMask16Value++, data.sector3TimeInMS());
            assertEquals(bitMask8Value++, data.sector3TimeMinutesPart());
            assertEquals(bitMask8Value++, data.lapValidBitFlags());
        }
    }
}

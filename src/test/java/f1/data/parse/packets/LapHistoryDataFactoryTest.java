package f1.data.parse.packets;

import f1.data.parse.packets.history.LapHistoryData;
import f1.data.parse.packets.history.LapHistoryDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class LapHistoryDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds the Lap History Data from 2021 to 2022.")
    void testBuild_lapHistoryData2021To2022(int packetFormat) {
        int bitMask8Count = 1;
        int bitMask16Count = 3;
        int bitMask32Count = 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            LapHistoryData result = LapHistoryDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_32_START, result.lapTimeInMS());
            assertEquals(BIT_16_START, result.sector1TimeInMS());
            assertEquals(BIT_16_START + 1, result.sector2TimeInMS());
            assertEquals(BIT_16_START + 2, result.sector3TimeInMS());
            assertEquals(BIT_8_START, result.lapValidBitFlags());

            assertEquals(0, result.sector1TimeMinutesPart());
            assertEquals(0, result.sector2TimeMinutesPart());
            assertEquals(0, result.sector3TimeMinutesPart());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Lap History Data from 2023 to Present.")
    void testBuild_lapHistoryData2023ToPresent(int packetFormat) {
        int bitMask8Count = 4;
        int bitMask16Count = 3;
        int bitMask32Count = 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            LapHistoryData result = LapHistoryDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_32_START, result.lapTimeInMS());
            assertEquals(BIT_16_START, result.sector1TimeInMS());
            assertEquals(BIT_8_START, result.sector1TimeMinutesPart());
            assertEquals(BIT_16_START + 1, result.sector2TimeInMS());
            assertEquals(BIT_8_START + 1, result.sector2TimeMinutesPart());
            assertEquals(BIT_16_START + 2, result.sector3TimeInMS());
            assertEquals(BIT_8_START + 2, result.sector3TimeMinutesPart());
            assertEquals(BIT_8_START + 3, result.lapValidBitFlags());
        }
    }
}

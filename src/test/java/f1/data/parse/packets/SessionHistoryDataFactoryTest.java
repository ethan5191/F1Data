package f1.data.parse.packets;

import f1.data.parse.packets.history.*;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class SessionHistoryDataFactoryTest extends AbstractFactoryTest {

    private final int LAP_SIZE = LapHistoryDataFactory.LAP_HISTORY_SIZE;
    private final int TYRE_SIZE = TyreStintHistoryDataFactory.TYRE_STINT_HISTORY_SIZE;

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds the Session History Data for 2021 to 2022.")
    void testBuild_sessionHistoryData2021To2022(int packetFormat) {
        int lapHistory8Count = LAP_SIZE;
        int lapHistory16Count = 3 * LAP_SIZE;
        int lapHistory32Count = LAP_SIZE;
        int tyreStints8Count = 3 * TYRE_SIZE;
        int bitMask8Count = 7 + lapHistory8Count + tyreStints8Count;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, lapHistory16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, lapHistory32Count);
            SessionHistoryData result = new SessionHistoryDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertEquals(bitMask8Value++, result.carIndex());
            assertEquals(bitMask8Value++, result.numLaps());
            assertEquals(bitMask8Value++, result.numTyreStints());
            assertEquals(bitMask8Value++, result.bestLapTimeLapNum());
            assertEquals(bitMask8Value++, result.bestSector1LapNum());
            assertEquals(bitMask8Value++, result.bestSector2LapNum());
            assertEquals(bitMask8Value++, result.bestSector3LapNum());
            int nextBit8Val = validateLapHistoryData21(result, bitMask8Value++, BIT_16_START, BIT_32_START);
            validateTyreStintsHistoryData(result, nextBit8Val);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Session History Data for 2023 to 2024.")
    void testBuild_sessionHistoryData2023To2024(int packetFormat) {
        int lapHistory8Count = 4 * LAP_SIZE;
        int lapHistory16Count = 3 * LAP_SIZE;
        int lapHistory32Count = LAP_SIZE;
        int tyreStints8Count = 3 * TYRE_SIZE;
        int bitMask8Count = 7 + lapHistory8Count + tyreStints8Count;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, lapHistory16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, lapHistory32Count);
            SessionHistoryData result = new SessionHistoryDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertEquals(bitMask8Value++, result.carIndex());
            assertEquals(bitMask8Value++, result.numLaps());
            assertEquals(bitMask8Value++, result.numTyreStints());
            assertEquals(bitMask8Value++, result.bestLapTimeLapNum());
            assertEquals(bitMask8Value++, result.bestSector1LapNum());
            assertEquals(bitMask8Value++, result.bestSector2LapNum());
            assertEquals(bitMask8Value++, result.bestSector3LapNum());
            int nextBit8Val = validateLapHistoryData23(result, bitMask8Value++, BIT_16_START, BIT_32_START);
            validateTyreStintsHistoryData(result, nextBit8Val);
        }
    }

    private int validateLapHistoryData21(SessionHistoryData result, int bit8Val, int bit16Val, int bit32Val) {
        for (int n = 0; n < LAP_SIZE; n++) {
            LapHistoryData data = result.lapHistoryData()[n];
            assertEquals(bit32Val++, data.lapTimeInMS());
            assertEquals(bit16Val++, data.sector1TimeInMS());
            assertEquals(bit16Val++, data.sector2TimeInMS());
            assertEquals(bit16Val++, data.sector3TimeInMS());
            assertEquals(bit8Val++, data.lapValidBitFlags());
            assertEquals(0, data.sector1TimeMinutesPart());
            assertEquals(0, data.sector2TimeMinutesPart());
            assertEquals(0, data.sector3TimeMinutesPart());
        }
        return bit8Val;
    }

    private int validateLapHistoryData23(SessionHistoryData result, int bit8Val, int bit16Val, int bit32Val) {
        for (int n = 0; n < LAP_SIZE; n++) {
            LapHistoryData data = result.lapHistoryData()[n];
            assertEquals(bit32Val++, data.lapTimeInMS());
            assertEquals(bit16Val++, data.sector1TimeInMS());
            assertEquals(bit8Val++, data.sector1TimeMinutesPart());
            assertEquals(bit16Val++, data.sector2TimeInMS());
            assertEquals(bit8Val++, data.sector2TimeMinutesPart());
            assertEquals(bit16Val++, data.sector3TimeInMS());
            assertEquals(bit8Val++, data.sector3TimeMinutesPart());
            assertEquals(bit8Val++, data.lapValidBitFlags());
        }
        return bit8Val;
    }

    private void validateTyreStintsHistoryData(SessionHistoryData result, int bit8Val) {
        for (int n = 0; n < TYRE_SIZE; n++) {
            TyreStintHistoryData data = result.tyreStintHistoryData()[n];
            assertEquals(bit8Val++, data.endLap());
            assertEquals(bit8Val++, data.tyreActualCompound());
            assertEquals(bit8Val++, data.tyreVisualCompound());
        }
    }
}

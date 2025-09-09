package f1.data.parse.packets;

import f1.data.parse.packets.history.LapHistoryData;
import f1.data.parse.packets.history.SessionHistoryData;
import f1.data.parse.packets.history.SessionHistoryDataFactory;
import f1.data.parse.packets.history.TyreStintHistoryData;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class SessionHistoryDataFactoryTest extends AbstractFactoryTest {

    private final int LAP_HISTORY_SIZE = 100;
    private final int TYRE_STINT_HISTORY_SIZE = 8;

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021})
    @DisplayName("Builds the Session History Data for 2021.")
    void testBuild_sessionHistoryData2021(int packetFormat) {
        int lapHistory8Count = LAP_HISTORY_SIZE;
        int lapHistory16Count = 3 * LAP_HISTORY_SIZE;
        int lapHistory32Count = LAP_HISTORY_SIZE;
        int tyreStints8Count = 3 * TYRE_STINT_HISTORY_SIZE;
        int bitMask8Count = 7 + lapHistory8Count + tyreStints8Count;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, lapHistory16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, lapHistory32Count);
            SessionHistoryData result = SessionHistoryDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);

            assertEquals(BIT_8_START, result.carIndex());
            assertEquals(BIT_8_START + 1, result.numLaps());;
            assertEquals(BIT_8_START + 2, result.numTyreStints());;
            assertEquals(BIT_8_START + 3, result.bestLapTimeLapNum());;
            assertEquals(BIT_8_START + 4, result.bestSector1LapNum());;
            assertEquals(BIT_8_START + 5, result.bestSector2LapNum());;
            assertEquals(BIT_8_START + 6, result.bestSector3LapNum());;
            int nextBit8Val = validateLapHistoryData21(result, BIT_8_START + 7, BIT_16_START, BIT_32_START);
            validateTyreStintsHistoryData(result, nextBit8Val);
        }
    }

    private int validateLapHistoryData21(SessionHistoryData result, int bit8Val, int bit16Val, int bit32Val) {
        for (int n = 0; n < LAP_HISTORY_SIZE; n++) {
            LapHistoryData data = result.lapHistoryData()[n];
            assertEquals(bit32Val++, data.lapTimeInMS());
            assertEquals(bit16Val++, data.sector1TimeInMS());
            assertEquals(bit16Val++, data.sector2TimeInMS());
            assertEquals(bit16Val++, data.sector3TimeInMS());
            assertEquals(bit8Val++, data.lapValidBitFlags());
        }
        return bit8Val;
    }

    private void validateTyreStintsHistoryData(SessionHistoryData result, int bit8Val) {
        for (int n = 0; n < TYRE_STINT_HISTORY_SIZE; n++) {
            TyreStintHistoryData data = result.tyreStintHistoryData()[n];
            assertEquals(bit8Val++, data.endLap());
            assertEquals(bit8Val++, data.tyreActualCompound());
            assertEquals(bit8Val++, data.tyreVisualCompound());
        }
    }
}

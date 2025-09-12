package f1.data.parse.packets;

import f1.data.parse.packets.history.TyreStintHistoryData;
import f1.data.parse.packets.history.TyreStintHistoryDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class TyreStintHistoryDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Tyre Stint History Data from 2021 to Present.")
    void testBuild_tyreStintHistoryData2021ToPresent(int packetFormat) {
        int bitMask8Count = 3;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            TyreStintHistoryData[] result = new TyreStintHistoryDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            Assertions.assertEquals(TyreStintHistoryDataFactory.TYRE_STINT_HISTORY_SIZE, result.length);
            TyreStintHistoryData data = result[0];
            assertEquals(bitMask8Value++, data.endLap());
            assertEquals(bitMask8Value++, data.tyreActualCompound());
            assertEquals(bitMask8Value, data.tyreVisualCompound());
        }
    }
}

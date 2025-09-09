package f1.data.parse.packets;

import f1.data.parse.packets.history.TyreStintHistoryData;
import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class TyreStintHistoryDataTest extends AbstractFactoryTest{

    @Test
    @DisplayName("Builds the Tyre Stint History Data packet.")
    void testBuilt_tyreStintHistoryData() {
        int bitMask8Count = 3;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            TyreStintHistoryData result = new TyreStintHistoryData(mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.endLap());
            assertEquals(BIT_8_START + 1, result.tyreActualCompound());
            assertEquals(BIT_8_START + 2, result.tyreVisualCompound());
        }
    }
}

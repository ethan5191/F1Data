package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class TireSetsDataTest extends AbstractFactoryTest {

    @Test
    @DisplayName("Builds the Tire Sets Data packet.")
    void testBuild_tireSetsData() {
        int bitMask8Count = 8;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            when(mockByteBuffer.getShort()).thenReturn((short) 10);
            TireSetsData result = new TireSetsData(mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.actualTireCompound());
            assertEquals(BIT_8_START + 1, result.visualTireCompound());
            assertEquals(BIT_8_START + 2, result.wear());
            assertEquals(BIT_8_START + 3, result.available());
            assertEquals(BIT_8_START + 4, result.recommendedSession());
            assertEquals(BIT_8_START + 5, result.lifeSpan());
            assertEquals(BIT_8_START + 6, result.usableLife());
            assertEquals(10, result.lapDeltaTime());
            assertEquals(BIT_8_START + 7, result.fitted());
        }
    }
}

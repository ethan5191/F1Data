package f1.data.parse.packets;

import f1.data.parse.packets.events.ButtonsData;
import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class ButtonsDataTest extends AbstractFactoryTest {

    @Test
    @DisplayName("Builds the Button Event Data packet.")
    void testBuilt_buttonsEvent() {
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask32(bitMaskUtils, 1);
            ButtonsData result = new ButtonsData(mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_32_START, result.buttonsStatus());
        }
    }
}

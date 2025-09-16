package f1.data.parse.packets;

import f1.data.parse.packets.events.ButtonsData;
import f1.data.parse.packets.events.ButtonsDataFactory;
import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class ButtonsDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("supportedYears2021ToPresent")
    @DisplayName("Builds the Button Event from 2021 to Present.")
    void testBuild_buttonEvent2021ToPresent(int packetFormat) {
        int bitMask32Count = 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            ButtonsData result = new ButtonsDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_32_START, result.buttonsStatus());
        }
    }
}

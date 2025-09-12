package f1.data.parse.packets;

import f1.data.parse.packets.events.ButtonsData;
import f1.data.parse.packets.events.ButtonsDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class ButtonsDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
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

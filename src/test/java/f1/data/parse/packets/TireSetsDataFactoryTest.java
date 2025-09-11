package f1.data.parse.packets;

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

public class TireSetsDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Tire Sets Data for 2023 to Present")
    void testBuild_tireSetsData2023ToPresent(int packetFormat) {
        int bitMask8Count = 8 * Constants.TIRE_SETS_PACKET_COUNT;
        int shortCount = 1;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockSingleGetShortValue(mockByteBuffer, shortCount);
            TireSetsData[] result = TireSetsDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            Assertions.assertEquals(Constants.TIRE_SETS_PACKET_COUNT, result.length);
            for (TireSetsData data : result) {
                assertEquals(bitMask8Value++, data.actualTireCompound());
                assertEquals(bitMask8Value++, data.visualTireCompound());
                assertEquals(bitMask8Value++, data.wear());
                assertEquals(bitMask8Value++, data.available());
                assertEquals(bitMask8Value++, data.recommendedSession());
                assertEquals(bitMask8Value++, data.lifeSpan());
                assertEquals(bitMask8Value++, data.usableLife());
                assertEquals(shortCount, data.lapDeltaTime());
                assertEquals(bitMask8Value++, data.fitted());
            }
        }
    }
}

package f1.data.parse.packets;

import f1.data.parse.packets.participant.LiveryColourData;
import f1.data.parse.packets.participant.LiveryColourDataFactory;
import f1.data.parse.packets.participant.ParticipantData;
import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class LiveryColourDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("supportedYears2025")
    @DisplayName("Builds the Livery Colour Data Factory for 2025 to Present")
    void testBuild_liveryColourData2025ToPresent(int packetFormat) {
        int SIZE_2025 = ParticipantData.LIVERY_COLOUR_DATA_25_SIZE;
        int bitMask8Count = (3 * SIZE_2025);
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            LiveryColourData[] result = new LiveryColourDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            Assertions.assertEquals(SIZE_2025, result.length);
            for (LiveryColourData lcd : result) {
                assertEquals(bitMask8Value++, lcd.red());
                assertEquals(bitMask8Value++, lcd.green());
                assertEquals(bitMask8Value++, lcd.blue());
            }
        }
    }
}

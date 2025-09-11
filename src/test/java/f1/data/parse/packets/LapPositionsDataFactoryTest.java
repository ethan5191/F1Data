package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class LapPositionsDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2025})
    @DisplayName("Builds the Lap Positions Data for 2025 to Present.")
    void testBuild_lapPositionsDataFor2025ToPresent(int packetFormat) {
        int bit8Value = BIT_8_START;
        int arraySize = 50;
        int innerSize = (packetFormat >= Constants.YEAR_2026) ? Constants.F1_26_AND_LATER_CAR_COUNT : Constants.F1_20_TO_25_CAR_COUNT;
        int bitMask8Count = 3 + (arraySize * innerSize);
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, arraySize);
            LapPositionsData result = new LapPositionsDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertEquals(bit8Value++, result.numLaps());
            assertEquals(bit8Value++, result.lapStart());
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < innerSize; j++) {
                    assertEquals(bit8Value++, result.positionForVehicleIdx()[i][j]);
                }
            }
        }
    }
}

package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class FinalClassificationFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020})
    @DisplayName("Builds the Participant Data for 2020.")
    void testBuild_participantData2020(int packetFormat) {
        int bitMask8Count = 11;
        int floatCount = 1;
        int doubleCount = 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.mockDoubleValues(mockByteBuffer, doubleCount);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 8);
            FinalClassificationData result = FinalClassificationDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);

            assertEquals(BIT_8_START, result.position());
            assertEquals(BIT_8_START + 1, result.numLaps());
            assertEquals(BIT_8_START + 2, result.gridPosition());
            assertEquals(BIT_8_START + 3, result.points());
            assertEquals(BIT_8_START + 4, result.numPitsStops());
            assertEquals(BIT_8_START + 5, result.resultStatus());
            assertEquals(FLOAT_START, result.bestLapTime());
            assertEquals(DOUBLE_START, result.totalRaceTime());
            assertEquals(BIT_8_START + 6, result.penaltiesTime());
            assertEquals(BIT_8_START + 7, result.numPenalties());
            assertEquals(BIT_8_START + 8, result.numTyreStints());
            assertArrayEquals(new int[8], result.tyreStintsActual());
            assertArrayEquals(new int[8], result.tyreStintsActual());
        }
    }
}

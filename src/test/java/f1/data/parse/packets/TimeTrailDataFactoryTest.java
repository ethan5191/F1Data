package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class TimeTrailDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Time Trail Data for 2024 to Present.")
    void testBuilt_timeTrailData2024ToPresent(int packetFormat) {
        int bitMask8Count = 8;
        int bitMask32Count = 4;
        int bit8Value = BIT_8_START;
        int bit32Value = BIT_32_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            TimeTrialData result = new TimeTrialDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bit8Value++, result.carIndex());
            assertEquals(bit8Value++, result.teamId());
            assertEquals(bit32Value++, result.lapTimeInMS());
            assertEquals(bit32Value++, result.sector1TimeInMS());
            assertEquals(bit32Value++, result.sector2TimeInMS());
            assertEquals(bit32Value++, result.sector3TimeInMS());
            assertEquals(bit8Value++, result.tractionControl());
            assertEquals(bit8Value++, result.gearboxAssist());
            assertEquals(bit8Value++, result.antiLockBrakes());
            assertEquals(bit8Value++, result.equalCarPerformance());
            assertEquals(bit8Value++, result.customSetup());
            assertEquals(bit8Value++, result.valid());
        }
    }
}

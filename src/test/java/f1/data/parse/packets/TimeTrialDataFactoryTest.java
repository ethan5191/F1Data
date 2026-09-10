package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;
import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class TimeTrialDataFactoryTest extends AbstractFactoryTest {

    static Stream<Integer> supportedYears2024To2025() {
        return Stream.of(SupportedYearsEnum.F1_2024.getYear(),
                SupportedYearsEnum.F1_2025.getYear());
    }

    @ParameterizedTest
    @MethodSource("supportedYears2024To2025")
    @DisplayName("Builds the Time Trail Data for 2024 to 2025.")
    void testBuilt_timeTrailData2024To2025(int packetFormat) {
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

    @ParameterizedTest
    @MethodSource("supportedYears2026")
    @DisplayName("Builds the Time Trail Data for 2026 To Present.")
    void testBuilt_timeTrailData2026ToPresent(int packetFormat) {
        int bitMask8Count = 7;
        int bitMask16Count = 1;
        int bitMask32Count = 4;
        int bit8Value = BIT_8_START;
        int bit32Value = BIT_32_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            TimeTrialData result = new TimeTrialDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bit8Value++, result.carIndex());
            assertEquals(BIT_16_START, result.teamId());
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

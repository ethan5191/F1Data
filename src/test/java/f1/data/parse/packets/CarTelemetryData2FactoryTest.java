package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class CarTelemetryData2FactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("supportedYears2026")
    @DisplayName("Builds the Car Telemetry Data 2 for 2026 To Present.")
    void testBuild_carTelemetryTwo2026ToPresent(int packetFormat) {
        int bitMask8Count = 6;
        int bitMask16Count = 2;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            CarTelemetryData2 result = new CarTelemetryData2Factory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.activeAeroMode());
            assertEquals(bitMask8Value++, result.activeAeroAvailable());
            assertEquals(bitMask16Value++, result.activeAeroActivationDistance());
            assertEquals(bitMask8Value++, result.overtakeAvailable());
            assertEquals(bitMask8Value++, result.overtakeActive());
            assertEquals(bitMask16Value++, result.overtakeActivationDistance());
            assertEquals(bitMask8Value++, result.twentySixRegulations());
            assertEquals(bitMask8Value++, result.drivingWrongWay());
        }
    }
}

package f1.data.parse.packets;

import f1.data.parse.packets.events.SpeedTrapData;
import f1.data.parse.packets.events.SpeedTrapDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class SpeedTrapDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2020)
    @DisplayName("Builds the Speed Trap Event Data for 2020.")
    void testBuild_speedTrapEvent2020(int packetFormat) {
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, 1);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, 1);
            SpeedTrapData result = new SpeedTrapDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.vehicleId());
            assertEquals(FLOAT_START, result.speed());

            assertEquals(0, result.isOverallFastest());
            assertEquals(0, result.isDriverFastest());
            assertEquals(0, result.fastestVehicleId());
            assertEquals(0, result.fastestSpeed());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2021)
    @DisplayName("Builds the Speed Trap Event Data for 2021.")
    void testBuild_speedTrapEvent2021(int packetFormat) {
        int bitMask8Count = 3;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, 1);
            SpeedTrapData result = new SpeedTrapDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.vehicleId());
            assertEquals(FLOAT_START, result.speed());
            assertEquals(bitMask8Value++, result.isOverallFastest());
            assertEquals(bitMask8Value++, result.isDriverFastest());

            assertEquals(0, result.fastestVehicleId());
            assertEquals(0, result.fastestSpeed());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Speed Trap Event Data for 2022 to Present.")
    void testBuild_speedTrapEvent2022ToPresent(int packetFormat) {
        int bitMask8Count = 4;
        int floatCount = 2;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            SpeedTrapData result = new SpeedTrapDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.vehicleId());
            assertEquals(FLOAT_START, result.speed());
            assertEquals(bitMask8Value++, result.isOverallFastest());
            assertEquals(bitMask8Value++, result.isDriverFastest());
            assertEquals(bitMask8Value++, result.fastestVehicleId());
            assertEquals(FLOAT_START + 1, result.fastestSpeed());
        }
    }
}

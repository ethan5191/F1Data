package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class CarSetupDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023})
    @DisplayName("Builds the Car Setup Data from 2020 to 2023.")
    void testBuild_carSetup2020To2023(int packetFormat) {
        int bitMask8Count = 13;
        int floatCount = 9;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarSetupData result = CarSetupDataFactory.build(packetFormat, mockByteBuffer, "setUpName");
            assertNotNull(result);
            assertEquals(1, result.frontWing());
            assertEquals(2, result.rearWing());
            assertEquals(3, result.onThrottle());
            assertEquals(4, result.offThrottle());
            assertEquals(100, result.frontCamber());
            assertEquals(101, result.rearCamber());
            assertEquals(102, result.frontToe());
            assertEquals(103, result.rearToe());
            assertEquals(5, result.frontSusp());
            assertEquals(6, result.rearSusp());
            assertEquals(7, result.frontARB());
            assertEquals(8, result.rearARB());
            assertEquals(9, result.frontHeight());
            assertEquals(10, result.rearHeight());
            assertEquals(11, result.brakePressure());
            assertEquals(12, result.brakeBias());
            assertEquals(104, result.rearLeftPressure());
            assertEquals(105, result.rearRightPressure());
            assertEquals(106, result.frontLeftPressure());
            assertEquals(107, result.frontRightPressure());
            assertEquals(13, result.ballast());
            assertEquals(108, result.fuelLoad());
            assertEquals(0, result.engineBraking());
            assertEquals("setUpName", result.setupName());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Car Setup Data from 2024 to Present.")
    void testBuild_carSetup2024ToPresent(int packetFormat) {
        int bitMask8Count = 14;
        int floatCount = 9;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarSetupData result = CarSetupDataFactory.build(packetFormat, mockByteBuffer, "setUpName");
            assertNotNull(result);
            assertEquals(1, result.frontWing());
            assertEquals(2, result.rearWing());
            assertEquals(3, result.onThrottle());
            assertEquals(4, result.offThrottle());
            assertEquals(100, result.frontCamber());
            assertEquals(101, result.rearCamber());
            assertEquals(102, result.frontToe());
            assertEquals(103, result.rearToe());
            assertEquals(5, result.frontSusp());
            assertEquals(6, result.rearSusp());
            assertEquals(7, result.frontARB());
            assertEquals(8, result.rearARB());
            assertEquals(9, result.frontHeight());
            assertEquals(10, result.rearHeight());
            assertEquals(11, result.brakePressure());
            assertEquals(12, result.brakeBias());
            assertEquals(13, result.engineBraking());
            assertEquals(104, result.rearLeftPressure());
            assertEquals(105, result.rearRightPressure());
            assertEquals(106, result.frontLeftPressure());
            assertEquals(107, result.frontRightPressure());
            assertEquals(14, result.ballast());
            assertEquals(108, result.fuelLoad());
            assertEquals("setUpName", result.setupName());
        }
    }
}

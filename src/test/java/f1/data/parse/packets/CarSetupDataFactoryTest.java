package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class CarSetupDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("supportedYears2019")
    @DisplayName("Builds the Car Setup Data for 2019.")
    void testBuild_carSetup2019(int packetFormat) {
        int bitMask8Count = 13;
        int floatCount = 7;
        int bitMask8Value = BIT_8_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarSetupData result = new CarSetupDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.frontWing());
            assertEquals(bitMask8Value++, result.rearWing());
            assertEquals(bitMask8Value++, result.onThrottle());
            assertEquals(bitMask8Value++, result.offThrottle());
            assertEquals(floatValue++, result.frontCamber());
            assertEquals(floatValue++, result.rearCamber());
            assertEquals(floatValue++, result.frontToe());
            assertEquals(floatValue++, result.rearToe());
            assertEquals(bitMask8Value++, result.frontSusp());
            assertEquals(bitMask8Value++, result.rearSusp());
            assertEquals(bitMask8Value++, result.frontARB());
            assertEquals(bitMask8Value++, result.rearARB());
            assertEquals(bitMask8Value++, result.frontHeight());
            assertEquals(bitMask8Value++, result.rearHeight());
            assertEquals(bitMask8Value++, result.brakePressure());
            assertEquals(bitMask8Value++, result.brakeBias());
            //This iteration of the game only sent front and rear pressures, so only 2 values are needed.
            assertEquals(floatValue, result.frontLeftPressure());
            assertEquals(floatValue++, result.frontRightPressure());
            assertEquals(floatValue, result.rearLeftPressure());
            assertEquals(floatValue++, result.rearRightPressure());
            assertEquals(bitMask8Value++, result.ballast());
            assertEquals(floatValue++, result.fuelLoad());
            assertEquals(0, result.engineBraking());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023})
    @DisplayName("Builds the Car Setup Data from 2020 to 2023.")
    void testBuild_carSetup2020To2023(int packetFormat) {
        int bitMask8Count = 13;
        int floatCount = 9;
        int bitMask8Value = BIT_8_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarSetupData result = new CarSetupDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.frontWing());
            assertEquals(bitMask8Value++, result.rearWing());
            assertEquals(bitMask8Value++, result.onThrottle());
            assertEquals(bitMask8Value++, result.offThrottle());
            assertEquals(floatValue++, result.frontCamber());
            assertEquals(floatValue++, result.rearCamber());
            assertEquals(floatValue++, result.frontToe());
            assertEquals(floatValue++, result.rearToe());
            assertEquals(bitMask8Value++, result.frontSusp());
            assertEquals(bitMask8Value++, result.rearSusp());
            assertEquals(bitMask8Value++, result.frontARB());
            assertEquals(bitMask8Value++, result.rearARB());
            assertEquals(bitMask8Value++, result.frontHeight());
            assertEquals(bitMask8Value++, result.rearHeight());
            assertEquals(bitMask8Value++, result.brakePressure());
            assertEquals(bitMask8Value++, result.brakeBias());
            assertEquals(floatValue++, result.rearLeftPressure());
            assertEquals(floatValue++, result.rearRightPressure());
            assertEquals(floatValue++, result.frontLeftPressure());
            assertEquals(floatValue++, result.frontRightPressure());
            assertEquals(bitMask8Value++, result.ballast());
            assertEquals(floatValue++, result.fuelLoad());
            assertEquals(0, result.engineBraking());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Car Setup Data from 2024 to Present.")
    void testBuild_carSetup2024ToPresent(int packetFormat) {
        int bitMask8Count = 14;
        int floatCount = 9;
        int bitMask8Value = BIT_8_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarSetupData result = new CarSetupDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.frontWing());
            assertEquals(bitMask8Value++, result.rearWing());
            assertEquals(bitMask8Value++, result.onThrottle());
            assertEquals(bitMask8Value++, result.offThrottle());
            assertEquals(floatValue++, result.frontCamber());
            assertEquals(floatValue++, result.rearCamber());
            assertEquals(floatValue++, result.frontToe());
            assertEquals(floatValue++, result.rearToe());
            assertEquals(bitMask8Value++, result.frontSusp());
            assertEquals(bitMask8Value++, result.rearSusp());
            assertEquals(bitMask8Value++, result.frontARB());
            assertEquals(bitMask8Value++, result.rearARB());
            assertEquals(bitMask8Value++, result.frontHeight());
            assertEquals(bitMask8Value++, result.rearHeight());
            assertEquals(bitMask8Value++, result.brakePressure());
            assertEquals(bitMask8Value++, result.brakeBias());
            assertEquals(bitMask8Value++, result.engineBraking());
            assertEquals(floatValue++, result.rearLeftPressure());
            assertEquals(floatValue++, result.rearRightPressure());
            assertEquals(floatValue++, result.frontLeftPressure());
            assertEquals(floatValue++, result.frontRightPressure());
            assertEquals(bitMask8Value++, result.ballast());
            assertEquals(floatValue++, result.fuelLoad());
        }
    }
}

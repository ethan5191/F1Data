package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class CarSetupDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023})
    @DisplayName("Builds the Car Setup Data from 2020 to 2023.")
    void testBuild_carSetup2020To2023(int packetFormat) {
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            when(mockByteBuffer.getFloat()).thenReturn((float) 1, (float) 2, (float) 3, (float) 4, (float) 5, (float) 6, (float) 7, (float) 8, (float) 9);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
            CarSetupData result = CarSetupDataFactory.build(packetFormat, mockByteBuffer, "setUpName");
            assertNotNull(result);
            assertEquals(1, result.frontWing());
            assertEquals(2, result.rearWing());
            assertEquals(3, result.onThrottle());
            assertEquals(4, result.offThrottle());
            assertEquals((float) 1, result.frontCamber());
            assertEquals((float) 2, result.rearCamber());
            assertEquals((float) 3, result.frontToe());
            assertEquals((float) 4, result.rearToe());
            assertEquals(5, result.frontSusp());
            assertEquals(6, result.rearSusp());
            assertEquals(7, result.frontARB());
            assertEquals(8, result.rearARB());
            assertEquals(9, result.frontHeight());
            assertEquals(10, result.rearHeight());
            assertEquals(11, result.brakePressure());
            assertEquals(12, result.brakeBias());
            assertEquals((float) 5, result.rearLeftPressure());
            assertEquals((float) 6, result.rearRightPressure());
            assertEquals((float) 7, result.frontLeftPressure());
            assertEquals((float) 8, result.frontRightPressure());
            assertEquals(13, result.ballast());
            assertEquals((float) 9, result.fuelLoad());
            assertEquals(0, result.engineBraking());
            assertEquals("setUpName", result.setupName());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Car Setup Data from 2024 to Present.")
    void testBuild_carSetup2024ToPresent(int packetFormat) {
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            when(mockByteBuffer.getFloat()).thenReturn((float) 1, (float) 2, (float) 3, (float) 4, (float) 5, (float) 6, (float) 7, (float) 8, (float) 9);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
            CarSetupData result = CarSetupDataFactory.build(packetFormat, mockByteBuffer, "setUpName");
            assertNotNull(result);
            assertEquals(1, result.frontWing());
            assertEquals(2, result.rearWing());
            assertEquals(3, result.onThrottle());
            assertEquals(4, result.offThrottle());
            assertEquals((float) 1, result.frontCamber());
            assertEquals((float) 2, result.rearCamber());
            assertEquals((float) 3, result.frontToe());
            assertEquals((float) 4, result.rearToe());
            assertEquals(5, result.frontSusp());
            assertEquals(6, result.rearSusp());
            assertEquals(7, result.frontARB());
            assertEquals(8, result.rearARB());
            assertEquals(9, result.frontHeight());
            assertEquals(10, result.rearHeight());
            assertEquals(11, result.brakePressure());
            assertEquals(12, result.brakeBias());
            assertEquals(13, result.engineBraking());
            assertEquals((float) 5, result.rearLeftPressure());
            assertEquals((float) 6, result.rearRightPressure());
            assertEquals((float) 7, result.frontLeftPressure());
            assertEquals((float) 8, result.frontRightPressure());
            assertEquals(14, result.ballast());
            assertEquals((float) 9, result.fuelLoad());
            assertEquals("setUpName", result.setupName());
        }
    }
}

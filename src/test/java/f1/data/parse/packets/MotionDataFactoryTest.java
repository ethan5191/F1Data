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
import static org.mockito.Mockito.when;

public class MotionDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2019, Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds the Motion Data Legacy 2019 to 2022")
    void testBuild_motionDataLegacy2019To2022(int packetFormat) {
        int floatCount = 10;
        int floatCountValue = FLOAT_START;
        float[] value = new float[4];
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            MotionExData result = MotionDataFactory.buildLegacy(mockByteBuffer);
            assertNotNull(result);
            assertArrayEquals(value, result.suspensionPosition());
            assertArrayEquals(value, result.suspensionVelocity());
            assertArrayEquals(value, result.suspensionAcceleration());
            assertArrayEquals(value, result.wheelSpeed());
            assertArrayEquals(value, result.wheelSlipRatio());
            assertArrayEquals(value, result.wheelSlipAngle());
            assertArrayEquals(value, result.wheelLatForce());
            assertArrayEquals(value, result.wheelLongForce());
            assertEquals(floatCountValue++, result.localVelocityX());
            assertEquals(floatCountValue++, result.localVelocityY());
            assertEquals(floatCountValue++, result.localVelocityZ());
            assertEquals(floatCountValue++, result.angularVelocityX());
            assertEquals(floatCountValue++, result.angularVelocityY());
            assertEquals(floatCountValue++, result.angularVelocityZ());
            assertEquals(floatCountValue++, result.angularAccelerationX());
            assertEquals(floatCountValue++, result.angularAccelerationY());
            assertEquals(floatCountValue++, result.angularAccelerationZ());
            assertEquals(floatCountValue++, result.frontWheelsAngle());

            assertEquals(0, result.heightOfCOGAboveGround());
            assertArrayEquals(value, result.wheelVertForce());
            assertEquals(0, result.frontAeroHeight());
            assertEquals(0, result.rearAeroHeight());
            assertEquals(0, result.frontRollAngle());
            assertEquals(0, result.rearRollAngle());
            assertEquals(0, result.chassisYaw());
            assertEquals(0, result.chassisPitch());
            assertArrayEquals(value, result.wheelCamber());
            assertArrayEquals(value, result.wheelCamberGain());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2019, Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022,
            Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Motion Data 2019 to Present")
    void testBuild_motionData2019ToPresent(int packetFormat) {
        int bitMask16Count = 6;
        int bitMask16Value = BIT_16_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            when(mockByteBuffer.getFloat()).thenReturn(Constants.DIVISOR);
            MotionData result = MotionDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(1, result.worldPositionX());
            assertEquals(1, result.worldPositionY());
            assertEquals(1, result.worldPositionZ());
            assertEquals(1, result.worldVelocityX());
            assertEquals(1, result.worldVelocityY());
            assertEquals(1, result.worldVelocityZ());
            assertEquals(bitMask16Value++, result.worldForwardDirX());
            assertEquals(bitMask16Value++, result.worldForwardDirY());
            assertEquals(bitMask16Value++, result.worldForwardDirZ());
            assertEquals(bitMask16Value++, result.worldRightDirX());
            assertEquals(bitMask16Value++, result.worldRightDirY());
            assertEquals(bitMask16Value++, result.worldRightDirZ());
            assertEquals(1, result.gForceLat());
            assertEquals(1, result.gForceLon());
            assertEquals(1, result.gForceVer());
            assertEquals(1, result.yaw());
            assertEquals(1, result.pitch());
            assertEquals(1, result.roll());
        }
    }
}

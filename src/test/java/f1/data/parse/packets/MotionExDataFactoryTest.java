package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class MotionExDataFactoryTest extends AbstractFactoryTest {

    private static final float[] VALUE = new float[4];

    @ParameterizedTest
    @MethodSource("supportedYears2023")
    @DisplayName("Builds the Motion EX Data for 2023.")
    void testBuild_motionExData2023(int packetFormat) {
        int floatCount = 20;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            MotionExData result = new MotionExDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertArrayEquals(VALUE, result.suspensionPosition());
            assertArrayEquals(VALUE, result.suspensionVelocity());
            assertArrayEquals(VALUE, result.suspensionAcceleration());
            assertArrayEquals(VALUE, result.wheelSpeed());
            assertArrayEquals(VALUE, result.wheelSlipRatio());
            assertArrayEquals(VALUE, result.wheelSlipAngle());
            assertArrayEquals(VALUE, result.wheelLatForce());
            assertArrayEquals(VALUE, result.wheelLongForce());
            int floatValue = FLOAT_START;
            assertEquals(floatValue++, result.heightOfCOGAboveGround());
            assertEquals(floatValue++, result.localVelocityX());
            assertEquals(floatValue++, result.localVelocityY());
            assertEquals(floatValue++, result.localVelocityZ());
            assertEquals(floatValue++, result.angularVelocityX());
            assertEquals(floatValue++, result.angularVelocityY());
            assertEquals(floatValue++, result.angularVelocityZ());
            assertEquals(floatValue++, result.angularAccelerationX());
            assertEquals(floatValue++, result.angularAccelerationY());
            assertEquals(floatValue++, result.angularAccelerationZ());
            assertEquals(floatValue++, result.frontWheelsAngle());
            assertArrayEquals(VALUE, result.wheelVertForce());

            assertEquals(0, result.frontAeroHeight());
            assertEquals(0, result.rearAeroHeight());
            assertEquals(0, result.frontRollAngle());
            assertEquals(0, result.rearRollAngle());
            assertEquals(0, result.chassisYaw());
            assertEquals(0, result.chassisPitch());
            assertArrayEquals(VALUE, result.wheelCamber());
            assertArrayEquals(VALUE, result.wheelCamberGain());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2024})
    @DisplayName("Builds the Motion EX Data for 2024.")
    void testBuild_motionExData2024(int packetFormat) {
        int floatCount = 25;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            MotionExData result = new MotionExDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertArrayEquals(VALUE, result.suspensionPosition());
            assertArrayEquals(VALUE, result.suspensionVelocity());
            assertArrayEquals(VALUE, result.suspensionAcceleration());
            assertArrayEquals(VALUE, result.wheelSpeed());
            assertArrayEquals(VALUE, result.wheelSlipRatio());
            assertArrayEquals(VALUE, result.wheelSlipAngle());
            assertArrayEquals(VALUE, result.wheelLatForce());
            assertArrayEquals(VALUE, result.wheelLongForce());
            int floatValue = FLOAT_START;
            assertEquals(floatValue++, result.heightOfCOGAboveGround());
            assertEquals(floatValue++, result.localVelocityX());
            assertEquals(floatValue++, result.localVelocityY());
            assertEquals(floatValue++, result.localVelocityZ());
            assertEquals(floatValue++, result.angularVelocityX());
            assertEquals(floatValue++, result.angularVelocityY());
            assertEquals(floatValue++, result.angularVelocityZ());
            assertEquals(floatValue++, result.angularAccelerationX());
            assertEquals(floatValue++, result.angularAccelerationY());
            assertEquals(floatValue++, result.angularAccelerationZ());
            assertEquals(floatValue++, result.frontWheelsAngle());
            assertArrayEquals(VALUE, result.wheelVertForce());
            assertEquals(floatValue++, result.frontAeroHeight());
            assertEquals(floatValue++, result.rearAeroHeight());
            assertEquals(floatValue++, result.frontRollAngle());
            assertEquals(floatValue++, result.rearRollAngle());
            assertEquals(floatValue++, result.chassisYaw());

            assertEquals(0, result.chassisPitch());
            assertArrayEquals(VALUE, result.wheelCamber());
            assertArrayEquals(VALUE, result.wheelCamberGain());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2025})
    @DisplayName("Builds the Motion EX Data for 2025 to Present.")
    void testBuild_motionExData2025ToPresent(int packetFormat) {
        int floatCount = 25;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            MotionExData result = new MotionExDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);

            assertArrayEquals(VALUE, result.suspensionPosition());
            assertArrayEquals(VALUE, result.suspensionVelocity());
            assertArrayEquals(VALUE, result.suspensionAcceleration());
            assertArrayEquals(VALUE, result.wheelSpeed());
            assertArrayEquals(VALUE, result.wheelSlipRatio());
            assertArrayEquals(VALUE, result.wheelSlipAngle());
            assertArrayEquals(VALUE, result.wheelLatForce());
            assertArrayEquals(VALUE, result.wheelLongForce());
            int floatValue = FLOAT_START;
            assertEquals(floatValue++, result.heightOfCOGAboveGround());
            assertEquals(floatValue++, result.localVelocityX());
            assertEquals(floatValue++, result.localVelocityY());
            assertEquals(floatValue++, result.localVelocityZ());
            assertEquals(floatValue++, result.angularVelocityX());
            assertEquals(floatValue++, result.angularVelocityY());
            assertEquals(floatValue++, result.angularVelocityZ());
            assertEquals(floatValue++, result.angularAccelerationX());
            assertEquals(floatValue++, result.angularAccelerationY());
            assertEquals(floatValue++, result.angularAccelerationZ());
            assertEquals(floatValue++, result.frontWheelsAngle());
            assertArrayEquals(VALUE, result.wheelVertForce());
            assertEquals(floatValue++, result.frontAeroHeight());
            assertEquals(floatValue++, result.rearAeroHeight());
            assertEquals(floatValue++, result.frontRollAngle());
            assertEquals(floatValue++, result.rearRollAngle());
            assertEquals(floatValue++, result.chassisYaw());
            assertEquals(floatValue++, result.chassisPitch());
            assertArrayEquals(VALUE, result.wheelCamber());
            assertArrayEquals(VALUE, result.wheelCamberGain());
        }
    }
}

package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class CarStatusDataFactoryTest extends AbstractFactoryTest {

    private final int[] populatedArray = new int[4];
    private final int[] emptyIntArray = new int[0];
    private final float[] emptyFloatArray = new float[0];

    @ParameterizedTest
    @MethodSource("supportedYears2019")
    @DisplayName("Builds the Car Status Data for 2019.")
    void testBuild_carStatus2019(int packetFormat) {
        int bitMask8Count = 18;
        int bitMask16Count = 2;
        int floatCount = 7;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarStatusData result = new CarStatusDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.tractionControl());
            assertEquals(bitMask8Value++, result.antiLockBrakes());
            assertEquals(bitMask8Value++, result.fuelMix());
            assertEquals(bitMask8Value++, result.frontBrakeBias());
            assertEquals(bitMask8Value++, result.pitLimitStatus());
            assertEquals(floatValue++, result.fuelInTank());
            assertEquals(floatValue++, result.fuelCapacity());
            assertEquals(floatValue++, result.fuelRemainingLaps());
            assertEquals(bitMask16Value++, result.maxRPM());
            assertEquals(bitMask16Value++, result.idleRPM());
            assertEquals(bitMask8Value++, result.maxGears());
            assertEquals(bitMask8Value++, result.drsAllowed());
            float[] mockTireWear = new float[]{bitMask8Value++, bitMask8Value++, bitMask8Value++, bitMask8Value++};
            assertArrayEquals(mockTireWear, result.tyresWear());
            assertEquals(bitMask8Value++, result.actualTireCompound());
            assertEquals(bitMask8Value++, result.visualTireCompound());
            assertArrayEquals(populatedArray, result.tyresDamage());
            assertEquals(bitMask8Value++, result.frontLeftWingDamage());
            assertEquals(bitMask8Value++, result.frontRightWingDamage());
            assertEquals(bitMask8Value++, result.rearWingDamage());
            assertEquals(bitMask8Value++, result.engineDamage());
            assertEquals(bitMask8Value++, result.gearBoxDamage());
            assertEquals(bitMask8Value++, result.vehicleFiaFlags());
            assertEquals(floatValue++, result.ersStoreEnergy());
            assertEquals(bitMask8Count, result.ersDeployMode());
            assertEquals(floatValue++, result.ersHarvestedThisLapMGUK());
            assertEquals(floatValue++, result.ersHarvestedThisLapMGUH());
            assertEquals(floatValue++, result.ersDeployedThisLap());

            assertEquals(0, result.tiresAgeLaps());
            assertEquals(0, result.drsFault());
            assertEquals(0, result.drsActivationDistance());
            assertEquals(0, result.networkPaused());
            assertEquals(0, result.enginePowerICE());
            assertEquals(0, result.enginePowerMGUK());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2020")
    @DisplayName("Builds the Car Status Data for 2020.")
    void testBuild_carStatus2020(int packetFormat) {
        int bitMask8Count = 21;
        int bitMask16Count = 3;
        int floatCount = 7;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            CarStatusData result = new CarStatusDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.tractionControl());
            assertEquals(bitMask8Value++, result.antiLockBrakes());
            assertEquals(bitMask8Value++, result.fuelMix());
            assertEquals(bitMask8Value++, result.frontBrakeBias());
            assertEquals(bitMask8Value++, result.pitLimitStatus());
            assertEquals(floatValue++, result.fuelInTank());
            assertEquals(floatValue++, result.fuelCapacity());
            assertEquals(floatValue++, result.fuelRemainingLaps());
            assertEquals(bitMask16Value++, result.maxRPM());
            assertEquals(bitMask16Value++, result.idleRPM());
            assertEquals(bitMask8Value++, result.maxGears());
            assertEquals(bitMask8Value++, result.drsAllowed());
            assertEquals(bitMask16Value++, result.drsActivationDistance());
            float[] mockTireWear = new float[]{bitMask8Value++, bitMask8Value++, bitMask8Value++, bitMask8Value++};
            assertArrayEquals(mockTireWear, result.tyresWear());
            assertEquals(bitMask8Value++, result.actualTireCompound());
            assertEquals(bitMask8Value++, result.visualTireCompound());
            assertEquals(bitMask8Value++, result.tiresAgeLaps());
            assertArrayEquals(populatedArray, result.tyresDamage());
            assertEquals(bitMask8Value++, result.frontLeftWingDamage());
            assertEquals(bitMask8Value++, result.frontRightWingDamage());
            assertEquals(bitMask8Value++, result.rearWingDamage());
            assertEquals(bitMask8Value++, result.drsFault());
            assertEquals(bitMask8Value++, result.engineDamage());
            assertEquals(bitMask8Value++, result.gearBoxDamage());
            assertEquals(bitMask8Count + 1, result.vehicleFiaFlags());
            assertEquals(floatValue++, result.ersStoreEnergy());
            assertEquals(bitMask8Count, result.ersDeployMode());
            assertEquals(floatValue++, result.ersHarvestedThisLapMGUK());
            assertEquals(floatValue++, result.ersHarvestedThisLapMGUH());
            assertEquals(floatValue++, result.ersDeployedThisLap());
            assertEquals(0, result.networkPaused());
            assertEquals(0, result.enginePowerICE());
            assertEquals(0, result.enginePowerMGUK());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2021To2022")
    @DisplayName("Builds the Car Status Data for 2021 and 2022.")
    void testBuild_carStatus2021And2022(int packetFormat) {
        int bitMask8Count = 12;
        int bitMask16Count = 3;
        int floatCount = 7;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            CarStatusData result = new CarStatusDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.tractionControl());
            assertEquals(bitMask8Value++, result.antiLockBrakes());
            assertEquals(bitMask8Value++, result.fuelMix());
            assertEquals(bitMask8Value++, result.frontBrakeBias());
            assertEquals(bitMask8Value++, result.pitLimitStatus());
            assertEquals(floatValue++, result.fuelInTank());
            assertEquals(floatValue++, result.fuelCapacity());
            assertEquals(floatValue++, result.fuelRemainingLaps());
            assertEquals(bitMask16Value++, result.maxRPM());
            assertEquals(bitMask16Value++, result.idleRPM());
            assertEquals(bitMask8Value++, result.maxGears());
            assertEquals(bitMask8Value++, result.drsAllowed());
            assertEquals(bitMask16Value++, result.drsActivationDistance());
            assertEquals(bitMask8Value++, result.actualTireCompound());
            assertEquals(bitMask8Value++, result.visualTireCompound());
            assertEquals(bitMask8Value++, result.tiresAgeLaps());
            assertEquals(bitMask8Count + 1, result.vehicleFiaFlags());
            assertEquals(floatValue++, result.ersStoreEnergy());
            assertEquals(bitMask8Value++, result.ersDeployMode());
            assertEquals(floatValue++, result.ersHarvestedThisLapMGUK());
            assertEquals(floatValue++, result.ersHarvestedThisLapMGUH());
            assertEquals(floatValue++, result.ersDeployedThisLap());
            assertEquals(bitMask8Value++, result.networkPaused());
            assertEquals(0, result.enginePowerICE());
            assertEquals(0, result.enginePowerMGUK());
            assertArrayEquals(emptyFloatArray, result.tyresWear());
            assertArrayEquals(emptyIntArray, result.tyresDamage());
            assertEquals(0, result.frontLeftWingDamage());
            assertEquals(0, result.frontRightWingDamage());
            assertEquals(0, result.rearWingDamage());
            assertEquals(0, result.drsFault());
            assertEquals(0, result.engineDamage());
            assertEquals(0, result.gearBoxDamage());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2023ToPresent")
    @DisplayName("Builds the Car Status Data for 2023 to Present.")
    void testBuild_carStatus2023ToPresent(int packetFormat) {
        int bitMask8Count = 12;
        int bitMask16Count = 3;
        int floatCount = 9;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);
            CarStatusData result = new CarStatusDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.tractionControl());
            assertEquals(bitMask8Value++, result.antiLockBrakes());
            assertEquals(bitMask8Value++, result.fuelMix());
            assertEquals(bitMask8Value++, result.frontBrakeBias());
            assertEquals(bitMask8Value++, result.pitLimitStatus());
            assertEquals(floatValue++, result.fuelInTank());
            assertEquals(floatValue++, result.fuelCapacity());
            assertEquals(floatValue++, result.fuelRemainingLaps());
            assertEquals(bitMask16Value++, result.maxRPM());
            assertEquals(bitMask16Value++, result.idleRPM());
            assertEquals(bitMask8Value++, result.maxGears());
            assertEquals(bitMask8Value++, result.drsAllowed());
            assertEquals(bitMask16Value++, result.drsActivationDistance());
            assertEquals(bitMask8Value++, result.actualTireCompound());
            assertEquals(bitMask8Value++, result.visualTireCompound());
            assertEquals(bitMask8Value++, result.tiresAgeLaps());
            assertEquals(bitMask8Count + 1, result.vehicleFiaFlags());
            assertEquals(floatValue++, result.enginePowerICE());
            assertEquals(floatValue++, result.enginePowerMGUK());
            assertEquals(floatValue++, result.ersStoreEnergy());
            assertEquals(bitMask8Value++, result.ersDeployMode());
            assertEquals(floatValue++, result.ersHarvestedThisLapMGUK());
            assertEquals(floatValue++, result.ersHarvestedThisLapMGUH());
            assertEquals(floatValue++, result.ersDeployedThisLap());
            assertEquals(bitMask8Value++, result.networkPaused());
            assertArrayEquals(emptyFloatArray, result.tyresWear());
            assertArrayEquals(emptyIntArray, result.tyresDamage());
            assertEquals(0, result.frontLeftWingDamage());
            assertEquals(0, result.frontRightWingDamage());
            assertEquals(0, result.rearWingDamage());
            assertEquals(0, result.drsFault());
            assertEquals(0, result.engineDamage());
            assertEquals(0, result.gearBoxDamage());
        }
    }
}

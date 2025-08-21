package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.*;

public class CarStatusDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2020)
    @DisplayName("Builds the Car Status Data for 2020.")
    void testBuild_carStatus2020(int packetFormat) {
        float[] mockTireWear = new float[]{8F, 9F, 10F, 11F};
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            parseUtils.when(() -> ParseUtils.parseIntArray(mockByteBuffer, 4)).thenReturn(new int[4]);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
            when(mockByteBuffer.get()).thenReturn((byte) 22);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(1, 2, 3);
            when(mockByteBuffer.getFloat()).thenReturn((float) 1, (float) 2, (float) 3, (float) 4, (float) 5, (float) 6, (float) 7);
            CarStatusData result = CarStatusDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(1, result.tractionControl());
            assertEquals(2, result.antiLockBrakes());
            assertEquals(3, result.fuelMix());
            assertEquals(4, result.frontBrakeBias());
            assertEquals(5, result.pitLimitStatus());
            assertEquals(1, result.fuelInTank());
            assertEquals(2, result.fuelCapacity());
            assertEquals(3, result.fuelRemainingLaps());
            assertEquals(1, result.maxRPM());
            assertEquals(2, result.idleRPM());
            assertEquals(6, result.maxGears());
            assertEquals(7, result.drsAllowed());
            assertEquals(3, result.drsActivationDistance());
            assertArrayEquals(mockTireWear, result.tyresWear());
            assertEquals(12, result.actualTireCompound());
            assertEquals(13, result.visualTireCompound());
            assertEquals(14, result.tiresAgeLaps());
            assertArrayEquals(new int[4], result.tyresDamage());
            assertEquals(15, result.frontLeftWingDamage());
            assertEquals(16, result.frontRightWingDamage());
            assertEquals(17, result.rearWingDamage());
            assertEquals(18, result.drsFault());
            assertEquals(19, result.engineDamage());
            assertEquals(20, result.gearBoxDamage());
            assertEquals(22, result.vehicleFiaFlags());
            assertEquals(4, result.ersStoreEnergy());
            assertEquals(21, result.ersDeployMode());
            assertEquals(5, result.ersHarvestedThisLapMGUK());
            assertEquals(6, result.ersHarvestedThisLapMGUH());
            assertEquals(7, result.ersDeployedThisLap());
            assertEquals(0, result.networkPaused());
            assertEquals(0, result.enginePowerICE());
            assertEquals(0, result.enginePowerMGUK());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds the Car Status Data for 2021 and 2022.")
    void testBuild_carStatus2021And2022(int packetFormat) {
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            when(mockByteBuffer.get()).thenReturn((byte) 13);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(1, 2, 3);
            when(mockByteBuffer.getFloat()).thenReturn((float) 1, (float) 2, (float) 3, (float) 4, (float) 5, (float) 6, (float) 7);
            parseUtils.when(() -> ParseUtils.parseIntArray(mockByteBuffer, 4)).thenReturn(new int[4]);
            CarStatusData result = CarStatusDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(1, result.tractionControl());
            assertEquals(2, result.antiLockBrakes());
            assertEquals(3, result.fuelMix());
            assertEquals(4, result.frontBrakeBias());
            assertEquals(5, result.pitLimitStatus());
            assertEquals(1, result.fuelInTank());
            assertEquals(2, result.fuelCapacity());
            assertEquals(3, result.fuelRemainingLaps());
            assertEquals(1, result.maxRPM());
            assertEquals(2, result.idleRPM());
            assertEquals(6, result.maxGears());
            assertEquals(7, result.drsAllowed());
            assertEquals(3, result.drsActivationDistance());
            assertEquals(8, result.actualTireCompound());
            assertEquals(9, result.visualTireCompound());
            assertEquals(10, result.tiresAgeLaps());
            assertEquals(13, result.vehicleFiaFlags());
            assertEquals(4, result.ersStoreEnergy());
            assertEquals(11, result.ersDeployMode());
            assertEquals(5, result.ersHarvestedThisLapMGUK());
            assertEquals(6, result.ersHarvestedThisLapMGUH());
            assertEquals(7, result.ersDeployedThisLap());
            assertEquals(12, result.networkPaused());
            assertEquals(0, result.enginePowerICE());
            assertEquals(0, result.enginePowerMGUK());
            assertArrayEquals(new float[0], result.tyresWear());
            assertArrayEquals(new int[0], result.tyresDamage());
            assertEquals(0, result.frontLeftWingDamage());
            assertEquals(0, result.frontRightWingDamage());
            assertEquals(0, result.rearWingDamage());
            assertEquals(0, result.drsFault());
            assertEquals(0, result.engineDamage());
            assertEquals(0, result.gearBoxDamage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Car Status Data for 2023 to Present.")
    void testBuild_carStatus2023ToPresent(int packetFormat) {
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            bitMaskUtils.when(() -> BitMaskUtils.bitMask8(anyByte())).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            when(mockByteBuffer.get()).thenReturn((byte) 13);
            bitMaskUtils.when(() -> BitMaskUtils.bitMask16(anyShort())).thenReturn(1, 2, 3);
            when(mockByteBuffer.getFloat()).thenReturn((float) 1, (float) 2, (float) 3, (float) 4, (float) 5, (float) 6, (float) 7, (float) 8, (float) 9);
            parseUtils.when(() -> ParseUtils.parseIntArray(mockByteBuffer, 4)).thenReturn(new int[4]);
            CarStatusData result = CarStatusDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(1, result.tractionControl());
            assertEquals(2, result.antiLockBrakes());
            assertEquals(3, result.fuelMix());
            assertEquals(4, result.frontBrakeBias());
            assertEquals(5, result.pitLimitStatus());
            assertEquals(1, result.fuelInTank());
            assertEquals(2, result.fuelCapacity());
            assertEquals(3, result.fuelRemainingLaps());
            assertEquals(1, result.maxRPM());
            assertEquals(2, result.idleRPM());
            assertEquals(6, result.maxGears());
            assertEquals(7, result.drsAllowed());
            assertEquals(3, result.drsActivationDistance());
            assertEquals(8, result.actualTireCompound());
            assertEquals(9, result.visualTireCompound());
            assertEquals(10, result.tiresAgeLaps());
            assertEquals(13, result.vehicleFiaFlags());
            assertEquals(4, result.enginePowerICE());
            assertEquals(5, result.enginePowerMGUK());
            assertEquals(6, result.ersStoreEnergy());
            assertEquals(11, result.ersDeployMode());
            assertEquals(7, result.ersHarvestedThisLapMGUK());
            assertEquals(8, result.ersHarvestedThisLapMGUH());
            assertEquals(9, result.ersDeployedThisLap());
            assertEquals(12, result.networkPaused());
            assertArrayEquals(new float[0], result.tyresWear());
            assertArrayEquals(new int[0], result.tyresDamage());
            assertEquals(0, result.frontLeftWingDamage());
            assertEquals(0, result.frontRightWingDamage());
            assertEquals(0, result.rearWingDamage());
            assertEquals(0, result.drsFault());
            assertEquals(0, result.engineDamage());
            assertEquals(0, result.gearBoxDamage());
        }
    }
}

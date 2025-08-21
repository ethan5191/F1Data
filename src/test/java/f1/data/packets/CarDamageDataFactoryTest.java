package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class CarDamageDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020, Constants.YEAR_2021})
    @DisplayName("Builds the Car Damage Data from 2020 and 2021.")
    void testBuild_carDamage2020To2021(int packetFormat) {
        int bitMask8Count = 15;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils);

            CarDamageData result = CarDamageDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertArrayEquals(new float[4], result.tyresWear());
            assertArrayEquals(new int[4], result.tyresDamage());
            assertArrayEquals(new int[4], result.brakesDamage());
            assertEquals(1, result.frontLeftWingDamage());
            assertEquals(2, result.frontRightWingDamage());
            assertEquals(3, result.rearWingDamage());
            assertEquals(4, result.floorDamage());
            assertEquals(5, result.diffuserDamage());
            assertEquals(6, result.sidepodDamage());
            assertEquals(7, result.drsFault());
            assertEquals(8, result.gearBoxDamage());
            assertEquals(9, result.engineDamage());
            assertEquals(10, result.engineMGUHWear());
            assertEquals(11, result.engineESWear());
            assertEquals(12, result.engineCEWear());
            assertEquals(13, result.engineICEWear());
            assertEquals(14, result.engineMGUKWear());
            assertEquals(15, result.engineTCWear());
            assertEquals(0, result.ersFault());
            assertEquals(0, result.engineBlown());
            assertEquals(0, result.engineSeized());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Car Damage Data from 2022 to Present.")
    void testBuild_carDamage2022ToPresent(int packetFormat) {
        int bitMask8Count = 18;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils);

            CarDamageData result = CarDamageDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertArrayEquals(new float[4], result.tyresWear());
            assertArrayEquals(new int[4], result.tyresDamage());
            assertArrayEquals(new int[4], result.brakesDamage());
            assertEquals(1, result.frontLeftWingDamage());
            assertEquals(2, result.frontRightWingDamage());
            assertEquals(3, result.rearWingDamage());
            assertEquals(4, result.floorDamage());
            assertEquals(5, result.diffuserDamage());
            assertEquals(6, result.sidepodDamage());
            assertEquals(7, result.drsFault());
            assertEquals(8, result.ersFault());
            assertEquals(9, result.gearBoxDamage());
            assertEquals(10, result.engineDamage());
            assertEquals(11, result.engineMGUHWear());
            assertEquals(12, result.engineESWear());
            assertEquals(13, result.engineCEWear());
            assertEquals(14, result.engineICEWear());
            assertEquals(15, result.engineMGUKWear());
            assertEquals(16, result.engineTCWear());
            assertEquals(17, result.engineBlown());
            assertEquals(18, result.engineSeized());
        }
    }
}

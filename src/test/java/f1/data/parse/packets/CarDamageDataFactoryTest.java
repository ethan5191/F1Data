package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class CarDamageDataFactoryTest extends AbstractFactoryTest {

    private final float[] floatArray = new float[4];
    private final int[] intArray = new int[4];

    static Stream<Integer> supportedYears2020To2021() {
        return Stream.of(SupportedYearsEnum.F1_2020.getYear(),
                SupportedYearsEnum.F1_2021.getYear());
    }

    @ParameterizedTest
    @MethodSource("supportedYears2020To2021")
    @DisplayName("Builds the Car Damage Data from 2020 and 2021.")
    void testBuild_carDamage2020To2021(int packetFormat) {
        int bitMask8Count = 15;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);

            CarDamageData result = new CarDamageDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertArrayEquals(floatArray, result.tyresWear());
            assertArrayEquals(intArray, result.tyresDamage());
            assertArrayEquals(intArray, result.brakesDamage());
            assertEquals(bitMask8Value++, result.frontLeftWingDamage());
            assertEquals(bitMask8Value++, result.frontRightWingDamage());
            assertEquals(bitMask8Value++, result.rearWingDamage());
            assertEquals(bitMask8Value++, result.floorDamage());
            assertEquals(bitMask8Value++, result.diffuserDamage());
            assertEquals(bitMask8Value++, result.sidepodDamage());
            assertEquals(bitMask8Value++, result.drsFault());
            assertEquals(bitMask8Value++, result.gearBoxDamage());
            assertEquals(bitMask8Value++, result.engineDamage());
            assertEquals(bitMask8Value++, result.engineMGUHWear());
            assertEquals(bitMask8Value++, result.engineESWear());
            assertEquals(bitMask8Value++, result.engineCEWear());
            assertEquals(bitMask8Value++, result.engineICEWear());
            assertEquals(bitMask8Value++, result.engineMGUKWear());
            assertEquals(bitMask8Value++, result.engineTCWear());
            assertEquals(0, result.ersFault());
            assertEquals(0, result.engineBlown());
            assertEquals(0, result.engineSeized());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2022ToPresent")
    @DisplayName("Builds the Car Damage Data from 2022 to Present.")
    void testBuild_carDamage2022ToPresent(int packetFormat) {
        int bitMask8Count = 18;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 4);

            CarDamageData result = new CarDamageDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertArrayEquals(floatArray, result.tyresWear());
            assertArrayEquals(intArray, result.tyresDamage());
            assertArrayEquals(intArray, result.brakesDamage());
            assertEquals(bitMask8Value++, result.frontLeftWingDamage());
            assertEquals(bitMask8Value++, result.frontRightWingDamage());
            assertEquals(bitMask8Value++, result.rearWingDamage());
            assertEquals(bitMask8Value++, result.floorDamage());
            assertEquals(bitMask8Value++, result.diffuserDamage());
            assertEquals(bitMask8Value++, result.sidepodDamage());
            assertEquals(bitMask8Value++, result.drsFault());
            assertEquals(bitMask8Value++, result.ersFault());
            assertEquals(bitMask8Value++, result.gearBoxDamage());
            assertEquals(bitMask8Value++, result.engineDamage());
            assertEquals(bitMask8Value++, result.engineMGUHWear());
            assertEquals(bitMask8Value++, result.engineESWear());
            assertEquals(bitMask8Value++, result.engineCEWear());
            assertEquals(bitMask8Value++, result.engineICEWear());
            assertEquals(bitMask8Value++, result.engineMGUKWear());
            assertEquals(bitMask8Value++, result.engineTCWear());
            assertEquals(bitMask8Value++, result.engineBlown());
            assertEquals(bitMask8Value++, result.engineSeized());
        }
    }
}

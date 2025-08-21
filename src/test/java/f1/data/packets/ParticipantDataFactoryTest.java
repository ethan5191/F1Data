package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class ParticipantDataFactoryTest extends AbstractFactoryTest {

    private final int PRE_2025_NAME_LENGTH = 48;
    private final int POST_2025_NAME_LENGTH = 32;

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2020)
    @DisplayName("Builds the Participant Data for 2020.")
    void testBuild_carDamage2020(int packetFormat) {
        int bitMask8Count = 6;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            ParticipantData result = ParticipantDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.driverId());
            assertEquals(BIT_8_START + 2, result.teamId());
            assertEquals(BIT_8_START + 3, result.raceNumber());
            assertEquals(BIT_8_START + 4, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(BIT_8_START + 5, result.yourTelemetry());

            assertEquals(0, result.networkId());
            assertEquals(0, result.myTeam());
            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.platform());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds the Participant Data for 2021 and 2022.")
    void testBuild_carDamage2021And2022(int packetFormat) {
        int bitMask8Count = 8;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            ParticipantData result = ParticipantDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.driverId());
            assertEquals(BIT_8_START + 2, result.networkId());
            assertEquals(BIT_8_START + 3, result.teamId());
            assertEquals(BIT_8_START + 4, result.myTeam());
            assertEquals(BIT_8_START + 5, result.raceNumber());
            assertEquals(BIT_8_START + 6, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(BIT_8_START + 7, result.yourTelemetry());

            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.platform());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2023)
    @DisplayName("Builds the Participant Data for 2023.")
    void testBuild_carDamage2023(int packetFormat) {
        int bitMask8Count = 10;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            ParticipantData result = ParticipantDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.driverId());
            assertEquals(BIT_8_START + 2, result.networkId());
            assertEquals(BIT_8_START + 3, result.teamId());
            assertEquals(BIT_8_START + 4, result.myTeam());
            assertEquals(BIT_8_START + 5, result.raceNumber());
            assertEquals(BIT_8_START + 6, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(BIT_8_START + 7, result.yourTelemetry());
            assertEquals(BIT_8_START + 8, result.showOnlineNames());
            assertEquals(BIT_8_START + 9, result.platform());

            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2024)
    @DisplayName("Builds the Participant Data for 2024.")
    void testBuild_carDamage2024(int packetFormat) {
        int bitMask8Count = 10;
        int bitMask16Count = 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            ParticipantData result = ParticipantDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.driverId());
            assertEquals(BIT_8_START + 2, result.networkId());
            assertEquals(BIT_8_START + 3, result.teamId());
            assertEquals(BIT_8_START + 4, result.myTeam());
            assertEquals(BIT_8_START + 5, result.raceNumber());
            assertEquals(BIT_8_START + 6, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(BIT_8_START + 7, result.yourTelemetry());
            assertEquals(BIT_8_START + 8, result.showOnlineNames());
            assertEquals(BIT_16_START, result.techLevel());
            assertEquals(BIT_8_START + 9, result.platform());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2025)
    @DisplayName("Builds the Participant Data for 2025 to Present.")
    void testBuild_carDamage2025ToPresent(int packetFormat) {
        int bitMask8Count = 10;
        int bitMask16Count = 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            ParticipantData result = ParticipantDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.driverId());
            assertEquals(BIT_8_START + 2, result.networkId());
            assertEquals(BIT_8_START + 3, result.teamId());
            assertEquals(BIT_8_START + 4, result.myTeam());
            assertEquals(BIT_8_START + 5, result.raceNumber());
            assertEquals(BIT_8_START + 6, result.nationality());
            assertArrayEquals(new byte[POST_2025_NAME_LENGTH], result.name());
            assertEquals(BIT_8_START + 7, result.yourTelemetry());
            assertEquals(BIT_8_START + 8, result.showOnlineNames());
            assertEquals(BIT_16_START, result.techLevel());
            assertEquals(BIT_8_START + 9, result.platform());
            //TODO:Add 2025 new params.
        }
    }
}

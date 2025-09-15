package f1.data.parse.packets;

import f1.data.parse.packets.participant.ParticipantData;
import f1.data.parse.packets.participant.ParticipantDataFactory;
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
    @ValueSource(ints = {Constants.YEAR_2019, Constants.YEAR_2020})
    @DisplayName("Builds the Participant Data for 2019 and 2020.")
    void testBuild_participantData2019And2020(int packetFormat) {
        int bitMask8Count = 6;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            ParticipantData result = new ParticipantDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.driverId());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.raceNumber());
            assertEquals(bitMask8Value++, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.yourTelemetry());

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
    void testBuild_participantData2021And2022(int packetFormat) {
        int bitMask8Count = 8;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            ParticipantData result = new ParticipantDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.driverId());
            assertEquals(bitMask8Value++, result.networkId());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.myTeam());
            assertEquals(bitMask8Value++, result.raceNumber());
            assertEquals(bitMask8Value++, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.yourTelemetry());

            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.platform());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2023)
    @DisplayName("Builds the Participant Data for 2023.")
    void testBuild_participantData2023(int packetFormat) {
        int bitMask8Count = 10;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            ParticipantData result = new ParticipantDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.driverId());
            assertEquals(bitMask8Value++, result.networkId());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.myTeam());
            assertEquals(bitMask8Value++, result.raceNumber());
            assertEquals(bitMask8Value++, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.yourTelemetry());
            assertEquals(bitMask8Value++, result.showOnlineNames());
            assertEquals(bitMask8Value++, result.platform());

            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2024)
    @DisplayName("Builds the Participant Data for 2024.")
    void testBuild_participantData2024(int packetFormat) {
        int bitMask8Count = 10;
        int bitMask16Count = 1;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            ParticipantData result = new ParticipantDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.driverId());
            assertEquals(bitMask8Value++, result.networkId());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.myTeam());
            assertEquals(bitMask8Value++, result.raceNumber());
            assertEquals(bitMask8Value++, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.yourTelemetry());
            assertEquals(bitMask8Value++, result.showOnlineNames());
            assertEquals(BIT_16_START, result.techLevel());
            assertEquals(bitMask8Value++, result.platform());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2025)
    @DisplayName("Builds the Participant Data for 2025 to Present.")
    void testBuild_participantData2025ToPresent(int packetFormat) {
        int bitMask8Count = 10;
        int bitMask16Count = 1;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            ParticipantData result = new ParticipantDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.driverId());
            assertEquals(bitMask8Value++, result.networkId());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.myTeam());
            assertEquals(bitMask8Value++, result.raceNumber());
            assertEquals(bitMask8Value++, result.nationality());
            assertArrayEquals(new byte[POST_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.yourTelemetry());
            assertEquals(bitMask8Value++, result.showOnlineNames());
            assertEquals(BIT_16_START, result.techLevel());
            assertEquals(bitMask8Value++, result.platform());
            //TODO:Add 2025 new params.
        }
    }
}

package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.participant.LiveryColourData;
import f1.data.parse.packets.participant.ParticipantData;
import f1.data.parse.packets.participant.ParticipantDataFactory;
import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class ParticipantDataFactoryTest extends AbstractFactoryTest {

    private final int PRE_2025_NAME_LENGTH = 48;
    private final int POST_2025_NAME_LENGTH = 32;

    private final int LIVERY_COLOUR_DATA_25_SIZE = ParticipantData.LIVERY_COLOUR_DATA_25_SIZE;

    static Stream<Integer> supportedYears2019And2020() {
        return Stream.of(SupportedYearsEnum.F1_2019.getYear(),
                SupportedYearsEnum.F1_2020.getYear());
    }

    @ParameterizedTest
    @MethodSource("supportedYears2019And2020")
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
            validateNoLiveryData(result);
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2021To2022")
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
            validateNoLiveryData(result);
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2023")
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
            validateNoLiveryData(result);
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2024")
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

            validateNoLiveryData(result);
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2025")
    @DisplayName("Builds the Participant Data for 2025.")
    void testBuild_participantData2025(int packetFormat) {
        int bitMask8Count = 12;
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
            assertEquals(bitMask8Value++, result.numColours());
            for (int n = 0; n < LIVERY_COLOUR_DATA_25_SIZE; n++) {
                LiveryColourData temp = result.liveryColourData()[n];
                assertEquals(bitMask8Value, temp.red());
                assertEquals(bitMask8Value, temp.green());
                assertEquals(bitMask8Value, temp.blue());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2026")
    @DisplayName("Builds the Participant Data for 2026.")
    void testBuild_participantData2026(int packetFormat) {
        int bitMask8Count = 9;
        int bitMask16Count = 4;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            ParticipantData result = new ParticipantDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask16Value++, result.driverId());
            assertEquals(bitMask16Value++, result.networkId());
            assertEquals(bitMask16Value++, result.teamId());
            assertEquals(bitMask8Value++, result.myTeam());
            assertEquals(bitMask8Value++, result.raceNumber());
            assertEquals(bitMask8Value++, result.nationality());
            assertArrayEquals(new byte[POST_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.yourTelemetry());
            assertEquals(bitMask8Value++, result.showOnlineNames());
            assertEquals(bitMask16Value++, result.techLevel());
            assertEquals(bitMask8Value++, result.platform());
            assertEquals(bitMask8Value++, result.numColours());
            for (int n = 0; n < LIVERY_COLOUR_DATA_25_SIZE; n++) {
                LiveryColourData temp = result.liveryColourData()[n];
                assertEquals(bitMask8Value, temp.red());
                assertEquals(bitMask8Value, temp.green());
                assertEquals(bitMask8Value, temp.blue());
            }
        }
    }

    private void validateNoLiveryData(ParticipantData result) {
        assertEquals(0, result.numColours());
        assertEquals(LIVERY_COLOUR_DATA_25_SIZE, result.liveryColourData().length);
        for (int n = 0; n < LIVERY_COLOUR_DATA_25_SIZE; n++) {
            assertNull(result.liveryColourData()[n]);
        }
    }
}

package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class LobbyInfoDataFactoryTest extends AbstractFactoryTest {

    private final int PRE_2025_NAME_LENGTH = 48;
    private final int POST_2025_NAME_LENGTH = 32;

    static Stream<Integer> supportedYears2024To2025() {
        return Stream.of(SupportedYearsEnum.F1_2024.getYear(),
                SupportedYearsEnum.F1_2025.getYear());
    }

    @ParameterizedTest
    @MethodSource("supportedYears2020")
    @DisplayName("Builds the Lobby Info Data for 2020.")
    void testBuild_lobbyInfoData2020(int packetFormat) {
        int bitMask8Count = 4;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            LobbyInfoData result = new LobbyInfoDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.readyStatus());

            assertEquals(0, result.carNumber());
            assertEquals(0, result.platform());
            assertEquals(0, result.yourTelemetry());
            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2021To2022")
    @DisplayName("Builds the Lobby Info Data for 2021 to 2022.")
    void testBuild_lobbyInfoData2021To2022(int packetFormat) {
        int bitMask8Count = 5;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            LobbyInfoData result = new LobbyInfoDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.carNumber());
            assertEquals(bitMask8Value++, result.readyStatus());

            assertEquals(0, result.platform());
            assertEquals(0, result.yourTelemetry());
            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2023")
    @DisplayName("Builds the Lobby Info Data for 2023.")
    void testBuild_lobbyInfoData2023(int packetFormat) {
        int bitMask8Count = 6;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            LobbyInfoData result = new LobbyInfoDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.nationality());
            assertEquals(bitMask8Value++, result.platform());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(bitMask8Value++, result.carNumber());
            assertEquals(bitMask8Value++, result.readyStatus());

            assertEquals(0, result.yourTelemetry());
            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2024To2025")
    @DisplayName("Builds the Lobby Info Data for 2024 to 2025.")
    void testBuild_lobbyInfoData2024To2025(int packetFormat) {
        SupportedYearsEnum supportedYearsEnum = SupportedYearsEnum.fromYear(packetFormat);
        int nameLength = (supportedYearsEnum.is2024OrEarlier()) ? PRE_2025_NAME_LENGTH : POST_2025_NAME_LENGTH;
        int bitMask8Count = 8;
        int bitMask16Count = 1;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            LobbyInfoData result = new LobbyInfoDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask8Value++, result.teamId());
            assertEquals(bitMask8Value++, result.nationality());
            assertEquals(bitMask8Value++, result.platform());
            assertArrayEquals(new byte[nameLength], result.name());
            assertEquals(bitMask8Value++, result.carNumber());
            assertEquals(bitMask8Value++, result.yourTelemetry());
            assertEquals(bitMask8Value++, result.showOnlineNames());
            assertEquals(BIT_16_START, result.techLevel());
            assertEquals(bitMask8Value++, result.readyStatus());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2026")
    @DisplayName("Builds the Lobby Info Data for 2026 To Present.")
    void testBuild_lobbyInfoData2026ToPresent(int packetFormat) {
        SupportedYearsEnum supportedYearsEnum = SupportedYearsEnum.fromYear(packetFormat);
        int nameLength = (supportedYearsEnum.is2024OrEarlier()) ? PRE_2025_NAME_LENGTH : POST_2025_NAME_LENGTH;
        int bitMask8Count = 7;
        int bitMask16Count = 2;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            LobbyInfoData result = new LobbyInfoDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.aiControlled());
            assertEquals(bitMask16Value++, result.teamId());
            assertEquals(bitMask8Value++, result.nationality());
            assertEquals(bitMask8Value++, result.platform());
            assertArrayEquals(new byte[nameLength], result.name());
            assertEquals(bitMask8Value++, result.carNumber());
            assertEquals(bitMask8Value++, result.yourTelemetry());
            assertEquals(bitMask8Value++, result.showOnlineNames());
            assertEquals(bitMask16Value++, result.techLevel());
            assertEquals(bitMask8Value++, result.readyStatus());
        }
    }
}

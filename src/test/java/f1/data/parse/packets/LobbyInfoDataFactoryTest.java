package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class LobbyInfoDataFactoryTest extends AbstractFactoryTest {

    private final int PRE_2025_NAME_LENGTH = 48;
    private final int POST_2025_NAME_LENGTH = 32;

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020})
    @DisplayName("Builds the Lobby Info Data for 2020.")
    void testBuild_lobbyInfoData2020(int packetFormat) {
        int bitMask8Count = 4;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            LobbyInfoData result = LobbyInfoDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.teamId());
            assertEquals(BIT_8_START + 2, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(BIT_8_START + 3, result.readyStatus());

            assertEquals(0, result.carNumber());
            assertEquals(0, result.platform());
            assertEquals(0, result.yourTelemetry());
            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds the Lobby Info Data for 2021 to 2022.")
    void testBuild_lobbyInfoData2021To2022(int packetFormat) {
        int bitMask8Count = 5;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            LobbyInfoData result = LobbyInfoDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.teamId());
            assertEquals(BIT_8_START + 2, result.nationality());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(BIT_8_START + 3, result.carNumber());
            assertEquals(BIT_8_START + 4, result.readyStatus());

            assertEquals(0, result.platform());
            assertEquals(0, result.yourTelemetry());
            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2023})
    @DisplayName("Builds the Lobby Info Data for 2023.")
    void testBuild_lobbyInfoData2023(int packetFormat) {
        int bitMask8Count = 6;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            LobbyInfoData result = LobbyInfoDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.teamId());
            assertEquals(BIT_8_START + 2, result.nationality());
            assertEquals(BIT_8_START + 3, result.platform());
            assertArrayEquals(new byte[PRE_2025_NAME_LENGTH], result.name());
            assertEquals(BIT_8_START + 4, result.carNumber());
            assertEquals(BIT_8_START + 5, result.readyStatus());

            assertEquals(0, result.yourTelemetry());
            assertEquals(0, result.showOnlineNames());
            assertEquals(0, result.techLevel());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Lobby Info Data for 2024 to Present.")
    void testBuild_lobbyInfoData2024ToPresent(int packetFormat) {
        int nameLength = (packetFormat < Constants.YEAR_2025) ? PRE_2025_NAME_LENGTH : POST_2025_NAME_LENGTH;
        int bitMask8Count = 8;
        int bitMask16Count = 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            LobbyInfoData result = LobbyInfoDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_8_START, result.aiControlled());
            assertEquals(BIT_8_START + 1, result.teamId());
            assertEquals(BIT_8_START + 2, result.nationality());
            assertEquals(BIT_8_START + 3, result.platform());
            assertArrayEquals(new byte[nameLength], result.name());
            assertEquals(BIT_8_START + 4, result.carNumber());
            assertEquals(BIT_8_START + 5, result.yourTelemetry());
            assertEquals(BIT_8_START + 6, result.showOnlineNames());
            assertEquals(BIT_16_START, result.techLevel());
            assertEquals(BIT_8_START + 7, result.readyStatus());
        }
    }
}

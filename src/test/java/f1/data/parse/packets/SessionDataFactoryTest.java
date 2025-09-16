package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.session.*;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class SessionDataFactoryTest extends AbstractFactoryTest {

    private static final int SINGLE_GET_VAL = 36;
    private static final int MARSHAL_ZONE_SIZE = MarshalZoneDataFactory.MARSHAL_ZONE_SIZE;

    private static final int WEATHER_FORECAST_20_SIZE = SessionData.WEATHER_FORECAST_20_SIZE;
    private static final int WEATHER_FORECAST_21_TO_23_SIZE = SessionData.WEATHER_FORECAST_21_TO_23_SIZE;
    private static final int WEATHER_FORECAST_24_NEWER_SIZE = SessionData.WEATHER_FORECAST_24_NEWER_SIZE;

    static Stream<Integer> supportedYears2022() {
        return Stream.of(SupportedYearsEnum.F1_2022.getYear());
    }

    @ParameterizedTest
    @MethodSource("supportedYears2019")
    @DisplayName("Builds the Session Data for 2019.")
    void testBuild_sessionData2019(int packetFormat) {
        int bitMask8Count = 12;
        int bitMask8CountWeather = 1;//Extra param to ensure that the weather bitMask values all have the same data, easier to check this way.
        int bitMask16Count = 3;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count + bitMask8CountWeather);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, SINGLE_GET_VAL - 1);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, MARSHAL_ZONE_SIZE);
            SessionData result = new SessionDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.weather());
            assertEquals(SINGLE_GET_VAL, result.trackTemperature());
            assertEquals(SINGLE_GET_VAL, result.airTemperature());
            assertEquals(bitMask8Value++, result.totalLaps());
            assertEquals(bitMask16Value++, result.trackLength());
            assertEquals(bitMask8Value++, result.sessionType());
            assertEquals(SINGLE_GET_VAL, result.trackId());
            assertEquals(bitMask8Value++, result.formula());
            assertEquals(bitMask16Value++, result.sessionTimeLeft());
            assertEquals(bitMask16Value++, result.sessionDuration());
            assertEquals(bitMask8Value++, result.pitSpeedLimit());
            assertEquals(bitMask8Value++, result.gamePaused());
            assertEquals(bitMask8Value++, result.isSpectating());
            assertEquals(bitMask8Value++, result.spectatorCarIndex());
            assertEquals(bitMask8Value++, result.sliProNativeSupport());
            assertEquals(bitMask8Value++, result.numMarshalZones());
            verifyMarshalZones(result, SINGLE_GET_VAL);
            assertEquals(bitMask8Value++, result.safetyCarStatus());
            assertEquals(bitMask8Value++, result.networkGame());

            //params that didn't exist in 2019
            assertEquals(0, result.numWeatherForecastSamples());
            assertNull(result.weatherForecastSamples());
            assertEquals(0, result.forecastAccuracy());
            assertEquals(0, result.aiDifficulty());
            assertEquals(0, result.seasonLinkIdentifier());
            assertEquals(0, result.weekendLinkIdentifier());
            assertEquals(0, result.sessionLinkIdentifier());
            assertEquals(0, result.pitStopWindowIdealLap());
            assertEquals(0, result.pitStopWindowLatestLap());
            assertEquals(0, result.pitStopRejoinPosition());
            assertNull(result.assistData());
            assertEquals(0, result.gameMode());
            assertEquals(0, result.ruleSet());
            assertEquals(0, result.timeOfDay());
            assertEquals(0, result.sessionLength());
            assertEquals(0, result.speedUnitsLeadPlayer());
            assertEquals(0, result.tempUnitsLeadPlayer());
            assertEquals(0, result.speedUnitsSecondaryPlayer());
            assertEquals(0, result.tempUnitsSecondaryPlayer());
            assertEquals(0, result.numSafetyCarPeriods());
            assertEquals(0, result.numVirtualSafetyCarPeriods());
            assertEquals(0, result.numRedFlagPeriods());
            assertNull(result.gameModeData());
            assertEquals(0, result.sector2LapDistanceStart());
            assertEquals(0, result.sector3LapDistanceStart());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2020")
    @DisplayName("Builds the Session Data for 2020.")
    void testBuild_sessionData2020(int packetFormat) {
        int bitMask8Count = 13;
        int bitMask8CountWeather = 1;//Extra param to ensure that the weather bitMask values all have the same data, easier to check this way.
        int bitMask16Count = 3;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count + bitMask8CountWeather);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, SINGLE_GET_VAL - 1);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, MARSHAL_ZONE_SIZE);
            SessionData result = new SessionDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.weather());
            assertEquals(SINGLE_GET_VAL, result.trackTemperature());
            assertEquals(SINGLE_GET_VAL, result.airTemperature());
            assertEquals(bitMask8Value++, result.totalLaps());
            assertEquals(bitMask16Value++, result.trackLength());
            assertEquals(bitMask8Value++, result.sessionType());
            assertEquals(SINGLE_GET_VAL, result.trackId());
            assertEquals(bitMask8Value++, result.formula());
            assertEquals(bitMask16Value++, result.sessionTimeLeft());
            assertEquals(bitMask16Value++, result.sessionDuration());
            assertEquals(bitMask8Value++, result.pitSpeedLimit());
            assertEquals(bitMask8Value++, result.gamePaused());
            assertEquals(bitMask8Value++, result.isSpectating());
            assertEquals(bitMask8Value++, result.spectatorCarIndex());
            assertEquals(bitMask8Value++, result.sliProNativeSupport());
            assertEquals(bitMask8Value++, result.numMarshalZones());
            verifyMarshalZones(result, SINGLE_GET_VAL);
            assertEquals(bitMask8Value++, result.safetyCarStatus());
            assertEquals(bitMask8Value++, result.networkGame());
            assertEquals(bitMask8Value++, result.numWeatherForecastSamples());
            for (int n = 0; n < WEATHER_FORECAST_20_SIZE; n++) {
                WeatherForecastSampleData temp = result.weatherForecastSamples()[n];
                assertEquals(bitMask8Value, temp.sessionType());
                assertEquals(bitMask8Value, temp.timeOffset());
                assertEquals(bitMask8Value, temp.weather());
                assertEquals(SINGLE_GET_VAL, temp.trackTemperature());
                assertEquals(SINGLE_GET_VAL, temp.airTemperature());
            }

            //params that didn't exist in 2020
            assertEquals(0, result.forecastAccuracy());
            assertEquals(0, result.aiDifficulty());
            assertEquals(0, result.seasonLinkIdentifier());
            assertEquals(0, result.weekendLinkIdentifier());
            assertEquals(0, result.sessionLinkIdentifier());
            assertEquals(0, result.pitStopWindowIdealLap());
            assertEquals(0, result.pitStopWindowLatestLap());
            assertEquals(0, result.pitStopRejoinPosition());
            assertNull(result.assistData());
            assertEquals(0, result.gameMode());
            assertEquals(0, result.ruleSet());
            assertEquals(0, result.timeOfDay());
            assertEquals(0, result.sessionLength());
            assertEquals(0, result.speedUnitsLeadPlayer());
            assertEquals(0, result.tempUnitsLeadPlayer());
            assertEquals(0, result.speedUnitsSecondaryPlayer());
            assertEquals(0, result.tempUnitsSecondaryPlayer());
            assertEquals(0, result.numSafetyCarPeriods());
            assertEquals(0, result.numVirtualSafetyCarPeriods());
            assertEquals(0, result.numRedFlagPeriods());
            assertNull(result.gameModeData());
            assertEquals(0, result.sector2LapDistanceStart());
            assertEquals(0, result.sector3LapDistanceStart());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2021")
    @DisplayName("Builds the Session Data for 2021.")
    void testBuild_sessionData2021(int packetFormat) {
        int numBitMask8InWeather = 4;
        int bitMask8Count = 27 + (numBitMask8InWeather * WEATHER_FORECAST_21_TO_23_SIZE);
        int bitMask16Count = 3;
        int bitMask32Count = 3;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int bitMask32Value = BIT_32_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, SINGLE_GET_VAL - 1);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, MARSHAL_ZONE_SIZE);
            SessionData result = new SessionDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.weather());
            assertEquals(SINGLE_GET_VAL, result.trackTemperature());
            assertEquals(SINGLE_GET_VAL, result.airTemperature());
            assertEquals(bitMask8Value++, result.totalLaps());
            assertEquals(bitMask16Value++, result.trackLength());
            assertEquals(bitMask8Value++, result.sessionType());
            assertEquals(SINGLE_GET_VAL, result.trackId());
            assertEquals(bitMask8Value++, result.formula());
            assertEquals(bitMask16Value++, result.sessionTimeLeft());
            assertEquals(bitMask16Value++, result.sessionDuration());
            assertEquals(bitMask8Value++, result.pitSpeedLimit());
            assertEquals(bitMask8Value++, result.gamePaused());
            assertEquals(bitMask8Value++, result.isSpectating());
            assertEquals(bitMask8Value++, result.spectatorCarIndex());
            assertEquals(bitMask8Value++, result.sliProNativeSupport());
            assertEquals(bitMask8Value++, result.numMarshalZones());
            verifyMarshalZones(result, SINGLE_GET_VAL);
            assertEquals(bitMask8Value++, result.safetyCarStatus());
            assertEquals(bitMask8Value++, result.networkGame());
            assertEquals(bitMask8Value++, result.numWeatherForecastSamples());
            int nextBit8Value = verifyWeatherForecast21AndLater(result, SINGLE_GET_VAL, WEATHER_FORECAST_21_TO_23_SIZE, bitMask8Value++);
            assertEquals(nextBit8Value, result.forecastAccuracy());
            assertEquals(++nextBit8Value, result.aiDifficulty());
            assertEquals(bitMask32Value++, result.seasonLinkIdentifier());
            assertEquals(bitMask32Value++, result.weekendLinkIdentifier());
            assertEquals(bitMask32Value++, result.sessionLinkIdentifier());
            assertEquals(++nextBit8Value, result.pitStopWindowIdealLap());
            assertEquals(++nextBit8Value, result.pitStopWindowLatestLap());
            assertEquals(++nextBit8Value, result.pitStopRejoinPosition());
            verifyAssistData(result, ++nextBit8Value);

            assertEquals(0, result.gameMode());
            assertEquals(0, result.ruleSet());
            assertEquals(0, result.timeOfDay());
            assertEquals(0, result.sessionLength());
            assertEquals(0, result.speedUnitsLeadPlayer());
            assertEquals(0, result.tempUnitsLeadPlayer());
            assertEquals(0, result.speedUnitsSecondaryPlayer());
            assertEquals(0, result.tempUnitsSecondaryPlayer());
            assertEquals(0, result.numSafetyCarPeriods());
            assertEquals(0, result.numVirtualSafetyCarPeriods());
            assertEquals(0, result.numRedFlagPeriods());
            assertNull(result.gameModeData());
            assertEquals(0, result.sector2LapDistanceStart());
            assertEquals(0, result.sector3LapDistanceStart());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2022")
    @DisplayName("Builds the Session Data for 2022.")
    void testBuild_sessionData2022(int packetFormat) {
        int numBitMask8InWeather = 4;
        int bitMask8Count = 30 + (numBitMask8InWeather * WEATHER_FORECAST_21_TO_23_SIZE);
        int bitMask16Count = 3;
        int bitMask32Count = 4;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int bitMask32Value = BIT_32_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, SINGLE_GET_VAL - 1);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, MARSHAL_ZONE_SIZE);
            SessionData result = new SessionDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.weather());
            assertEquals(SINGLE_GET_VAL, result.trackTemperature());
            assertEquals(SINGLE_GET_VAL, result.airTemperature());
            assertEquals(bitMask8Value++, result.totalLaps());
            assertEquals(bitMask16Value++, result.trackLength());
            assertEquals(bitMask8Value++, result.sessionType());
            assertEquals(SINGLE_GET_VAL, result.trackId());
            assertEquals(bitMask8Value++, result.formula());
            assertEquals(bitMask16Value++, result.sessionTimeLeft());
            assertEquals(bitMask16Value++, result.sessionDuration());
            assertEquals(bitMask8Value++, result.pitSpeedLimit());
            assertEquals(bitMask8Value++, result.gamePaused());
            assertEquals(bitMask8Value++, result.isSpectating());
            assertEquals(bitMask8Value++, result.spectatorCarIndex());
            assertEquals(bitMask8Value++, result.sliProNativeSupport());
            assertEquals(bitMask8Value++, result.numMarshalZones());
            verifyMarshalZones(result, SINGLE_GET_VAL);
            assertEquals(bitMask8Value++, result.safetyCarStatus());
            assertEquals(bitMask8Value++, result.networkGame());
            assertEquals(bitMask8Value++, result.numWeatherForecastSamples());
            int nextBit8Value = verifyWeatherForecast21AndLater(result, SINGLE_GET_VAL, WEATHER_FORECAST_21_TO_23_SIZE, bitMask8Value++);
            assertEquals(nextBit8Value, result.forecastAccuracy());
            assertEquals(++nextBit8Value, result.aiDifficulty());
            assertEquals(bitMask32Value++, result.seasonLinkIdentifier());
            assertEquals(bitMask32Value++, result.weekendLinkIdentifier());
            assertEquals(bitMask32Value++, result.sessionLinkIdentifier());
            assertEquals(++nextBit8Value, result.pitStopWindowIdealLap());
            assertEquals(++nextBit8Value, result.pitStopWindowLatestLap());
            assertEquals(++nextBit8Value, result.pitStopRejoinPosition());
            nextBit8Value = verifyAssistData(result, ++nextBit8Value);
            assertEquals(++nextBit8Value, result.gameMode());
            assertEquals(++nextBit8Value, result.ruleSet());
            assertEquals(bitMask32Value++, result.timeOfDay());
            assertEquals(++nextBit8Value, result.sessionLength());

            assertEquals(0, result.speedUnitsLeadPlayer());
            assertEquals(0, result.tempUnitsLeadPlayer());
            assertEquals(0, result.speedUnitsSecondaryPlayer());
            assertEquals(0, result.tempUnitsSecondaryPlayer());
            assertEquals(0, result.numSafetyCarPeriods());
            assertEquals(0, result.numVirtualSafetyCarPeriods());
            assertEquals(0, result.numRedFlagPeriods());
            assertNull(result.gameModeData());
            assertEquals(0, result.sector2LapDistanceStart());
            assertEquals(0, result.sector3LapDistanceStart());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2023")
    @DisplayName("Builds the Session Data for 2023.")
    void testBuild_sessionData2023(int packetFormat) {
        int numBitMask8InWeather = 4;
        int bitMask8Count = 37 + (numBitMask8InWeather * WEATHER_FORECAST_21_TO_23_SIZE);
        int bitMask16Count = 3;
        int bitMask32Count = 4;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int bitMask32Value = BIT_32_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, SINGLE_GET_VAL - 1);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, MARSHAL_ZONE_SIZE);
            SessionData result = new SessionDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.weather());
            assertEquals(SINGLE_GET_VAL, result.trackTemperature());
            assertEquals(SINGLE_GET_VAL, result.airTemperature());
            assertEquals(bitMask8Value++, result.totalLaps());
            assertEquals(bitMask16Value++, result.trackLength());
            assertEquals(bitMask8Value++, result.sessionType());
            assertEquals(SINGLE_GET_VAL, result.trackId());
            assertEquals(bitMask8Value++, result.formula());
            assertEquals(bitMask16Value++, result.sessionTimeLeft());
            assertEquals(bitMask16Value++, result.sessionDuration());
            assertEquals(bitMask8Value++, result.pitSpeedLimit());
            assertEquals(bitMask8Value++, result.gamePaused());
            assertEquals(bitMask8Value++, result.isSpectating());
            assertEquals(bitMask8Value++, result.spectatorCarIndex());
            assertEquals(bitMask8Value++, result.sliProNativeSupport());
            assertEquals(bitMask8Value++, result.numMarshalZones());
            verifyMarshalZones(result, SINGLE_GET_VAL);
            assertEquals(bitMask8Value++, result.safetyCarStatus());
            assertEquals(bitMask8Value++, result.networkGame());
            assertEquals(bitMask8Value++, result.numWeatherForecastSamples());
            int nextBit8Value = verifyWeatherForecast21AndLater(result, SINGLE_GET_VAL, WEATHER_FORECAST_21_TO_23_SIZE, bitMask8Value++);
            assertEquals(nextBit8Value, result.forecastAccuracy());
            assertEquals(++nextBit8Value, result.aiDifficulty());
            assertEquals(bitMask32Value++, result.seasonLinkIdentifier());
            assertEquals(bitMask32Value++, result.weekendLinkIdentifier());
            assertEquals(bitMask32Value++, result.sessionLinkIdentifier());
            assertEquals(++nextBit8Value, result.pitStopWindowIdealLap());
            assertEquals(++nextBit8Value, result.pitStopWindowLatestLap());
            assertEquals(++nextBit8Value, result.pitStopRejoinPosition());
            nextBit8Value = verifyAssistData(result, ++nextBit8Value);
            assertEquals(++nextBit8Value, result.gameMode());
            assertEquals(++nextBit8Value, result.ruleSet());
            assertEquals(bitMask32Value++, result.timeOfDay());
            assertEquals(++nextBit8Value, result.sessionLength());
            assertEquals(++nextBit8Value, result.speedUnitsLeadPlayer());
            assertEquals(++nextBit8Value, result.tempUnitsLeadPlayer());
            assertEquals(++nextBit8Value, result.speedUnitsSecondaryPlayer());
            assertEquals(++nextBit8Value, result.tempUnitsSecondaryPlayer());
            assertEquals(++nextBit8Value, result.numSafetyCarPeriods());
            assertEquals(++nextBit8Value, result.numVirtualSafetyCarPeriods());
            assertEquals(++nextBit8Value, result.numRedFlagPeriods());

            assertNull(result.gameModeData());
            assertEquals(0, result.sector2LapDistanceStart());
            assertEquals(0, result.sector3LapDistanceStart());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2024ToPresent")
    @DisplayName("Builds the Session Data for 2024 to Present.")
    void testBuild_sessionData2024ToPresent(int packetFormat) {
        int numBitMask8InWeather = 4;
        int bitMask8Count = 62 + (numBitMask8InWeather * WEATHER_FORECAST_24_NEWER_SIZE);
        int bitMask16Count = 3;
        int bitMask32Count = 4;
        int floatCount = 4;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int bitMask32Value = BIT_32_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, SINGLE_GET_VAL - 1);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, MARSHAL_ZONE_SIZE);
            FactoryTestHelper.parseIntArray(mockByteBuffer, parseUtils, 12);
            SessionData result = new SessionDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask8Value++, result.weather());
            assertEquals(SINGLE_GET_VAL, result.trackTemperature());
            assertEquals(SINGLE_GET_VAL, result.airTemperature());
            assertEquals(bitMask8Value++, result.totalLaps());
            assertEquals(bitMask16Value++, result.trackLength());
            assertEquals(bitMask8Value++, result.sessionType());
            assertEquals(SINGLE_GET_VAL, result.trackId());
            assertEquals(bitMask8Value++, result.formula());
            assertEquals(bitMask16Value++, result.sessionTimeLeft());
            assertEquals(bitMask16Value++, result.sessionDuration());
            assertEquals(bitMask8Value++, result.pitSpeedLimit());
            assertEquals(bitMask8Value++, result.gamePaused());
            assertEquals(bitMask8Value++, result.isSpectating());
            assertEquals(bitMask8Value++, result.spectatorCarIndex());
            assertEquals(bitMask8Value++, result.sliProNativeSupport());
            assertEquals(bitMask8Value++, result.numMarshalZones());
            int nextFloat = verifyMarshalZones(result, SINGLE_GET_VAL);
            assertEquals(bitMask8Value++, result.safetyCarStatus());
            assertEquals(bitMask8Value++, result.networkGame());
            assertEquals(bitMask8Value++, result.numWeatherForecastSamples());
            int nextBit8Value = verifyWeatherForecast21AndLater(result, SINGLE_GET_VAL, WEATHER_FORECAST_24_NEWER_SIZE, bitMask8Value++);
            assertEquals(nextBit8Value, result.forecastAccuracy());
            assertEquals(++nextBit8Value, result.aiDifficulty());
            assertEquals(bitMask32Value++, result.seasonLinkIdentifier());
            assertEquals(bitMask32Value++, result.weekendLinkIdentifier());
            assertEquals(bitMask32Value++, result.sessionLinkIdentifier());
            assertEquals(++nextBit8Value, result.pitStopWindowIdealLap());
            assertEquals(++nextBit8Value, result.pitStopWindowLatestLap());
            assertEquals(++nextBit8Value, result.pitStopRejoinPosition());
            nextBit8Value = verifyAssistData(result, ++nextBit8Value);
            assertEquals(++nextBit8Value, result.gameMode());
            assertEquals(++nextBit8Value, result.ruleSet());
            assertEquals(bitMask32Value++, result.timeOfDay());
            assertEquals(++nextBit8Value, result.sessionLength());
            assertEquals(++nextBit8Value, result.speedUnitsLeadPlayer());
            assertEquals(++nextBit8Value, result.tempUnitsLeadPlayer());
            assertEquals(++nextBit8Value, result.speedUnitsSecondaryPlayer());
            assertEquals(++nextBit8Value, result.tempUnitsSecondaryPlayer());
            assertEquals(++nextBit8Value, result.numSafetyCarPeriods());
            assertEquals(++nextBit8Value, result.numVirtualSafetyCarPeriods());
            assertEquals(++nextBit8Value, result.numRedFlagPeriods());
            verifyGameModData(result, ++nextBit8Value);
            assertEquals(--nextFloat, result.sector2LapDistanceStart());
            assertEquals(nextFloat, result.sector3LapDistanceStart());
        }
    }

    private int verifyMarshalZones(SessionData result, int singleGetValue) {
        int marshalZone = FLOAT_START;
        for (int i = 0; i < MARSHAL_ZONE_SIZE; i++) {
            MarshalZoneData temp = result.marshalZones()[i];
            assertEquals(marshalZone, temp.zoneStart());
            assertEquals(singleGetValue, temp.zoneFlag());
            marshalZone++;
        }
        return marshalZone;
    }

    private int verifyWeatherForecast21AndLater(SessionData result, int singleGetValue, int arraySize, int startValue) {
        for (int n = 0; n < arraySize; n++) {
            WeatherForecastSampleData temp = result.weatherForecastSamples()[n];
            assertEquals(startValue, temp.sessionType());
            assertEquals(++startValue, temp.timeOffset());
            assertEquals(++startValue, temp.weather());
            assertEquals(singleGetValue, temp.trackTemperature());
            assertEquals(singleGetValue, temp.trackTemperatureChange());
            assertEquals(singleGetValue, temp.airTemperature());
            assertEquals(singleGetValue, temp.airTemperatureChange());
            assertEquals(++startValue, temp.rainPercentage());
            ++startValue;
        }
        return startValue;
    }

    private int verifyAssistData(SessionData result, int startValue) {
        AssistData ad = result.assistData();
        assertEquals(startValue, ad.steering());
        assertEquals(++startValue, ad.braking());
        assertEquals(++startValue, ad.gearbox());
        assertEquals(++startValue, ad.pit());
        assertEquals(++startValue, ad.pitRelease());
        assertEquals(++startValue, ad.ers());
        assertEquals(++startValue, ad.drs());
        assertEquals(++startValue, ad.dynamicRacingLine());
        assertEquals(++startValue, ad.dynamicRacingLineType());
        return startValue;
    }

    private void verifyGameModData(SessionData result, int startValue) {
        GameModeData gmd = result.gameModeData();
        assertEquals(startValue, gmd.equalCarPerformance());
        assertEquals(++startValue, gmd.recoveryMode());
        assertEquals(++startValue, gmd.flashbackLimit());
        assertEquals(++startValue, gmd.surfaceType());
        assertEquals(++startValue, gmd.lowFuelMode());
        assertEquals(++startValue, gmd.raceStarts());
        assertEquals(++startValue, gmd.tyreTemperature());
        assertEquals(++startValue, gmd.pitLaneTyreSim());
        assertEquals(++startValue, gmd.carDamage());
        assertEquals(++startValue, gmd.carDamageRate());
        assertEquals(++startValue, gmd.collisions());
        assertEquals(++startValue, gmd.collisionsOffFirstLap());
        assertEquals(++startValue, gmd.mpUnsafePitRelease());
        assertEquals(++startValue, gmd.mpOffForGriefing());
        assertEquals(++startValue, gmd.cornerCuttingStringency());
        assertEquals(++startValue, gmd.parcFermeRules());
        assertEquals(++startValue, gmd.pitStopExperience());
        assertEquals(++startValue, gmd.safetyCar());
        assertEquals(++startValue, gmd.safetyCarExperience());
        assertEquals(++startValue, gmd.formationLap());
        assertEquals(++startValue, gmd.formationLapExperience());
        assertEquals(++startValue, gmd.redFlags());
        assertEquals(++startValue, gmd.affectsLicenseLevelSolo());
        assertEquals(++startValue, gmd.affectsLicenseLevelMP());
        assertEquals(++startValue, gmd.numSessionsInWeekend());
        assertArrayEquals(new int[12], gmd.weekendStructure());
    }
}

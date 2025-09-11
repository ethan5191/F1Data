package f1.data.parse.packets;

import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.WeatherForecastSampleData;
import f1.data.parse.packets.session.WeatherForecastSampleDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class WeatherForecastSampleDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020})
    @DisplayName("Builds the Weather Forecast Sample Data Factory for 2020")
    void testBuild_weatherForecastSampleData2020(int packetFormat) {
        int bitMask8Count = (3 * SessionData.WEATHER_FORECAST_20_SIZE);
        int intCount = 2;
        int bitMask8Value = BIT_8_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, intCount);
            WeatherForecastSampleData[] result = WeatherForecastSampleDataFactory.build(packetFormat, mockByteBuffer, SessionData.WEATHER_FORECAST_20_SIZE);
            assertNotNull(result);
            Assertions.assertEquals(SessionData.WEATHER_FORECAST_20_SIZE, result.length);
            for (WeatherForecastSampleData data : result) {
                assertEquals(bitMask8Value++, data.sessionType());
                assertEquals(bitMask8Value++, data.timeOffset());
                assertEquals(bitMask8Value++, data.weather());
                assertEquals(intCount + 1, data.trackTemperature());
                assertEquals(intCount + 1, data.airTemperature());

                assertEquals(0, data.trackTemperatureChange());
                assertEquals(0, data.airTemperatureChange());
                assertEquals(0, data.rainPercentage());
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022,
            Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Weather Forecast Sample Data Factory for 2021 to Present")
    void testBuild_weatherForecastSampleData2021ToPresent(int packetFormat) {
        int arraySize = (packetFormat <= Constants.YEAR_2023) ? SessionData.WEATHER_FORECAST_21_TO_23_SIZE : SessionData.WEATHER_FORECAST_24_NEWER_SIZE;
        int bitMask8Count = (4 * arraySize);
        int intCount = 4;
        int bitMask8Value = BIT_8_START;
        int intCountValue = intCount + 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, intCount);
            WeatherForecastSampleData[] result = WeatherForecastSampleDataFactory.build(packetFormat, mockByteBuffer, arraySize);
            assertNotNull(result);
            Assertions.assertEquals(arraySize, result.length);
            for (WeatherForecastSampleData data : result) {
                assertEquals(bitMask8Value++, data.sessionType());
                assertEquals(bitMask8Value++, data.timeOffset());
                assertEquals(bitMask8Value++, data.weather());
                assertEquals(intCountValue, data.trackTemperature());
                assertEquals(intCountValue, data.trackTemperatureChange());
                assertEquals(intCountValue, data.airTemperature());
                assertEquals(intCountValue, data.airTemperatureChange());
                assertEquals(bitMask8Value++, data.rainPercentage());
            }
        }
    }
}

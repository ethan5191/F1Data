package f1.data.parse.packets;

import f1.data.parse.packets.session.WeatherForecastSampleData;
import f1.data.parse.packets.session.WeatherForecastSampleDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class WeatherForecastSampleDataFactoryTest extends AbstractFactoryTest {

    private static final int WEATHER_FORECAST_20_SIZE = 20;
    private static final int WEATHER_FORECAST_21_TO_23_SIZE = 56;
    private static final int WEATHER_FORECAST_24_NEWER_SIZE = 64;

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2020})
    @DisplayName("Builds the Weather Forecast Sample Data for 2020.")
    void testBuild_weatherForecastSampleData2020(int packetFormat) {
        int bitMask8Count = (3 * WEATHER_FORECAST_20_SIZE);
        int intCount = (2 * WEATHER_FORECAST_20_SIZE);
        int bit8Value = BIT_8_START;
        int intValue = intCount + 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, intCount);
            WeatherForecastSampleData[] result = WeatherForecastSampleDataFactory.build(packetFormat, mockByteBuffer, WEATHER_FORECAST_20_SIZE);
            assertNotNull(result);
            for (WeatherForecastSampleData data : result) {
                assertEquals(bit8Value++, data.sessionType());
                assertEquals(bit8Value++, data.timeOffset());
                assertEquals(bit8Value++, data.weather());
                assertEquals(intValue, data.trackTemperature());
                assertEquals(intValue, data.airTemperature());
                assertEquals(0, data.trackTemperatureChange());
                assertEquals(0, data.airTemperatureChange());
                assertEquals(0, data.rainPercentage());
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Weather Forecast Sample Data for 2021 to Present.")
    void testBuild_weatherForecastSampleData2021ToPresent(int packetFormat) {
        int arraySize = (packetFormat <= Constants.YEAR_2023) ? WEATHER_FORECAST_21_TO_23_SIZE : WEATHER_FORECAST_24_NEWER_SIZE;
        int bitMask8Count = (4 * arraySize);
        int intCount = 4;
        int bit8Value = BIT_8_START;
        int intValue = intCount + 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, intCount);
            WeatherForecastSampleData[] result = WeatherForecastSampleDataFactory.build(packetFormat, mockByteBuffer, arraySize);
            assertNotNull(result);
            for (WeatherForecastSampleData data : result) {
                assertEquals(bit8Value++, data.sessionType());
                assertEquals(bit8Value++, data.timeOffset());
                assertEquals(bit8Value++, data.weather());
                assertEquals(intValue, data.trackTemperature());
                assertEquals(intValue, data.trackTemperatureChange());
                assertEquals(intValue, data.airTemperature());
                assertEquals(intValue, data.airTemperatureChange());
                assertEquals(bit8Value++, data.rainPercentage());
            }
        }
    }
}

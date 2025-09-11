package f1.data.parse.packets.session;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class WeatherForecastSampleDataFactory {

    public static WeatherForecastSampleData[] build(int packetFormat, ByteBuffer byteBuffer, int size) {
        WeatherForecastSampleData[] results = new WeatherForecastSampleData[size];
        switch (packetFormat) {
            case Constants.YEAR_2020:
                for (int i = 0; i < results.length; i++) {
                    WeatherForecastSampleData.WeatherForecastSampleData20 w20 = new WeatherForecastSampleData.WeatherForecastSampleData20(byteBuffer);
                    results[i] = new WeatherForecastSampleData(w20.sessionType(), w20.timeOffset(), w20.weather(), w20.trackTemperature(), 0, w20.airTemperature(), 0, 0);
                }
                break;
            case Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024,
                 Constants.YEAR_2025:
                for (int i = 0; i < results.length; i++) {
                    WeatherForecastSampleData.WeatherForecastSampleData21 w21 = new WeatherForecastSampleData.WeatherForecastSampleData21(byteBuffer);
                    results[i] = new WeatherForecastSampleData(w21.sessionType(), w21.timeOffset(), w21.weather(), w21.trackTemperature(), w21.trackTemperatureChange(), w21.airTemperature(), w21.airTemperatureChange(), w21.rainPercentage());
                }
                break;
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        }
        return results;
    }
}

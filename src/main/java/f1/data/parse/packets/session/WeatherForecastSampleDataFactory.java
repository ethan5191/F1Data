package f1.data.parse.packets.session;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class WeatherForecastSampleDataFactory {

    public static WeatherForecastSampleData[] build(int packetFormat, ByteBuffer byteBuffer, int size) {
        WeatherForecastSampleData[] results = new WeatherForecastSampleData[size];
        for (int i = 0; i < results.length; i++) {
            if (packetFormat == Constants.YEAR_2020) {
                WeatherForecastSampleData.WeatherForecastSampleData20 w20 = new WeatherForecastSampleData.WeatherForecastSampleData20(byteBuffer);
                results[i] = new WeatherForecastSampleData(w20.sessionType(), w20.timeOffset(), w20.weather(), w20.trackTemperature(), 0, w20.airTemperature(), 0, 0);
            } else {
                WeatherForecastSampleData.WeatherForecastSampleData21 w21 = new WeatherForecastSampleData.WeatherForecastSampleData21(byteBuffer);
                results[i] = new WeatherForecastSampleData(w21.sessionType(), w21.timeOffset(), w21.weather(), w21.trackTemperature(), w21.trackTemperatureChange(), w21.airTemperature(), w21.airTemperatureChange(), w21.rainPercentage());
            }
        }
        return results;
    }
}

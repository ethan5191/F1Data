package f1.data.parse.packets.session;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;

import java.nio.ByteBuffer;

public class WeatherForecastSampleDataFactory implements DataFactory<WeatherForecastSampleData[]> {

    private final SupportedYearsEnum packetFormat;
    private final int arraySize;

    public WeatherForecastSampleDataFactory(int packetFormat, int arraySize) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
        this.arraySize = arraySize;
    }

    public WeatherForecastSampleData[] build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2020 -> buildData20(byteBuffer);
            case F1_2021, F1_2022, F1_2023, F1_2024, F1_2025 -> buildData21(byteBuffer);
            default ->
                    throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }

    public WeatherForecastSampleData[] buildData20(ByteBuffer byteBuffer) {
        WeatherForecastSampleData[] results = new WeatherForecastSampleData[arraySize];
        for (int i = 0; i < arraySize; i++) {
            WeatherForecastSampleData.WeatherForecastSampleData20 w20 = new WeatherForecastSampleData.WeatherForecastSampleData20(byteBuffer);
            results[i] = new WeatherForecastSampleData(w20.sessionType(), w20.timeOffset(), w20.weather(), w20.trackTemperature(), 0, w20.airTemperature(), 0, 0);
        }
        return results;
    }

    public WeatherForecastSampleData[] buildData21(ByteBuffer byteBuffer) {
        WeatherForecastSampleData[] results = new WeatherForecastSampleData[arraySize];
        for (int i = 0; i < arraySize; i++) {
            WeatherForecastSampleData.WeatherForecastSampleData21 w21 = new WeatherForecastSampleData.WeatherForecastSampleData21(byteBuffer);
            results[i] = new WeatherForecastSampleData(w21.sessionType(), w21.timeOffset(), w21.weather(), w21.trackTemperature(), w21.trackTemperatureChange(), w21.airTemperature(), w21.airTemperatureChange(), w21.rainPercentage());
        }
        return results;
    }
}

package f1.data.packets.session;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 *  - F1 2020 WeatherForecast Length: 5 bytes
 *  - F1 2021 - 2025 WeatherForecast Length: 8 bytes
 * This struct is 8 bytes long and provides a single sample of weather and track
 * conditions for a specific point in the near future.
 * <p>
 * Member Name            | Data Type | Size (bytes) | First Appeared | Notes
 * -----------------------|-----------|--------------|----------------|-----------------------------
 * m_sessionType          | uint8     | 1            | 2020           | 0 = unknown, see appendix
 * m_timeOffset           | uint8     | 1            | 2020           | Time in minutes the forecast is for
 * m_weather              | uint8     | 1            | 2020           | 0 = clear, 1 = light cloud, 2 = overcast, 3 = light rain, 4 = heavy rain, 5 = storm
 * m_trackTemperature     | int8      | 1            | 2020           | Track temp. in degrees Celsius
 * m_trackTemperatureChange | int8    | 1            | 2021           | Track temp. change – 0 = up, 1 = down, 2 = no change
 * m_airTemperature       | int8      | 1            | 2020           | Air temp. in degrees celsius
 * m_airTemperatureChange | int8      | 1            | 2021           | Air temp. change – 0 = up, 1 = down, 2 = no change
 * m_rainPercentage       | uint8     | 1            | 2021           | Rain percentage (0-100)
 */

/**
 *  Represents an element in an array in the SessionData packet.
 * - F1 2020 Length: 20
 * - F1 2021 - 2023 Length: 56
 * - F1 2024/2025 Length: 64
 */
public record WeatherForecastSampleData(int sessionType, int timeOffset, int weather, int trackTemperature, int trackTemperatureChange,
                                        int airTemperature, int airTemperatureChange, int rainPercentage) {

    public record WeatherForecastSampleData20(int sessionType, int timeOffset, int weather, int trackTemperature, int airTemperature) {
        public WeatherForecastSampleData20(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.get(),
                    byteBuffer.get()
            );
        }
    }

    public record WeatherForecastSampleData21(int sessionType, int timeOffset, int weather, int trackTemperature, int trackTemperatureChange,
                                              int airTemperature, int airTemperatureChange, int rainPercentage) {
        public WeatherForecastSampleData21(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.get(),
                    byteBuffer.get(),
                    byteBuffer.get(),
                    byteBuffer.get(),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }
}

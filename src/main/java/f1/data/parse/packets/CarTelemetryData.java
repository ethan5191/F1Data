package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 CarTelemetryData Breakdown (Little Endian)
 * - F1 2019 Length: 66 bytes
 * - F1 2020 Length: 58 bytes (m_tyresSurfaceTemperature and m_tyresInnerTemperature changed from u_int16 to u_int8)
 * - F1 2021-2025 Length: 60 bytes
 * This struct is 60 bytes long and contains a snapshot of a single car's
 * telemetry data, including speed, temperatures, pressures, and controls.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * /*
 * -------------------------------
 * Member Name                     | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------------|----------------|--------------|----------------|-------------------------
 * m_header                        | PacketHeader    | ...          | 2019           | Full packet header
 * m_carTelemetryData[22]          | CarTelemetryData| ...          | 2019           | Array for each car
 * - m_speed                        | uint16          | 2            | 2019           | Speed of car in kph
 * - m_throttle                     | float           | 4            | 2019           | Amount of throttle applied (0.0-1.0)
 * - m_steer                        | float           | 4            | 2019           | Steering (-1.0 full left, 1.0 full right)
 * - m_brake                        | float           | 4            | 2019           | Amount of brake applied (0.0-1.0)
 * - m_clutch                       | uint8           | 1            | 2019           | Clutch applied (0-100)
 * - m_gear                         | int8            | 1            | 2019           | Gear selected (1-8, N=0, R=-1)
 * - m_engineRPM                    | uint16          | 2            | 2019           | Engine RPM
 * - m_drs                          | uint8           | 1            | 2019           | 0 = off, 1 = on
 * - m_revLightsPercent             | uint8           | 1            | 2019           | Rev lights indicator (percentage)
 * - m_revLightsBitValue            | uint16          | 2            | 2021           | Bitmask for rev lights (0=leftmost LED, 14=rightmost)
 * - m_brakesTemperature[4]         | uint16[4]       | 8            | 2019           | Brake temperatures in Celsius
 * - m_tyresSurfaceTemperature[4]   | uint8[4]        | 4            | 2019           | Tyre surface temperatures
 * - m_tyresInnerTemperature[4]     | uint8[4]        | 4            | 2019           | Tyre inner temperatures
 * - m_engineTemperature            | uint16          | 2            | 2019           | Engine temperature in Celsius
 * - m_tyresPressure[4]             | float[4]        | 16           | 2019           | Tyre pressures in PSI
 * - m_surfaceType[4]               | uint8[4]        | 4            | 2019           | Driving surface type
 * m_mfdPanelIndex                  | uint8           | 1            | 2020           | Index of MFD panel open (255 = closed)
 * m_mfdPanelIndexSecondaryPlayer   | uint8           | 1            | 2020           | Same as above for secondary player
 * m_suggestedGear                  | int8            | 1            | 2020           | Suggested gear for the player (0 = none)
 * <p>
 * Note:
 * - uint16 and uint8 types require bitmasking to be read as positive integers in Java.
 * - float and int8 map directly.
 * - Arrays must be read by looping or using get() with a destination array.
 */
public record CarTelemetryData(int speed, float throttle, float steer, float brake, int clutch, int gear, int engineRPM,
                               int drs, int revLightPercent, int[] brakeTemps, int[] tireSurfaceTemps,
                               int[] tireInnerTemps, int engineTemp, float[] tirePressure, int[] surfaceType,
                               int revLightBitVal) {

    record CarTelemetryData19(int speed, float throttle, float steer, float brake, int clutch, int gear, int engineRPM,
                              int drs, int revLightPercent, int[] brakeTemps, int[] tireSurfaceTemps,
                              int[] tireInnerTemps, int engineTemp, float[] tirePressure, int[] surfaceType) {
        public CarTelemetryData19(int packetFormat, ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.get(),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    ParseUtils.parseShortArray(byteBuffer, 4),
                    (packetFormat <= Constants.YEAR_2019) ? ParseUtils.parseShortArray(byteBuffer, 4) : ParseUtils.parseIntArray(byteBuffer, 4),
                    (packetFormat <= Constants.YEAR_2019) ? ParseUtils.parseShortArray(byteBuffer, 4) : ParseUtils.parseIntArray(byteBuffer, 4),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseIntArray(byteBuffer, 4)
            );
        }
    }

    record CarTelemetryData21(int speed, float throttle, float steer, float brake, int clutch, int gear, int engineRPM,
                              int drs, int revLightPercent, int revLightBitVal, int[] brakeTemps,
                              int[] tireSurfaceTemps, int[] tireInnerTemps, int engineTemp, float[] tirePressure,
                              int[] surfaceType) {
        public CarTelemetryData21(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.get(),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    ParseUtils.parseShortArray(byteBuffer, 4),
                    ParseUtils.parseIntArray(byteBuffer, 4),
                    ParseUtils.parseIntArray(byteBuffer, 4),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    ParseUtils.parseFloatArray(byteBuffer, 4),
                    ParseUtils.parseIntArray(byteBuffer, 4)
            );
        }
    }
}

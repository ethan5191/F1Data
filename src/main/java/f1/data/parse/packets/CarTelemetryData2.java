package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 24 CarTelemetryData Breakdown (Little Endian)
 * - F1 2026 Length: 10 bytes
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * /*
 * -------------------------------
 * Member Name                     | Data Type             | Size (bytes) | First Appeared | Notes
 * --------------------------------|-----------------------|--------------|----------------|-------------------------
 * m_header                        | PacketHeader          | ...          | 2026           | Full packet header
 * m_carTelemetry2Data[22]          | CarTelemetryData2    | ...          | 2026           | Array for each car
 * - m_activeAeroMode               | uint8                | 1            | 2026           | Speed of car in kph
 * - m_activeAeroAvailable          | uint8                | 1            | 2026           | Amount of throttle applied (0.0-1.0)
 * - m_activeAeroActivationDistance | uint16               | 2            | 2026           | Steering (-1.0 full left, 1.0 full right)
 * - m_overtakeAvailable            | uint8                | 1            | 2026           | Amount of brake applied (0.0-1.0)
 * - m_overtakeActive               | uint8                | 1            | 2026           | Clutch applied (0-100)
 * - m_overtakeActivationDistance   | uint16               | 2            | 2026           | Gear selected (1-8, N=0, R=-1)
 * - m_2026Regulations              | uint8                | 1            | 2026           | Engine RPM
 * - m_drivingWrongWay              | uint8                | 1            | 2026           | 0 = off, 1 = on
 * * <p>
 * Note:
 * - uint16 and uint8 types require bitmasking to be read as positive integers in Java.
 * - float and int8 map directly.
 * - Arrays must be read by looping or using get() with a destination array.
 */
public record CarTelemetryData2(int activeAeroMode, int activeAeroAvailable, int activeAeroActivationDistance, int overtakeAvailable,
                                int overtakeActive, int overtakeActivationDistance, int twentySixRegulations, int drivingWrongWay) {

    record CarTelemetryData26(int activeAeroMode, int activeAeroAvailable, int activeAeroActivationDistance, int overtakeAvailable,
                              int overtakeActive, int overtakeActivationDistance, int twentySixRegulations, int drivingWrongWay) {
        public CarTelemetryData26(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }
}

package f1.data.parse.packets.session;

import java.nio.ByteBuffer;

/**
 * - F1 2020 - 2025 MarshalZone Length: 5 bytes
 * This struct is 5 bytes long and describes a single marshal zone on the track,
 * used to indicate the status of a specific area.
 * <p>
 * Member Name      | Data Type | Size (bytes) | First Appeared | Notes
 * -----------------|-----------|--------------|----------------|--------------------------------
 * m_zoneStart      | float     | 4            |                | Fraction (0..1) of way through the lap the marshal zone starts
 * m_zoneFlag       | int8      | 1            |                | -1 = invalid/unknown, 0 = none, 1 = green, 2 = blue, 3 = yellow, 4 = red
 */
public record MarshalZoneData(float zoneStart, int zoneFlag) {
    public MarshalZoneData(ByteBuffer byteBuffer) {
        this(
                byteBuffer.getFloat(),
                byteBuffer.get()
        );
    }
}

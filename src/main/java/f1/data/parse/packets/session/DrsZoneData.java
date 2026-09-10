package f1.data.parse.packets.session;

import f1.data.parse.packets.PacketUtils;

import java.nio.ByteBuffer;

public record DrsZoneData(float zoneStart, float zoneEnd) {

/**
 *  - F1 2026 DrsZoneData Length: 8 bytes
 * <p>
 * Member Name   | Data Type | Size (bytes) | First Appeared | Notes
 * --------------|-----------|--------------|----------------|-----------------------------
 * - m_zoneStart | float     | 4            | 2026           | Fraction (0..1) of way through the lap the Active Aero zone starts
 * - m_zoneEnd   | float     | 4            | 2026           |  Fraction (0..1) of way through the lap the Active Aero zone ends
 */

    public DrsZoneData(ByteBuffer byteBuffer) {
        this(
                PacketUtils.determineFloatValue(byteBuffer.getFloat()),
                PacketUtils.determineFloatValue(byteBuffer.getFloat())
        );
    }
}

package packets.events;

import utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 ButtonStatus Breakdown (Little Endian)
 * <p>
 * This struct is 4 bytes long and contains bit flags for button presses.
 * It is a member of the PacketCarTelemetryData packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * Member Name                       | Data Type | Size (bytes) | Starting Offset
 * ----------------------------------|-----------|--------------|-----------------
 * buttonStatus                      | uint32    | 4            | 0
 * <p>
 * Note: The uint32 type maps to a Java 'int'. This field is a bitmask where
 * each bit represents a specific button being pressed.
 */

public class ButtonsData {

    public ButtonsData(ByteBuffer byteBuffer) {
        this.buttonsStatus = byteBuffer.getInt() & Constants.BIT_MASK_32;
    }

    private final long buttonsStatus;

    public long getButtonsStatus() {
        return buttonsStatus;
    }
}

package packets.events;

import packets.Data;

import java.nio.ByteBuffer;

/**
 * F1 24 ButtonStatus Breakdown (Little Endian)
 *
 * This struct is 4 bytes long and contains bit flags for button presses.
 * It is a member of the PacketCarTelemetryData packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name                       | Data Type | Size (bytes) | Starting Offset
 * ----------------------------------|-----------|--------------|-----------------
 * buttonStatus                      | uint32    | 4            | 0
 *
 * Note: The uint32 type maps to a Java 'int'. This field is a bitmask where
 * each bit represents a specific button being pressed.
 */

public class ButtonsData extends Data {

    public ButtonsData(ByteBuffer byteBuffer) {
//        printMessage("Buttons Event ", byteBuffer.array().length);
        this.buttonsStatus = byteBuffer.getInt();
    }

    private final int buttonsStatus;

    public int getButtonsStatus() {
        return buttonsStatus;
    }
}

package f1.data.parse.packets.participant;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/** F1 25 ParticipantData Breakdown (Little Endian)
 * <p>
 * RGB value of a colour.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * * **Note:** The header length and some fields vary by game year.
 * - F1 2025 - 2026 Length: 3 bytes
 * /*
 * LiveryColourData
 * ----------------------
 * Member Name| Data Type          | Size (bytes) | First Appeared | Notes
 * -----------|--------------------|--------------|----------------|-------------------------
 * - red      | uint8              | 1            | 2025           |
 * - green    | uint8              | 1            | 2025           |
 * - blue     | uint8              | 1            | 2025           |

 */

public record LiveryColourData(int red, int green, int blue) {

    public LiveryColourData(ByteBuffer byteBuffer) {
        this(BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get())
        );
    }
}

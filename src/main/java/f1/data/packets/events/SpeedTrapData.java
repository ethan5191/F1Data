package f1.data.packets.events;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 24 SpeedTrap Breakdown (Little Endian)
 * - F1 2020 Length: 5 bytes
 * - F1 2022 Length: 7 bytes
 * - F1 2022-2025 Length: 12 bytes
 * This struct is 12 bytes long and contains data about a speed trap event,
 * including the fastest speeds recorded in the session.
 * It is a member of the PacketEventData packet.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * -------------------------------
 * Member Name                     | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------------|----------------|--------------|----------------|-------------------------
 * m_header                        | PacketHeader    | ...          | 2020           | Full packet header
 * m_eventStringCode[4]            | uint8[4]        | 4            | 2020           | Event string code, e.g., 'SPTP' for speed trap
 * m_eventDetails                  | EventDataDetails| 12           | 2020           | Union of event details, size is max of all members
 * - vehicleIdx                    | uint8           | 1            | 2020           | Vehicle index of the vehicle
 * - speed                         | float           | 4            | 2020           | Top speed achieved in kph
 * - isOverallFastestInSession     | uint8           | 1            | 2021           | Overall fastest speed in session = 1, otherwise 0
 * - isDriverFastestInSession      | uint8           | 1            | 2021           | Fastest speed for driver in session = 1, otherwise 0
 * - fastestVehicleIdxInSession    | uint8           | 1            | 2022           | Vehicle index of the fastest in this session
 * - fastestSpeedInSession         | float           | 4            | 2022           | Speed of the fastest vehicle in this session
 */
public record SpeedTrapData(int vehicleId, float speed, int isOverallFastest, int isDriverFastest, int fastestVehicleId,
                            float fastestSpeed) {

    record SpeedTrapData20(int vehicleId, float speed) {
        public SpeedTrapData20(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat()
            );
        }
    }

    record SpeedTrapData21(int vehicleId, float speed, int isOverallFastest, int isDriverFastest) {
        public SpeedTrapData21(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record SpeedTrapData22(int vehicleId, float speed, int isOverallFastest, int isDriverFastest, int fastestVehicleId,
                           float fastestSpeed) {
        public SpeedTrapData22(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat()
            );
        }
    }
}

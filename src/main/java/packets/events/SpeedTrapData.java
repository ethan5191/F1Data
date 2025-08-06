package packets.events;

import packets.Data;
import utils.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 SpeedTrap Breakdown (Little Endian)
 *
 * This struct is 12 bytes long and contains data about a speed trap event,
 * including the fastest speeds recorded in the session.
 * It is a member of the PacketEventData packet.
 *
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name                       | Data Type | Size (bytes) | Starting Offset
 * ----------------------------------|-----------|--------------|-----------------
 * vehicleIdx                        | uint8     | 1            | 0
 * speed                             | float     | 4            | 1
 * isOverallFastestInSession         | uint8     | 1            | 5
 * isDriverFastestInSession          | uint8     | 1            | 6
 * fastestVehicleIdxInSession        | uint8     | 1            | 7
 * fastestSpeedInSession             | float     | 4            | 8
 *
 */
public class SpeedTrapData extends Data {

    public SpeedTrapData(ByteBuffer byteBuffer) {
        //        printMessage("Speed Trap Event ", byteBuffer.array().length);
        this.vehicleId = byteBuffer.get() & Constants.BIT_MASK_8;
        this.speed = byteBuffer.getFloat();
        this.isOverallFastest = byteBuffer.get() & Constants.BIT_MASK_8;
        this.isDriverFastest = byteBuffer.get() & Constants.BIT_MASK_8;
        this.fastestVehicleId = byteBuffer.get() & Constants.BIT_MASK_8;
        this.fastestSpeed = byteBuffer.getFloat();
    }

    private final int vehicleId;
    private final float speed;
    private final int isOverallFastest;
    private final int isDriverFastest;
    private final int fastestVehicleId;
    private final float fastestSpeed;

    public int getVehicleId() {
        return vehicleId;
    }

    public float getSpeed() {
        return speed;
    }

    public int getIsOverallFastest() {
        return isOverallFastest;
    }

    public int getIsDriverFastest() {
        return isDriverFastest;
    }

    public int getFastestVehicleId() {
        return fastestVehicleId;
    }

    public float getFastestSpeed() {
        return fastestSpeed;
    }
}

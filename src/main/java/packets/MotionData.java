package packets;

import java.nio.ByteBuffer;

public class MotionData extends Data {

    private static final float DIVISOR = 32767.0f;
    private static final int PACK_SIZE = 60;

    public MotionData(ByteBuffer byteBuffer, int playerCarIndex) {
        printMessage("Motion packets.Data ", byteBuffer.array().length);
        //Used to ensure we get the players motion data as all cars data is in the byteBuffer.
        byteBuffer.position(playerCarIndex * PACK_SIZE);
        this.worldPositionX = determineFloatValue(byteBuffer);
        this.worldPositionY = determineFloatValue(byteBuffer);
        this.worldPositionZ = determineFloatValue(byteBuffer);
        this.worldVelocityX = determineFloatValue(byteBuffer);
        this.worldVelocityY = determineFloatValue(byteBuffer);
        this.worldVelocityZ = determineFloatValue(byteBuffer);
        this.worldForwardDirX = byteBuffer.getShort();
        this.worldForwardDirY = byteBuffer.getShort();
        this.worldForwardDirZ = byteBuffer.getShort();
    }

    /**
     * F1 24 CarMotionData Breakdown (Little Endian)
     * <p>
     * This struct is 60 bytes long and represents the motion data for a single car.
     * It is repeated 22 times within the PacketMotionData struct.
     * The values must be read from a ByteBuffer configured for Little Endian byte order.
     * <p>
     * Member Name                    | packets.Data Type | Size (bytes) | Starting Offset
     * -------------------------------|-----------|--------------|-----------------
     * m_worldPositionX               | float     | 4            | 0
     * m_worldPositionY               | float     | 4            | 4
     * m_worldPositionZ               | float     | 4            | 8
     * m_worldVelocityX               | float     | 4            | 12
     * m_worldVelocityY               | float     | 4            | 16
     * m_worldVelocityZ               | float     | 4            | 20
     * m_worldForwardDirX             | int16     | 2            | 24
     * m_worldForwardDirY             | int16     | 2            | 26
     * m_worldForwardDirZ             | int16     | 2            | 28
     * m_worldRightDirX               | int16     | 2            | 30
     * m_worldRightDirY               | int16     | 2            | 32
     * m_worldRightDirZ               | int16     | 2            | 34
     * m_gForceLateral                | float     | 4            | 36
     * m_gForceLongitudinal           | float     | 4            | 40
     * m_gForceVertical               | float     | 4            | 44
     * m_yaw                          | float     | 4            | 48
     * m_pitch                        | float     | 4            | 52
     * m_roll                         | float     | 4            | 56
     */

    private final float worldPositionX;
    private final float worldPositionY;
    private final float worldPositionZ;
    private final float worldVelocityX;
    private final float worldVelocityY;
    private final float worldVelocityZ;
    private final short worldForwardDirX;
    private final short worldForwardDirY;
    private final short worldForwardDirZ;

    public float getWorldPositionX() {
        return worldPositionX;
    }

    public float getWorldPositionY() {
        return worldPositionY;
    }

    public float getWorldPositionZ() {
        return worldPositionZ;
    }

    public float getWorldVelocityX() {
        return worldVelocityX;
    }

    public float getWorldVelocityY() {
        return worldVelocityY;
    }

    public float getWorldVelocityZ() {
        return worldVelocityZ;
    }

    public short getWorldForwardDirX() {
        return worldForwardDirX;
    }

    public short getWorldForwardDirY() {
        return worldForwardDirY;
    }

    public short getWorldForwardDirZ() {
        return worldForwardDirZ;
    }

    private float determineFloatValue(ByteBuffer byteBuffer) {
        float val = byteBuffer.getFloat();
        return val / DIVISOR;
    }
}

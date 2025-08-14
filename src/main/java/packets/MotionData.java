package packets;

/**
 * F1 24 CarMotionData Breakdown (Little Endian)
 * - F1 2020-2022 Length: 60 bytes (had 9 other elements AFTER the array)
 * - F1 2023-2025 Length: 60 bytes
 * This struct is 60 bytes long and represents the motion data for a single car.
 * It is repeated 22 times within the PacketMotionData struct.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * -------------------------------
 * Member Name                     | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------------|----------------|--------------|----------------|-------------------------
 * m_header                        | PacketHeader    | ...          | 2020           | Full packet header
 * m_carMotionData[22]             | CarMotionData   | ...          | 2020           | Array for each car
 * - m_worldPositionX              | float           | 4            | 2020           | World space X position - metres
 * - m_worldPositionY              | float           | 4            | 2020           | World space Y position
 * - m_worldPositionZ              | float           | 4            | 2020           | World space Z position
 * - m_worldVelocityX              | float           | 4            | 2020           | Velocity in world space X – metres/s
 * - m_worldVelocityY              | float           | 4            | 2020           | Velocity in world space Y
 * - m_worldVelocityZ              | float           | 4            | 2020           | Velocity in world space Z
 * - m_worldForwardDirX            | int16           | 2            | 2020           | World space forward X direction (normalised)
 * - m_worldForwardDirY            | int16           | 2            | 2020           | World space forward Y direction (normalised)
 * - m_worldForwardDirZ            | int16           | 2            | 2020           | World space forward Z direction (normalised)
 * - m_worldRightDirX              | int16           | 2            | 2020           | World space right X direction (normalised)
 * - m_worldRightDirY              | int16           | 2            | 2020           | World space right Y direction (normalised)
 * - m_worldRightDirZ              | int16           | 2            | 2020           | World space right Z direction (normalised)
 * - m_gForceLateral               | float           | 4            | 2020           | Lateral G-Force component
 * - m_gForceLongitudinal          | float           | 4            | 2020           | Longitudinal G-Force component
 * - m_gForceVertical              | float           | 4            | 2020           | Vertical G-Force component
 * - m_yaw                         | float           | 4            | 2020           | Yaw angle in radians
 * - m_pitch                       | float           | 4            | 2020           | Pitch angle in radians
 * - m_roll                        | float           | 4            | 2020           | Roll angle in radians
 */

public class MotionData {

    public MotionData(Builder builder) {
        this.worldPositionX = builder.worldPositionX;
        this.worldPositionY = builder.worldPositionY;
        this.worldPositionZ = builder.worldPositionZ;
        this.worldVelocityX = builder.worldVelocityX;
        this.worldVelocityY = builder.worldVelocityY;
        this.worldVelocityZ = builder.worldVelocityZ;
        this.worldForwardDirX = builder.worldForwardDirX;
        this.worldForwardDirY = builder.worldForwardDirY;
        this.worldForwardDirZ = builder.worldForwardDirZ;
        this.worldRightDirX = builder.worldRightDirX;
        this.worldRightDirY = builder.worldRightDirY;
        this.worldRightDirZ = builder.worldRightDirZ;
        this.gForceLat = builder.gForceLat;
        this.gForceLon = builder.gForceLon;
        this.gForceVer = builder.gForceVer;
        this.yaw = builder.yaw;
        this.pitch = builder.pitch;
        this.roll = builder.roll;
    }

    private final float worldPositionX;
    private final float worldPositionY;
    private final float worldPositionZ;
    private final float worldVelocityX;
    private final float worldVelocityY;
    private final float worldVelocityZ;
    private final int worldForwardDirX;
    private final int worldForwardDirY;
    private final int worldForwardDirZ;
    private final int worldRightDirX;
    private final int worldRightDirY;
    private final int worldRightDirZ;
    private final float gForceLat;
    private final float gForceLon;
    private final float gForceVer;
    private final float yaw;
    private final float pitch;
    private final float roll;

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

    public int getWorldForwardDirX() {
        return worldForwardDirX;
    }

    public int getWorldForwardDirY() {
        return worldForwardDirY;
    }

    public int getWorldForwardDirZ() {
        return worldForwardDirZ;
    }

    public int getWorldRightDirX() {
        return worldRightDirX;
    }

    public int getWorldRightDirY() {
        return worldRightDirY;
    }

    public int getWorldRightDirZ() {
        return worldRightDirZ;
    }

    public float getgForceLat() {
        return gForceLat;
    }

    public float getgForceLon() {
        return gForceLon;
    }

    public float getgForceVer() {
        return gForceVer;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }

    public static class Builder {

        private float worldPositionX;
        private float worldPositionY;
        private float worldPositionZ;
        private float worldVelocityX;
        private float worldVelocityY;
        private float worldVelocityZ;
        private int worldForwardDirX;
        private int worldForwardDirY;
        private int worldForwardDirZ;
        private int worldRightDirX;
        private int worldRightDirY;
        private int worldRightDirZ;
        private float gForceLat;
        private float gForceLon;
        private float gForceVer;
        private float yaw;
        private float pitch;
        private float roll;

        public Builder setWorldPositionX(float worldPositionX) {
            this.worldPositionX = worldPositionX;
            return this;
        }

        public Builder setWorldPositionY(float worldPositionY) {
            this.worldPositionY = worldPositionY;
            return this;
        }

        public Builder setWorldPositionZ(float worldPositionZ) {
            this.worldPositionZ = worldPositionZ;
            return this;
        }

        public Builder setWorldVelocityX(float worldVelocityX) {
            this.worldVelocityX = worldVelocityX;
            return this;
        }

        public Builder setWorldVelocityY(float worldVelocityY) {
            this.worldVelocityY = worldVelocityY;
            return this;
        }

        public Builder setWorldVelocityZ(float worldVelocityZ) {
            this.worldVelocityZ = worldVelocityZ;
            return this;
        }

        public Builder setWorldForwardDirX(int worldForwardDirX) {
            this.worldForwardDirX = worldForwardDirX;
            return this;
        }

        public Builder setWorldForwardDirY(int worldForwardDirY) {
            this.worldForwardDirY = worldForwardDirY;
            return this;
        }

        public Builder setWorldForwardDirZ(int worldForwardDirZ) {
            this.worldForwardDirZ = worldForwardDirZ;
            return this;
        }

        public Builder setWorldRightDirX(int worldRightDirX) {
            this.worldRightDirX = worldRightDirX;
            return this;
        }

        public Builder setWorldRightDirY(int worldRightDirY) {
            this.worldRightDirY = worldRightDirY;
            return this;
        }

        public Builder setWorldRightDirZ(int worldRightDirZ) {
            this.worldRightDirZ = worldRightDirZ;
            return this;
        }

        public Builder setgForceLat(float gForceLat) {
            this.gForceLat = gForceLat;
            return this;
        }

        public Builder setgForceLon(float gForceLon) {
            this.gForceLon = gForceLon;
            return this;
        }

        public Builder setgForceVer(float gForceVer) {
            this.gForceVer = gForceVer;
            return this;
        }

        public Builder setYaw(float yaw) {
            this.yaw = yaw;
            return this;
        }

        public Builder setPitch(float pitch) {
            this.pitch = pitch;
            return this;
        }

        public Builder setRoll(float roll) {
            this.roll = roll;
            return this;
        }

        public MotionData build() {
            return new MotionData(this);
        }
    }
}

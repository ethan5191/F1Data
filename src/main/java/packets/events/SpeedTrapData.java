package packets.events;

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
public class SpeedTrapData {

    public SpeedTrapData(Builder builder) {
        this.vehicleId = builder.vehicleId;
        this.speed = builder.speed;
        this.isOverallFastest = builder.isOverallFastest;
        this.isDriverFastest = builder.isDriverFastest;
        this.fastestVehicleId = builder.fastestVehicleId;
        this.fastestSpeed = builder.fastestSpeed;
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

    public static class Builder {

        private int vehicleId;
        private float speed;
        private int isOverallFastest;
        private int isDriverFastest;
        private int fastestVehicleId;
        private float fastestSpeed;

        public Builder setVehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Builder setSpeed(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder setIsOverallFastest(int isOverallFastest) {
            this.isOverallFastest = isOverallFastest;
            return this;
        }

        public Builder setIsDriverFastest(int isDriverFastest) {
            this.isDriverFastest = isDriverFastest;
            return this;
        }

        public Builder setFastestVehicleId(int fastestVehicleId) {
            this.fastestVehicleId = fastestVehicleId;
            return this;
        }

        public Builder setFastestSpeed(float fastestSpeed) {
            this.fastestSpeed = fastestSpeed;
            return this;
        }

        public SpeedTrapData build() {
            return new SpeedTrapData(this);
        }
    }
}

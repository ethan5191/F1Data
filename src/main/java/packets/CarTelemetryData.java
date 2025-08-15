package packets;

/**
 * F1 24 CarTelemetryData Breakdown (Little Endian)
 * - F1 2020 Length: 58 bytes
 * - F1 2021-2025 Length: 60 bytes
 * This struct is 60 bytes long and contains a snapshot of a single car's
 * telemetry data, including speed, temperatures, pressures, and controls.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * /*
 * -------------------------------
 * Member Name                     | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------------|----------------|--------------|----------------|-------------------------
 * m_header                        | PacketHeader    | ...          | 2020           | Full packet header
 * m_carTelemetryData[22]          | CarTelemetryData| ...          | 2020           | Array for each car
 * - m_speed                        | uint16          | 2            | 2020           | Speed of car in kph
 * - m_throttle                     | float           | 4            | 2020           | Amount of throttle applied (0.0-1.0)
 * - m_steer                        | float           | 4            | 2020           | Steering (-1.0 full left, 1.0 full right)
 * - m_brake                        | float           | 4            | 2020           | Amount of brake applied (0.0-1.0)
 * - m_clutch                       | uint8           | 1            | 2020           | Clutch applied (0-100)
 * - m_gear                         | int8            | 1            | 2020           | Gear selected (1-8, N=0, R=-1)
 * - m_engineRPM                    | uint16          | 2            | 2020           | Engine RPM
 * - m_drs                          | uint8           | 1            | 2020           | 0 = off, 1 = on
 * - m_revLightsPercent             | uint8           | 1            | 2020           | Rev lights indicator (percentage)
 * - m_revLightsBitValue            | uint16          | 2            | 2021           | Bitmask for rev lights (0=leftmost LED, 14=rightmost)
 * - m_brakesTemperature[4]         | uint16[4]       | 8            | 2020           | Brake temperatures in Celsius
 * - m_tyresSurfaceTemperature[4]   | uint8[4]        | 4            | 2020           | Tyre surface temperatures
 * - m_tyresInnerTemperature[4]     | uint8[4]        | 4            | 2020           | Tyre inner temperatures
 * - m_engineTemperature            | uint16          | 2            | 2020           | Engine temperature in Celsius
 * - m_tyresPressure[4]             | float[4]        | 16           | 2020           | Tyre pressures in PSI
 * - m_surfaceType[4]               | uint8[4]        | 4            | 2020           | Driving surface type
 * m_mfdPanelIndex                  | uint8           | 1            | 2020           | Index of MFD panel open (255 = closed)
 * m_mfdPanelIndexSecondaryPlayer   | uint8           | 1            | 2020           | Same as above for secondary player
 * m_suggestedGear                  | int8            | 1            | 2020           | Suggested gear for the player (0 = none)
 * <p>
 * Note:
 * - uint16 and uint8 types require bitmasking to be read as positive integers in Java.
 * - float and int8 map directly.
 * - Arrays must be read by looping or using get() with a destination array.
 */
public class CarTelemetryData {

    public CarTelemetryData(Builder builder) {
        this.speed = builder.speed;
        this.throttle = builder.throttle;
        this.steer = builder.steer;
        this.brake = builder.brake;
        this.clutch = builder.clutch;
        this.gear = builder.gear;
        this.engineRPM = builder.engineRPM;
        this.drs = builder.drs;
        this.revLightPercent = builder.revLightPercent;
        this.revLightBitVal = builder.revLightBitVal;
        this.brakeTemps = builder.brakeTemps;
        this.tireSurfaceTemps = builder.tireSurfaceTemps;
        this.tireInnerTemps = builder.tireInnerTemps;
        this.engineTemp = builder.engineTemp;
        this.tirePressure = builder.tirePressure;
        this.surfaceType = builder.surfaceType;
    }

    private final int speed;
    private final float throttle;
    private final float steer;
    private final float brake;
    private final int clutch;
    private final int gear;
    private final int engineRPM;
    private final int drs;
    private final int revLightPercent;
    private final int revLightBitVal;
    private final int[] brakeTemps;
    private final int[] tireSurfaceTemps;
    private final int[] tireInnerTemps;
    private final int engineTemp;
    private final float[] tirePressure;
    private final int[] surfaceType;

    public int getSpeed() {
        return speed;
    }

    public float getThrottle() {
        return throttle;
    }

    public float getSteer() {
        return steer;
    }

    public float getBrake() {
        return brake;
    }

    public int getClutch() {
        return clutch;
    }

    public int getGear() {
        return gear;
    }

    public int getEngineRPM() {
        return engineRPM;
    }

    public int getDrs() {
        return drs;
    }

    public int getRevLightPercent() {
        return revLightPercent;
    }

    public int getRevLightBitVal() {
        return revLightBitVal;
    }

    public int[] getBrakeTemps() {
        return brakeTemps;
    }

    public int[] getTireSurfaceTemps() {
        return tireSurfaceTemps;
    }

    public int[] getTireInnerTemps() {
        return tireInnerTemps;
    }

    public int getEngineTemp() {
        return engineTemp;
    }

    public float[] getTirePressure() {
        return tirePressure;
    }

    public int[] getSurfaceType() {
        return surfaceType;
    }

    public static class Builder {

        private int speed;
        private float throttle;
        private float steer;
        private float brake;
        private int clutch;
        private int gear;
        private int engineRPM;
        private int drs;
        private int revLightPercent;
        private int revLightBitVal;
        private int[] brakeTemps = new int[4];
        private int[] tireSurfaceTemps = new int[4];
        private int[] tireInnerTemps = new int[4];
        private int engineTemp;
        private float[] tirePressure = new float[4];
        private int[] surfaceType = new int[4];

        public Builder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder setThrottle(float throttle) {
            this.throttle = throttle;
            return this;
        }

        public Builder setSteer(float steer) {
            this.steer = steer;
            return this;
        }

        public Builder setBrake(float brake) {
            this.brake = brake;
            return this;
        }

        public Builder setClutch(int clutch) {
            this.clutch = clutch;
            return this;
        }

        public Builder setGear(int gear) {
            this.gear = gear;
            return this;
        }

        public Builder setEngineRPM(int engineRPM) {
            this.engineRPM = engineRPM;
            return this;
        }

        public Builder setDrs(int drs) {
            this.drs = drs;
            return this;
        }

        public Builder setRevLightPercent(int revLightPercent) {
            this.revLightPercent = revLightPercent;
            return this;
        }

        public Builder setRevLightBitVal(int revLightBitVal) {
            this.revLightBitVal = revLightBitVal;
            return this;
        }

        public Builder setBrakeTemps(int[] brakeTemps) {
            this.brakeTemps = brakeTemps;
            return this;
        }

        public Builder setTireSurfaceTemps(int[] tireSurfaceTemps) {
            this.tireSurfaceTemps = tireSurfaceTemps;
            return this;
        }

        public Builder setTireInnerTemps(int[] tireInnerTemps) {
            this.tireInnerTemps = tireInnerTemps;
            return this;
        }

        public Builder setEngineTemp(int engineTemp) {
            this.engineTemp = engineTemp;
            return this;
        }

        public Builder setTirePressure(float[] tirePressure) {
            this.tirePressure = tirePressure;
            return this;
        }

        public Builder setSurfaceType(int[] surfaceType) {
            this.surfaceType = surfaceType;
            return this;
        }

        public CarTelemetryData build() {
            return new CarTelemetryData(this);
        }
    }
}

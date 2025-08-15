package packets;

/**
 * F1 24 CarStatusData Breakdown (Little Endian)
 * <p>
 * - F1 2020 Length: 56 bytes CarDamage info was part of CarStatusPacket
 * - F1 2021 length: 47 bytes
 * - F1 2024 length: 55 bytes
 * This struct is 55 bytes long and contains details of the car's components,
 * including fuel, tyres, ERS, and vehicle settings. This data is sent for all cars in the session.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * /*
 * --------------------------
 * Member Name                  | Data Type       | Size (bytes) | First Appeared | Notes
 * ----------------------------|----------------|--------------|----------------|-------------------------
 * m_header                     | PacketHeader    | ...          | 2020           | Full packet header
 * m_carStatusData[22]          | CarStatusData   | ...          | 2024           | Array for each car
 * - m_tractionControl           | uint8           | 1            | 2020           |
 * - m_antiLockBrakes            | uint8           | 1            | 2020           |
 * - m_fuelMix                   | uint8           | 1            | 2020           |
 * - m_frontBrakeBias            | uint8           | 1            | 2020           |
 * - m_pitLimiterStatus          | uint8           | 1            | 2020           |
 * - m_fuelInTank                | float           | 4            | 2020           |
 * - m_fuelCapacity              | float           | 4            | 2020           |
 * - m_fuelRemainingLaps         | float           | 4            | 2020           |
 * - m_maxRPM                    | uint16          | 2            | 2020           |
 * - m_idleRPM                   | uint16          | 2            | 2020           |
 * - m_maxGears                  | uint8           | 1            | 2020           |
 * - m_drsAllowed                | uint8           | 1            | 2020           |
 * - m_drsActivationDistance     | uint16          | 2            | 2020           |
 * - m_actualTyreCompound        | uint8           | 1            | 2020           |
 * - m_visualTyreCompound        | uint8           | 1            | 2020           |
 * - m_tyresAgeLaps              | uint8           | 1            | 2020           |
 * - m_vehicleFiaFlags           | int8            | 1            | 2020           |
 * - m_enginePowerICE            | float           | 4            | 2023           |
 * - m_enginePowerMGUK           | float           | 4            | 2023           |
 * - m_ersStoreEnergy            | float           | 4            | 2020           |
 * - m_ersDeployMode             | uint8           | 1            | 2020           |
 * - m_ersHarvestedThisLapMGUK   | float           | 4            | 2020           |
 * - m_ersHarvestedThisLapMGUH   | float           | 4            | 2020           |
 * - m_ersDeployedThisLap        | float           | 4            | 2020           |
 * - m_networkPaused             | uint8           | 1            | 2021           |
 * <p>
 * Total size per CarStatusData (with padding/alignment) ≈ 55 bytes
 * <p>
 * Note:
 * - uint8 and uint16 types must be read with bitmasking to get positive integer values.
 * - int8 and float map directly to their Java counterparts.
 */

public class CarStatusData {

    public CarStatusData(Builder builder) {
        this.tractionControl = builder.tractionControl;
        this.antiLockBrakes = builder.antiLockBrakes;
        this.fuelMix = builder.fuelMix;
        this.frontBrakeBias = builder.frontBrakeBias;
        this.pitLimitStatus = builder.pitLimitStatus;
        this.fuelInTank = builder.fuelInTank;
        this.fuelCapacity = builder.fuelCapacity;
        this.fuelRemainingLaps = builder.fuelRemainingLaps;
        this.maxRPM = builder.maxRPM;
        this.idleRPM = builder.idleRPM;
        this.maxGears = builder.maxGears;
        this.drsAllowed = builder.drsAllowed;
        this.drsActivationDistance = builder.drsActivationDistance;
        this.actualTireCompound = builder.actualTireCompound;
        this.visualTireCompound = builder.visualTireCompound;
        this.tiresAgeLaps = builder.tiresAgeLaps;
        this.vehicleFiaFlags = builder.vehicleFiaFlags;
        this.enginePowerICE = builder.enginePowerICE;
        this.enginePowerMGUK = builder.enginePowerMGUK;
        this.ersStoreEnergy = builder.ersStoreEnergy;
        this.ersDeployMode = builder.ersDeployMode;
        this.ersHarvestedThisLapMGUK = builder.ersHarvestedThisLapMGUK;
        this.ersHarvestedThisLapMGUH = builder.ersHarvestedThisLapMGUH;
        this.ersDeployedThisLap = builder.ersDeployedThisLap;
        this.networkPaused = builder.networkPaused;

        this.tyresWear = builder.tyresWear;
        this.tyresDamage = builder.tyresDamage;
        this.frontLeftWingDamage = builder.frontLeftWingDamage;
        this.frontRightWingDamage = builder.frontRightWingDamage;
        this.rearWingDamage = builder.rearWingDamage;
        this.drsFault = builder.drsFault;
        this.engineDamage = builder.engineDamage;
        this.gearBoxDamage = builder.gearBoxDamage;
    }

    private final int tractionControl;
    private final int antiLockBrakes;
    private final int fuelMix;
    private final int frontBrakeBias;
    private final int pitLimitStatus;
    private final float fuelInTank;
    private final float fuelCapacity;
    private final float fuelRemainingLaps;
    private final int maxRPM;
    private final int idleRPM;
    private final int maxGears;
    private final int drsAllowed;
    private final int drsActivationDistance;
    private final int actualTireCompound;
    private final int visualTireCompound;
    private final int tiresAgeLaps;
    private final int vehicleFiaFlags;
    private final float enginePowerICE;
    private final float enginePowerMGUK;
    private final float ersStoreEnergy;
    private final int ersDeployMode;
    private final float ersHarvestedThisLapMGUK;
    private final float ersHarvestedThisLapMGUH;
    private final float ersDeployedThisLap;
    private final int networkPaused;

    //Params where part of the CarStatus packet in 2020, moved to their own packet in 2021.
    private final float[] tyresWear;
    private final int[] tyresDamage;
    private final int frontLeftWingDamage;
    private final int frontRightWingDamage;
    private final int rearWingDamage;
    private final int drsFault;
    private final int engineDamage;
    private final int gearBoxDamage;

    public int getTractionControl() {
        return tractionControl;
    }

    public int getAntiLockBrakes() {
        return antiLockBrakes;
    }

    public int getFuelMix() {
        return fuelMix;
    }

    public int getFrontBrakeBias() {
        return frontBrakeBias;
    }

    public int getPitLimitStatus() {
        return pitLimitStatus;
    }

    public float getFuelInTank() {
        return fuelInTank;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public float getFuelRemainingLaps() {
        return fuelRemainingLaps;
    }

    public int getMaxRPM() {
        return maxRPM;
    }

    public int getIdleRPM() {
        return idleRPM;
    }

    public int getMaxGears() {
        return maxGears;
    }

    public int getDrsAllowed() {
        return drsAllowed;
    }

    public int getDrsActivationDistance() {
        return drsActivationDistance;
    }

    public int getActualTireCompound() {
        return actualTireCompound;
    }

    public int getVisualTireCompound() {
        return visualTireCompound;
    }

    public int getTiresAgeLaps() {
        return tiresAgeLaps;
    }

    public int getVehicleFiaFlags() {
        return vehicleFiaFlags;
    }

    public float getEnginePowerICE() {
        return enginePowerICE;
    }

    public float getEnginePowerMGUK() {
        return enginePowerMGUK;
    }

    public float getErsStoreEnergy() {
        return ersStoreEnergy;
    }

    public int getErsDeployMode() {
        return ersDeployMode;
    }

    public float getErsHarvestedThisLapMGUK() {
        return ersHarvestedThisLapMGUK;
    }

    public float getErsHarvestedThisLapMGUH() {
        return ersHarvestedThisLapMGUH;
    }

    public float getErsDeployedThisLap() {
        return ersDeployedThisLap;
    }

    public int getNetworkPaused() {
        return networkPaused;
    }

    public float[] getTyresWear() {
        return tyresWear;
    }

    public int[] getTyresDamage() {
        return tyresDamage;
    }

    public int getFrontLeftWingDamage() {
        return frontLeftWingDamage;
    }

    public int getFrontRightWingDamage() {
        return frontRightWingDamage;
    }

    public int getRearWingDamage() {
        return rearWingDamage;
    }

    public int getDrsFault() {
        return drsFault;
    }

    public int getEngineDamage() {
        return engineDamage;
    }

    public int getGearBoxDamage() {
        return gearBoxDamage;
    }

    public static class Builder {

        private int tractionControl;
        private int antiLockBrakes;
        private int fuelMix;
        private int frontBrakeBias;
        private int pitLimitStatus;
        private float fuelInTank;
        private float fuelCapacity;
        private float fuelRemainingLaps;
        private int maxRPM;
        private int idleRPM;
        private int maxGears;
        private int drsAllowed;
        private int drsActivationDistance;
        private int actualTireCompound;
        private int visualTireCompound;
        private int tiresAgeLaps;
        private int vehicleFiaFlags;
        private float enginePowerICE;
        private float enginePowerMGUK;
        private float ersStoreEnergy;
        private int ersDeployMode;
        private float ersHarvestedThisLapMGUK;
        private float ersHarvestedThisLapMGUH;
        private float ersDeployedThisLap;
        private int networkPaused;

        private float[] tyresWear = new float[4];
        private int[] tyresDamage = new int[4];
        private int frontLeftWingDamage;
        private int frontRightWingDamage;
        private int rearWingDamage;
        private int drsFault;
        private int engineDamage;
        private int gearBoxDamage;

        public Builder setTractionControl(int tractionControl) {
            this.tractionControl = tractionControl;
            return this;
        }

        public Builder setAntiLockBrakes(int antiLockBrakes) {
            this.antiLockBrakes = antiLockBrakes;
            return this;
        }

        public Builder setFuelMix(int fuelMix) {
            this.fuelMix = fuelMix;
            return this;
        }

        public Builder setFrontBrakeBias(int frontBrakeBias) {
            this.frontBrakeBias = frontBrakeBias;
            return this;
        }

        public Builder setPitLimitStatus(int pitLimitStatus) {
            this.pitLimitStatus = pitLimitStatus;
            return this;
        }

        public Builder setFuelInTank(float fuelInTank) {
            this.fuelInTank = fuelInTank;
            return this;
        }

        public Builder setFuelCapacity(float fuelCapacity) {
            this.fuelCapacity = fuelCapacity;
            return this;
        }

        public Builder setFuelRemainingLaps(float fuelRemainingLaps) {
            this.fuelRemainingLaps = fuelRemainingLaps;
            return this;
        }

        public Builder setMaxRPM(int maxRPM) {
            this.maxRPM = maxRPM;
            return this;
        }

        public Builder setIdleRPM(int idleRPM) {
            this.idleRPM = idleRPM;
            return this;
        }

        public Builder setMaxGears(int maxGears) {
            this.maxGears = maxGears;
            return this;
        }

        public Builder setDrsAllowed(int drsAllowed) {
            this.drsAllowed = drsAllowed;
            return this;
        }

        public Builder setDrsActivationDistance(int drsActivationDistance) {
            this.drsActivationDistance = drsActivationDistance;
            return this;
        }

        public Builder setActualTireCompound(int actualTireCompound) {
            this.actualTireCompound = actualTireCompound;
            return this;
        }

        public Builder setVisualTireCompound(int visualTireCompound) {
            this.visualTireCompound = visualTireCompound;
            return this;
        }

        public Builder setTiresAgeLaps(int tiresAgeLaps) {
            this.tiresAgeLaps = tiresAgeLaps;
            return this;
        }

        public Builder setVehicleFiaFlags(int vehicleFiaFlags) {
            this.vehicleFiaFlags = vehicleFiaFlags;
            return this;
        }

        public Builder setEnginePowerICE(float enginePowerICE) {
            this.enginePowerICE = enginePowerICE;
            return this;
        }

        public Builder setEnginePowerMGUK(float enginePowerMGUK) {
            this.enginePowerMGUK = enginePowerMGUK;
            return this;
        }

        public Builder setErsStoreEnergy(float ersStoreEnergy) {
            this.ersStoreEnergy = ersStoreEnergy;
            return this;
        }

        public Builder setErsDeployMode(int ersDeployMode) {
            this.ersDeployMode = ersDeployMode;
            return this;
        }

        public Builder setErsHarvestedThisLapMGUK(float ersHarvestedThisLapMGUK) {
            this.ersHarvestedThisLapMGUK = ersHarvestedThisLapMGUK;
            return this;
        }

        public Builder setErsHarvestedThisLapMGUH(float ersHarvestedThisLapMGUH) {
            this.ersHarvestedThisLapMGUH = ersHarvestedThisLapMGUH;
            return this;
        }

        public Builder setErsDeployedThisLap(float ersDeployedThisLap) {
            this.ersDeployedThisLap = ersDeployedThisLap;
            return this;
        }

        public Builder setNetworkPaused(int networkPaused) {
            this.networkPaused = networkPaused;
            return this;
        }

        public Builder setTyresWear(float[] tyresWear) {
            this.tyresWear = tyresWear;
            return this;
        }

        public Builder setTyresDamage(int[] tyresDamage) {
            this.tyresDamage = tyresDamage;
            return this;
        }

        public Builder setFrontLeftWingDamage(int frontLeftWingDamage) {
            this.frontLeftWingDamage = frontLeftWingDamage;
            return this;
        }

        public Builder setFrontRightWingDamage(int frontRightWingDamage) {
            this.frontRightWingDamage = frontRightWingDamage;
            return this;
        }

        public Builder setRearWingDamage(int rearWingDamage) {
            this.rearWingDamage = rearWingDamage;
            return this;
        }

        public Builder setDrsFault(int drsFault) {
            this.drsFault = drsFault;
            return this;
        }

        public Builder setEngineDamage(int engineDamage) {
            this.engineDamage = engineDamage;
            return this;
        }

        public Builder setGearBoxDamage(int gearBoxDamage) {
            this.gearBoxDamage = gearBoxDamage;
            return this;
        }

        public CarStatusData build() {
            return new CarStatusData(this);
        }
    }
}

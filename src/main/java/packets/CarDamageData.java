package packets;

/**
 * F1 24 CarDamageData Breakdown (Little Endian)
 * - F1 2020 CarDamage info was part of CarStatusPacket
 * - F1 2021 Length: 39 bytes
 * - F1 2022-2024 Length: 42 bytes
 * - F1 2025 Length: 46 bytes TODO:add m_tyreBlisters[4] after breakDamage[4] for 2025.
 * This struct is 42 bytes long and contains details of the car's damage state,
 * including bodywork, tyres, brakes, and engine components. This data is sent for all cars.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * PacketCarDamageData
 * ------------------
 * Member Name               | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------|----------------|--------------|----------------|-------------------------
 * m_header                  | PacketHeader    | ...          | 2021           | Full packet header
 * m_carDamageData[22]       | CarDamageData   | ...          | 2021           | Array for each car
 * - m_tyresWear[4]          | float           | 16           | 2021           |
 * - m_tyresDamage[4]        | uint8           | 4            | 2021           |
 * - m_brakesDamage[4]       | uint8           | 4            | 2021           |
 * - m_frontLeftWingDamage    | uint8           | 1            | 2021           |
 * - m_frontRightWingDamage   | uint8           | 1            | 2021           |
 * - m_rearWingDamage         | uint8           | 1            | 2021           |
 * - m_floorDamage            | uint8           | 1            | 2021           |
 * - m_diffuserDamage         | uint8           | 1            | 2021           |
 * - m_sidepodDamage          | uint8           | 1            | 2021           |
 * - m_drsFault               | uint8           | 1            | 2021           |
 * - m_ersFault               | uint8           | 1            | 2022           |
 * - m_gearBoxDamage          | uint8           | 1            | 2021           |
 * - m_engineDamage           | uint8           | 1            | 2021           |
 * - m_engineMGUHWear         | uint8           | 1            | 2021           |
 * - m_engineESWear           | uint8           | 1            | 2021           |
 * - m_engineCEWear           | uint8           | 1            | 2021           |
 * - m_engineICEWear          | uint8           | 1            | 2021           |
 * - m_engineMGUKWear         | uint8           | 1            | 2021           |
 * - m_engineTCWear           | uint8           | 1            | 2021           |
 * - m_engineBlown            | uint8           | 1            | 2022           |
 * - m_engineSeized           | uint8           | 1            | 2022           |
 * Note:
 * - The uint8 type must be read with bitmasking (e.g., byteBuffer.get() & Constants.BIT_MASK_8).
 * - float maps directly to a Java 'float'.
 * - Arrays must be read in a loop for proper data conversion.
 */
public class CarDamageData {

    public CarDamageData(Builder builder) {
        this.tyresWear = builder.tyresWear;
        this.tyresDamage = builder.tyresDamage;
        this.brakesDamage = builder.brakesDamage;
        this.frontLeftWingDamage = builder.frontLeftWingDamage;
        this.frontRightWingDamage = builder.frontRightWingDamage;
        this.rearWingDamage = builder.rearWingDamage;
        this.floorDamage = builder.floorDamage;
        this.diffuserDamage = builder.diffuserDamage;
        this.sidepodDamage = builder.sidepodDamage;
        this.drsFault = builder.drsFault;
        this.ersFault = builder.ersFault;
        this.gearBoxDamage = builder.gearBoxDamage;
        this.engineDamage = builder.engineDamage;
        this.engineMGUHWear = builder.engineMGUHWear;
        this.engineESWear = builder.engineESWear;
        this.engineCEWear = builder.engineCEWear;
        this.engineICEWear = builder.engineICEWear;
        this.engineMGUKWear = builder.engineMGUKWear;
        this.engineTCWear = builder.engineTCWear;
        this.engineBlown = builder.engineBlown;
        this.engineSeized = builder.engineSeized;
    }

    private final float[] tyresWear;
    private final int[] tyresDamage;
    private final int[] brakesDamage;
    private final int frontLeftWingDamage;
    private final int frontRightWingDamage;
    private final int rearWingDamage;
    private final int floorDamage;
    private final int diffuserDamage;
    private final int sidepodDamage;
    private final int drsFault;
    private final int ersFault;
    private final int gearBoxDamage;
    private final int engineDamage;
    private final int engineMGUHWear;
    private final int engineESWear;
    private final int engineCEWear;
    private final int engineICEWear;
    private final int engineMGUKWear;
    private final int engineTCWear;
    private final int engineBlown;
    private final int engineSeized;

    public float[] getTyresWear() {
        return tyresWear;
    }

    public int[] getTyresDamage() {
        return tyresDamage;
    }

    public int[] getBrakesDamage() {
        return brakesDamage;
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

    public int getFloorDamage() {
        return floorDamage;
    }

    public int getDiffuserDamage() {
        return diffuserDamage;
    }

    public int getSidepodDamage() {
        return sidepodDamage;
    }

    public int getDrsFault() {
        return drsFault;
    }

    public int getErsFault() {
        return ersFault;
    }

    public int getGearBoxDamage() {
        return gearBoxDamage;
    }

    public int getEngineDamage() {
        return engineDamage;
    }

    public int getEngineMGUHWear() {
        return engineMGUHWear;
    }

    public int getEngineESWear() {
        return engineESWear;
    }

    public int getEngineCEWear() {
        return engineCEWear;
    }

    public int getEngineICEWear() {
        return engineICEWear;
    }

    public int getEngineMGUKWear() {
        return engineMGUKWear;
    }

    public int getEngineTCWear() {
        return engineTCWear;
    }

    public int getEngineBlown() {
        return engineBlown;
    }

    public int getEngineSeized() {
        return engineSeized;
    }

    public static class Builder {

        private float[] tyresWear = new float[4];
        private int[] tyresDamage = new int[4];
        private int[] brakesDamage = new int[4];
        private int frontLeftWingDamage;
        private int frontRightWingDamage;
        private int rearWingDamage;
        private int floorDamage;
        private int diffuserDamage;
        private int sidepodDamage;
        private int drsFault;
        private int ersFault;
        private int gearBoxDamage;
        private int engineDamage;
        private int engineMGUHWear;
        private int engineESWear;
        private int engineCEWear;
        private int engineICEWear;
        private int engineMGUKWear;
        private int engineTCWear;
        private int engineBlown;
        private int engineSeized;

        public Builder setTyresWear(float[] tyresWear) {
            this.tyresWear = tyresWear;
            return this;
        }

        public Builder setTyresDamage(int[] tyresDamage) {
            this.tyresDamage = tyresDamage;
            return this;
        }

        public Builder setBrakesDamage(int[] brakesDamage) {
            this.brakesDamage = brakesDamage;
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

        public Builder setFloorDamage(int floorDamage) {
            this.floorDamage = floorDamage;
            return this;
        }

        public Builder setDiffuserDamage(int diffuserDamage) {
            this.diffuserDamage = diffuserDamage;
            return this;
        }

        public Builder setSidepodDamage(int sidepodDamage) {
            this.sidepodDamage = sidepodDamage;
            return this;
        }

        public Builder setDrsFault(int drsFault) {
            this.drsFault = drsFault;
            return this;
        }

        public Builder setErsFault(int ersFault) {
            this.ersFault = ersFault;
            return this;
        }

        public Builder setGearBoxDamage(int gearBoxDamage) {
            this.gearBoxDamage = gearBoxDamage;
            return this;
        }

        public Builder setEngineDamage(int engineDamage) {
            this.engineDamage = engineDamage;
            return this;
        }

        public Builder setEngineMGUHWear(int engineMGUHWear) {
            this.engineMGUHWear = engineMGUHWear;
            return this;
        }

        public Builder setEngineESWear(int engineESWear) {
            this.engineESWear = engineESWear;
            return this;
        }

        public Builder setEngineCEWear(int engineCEWear) {
            this.engineCEWear = engineCEWear;
            return this;
        }

        public Builder setEngineICEWear(int engineICEWear) {
            this.engineICEWear = engineICEWear;
            return this;
        }

        public Builder setEngineMGUKWear(int engineMGUKWear) {
            this.engineMGUKWear = engineMGUKWear;
            return this;
        }

        public Builder setEngineTCWear(int engineTCWear) {
            this.engineTCWear = engineTCWear;
            return this;
        }

        public Builder setEngineBlown(int engineBlown) {
            this.engineBlown = engineBlown;
            return this;
        }

        public Builder setEngineSeized(int engineSeized) {
            this.engineSeized = engineSeized;
            return this;
        }

        public CarDamageData build() {
            return new CarDamageData(this);
        }

        public static CarDamageData fromStatus(CarStatusData status) {
            return new Builder()
                    .setTyresWear(status.getTyresWear())
                    .setTyresDamage(status.getTyresDamage())
                    .setFrontLeftWingDamage(status.getFrontLeftWingDamage())
                    .setFrontRightWingDamage(status.getFrontRightWingDamage())
                    .setRearWingDamage(status.getRearWingDamage())
                    .setDrsFault(status.getDrsFault())
                    .setEngineDamage(status.getEngineDamage())
                    .setGearBoxDamage(status.getGearBoxDamage())
                    .build();
        }
    }
}

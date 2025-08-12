package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 CarDamageData Breakdown (Little Endian)
 *
 * This struct is 42 bytes long and contains details of the car's damage state,
 * including bodywork, tyres, brakes, and engine components. This data is sent for all cars.
 *
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name                       | Data Type        | Size (bytes) | Starting Offset
 * ----------------------------------|------------------|--------------|-----------------
 * m_tyresWear[4]                    | float[4]         | 16           | 0
 * m_tyresDamage[4]                  | uint8[4]         | 4            | 16
 * m_brakesDamage[4]                 | uint8[4]         | 4            | 20
 * m_frontLeftWingDamage             | uint8            | 1            | 24
 * m_frontRightWingDamage            | uint8            | 1            | 25
 * m_rearWingDamage                  | uint8            | 1            | 26
 * m_floorDamage                     | uint8            | 1            | 27
 * m_diffuserDamage                  | uint8            | 1            | 28
 * m_sidepodDamage                   | uint8            | 1            | 29
 * m_drsFault                        | uint8            | 1            | 30
 * m_ersFault                        | uint8            | 1            | 31
 * m_gearBoxDamage                   | uint8            | 1            | 32
 * m_engineDamage                    | uint8            | 1            | 33
 * m_engineMGUHWear                  | uint8            | 1            | 34
 * m_engineESWear                    | uint8            | 1            | 35
 * m_engineCEWear                    | uint8            | 1            | 36
 * m_engineICEWear                   | uint8            | 1            | 37
 * m_engineMGUKWear                  | uint8            | 1            | 38
 * m_engineTCWear                    | uint8            | 1            | 39
 * m_engineBlown                     | uint8            | 1            | 40
 * m_engineSeized                    | uint8            | 1            | 41
 *
 * Note:
 * - The uint8 type must be read with bitmasking (e.g., byteBuffer.get() & Constants.BIT_MASK_8).
 * - float maps directly to a Java 'float'.
 * - Arrays must be read in a loop for proper data conversion.
 */
public class CarDamageData extends Data {

    public CarDamageData(ByteBuffer byteBuffer) {
        //        printMessage("Car Damage ", byteBuffer.array().length);
        for (int i = 0; i < 4; i++) this.tyresWear[i] = byteBuffer.getFloat();
        for (int i = 0; i < 4; i++) this.tyresDamage[i] = byteBuffer.get() & Constants.BIT_MASK_8;
        for (int i = 0; i < 4; i++) this.brakesDamage[i] = byteBuffer.get() & Constants.BIT_MASK_8;
        this.frontLeftWingDamage = byteBuffer.get() & Constants.BIT_MASK_8;
        this.frontRightWingDamage = byteBuffer.get() & Constants.BIT_MASK_8;
        this.rearWingDamage = byteBuffer.get() & Constants.BIT_MASK_8;
        this.floorDamage = byteBuffer.get() & Constants.BIT_MASK_8;
        this.diffuserDamage = byteBuffer.get() & Constants.BIT_MASK_8;
        this.sidepodDamage = byteBuffer.get() & Constants.BIT_MASK_8;
        this.drsFault = byteBuffer.get() & Constants.BIT_MASK_8;
        this.ersFault = byteBuffer.get() & Constants.BIT_MASK_8;
        this.gearBoxDamage = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineDamage = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineMGUHWear = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineESWear = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineCEWear = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineICEWear = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineMGUKWear = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineTCWear = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineBlown = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineSeized = byteBuffer.get() & Constants.BIT_MASK_8;
    }

    private final float[] tyresWear = new float[4];
    private final int[] tyresDamage = new int[4];
    private final int[] brakesDamage = new int[4];
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
}

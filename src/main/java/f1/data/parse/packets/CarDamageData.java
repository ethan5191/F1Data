package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;

import java.nio.ByteBuffer;

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

public record CarDamageData(float[] tyresWear, int[] tyresDamage, int[] brakesDamage, int frontLeftWingDamage,
                            int frontRightWingDamage, int rearWingDamage, int floorDamage, int diffuserDamage,
                            int sidepodDamage, int drsFault, int gearBoxDamage, int engineDamage, int engineMGUHWear,
                            int engineESWear, int engineCEWear, int engineICEWear, int engineMGUKWear,
                            int engineTCWear, int ersFault, int engineBlown, int engineSeized) {

    public static CarDamageData fromStatus(CarStatusData status) {
        return new CarDamageData(status.tyresWear(), status.tyresDamage(), new int[4], status.frontLeftWingDamage(),
                status.frontRightWingDamage(), status.rearWingDamage(), 0, 0, 0,
                status.drsFault(), status.gearBoxDamage(), status.engineDamage(), 0, 0,
                0, 0, 0, 0, 0, 0, 0);
    }

    record CarDamageData20(float[] tyresWear, int[] tyresDamage, int[] brakesDamage, int frontLeftWingDamage,
                           int frontRightWingDamage, int rearWingDamage, int floorDamage, int diffuserDamage,
                           int sidepodDamage, int drsFault, int gearBoxDamage, int engineDamage, int engineMGUHWear,
                           int engineESWear, int engineCEWear, int engineICEWear, int engineMGUKWear,
                           int engineTCWear) {
        public CarDamageData20(ByteBuffer byteBuffer) {
            this(ParseUtils.parseFloatArray(byteBuffer, 4), ParseUtils.parseIntArray(byteBuffer, 4), ParseUtils.parseIntArray(byteBuffer, 4), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()));
        }
    }

    record CarDamageData22(float[] tyresWear, int[] tyresDamage, int[] brakesDamage, int frontLeftWingDamage,
                           int frontRightWingDamage, int rearWingDamage, int floorDamage, int diffuserDamage,
                           int sidepodDamage, int drsFault, int ersFault, int gearBoxDamage, int engineDamage,
                           int engineMGUHWear, int engineESWear, int engineCEWear, int engineICEWear,
                           int engineMGUKWear, int engineTCWear, int engineBlown, int engineSeized) {
        public CarDamageData22(ByteBuffer byteBuffer) {
            this(ParseUtils.parseFloatArray(byteBuffer, 4), ParseUtils.parseIntArray(byteBuffer, 4), ParseUtils.parseIntArray(byteBuffer, 4), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()));
        }
    }
}

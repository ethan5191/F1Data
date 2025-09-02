package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;

import java.nio.ByteBuffer;

/**
 * F1 24 CarStatusData Breakdown (Little Endian)
 * <p>
 * - F1 2019 Length: 56 bytes
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
 * m_header                     | PacketHeader    | ...          | 2019           | Full packet header
 * m_carStatusData[22]          | CarStatusData   | ...          | 2024           | Array for each car
 * - m_tractionControl           | uint8           | 1            | 2019           |
 * - m_antiLockBrakes            | uint8           | 1            | 2019           |
 * - m_fuelMix                   | uint8           | 1            | 2019           |
 * - m_frontBrakeBias            | uint8           | 1            | 2019           |
 * - m_pitLimiterStatus          | uint8           | 1            | 2019           |
 * - m_fuelInTank                | float           | 4            | 2019           |
 * - m_fuelCapacity              | float           | 4            | 2019           |
 * - m_fuelRemainingLaps         | float           | 4            | 2019           |
 * - m_maxRPM                    | uint16          | 2            | 2019           |
 * - m_idleRPM                   | uint16          | 2            | 2019           |
 * - m_maxGears                  | uint8           | 1            | 2019           |
 * - m_drsAllowed                | uint8           | 1            | 2019           |
 * - m_drsActivationDistance     | uint16          | 2            | 2020           |
 * - m_actualTyreCompound        | uint8           | 1            | 2019           |
 * - m_visualTyreCompound        | uint8           | 1            | 2019           |
 * - m_tyresAgeLaps              | uint8           | 1            | 2020           |
 * - m_vehicleFiaFlags           | int8            | 1            | 2019           |
 * - m_enginePowerICE            | float           | 4            | 2023           |
 * - m_enginePowerMGUK           | float           | 4            | 2023           |
 * - m_ersStoreEnergy            | float           | 4            | 2019           |
 * - m_ersDeployMode             | uint8           | 1            | 2019           |
 * - m_ersHarvestedThisLapMGUK   | float           | 4            | 2019           |
 * - m_ersHarvestedThisLapMGUH   | float           | 4            | 2019           |
 * - m_ersDeployedThisLap        | float           | 4            | 2019           |
 * - m_networkPaused             | uint8           | 1            | 2021           |
 * <p>
 * Total size per CarStatusData (with padding/alignment) ≈ 55 bytes
 * <p>
 * Note:
 * - uint8 and uint16 types must be read with bitmasking to get positive integer values.
 * - int8 and float map directly to their Java counterparts.
 *
 * @param tyresWear Params where part of the CarStatus packet in 2020, moved to their own packet in 2021.
 */

public record CarStatusData(int tractionControl, int antiLockBrakes, int fuelMix, int frontBrakeBias,
                            int pitLimitStatus, float fuelInTank, float fuelCapacity, float fuelRemainingLaps,
                            int maxRPM, int idleRPM, int maxGears, int drsAllowed, int drsActivationDistance,
                            int actualTireCompound, int visualTireCompound, int tiresAgeLaps, int vehicleFiaFlags,
                            float ersStoreEnergy, int ersDeployMode, float ersHarvestedThisLapMGUK,
                            float ersHarvestedThisLapMGUH, float ersDeployedThisLap, int networkPaused,
                            float enginePowerICE, float enginePowerMGUK, float[] tyresWear, int[] tyresDamage,
                            int frontLeftWingDamage, int frontRightWingDamage, int rearWingDamage, int drsFault,
                            int engineDamage, int gearBoxDamage) {

    private static float[] parseLegacyTireWear(ByteBuffer byteBuffer) {
        float[] legacyTireWear = new float[4];
        for (int i = 0; i < legacyTireWear.length; i++) {
            legacyTireWear[i] = BitMaskUtils.bitMask8(byteBuffer.get());
        }
        return legacyTireWear;
    }

    record CarStatusData19(int tractionControl, int antiLockBrakes, int fuelMix, int frontBrakeBias, int pitLimitStatus,
                           float fuelInTank, float fuelCapacity, float fuelRemainingLaps, int maxRPM, int idleRPM,
                           int maxGears, int drsAllowed, float[] tyresWear, int actualTireCompound, int visualTireCompound,
                           int[] tyresDamage, int frontLeftWingDamage, int frontRightWingDamage, int rearWingDamage,
                           int engineDamage, int gearBoxDamage, int vehicleFiaFlags, float ersStoreEnergy,
                           int ersDeployMode, float ersHarvestedThisLapMGUK, float ersHarvestedThisLapMGUH,
                           float ersDeployedThisLap) {
        public CarStatusData19(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    parseLegacyTireWear(byteBuffer),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    ParseUtils.parseIntArray(byteBuffer, 4),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.get(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat()
            );
        }
    }

    record CarStatusData20(int tractionControl, int antiLockBrakes, int fuelMix, int frontBrakeBias, int pitLimitStatus,
                           float fuelInTank, float fuelCapacity, float fuelRemainingLaps, int maxRPM, int idleRPM,
                           int maxGears, int drsAllowed, int drsActivationDistance, float[] tyresWear,
                           int actualTireCompound, int visualTireCompound, int tiresAgeLaps,
                           int[] tyresDamage, int frontLeftWingDamage, int frontRightWingDamage, int rearWingDamage,
                           int drsFault, int engineDamage, int gearBoxDamage, int vehicleFiaFlags, float ersStoreEnergy,
                           int ersDeployMode, float ersHarvestedThisLapMGUK, float ersHarvestedThisLapMGUH,
                           float ersDeployedThisLap) {
        public CarStatusData20(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    parseLegacyTireWear(byteBuffer),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    ParseUtils.parseIntArray(byteBuffer, 4),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.get(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat()
            );
        }
    }

    record CarStatusData21(int tractionControl, int antiLockBrakes, int fuelMix, int frontBrakeBias, int pitLimitStatus,
                           float fuelInTank, float fuelCapacity, float fuelRemainingLaps, int maxRPM, int idleRPM,
                           int maxGears, int drsAllowed, int drsActivationDistance, int actualTireCompound,
                           int visualTireCompound, int tiresAgeLaps, int vehicleFiaFlags, float ersStoreEnergy,
                           int ersDeployMode, float ersHarvestedThisLapMGUK, float ersHarvestedThisLapMGUH,
                           float ersDeployedThisLap, int networkPaused) {
        public CarStatusData21(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.get(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }

    record CarStatusData23(int tractionControl, int antiLockBrakes, int fuelMix, int frontBrakeBias, int pitLimitStatus,
                           float fuelInTank, float fuelCapacity, float fuelRemainingLaps, int maxRPM, int idleRPM,
                           int maxGears, int drsAllowed, int drsActivationDistance, int actualTireCompound,
                           int visualTireCompound, int tiresAgeLaps, int vehicleFiaFlags, float enginePowerICE,
                           float enginePowerMGUK, float ersStoreEnergy, int ersDeployMode,
                           float ersHarvestedThisLapMGUK, float ersHarvestedThisLapMGUH, float ersDeployedThisLap,
                           int networkPaused
    ) {
        public CarStatusData23(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask16(byteBuffer.getShort()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.get(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    byteBuffer.getFloat(),
                    BitMaskUtils.bitMask8(byteBuffer.get())
            );
        }
    }
}

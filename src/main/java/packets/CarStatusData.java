package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 CarStatusData Breakdown (Little Endian)
 *
 * This struct is 55 bytes long and contains details of the car's components,
 * including fuel, tyres, ERS, and vehicle settings. This data is sent for all cars in the session.
 *
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name                       | Data Type        | Size (bytes) | Starting Offset
 * ----------------------------------|------------------|--------------|-----------------
 * m_tractionControl                 | uint8            | 1            | 0
 * m_antiLockBrakes                  | uint8            | 1            | 1
 * m_fuelMix                         | uint8            | 1            | 2
 * m_frontBrakeBias                  | uint8            | 1            | 3
 * m_pitLimiterStatus                | uint8            | 1            | 4
 * m_fuelInTank                      | float            | 4            | 5
 * m_fuelCapacity                    | float            | 4            | 9
 * m_fuelRemainingLaps               | float            | 4            | 13
 * m_maxRPM                          | uint16           | 2            | 17
 * m_idleRPM                         | uint16           | 2            | 19
 * m_maxGears                        | uint8            | 1            | 21
 * m_drsAllowed                      | uint8            | 1            | 22
 * m_drsActivationDistance           | uint16           | 2            | 23
 * m_actualTyreCompound              | uint8            | 1            | 25
 * m_visualTyreCompound              | uint8            | 1            | 26
 * m_tyresAgeLaps                    | uint8            | 1            | 27
 * m_vehicleFiaFlags                 | int8             | 1            | 28
 * m_enginePowerICE                  | float            | 4            | 29
 * m_enginePowerMGUK                 | float            | 4            | 33
 * m_ersStoreEnergy                  | float            | 4            | 37
 * m_ersDeployMode                   | uint8            | 1            | 41
 * m_ersHarvestedThisLapMGUK         | float            | 4            | 42
 * m_ersHarvestedThisLapMGUH         | float            | 4            | 46
 * m_ersDeployedThisLap              | float            | 4            | 50
 * m_networkPaused                   | uint8            | 1            | 54
 *
 * Note:
 * - uint8 and uint16 types must be read with bitmasking to get positive integer values.
 * - int8 and float map directly to their Java counterparts.
 */

public class CarStatusData extends Data {

    public CarStatusData(ByteBuffer byteBuffer) {
        //        printMessage("Car Status ", byteBuffer.array().length);
        this.tractionControl = byteBuffer.get() & Constants.BIT_MASK_8;
        this.antiLockBrakes = byteBuffer.get() & Constants.BIT_MASK_8;
        this.fuelMix = byteBuffer.get() & Constants.BIT_MASK_8;
        this.frontBrakeBias = byteBuffer.get() & Constants.BIT_MASK_8;
        this.pitLimitStatus = byteBuffer.get() & Constants.BIT_MASK_8;
        this.fuelInTank = byteBuffer.getFloat();
        this.fuelCapacity = byteBuffer.getFloat();
        this.fuelRemainingLaps = byteBuffer.getFloat();
        this.maxRPM = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.idleRPM = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.maxGears = byteBuffer.get() & Constants.BIT_MASK_8;
        this.drsAllowed = byteBuffer.get() & Constants.BIT_MASK_8;
        this.drsActivationDistance = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.actualTireCompound = byteBuffer.get() & Constants.BIT_MASK_8;
        this.visualTireCompound = byteBuffer.get() & Constants.BIT_MASK_8;
        this.tiresAgeLaps = byteBuffer.get() & Constants.BIT_MASK_8;
        this.vehicleFiaFlags = byteBuffer.get();
        this.enginePowerICE = byteBuffer.getFloat();
        this.enginePowerMGUK = byteBuffer.getFloat();
        this.ersStoreEnergy = byteBuffer.getFloat();
        this.ersDeployMode = byteBuffer.get() & Constants.BIT_MASK_8;
        this.ersHarvestedThisLapMGUK = byteBuffer.getFloat();
        this.ersHarvestedThisLapMGUH = byteBuffer.getFloat();
        this.ersDeployedThisLap = byteBuffer.getFloat();
        this.networkPaused = byteBuffer.get() & Constants.BIT_MASK_8;
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
}

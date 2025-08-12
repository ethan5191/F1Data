package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 CarTelemetryData Breakdown (Little Endian)
 *
 * This struct is 60 bytes long and contains a snapshot of a single car's
 * telemetry data, including speed, temperatures, pressures, and controls.
 *
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name                       | Data Type        | Size (bytes) | Starting Offset
 * ----------------------------------|------------------|--------------|-----------------
 * m_speed                           | uint16           | 2            | 0
 * m_throttle                        | float            | 4            | 2
 * m_steer                           | float            | 4            | 6
 * m_brake                           | float            | 4            | 10
 * m_clutch                          | uint8            | 1            | 14
 * m_gear                            | int8             | 1            | 15
 * m_engineRPM                       | uint16           | 2            | 16
 * m_drs                             | uint8            | 1            | 18
 * m_revLightsPercent                | uint8            | 1            | 19
 * m_revLightsBitValue               | uint16           | 2            | 20
 * m_brakesTemperature[4]            | uint16[4]        | 8            | 22
 * m_tyresSurfaceTemperature[4]      | uint8[4]         | 4            | 30
 * m_tyresInnerTemperature[4]        | uint8[4]         | 4            | 34
 * m_engineTemperature               | uint16           | 2            | 38
 * m_tyresPressure[4]                | float[4]         | 16           | 40
 * m_surfaceType[4]                  | uint8[4]         | 4            | 56
 *
 * Note:
 * - uint16 and uint8 types require bitmasking to be read as positive integers in Java.
 * - float and int8 map directly.
 * - Arrays must be read by looping or using get() with a destination array.
 */
public class CarTelemetryData extends Data {

    public CarTelemetryData(ByteBuffer byteBuffer) {
        //        printMessage("Car Telemetry ", byteBuffer.array().length);
        this.speed = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.throttle = byteBuffer.getFloat();
        this.steer = byteBuffer.getFloat();
        this.brake = byteBuffer.getFloat();
        this.clutch = byteBuffer.get() & Constants.BIT_MASK_8;
        this.gear = byteBuffer.get();
        this.engineRPM = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.drs = byteBuffer.get() & Constants.BIT_MASK_8;
        this.revLightPercent = byteBuffer.get() & Constants.BIT_MASK_8;
        this.revLightBitVal = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.brakeTemps = populateArray(byteBuffer);
        this.tireSurfaceTemps = populateArray2(byteBuffer);
        this.tireInnerTemps = populateArray2(byteBuffer);
        this.engineTemp = byteBuffer.getShort() & Constants.BIT_MASK_16;
        this.tirePressure = populateArray3(byteBuffer);
        this.surfaceType = populateArray2(byteBuffer);
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

    private int[] populateArray(ByteBuffer byteBuffer) {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = byteBuffer.getShort() & Constants.BIT_MASK_16;
        }
        return result;
    }

    private int[] populateArray2(ByteBuffer byteBuffer) {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = byteBuffer.get() & Constants.BIT_MASK_8;
        }
        return result;
    }

    private float[] populateArray3(ByteBuffer byteBuffer) {
        float[] result = new float[4];
        for (int i = 0; i < 4; i++) {
            result[i] = byteBuffer.getFloat();
        }
        return result;
    }
}

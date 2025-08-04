package packets;

import java.nio.ByteBuffer;

/**
 * F1 24 CarSetupData Breakdown (Little Endian)
 *
 * This struct is 50 bytes long and contains data about a single car's setup.
 * It is repeated for each car in the PacketCarSetupData packet.
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name                       | Data Type | Size (bytes) | Starting Offset
 * ----------------------------------|-----------|--------------|-----------------
 * m_frontWing                       | uint8     | 1            | 0
 * m_rearWing                        | uint8     | 1            | 1
 * m_onThrottle                      | uint8     | 1            | 2
 * m_offThrottle                     | uint8     | 1            | 3
 * m_frontCamber                     | float     | 4            | 4
 * m_rearCamber                      | float     | 4            | 8
 * m_frontToe                        | float     | 4            | 12
 * m_rearToe                         | float     | 4            | 16
 * m_frontSuspension                 | uint8     | 1            | 20
 * m_rearSuspension                  | uint8     | 1            | 21
 * m_frontAntiRollBar                | uint8     | 1            | 22
 * m_rearAntiRollBar                 | uint8     | 1            | 23
 * m_frontSuspensionHeight           | uint8     | 1            | 24
 * m_rearSuspensionHeight            | uint8     | 1            | 25
 * m_brakePressure                   | uint8     | 1            | 26
 * m_brakeBias                       | uint8     | 1            | 27
 * m_engineBraking                   | uint8     | 1            | 28
 * m_rearLeftTyrePressure            | float     | 4            | 29
 * m_rearRightTyrePressure           | float     | 4            | 33
 * m_frontLeftTyrePressure           | float     | 4            | 37
 * m_frontRightTyrePressure          | float     | 4            | 41
 * m_ballast                         | uint8     | 1            | 45
 * m_fuelLoad                        | float     | 4            | 46
 */

public class CarSetupData extends Data {

    public CarSetupData(ByteBuffer byteBuffer) {
//        printMessage("Car Setup ", byteBuffer.array().length);
        this.frontWing = byteBuffer.get();
        this.rearWing = byteBuffer.get();
        this.onThrottle = byteBuffer.get();
        this.offThrottle = byteBuffer.get();
        this.frontCamber = byteBuffer.getFloat();
        this.rearCamber = byteBuffer.getFloat();
        this.frontToe = byteBuffer.getFloat();
        this.rearToe = byteBuffer.getFloat();
        this.frontSusp = byteBuffer.get();
        this.rearSusp = byteBuffer.get();
        this.frontARB = byteBuffer.get();
        this.rearARB = byteBuffer.get();
        this.frontHeight = byteBuffer.get();
        this.rearHeight = byteBuffer.get();
        this.brakePressure = byteBuffer.get();
        this.brakeBias = byteBuffer.get();
        this.engineBreaking = byteBuffer.get();
        this.rearLeftPressure = byteBuffer.getFloat();
        this.rearRightPressure = byteBuffer.getFloat();
        this.frontLeftPressure = byteBuffer.getFloat();
        this.frontRightPressure = byteBuffer.getFloat();
        this.ballast = byteBuffer.get();
        this.fuelLoad = byteBuffer.getFloat();
    }

    private final byte frontWing;
    private final byte rearWing;
    private final byte onThrottle;
    private final byte offThrottle;
    private final float frontCamber;
    private final float rearCamber;
    private final float frontToe;
    private final float rearToe;
    private final byte frontSusp;
    private final byte rearSusp;
    private final byte frontARB;
    private final byte rearARB;
    private final byte frontHeight;
    private final byte rearHeight;
    private final byte brakePressure;
    private final byte brakeBias;
    private final byte engineBreaking;
    private final float rearLeftPressure;
    private final float rearRightPressure;
    private final float frontLeftPressure;
    private final float frontRightPressure;
    private final byte ballast;
    private final float fuelLoad;

    public byte getFrontWing() {
        return frontWing;
    }

    public byte getRearWing() {
        return rearWing;
    }

    public byte getOnThrottle() {
        return onThrottle;
    }

    public byte getOffThrottle() {
        return offThrottle;
    }

    public float getFrontCamber() {
        return frontCamber;
    }

    public float getRearCamber() {
        return rearCamber;
    }

    public float getFrontToe() {
        return frontToe;
    }

    public float getRearToe() {
        return rearToe;
    }

    public byte getFrontSusp() {
        return frontSusp;
    }

    public byte getRearSusp() {
        return rearSusp;
    }

    public byte getFrontARB() {
        return frontARB;
    }

    public byte getRearARB() {
        return rearARB;
    }

    public byte getFrontHeight() {
        return frontHeight;
    }

    public byte getRearHeight() {
        return rearHeight;
    }

    public byte getBrakePressure() {
        return brakePressure;
    }

    public byte getBrakeBias() {
        return brakeBias;
    }

    public byte getEngineBreaking() {
        return engineBreaking;
    }

    public float getRearLeftPressure() {
        return rearLeftPressure;
    }

    public float getRearRightPressure() {
        return rearRightPressure;
    }

    public float getFrontLeftPressure() {
        return frontLeftPressure;
    }

    public float getFrontRightPressure() {
        return frontRightPressure;
    }

    public byte getBallast() {
        return ballast;
    }

    public float getFuelLoad() {
        return fuelLoad;
    }
}

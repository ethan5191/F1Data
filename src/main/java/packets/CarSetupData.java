package packets;

import javafx.scene.control.Label;
import utils.Constants;

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
        this.frontWing = byteBuffer.get() & Constants.BIT_MASK_8;
        this.rearWing = byteBuffer.get() & Constants.BIT_MASK_8;
        this.onThrottle = byteBuffer.get() & Constants.BIT_MASK_8;
        this.offThrottle = byteBuffer.get() & Constants.BIT_MASK_8;
        this.frontCamber = byteBuffer.getFloat();
        this.rearCamber = byteBuffer.getFloat();
        this.frontToe = byteBuffer.getFloat();
        this.rearToe = byteBuffer.getFloat();
        this.frontSusp = byteBuffer.get() & Constants.BIT_MASK_8;
        this.rearSusp = byteBuffer.get() & Constants.BIT_MASK_8;
        this.frontARB = byteBuffer.get() & Constants.BIT_MASK_8;
        this.rearARB = byteBuffer.get() & Constants.BIT_MASK_8;
        this.frontHeight = byteBuffer.get() & Constants.BIT_MASK_8;
        this.rearHeight = byteBuffer.get() & Constants.BIT_MASK_8;
        this.brakePressure = byteBuffer.get() & Constants.BIT_MASK_8;
        this.brakeBias = byteBuffer.get() & Constants.BIT_MASK_8;
        this.engineBreaking = byteBuffer.get() & Constants.BIT_MASK_8;
        this.rearLeftPressure = byteBuffer.getFloat();
        this.rearRightPressure = byteBuffer.getFloat();
        this.frontLeftPressure = byteBuffer.getFloat();
        this.frontRightPressure = byteBuffer.getFloat();
        this.ballast = byteBuffer.get() & Constants.BIT_MASK_8;
        this.fuelLoad = byteBuffer.getFloat();
    }

    private final int frontWing;
    private final int rearWing;
    private final int onThrottle;
    private final int offThrottle;
    private final float frontCamber;
    private final float rearCamber;
    private final float frontToe;
    private final float rearToe;
    private final int frontSusp;
    private final int rearSusp;
    private final int frontARB;
    private final int rearARB;
    private final int frontHeight;
    private final int rearHeight;
    private final int brakePressure;
    private final int brakeBias;
    private final int engineBreaking;
    private final float rearLeftPressure;
    private final float rearRightPressure;
    private final float frontLeftPressure;
    private final float frontRightPressure;
    private final int ballast;
    private final float fuelLoad;

    private String setupName;

    public int getFrontWing() {
        return frontWing;
    }

    public int getRearWing() {
        return rearWing;
    }

    public int getOnThrottle() {
        return onThrottle;
    }

    public int getOffThrottle() {
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

    public int getFrontSusp() {
        return frontSusp;
    }

    public int getRearSusp() {
        return rearSusp;
    }

    public int getFrontARB() {
        return frontARB;
    }

    public int getRearARB() {
        return rearARB;
    }

    public int getFrontHeight() {
        return frontHeight;
    }

    public int getRearHeight() {
        return rearHeight;
    }

    public int getBrakePressure() {
        return brakePressure;
    }

    public int getBrakeBias() {
        return brakeBias;
    }

    public int getEngineBreaking() {
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

    public int getBallast() {
        return ballast;
    }

    public float getFuelLoad() {
        return fuelLoad;
    }

    public String getSetupName() {
        return setupName;
    }

    public void setSetupName(String setupName) {
        this.setupName = setupName;
    }

    public Label[][] getSetupDashboardData() {
        return new Label[][]{{new Label(" Fuel Load ")}, {new Label(this.fuelLoad + "kg")},
                {new Label(" Front Wing "), new Label(" Rear Wing ")},
                {new Label(String.valueOf(this.frontWing)), new Label(String.valueOf(this.rearWing))},
                {new Label(" On Throttle "), new Label(" Off Throttle "), new Label(" Engine Breaking ")},
                {new Label(String.valueOf(this.onThrottle)), new Label(String.valueOf(this.offThrottle)), new Label(String.valueOf(this.engineBreaking))},
                {new Label(" Front Camber "), new Label(" Rear Camber "), new Label(" Front Toe-out "), new Label(" Rear Toe-in ")},
                {new Label(String.valueOf(this.frontCamber)), new Label(String.valueOf(this.rearCamber)), new Label(String.valueOf(this.frontToe)), new Label(String.valueOf(this.rearToe))},
                {new Label(" Front Suspension "), new Label(" Rear Suspension "), new Label(" Front ARB "),
                        new Label(" Rear ARB "), new Label(" Front Height "), new Label(" Rear Height ")},
                {new Label(String.valueOf(this.frontSusp)), new Label(String.valueOf(this.rearSusp)), new Label(String.valueOf(this.frontARB)),
                        new Label(String.valueOf(this.rearARB)), new Label(String.valueOf(this.frontHeight)), new Label(String.valueOf(this.rearHeight))},
                {new Label(" Brake Pressure "), new Label(" Front Brake Bias ")},
                {new Label(String.valueOf(this.brakePressure)), new Label(String.valueOf(this.brakeBias))},
                {new Label(" FR Pressure "), new Label(" FL Pressure "), new Label(" RR Pressure "), new Label(" RL Pressure ")},
                {new Label(String.valueOf(this.frontRightPressure)), new Label(String.valueOf(this.frontLeftPressure)),
                        new Label(String.valueOf(this.rearRightPressure)), new Label(String.valueOf(this.rearLeftPressure))}
        };
    }

    public boolean equals(CarSetupData csd) {
        return (csd.getFrontWing() == this.frontWing && csd.getRearWing() == this.rearWing
                && csd.getOnThrottle() == this.onThrottle && csd.getOffThrottle() == this.offThrottle
                && csd.getFrontCamber() == this.frontCamber && csd.getRearCamber() == this.rearCamber
                && csd.getFrontToe() == this.frontToe && csd.getRearToe() == this.rearToe
                && csd.getFrontSusp() == this.frontSusp && csd.getRearSusp() == this.rearSusp
                && csd.getFrontARB() == this.frontARB && csd.getRearARB() == this.rearARB
                && csd.getFrontHeight() == this.frontHeight && csd.getRearHeight() == this.rearHeight
                && csd.getFrontRightPressure() == this.frontRightPressure && csd.getFrontLeftPressure() == this.frontLeftPressure
                && csd.getRearRightPressure() == this.rearRightPressure && csd.getRearLeftPressure() == this.rearLeftPressure
        );
    }
}

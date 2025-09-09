package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class MotionExDataFactory {

    public static MotionExData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2023:
                MotionExData.MotionExData23 med23 = new MotionExData.MotionExData23(byteBuffer);
                yield new MotionExData(med23.suspensionPosition(), med23.suspensionVelocity(), med23.suspensionAcceleration(), med23.wheelSpeed(),
                        med23.wheelSlipRatio(), med23.wheelSlipAngle(), med23.wheelLatForce(), med23.wheelLongForce(), med23.heightOfCOGAboveGround(),
                        med23.localVelocityX(), med23.localVelocityY(), med23.localVelocityZ(), med23.angularVelocityX(), med23.angularVelocityY(),
                        med23.angularVelocityZ(), med23.angularAccelerationX(), med23.angularAccelerationY(), med23.angularAccelerationZ(), med23.frontWheelsAngle(), med23.wheelVertForce(),
                        0f, 0f, 0f, 0f, 0f, 0f, new float[4], new float[4]);
            case Constants.YEAR_2024:
                MotionExData.MotionExData24 med24 = new MotionExData.MotionExData24(byteBuffer);
                yield new MotionExData(med24.suspensionPosition(), med24.suspensionVelocity(), med24.suspensionAcceleration(), med24.wheelSpeed(),
                        med24.wheelSlipRatio(), med24.wheelSlipAngle(), med24.wheelLatForce(), med24.wheelLongForce(), med24.heightOfCOGAboveGround(),
                        med24.localVelocityX(), med24.localVelocityY(), med24.localVelocityZ(), med24.angularVelocityX(), med24.angularVelocityY(),
                        med24.angularVelocityZ(), med24.angularAccelerationX(), med24.angularAccelerationY(), med24.angularAccelerationZ(), med24.frontWheelsAngle(), med24.wheelVertForce(),
                        med24.frontAeroHeight(), med24.rearAeroHeight(), med24.frontRollAngle(), med24.rearRollAngle(), med24.chassisYaw(), 0f, new float[4], new float[4]);
            case Constants.YEAR_2025:
                MotionExData.MotionExData25 med25 = new MotionExData.MotionExData25(byteBuffer);
                yield new MotionExData(med25.suspensionPosition(), med25.suspensionVelocity(), med25.suspensionAcceleration(), med25.wheelSpeed(),
                        med25.wheelSlipRatio(), med25.wheelSlipAngle(), med25.wheelLatForce(), med25.wheelLongForce(), med25.heightOfCOGAboveGround(),
                        med25.localVelocityX(), med25.localVelocityY(), med25.localVelocityZ(), med25.angularVelocityX(), med25.angularVelocityY(),
                        med25.angularVelocityZ(), med25.angularAccelerationX(), med25.angularAccelerationY(), med25.angularAccelerationZ(), med25.frontWheelsAngle(), med25.wheelVertForce(),
                        med25.frontAeroHeight(), med25.rearAeroHeight(), med25.frontRollAngle(), med25.rearRollAngle(), med25.chassisYaw(), med25.chassisPitch(), med25.wheelCamber(), med25.wheelCamberGain());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2023 - 2025)");
        };
    }
}

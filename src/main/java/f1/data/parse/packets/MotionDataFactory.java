package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class MotionDataFactory {

    public static MotionData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2019, Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022,
                 Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025 -> new MotionData(byteBuffer);
            default ->
                    throw new IllegalStateException("Games Packet Format did not match an accepted format (2019 - 2025)");
        };
    }

    //Used to build a version of the MotionExData object from the extra packet information that is after the MotionData array in 2019-2022
    public static MotionExData buildLegacy(ByteBuffer byteBuffer) {
        MotionExData.MotionExData19 med19 = new MotionExData.MotionExData19(byteBuffer);
        return new MotionExData(med19.suspensionPosition(), med19.suspensionVelocity(), med19.suspensionAcceleration(), med19.wheelSpeed(),
                med19.wheelSlip(), new float[4], new float[4], new float[4], 0, med19.localVelocityX(),
                med19.localVelocityY(), med19.localVelocityZ(), med19.angularVelocityX(), med19.angularVelocityY(), med19.angularVelocityZ(),
                med19.angularAccelerationX(), med19.angularAccelerationY(), med19.angularAccelerationZ(), med19.frontWheelsAngle(),
                new float[4], 0, 0, 0, 0, 0, 0, new float[4], new float[4]);
    }
}

package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class MotionDataFactory implements DataFactory<MotionData> {

    private final SupportedYearsEnum packetFormat;

    public MotionDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public MotionData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2019, F1_2020, F1_2021, F1_2022, F1_2023, F1_2024, F1_2025 -> buildData(new MotionData.MotionData19(byteBuffer));
        };
    }

    //Used to build a version of the MotionExData object from the extra packet information that is after the MotionData array in 2019-2022
    public MotionExData buildLegacy(ByteBuffer byteBuffer) {
        MotionExData.MotionExData19 med19 = new MotionExData.MotionExData19(byteBuffer);
        return new MotionExData(med19.suspensionPosition(), med19.suspensionVelocity(), med19.suspensionAcceleration(), med19.wheelSpeed(),
                med19.wheelSlip(), new float[4], new float[4], new float[4], 0, med19.localVelocityX(),
                med19.localVelocityY(), med19.localVelocityZ(), med19.angularVelocityX(), med19.angularVelocityY(), med19.angularVelocityZ(),
                med19.angularAccelerationX(), med19.angularAccelerationY(), med19.angularAccelerationZ(), med19.frontWheelsAngle(),
                new float[4], 0, 0, 0, 0, 0, 0, new float[4], new float[4]);
    }

    private MotionData buildData(MotionData.MotionData19 md19) {
        return new MotionData(md19.worldPositionX(), md19.worldPositionY(), md19.worldPositionZ(), md19.worldVelocityX(), md19.worldVelocityY(), md19.worldVelocityZ(), md19.worldForwardDirX(), md19.worldForwardDirY(), md19.worldForwardDirZ(), md19.worldRightDirX(), md19.worldRightDirY(), md19.worldRightDirZ(), md19.gForceLat(), md19.gForceLon(), md19.gForceVer(), md19.yaw(), md19.pitch(), md19.roll(), 0, 0, 0);
    }
}

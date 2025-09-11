package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class CarSetupDataFactory implements DataFactory<CarSetupData> {

    private final SupportedYearsEnum packetFormat;

    public CarSetupDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public CarSetupData build(ByteBuffer byteBuffer) {
        return switch (this.packetFormat) {
            case F1_2019 -> buildData(new CarSetupData.CarSetupData19(byteBuffer));
            case F1_2020, F1_2021, F1_2022, F1_2023 -> buildData(new CarSetupData.CarSetupData20(byteBuffer));
            case F1_2024, F1_2025 -> buildData(new CarSetupData.CarSetupData24(byteBuffer));
        };
    }

    private CarSetupData buildData(CarSetupData.CarSetupData19 c19) {
        return new CarSetupData(c19.frontWing(), c19.rearWing(), c19.onThrottle(), c19.offThrottle(), c19.frontCamber(), c19.rearCamber(),
                c19.frontToe(), c19.rearToe(), c19.frontSusp(), c19.rearSusp(), c19.frontARB(), c19.rearARB(), c19.frontHeight(),
                c19.rearHeight(), c19.brakePressure(), c19.brakeBias(), c19.rearTirePressure(), c19.rearTirePressure(),
                c19.frontTirePressure(), c19.frontTirePressure(), c19.ballast(), c19.fuelLoad(), 0);
    }

    private CarSetupData buildData(CarSetupData.CarSetupData20 c20) {
        return new CarSetupData(c20.frontWing(), c20.rearWing(), c20.onThrottle(), c20.offThrottle(), c20.frontCamber(), c20.rearCamber(),
                c20.frontToe(), c20.rearToe(), c20.frontSusp(), c20.rearSusp(), c20.frontARB(), c20.rearARB(), c20.frontHeight(),
                c20.rearHeight(), c20.brakePressure(), c20.brakeBias(), c20.rearLeftPressure(), c20.rearRightPressure(),
                c20.frontLeftPressure(), c20.frontRightPressure(), c20.ballast(), c20.fuelLoad(), 0);
    }

    private CarSetupData buildData(CarSetupData.CarSetupData24 c24) {
        return new CarSetupData(c24.frontWing(), c24.rearWing(), c24.onThrottle(), c24.offThrottle(), c24.frontCamber(), c24.rearCamber(),
                c24.frontToe(), c24.rearToe(), c24.frontSusp(), c24.rearSusp(), c24.frontARB(), c24.rearARB(), c24.frontHeight(),
                c24.rearHeight(), c24.brakePressure(), c24.brakeBias(), c24.rearLeftPressure(), c24.rearRightPressure(),
                c24.frontLeftPressure(), c24.frontRightPressure(), c24.ballast(), c24.fuelLoad(), c24.engineBraking());
    }
}

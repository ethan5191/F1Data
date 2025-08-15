package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarSetupDataFactory {

    public static CarSetupData build(int packetFormat, ByteBuffer byteBuffer, String setupName) {
        return switch (packetFormat) {
            case Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023:
                CarSetupData.CarSetupData20 c20 = new CarSetupData.CarSetupData20(byteBuffer);
                yield new CarSetupData(c20.frontWing(), c20.rearWing(), c20.onThrottle(), c20.offThrottle(), c20.frontCamber(), c20.rearCamber(),
                        c20.frontToe(), c20.rearToe(), c20.frontSusp(), c20.rearSusp(), c20.frontARB(), c20.rearARB(), c20.frontHeight(),
                        c20.rearHeight(), c20.brakePressure(), c20.brakeBias(), c20.rearLeftPressure(), c20.rearRightPressure(),
                        c20.frontLeftPressure(), c20.frontRightPressure(), c20.ballast(), c20.fuelLoad(), 0, setupName);
            case Constants.YEAR_2024, Constants.YEAR_2025:
                CarSetupData.CarSetupData24 c24 = new CarSetupData.CarSetupData24(byteBuffer);
                yield new CarSetupData(c24.frontWing(), c24.rearWing(), c24.onThrottle(), c24.offThrottle(), c24.frontCamber(), c24.rearCamber(),
                        c24.frontToe(), c24.rearToe(), c24.frontSusp(), c24.rearSusp(), c24.frontARB(), c24.rearARB(), c24.frontHeight(),
                        c24.rearHeight(), c24.brakePressure(), c24.brakeBias(), c24.rearLeftPressure(), c24.rearRightPressure(),
                        c24.frontLeftPressure(), c24.frontRightPressure(), c24.ballast(), c24.fuelLoad(), c24.engineBraking(), setupName);
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

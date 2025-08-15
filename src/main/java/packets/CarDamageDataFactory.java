package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarDamageDataFactory {

    public static CarDamageData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2020, Constants.YEAR_2021:
                CarDamageData.CarDamageData20 c20 = new CarDamageData.CarDamageData20(byteBuffer);
                yield new CarDamageData(c20.tyresWear(), c20.tyresDamage(), c20.brakesDamage(), c20.frontLeftWingDamage(), c20.frontRightWingDamage(), c20.rearWingDamage(),
                        c20.floorDamage(), c20.diffuserDamage(), c20.sidepodDamage(), c20.drsFault(), c20.gearBoxDamage(), c20.engineDamage(),
                        c20.engineMGUHWear(), c20.engineESWear(), c20.engineCEWear(), c20.engineICEWear(), c20.engineMGUKWear(), c20.engineTCWear(),
                        0, 0, 0);
            case Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025:
                CarDamageData.CarDamageData22 c22 = new CarDamageData.CarDamageData22(byteBuffer);
                yield new CarDamageData(c22.tyresWear(), c22.tyresDamage(), c22.brakesDamage(), c22.frontLeftWingDamage(), c22.frontRightWingDamage(), c22.rearWingDamage(),
                        c22.floorDamage(), c22.diffuserDamage(), c22.sidepodDamage(), c22.drsFault(), c22.gearBoxDamage(), c22.engineDamage(),
                        c22.engineMGUHWear(), c22.engineESWear(), c22.engineCEWear(), c22.engineICEWear(), c22.engineMGUKWear(), c22.engineTCWear(),
                        c22.ersFault(), c22.engineBlown(), c22.engineSeized());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

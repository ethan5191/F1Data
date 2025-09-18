package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class CarDamageDataFactory implements DataFactory<CarDamageData>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public CarDamageDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public CarDamageData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2020, F1_2021 -> buildData(new CarDamageData.CarDamageData20(byteBuffer));
            case F1_2022, F1_2023, F1_2024, F1_2025 -> buildData(new CarDamageData.CarDamageData22(byteBuffer));
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    private CarDamageData buildData(CarDamageData.CarDamageData20 c20) {
        return new CarDamageData(c20.tyresWear(), c20.tyresDamage(), c20.brakesDamage(), c20.frontLeftWingDamage(), c20.frontRightWingDamage(), c20.rearWingDamage(),
                c20.floorDamage(), c20.diffuserDamage(), c20.sidepodDamage(), c20.drsFault(), c20.gearBoxDamage(), c20.engineDamage(),
                c20.engineMGUHWear(), c20.engineESWear(), c20.engineCEWear(), c20.engineICEWear(), c20.engineMGUKWear(), c20.engineTCWear(),
                0, 0, 0);
    }

    private CarDamageData buildData(CarDamageData.CarDamageData22 c22) {
        return new CarDamageData(c22.tyresWear(), c22.tyresDamage(), c22.brakesDamage(), c22.frontLeftWingDamage(), c22.frontRightWingDamage(), c22.rearWingDamage(),
                c22.floorDamage(), c22.diffuserDamage(), c22.sidepodDamage(), c22.drsFault(), c22.gearBoxDamage(), c22.engineDamage(),
                c22.engineMGUHWear(), c22.engineESWear(), c22.engineCEWear(), c22.engineICEWear(), c22.engineMGUKWear(), c22.engineTCWear(),
                c22.ersFault(), c22.engineBlown(), c22.engineSeized());
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2020.getYear();
    }
}

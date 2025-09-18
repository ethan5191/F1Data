package f1.data.parse.packets.events;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;
import f1.data.parse.packets.FirstYearProvided;

import java.nio.ByteBuffer;

public class SpeedTrapDataFactory implements DataFactory<SpeedTrapData>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public SpeedTrapDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public SpeedTrapData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2020 -> buildData(new SpeedTrapData.SpeedTrapData20(byteBuffer));
            case F1_2021 -> buildData(new SpeedTrapData.SpeedTrapData21(byteBuffer));
            case F1_2022, F1_2023, F1_2024, F1_2025 -> buildData(new SpeedTrapData.SpeedTrapData22(byteBuffer));
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    private SpeedTrapData buildData(SpeedTrapData.SpeedTrapData20 s20) {
        return new SpeedTrapData(s20.vehicleId(), s20.speed(), 0, 0, 0, 0);
    }

    private SpeedTrapData buildData(SpeedTrapData.SpeedTrapData21 s21) {
        return new SpeedTrapData(s21.vehicleId(), s21.speed(), s21.isOverallFastest(), s21.isDriverFastest(), 0, 0);
    }

    private SpeedTrapData buildData(SpeedTrapData.SpeedTrapData22 s22) {
        return new SpeedTrapData(s22.vehicleId(), s22.speed(), s22.isOverallFastest(), s22.isDriverFastest(), s22.fastestVehicleId(), s22.fastestSpeed());
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2020.getYear();
    }
}

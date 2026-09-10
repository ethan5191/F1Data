package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class CarTelemetryData2Factory implements DataFactory<CarTelemetryData2>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public CarTelemetryData2Factory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public CarTelemetryData2 build(ByteBuffer byteBuffer) {
        return switch (this.packetFormat) {
            case F1_2026 -> buildData(new CarTelemetryData2.CarTelemetryData26(byteBuffer));
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    private CarTelemetryData2 buildData(CarTelemetryData2.CarTelemetryData26 c26) {
        return new CarTelemetryData2(c26.activeAeroMode(), c26.activeAeroAvailable(), c26.activeAeroActivationDistance(), c26.overtakeAvailable(), c26.overtakeActive(), c26.overtakeActivationDistance(), c26.twentySixRegulations(), c26.drivingWrongWay());
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2026.getYear();
    }
}

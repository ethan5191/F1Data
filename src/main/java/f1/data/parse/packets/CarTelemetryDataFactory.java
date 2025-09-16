package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class CarTelemetryDataFactory implements DataFactory<CarTelemetryData> {

    private final SupportedYearsEnum packetFormat;

    public CarTelemetryDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public CarTelemetryData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2019, F1_2020 ->
                    buildData(new CarTelemetryData.CarTelemetryData19(this.packetFormat, byteBuffer));
            case F1_2021, F1_2022, F1_2023, F1_2024, F1_2025 ->
                    buildData(new CarTelemetryData.CarTelemetryData21(byteBuffer));
        };
    }

    private CarTelemetryData buildData(CarTelemetryData.CarTelemetryData19 c19) {
        return new CarTelemetryData(c19.speed(), c19.throttle(), c19.steer(), c19.brake(), c19.clutch(), c19.gear(), c19.engineRPM(),
                c19.drs(), c19.revLightPercent(), c19.brakeTemps(), c19.tireSurfaceTemps(), c19.tireInnerTemps(), c19.engineTemp(),
                c19.tirePressure(), c19.surfaceType(), 0);
    }

    private CarTelemetryData buildData(CarTelemetryData.CarTelemetryData21 c21) {
        return new CarTelemetryData(c21.speed(), c21.throttle(), c21.steer(), c21.brake(), c21.clutch(), c21.gear(), c21.engineRPM(),
                c21.drs(), c21.revLightPercent(), c21.brakeTemps(), c21.tireSurfaceTemps(), c21.tireInnerTemps(), c21.engineTemp(),
                c21.tirePressure(), c21.surfaceType(), c21.revLightBitVal());
    }
}

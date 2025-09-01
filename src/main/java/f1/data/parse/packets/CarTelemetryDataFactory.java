package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarTelemetryDataFactory {

    public static CarTelemetryData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2019, Constants.YEAR_2020:
                CarTelemetryData.CarTelemetryData19 c19 = new CarTelemetryData.CarTelemetryData19(packetFormat, byteBuffer);
                yield new CarTelemetryData(c19.speed(), c19.throttle(), c19.steer(), c19.brake(), c19.clutch(), c19.gear(), c19.engineRPM(),
                        c19.drs(), c19.revLightPercent(), c19.brakeTemps(), c19.tireSurfaceTemps(), c19.tireInnerTemps(), c19.engineTemp(),
                        c19.tirePressure(), c19.surfaceType(), 0);
            case Constants.YEAR_2021, Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024,
                 Constants.YEAR_2025:
                CarTelemetryData.CarTelemetryData21 c21 = new CarTelemetryData.CarTelemetryData21(byteBuffer);
                yield new CarTelemetryData(c21.speed(), c21.throttle(), c21.steer(), c21.brake(), c21.clutch(), c21.gear(), c21.engineRPM(),
                        c21.drs(), c21.revLightPercent(), c21.brakeTemps(), c21.tireSurfaceTemps(), c21.tireInnerTemps(), c21.engineTemp(),
                        c21.tirePressure(), c21.surfaceType(), c21.revLightBitVal());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

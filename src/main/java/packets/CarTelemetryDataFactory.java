package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarTelemetryDataFactory {

    public static CarTelemetryData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2020:
                CarTelemetryData.CarTelemetryData20 c20 = new CarTelemetryData.CarTelemetryData20(byteBuffer);
                yield new CarTelemetryData(c20.speed(), c20.throttle(), c20.steer(), c20.brake(), c20.clutch(), c20.gear(), c20.engineRPM(),
                        c20.drs(), c20.revLightPercent(), c20.brakeTemps(), c20.tireSurfaceTemps(), c20.tireInnerTemps(), c20.engineTemp(),
                        c20.tirePressure(), c20.surfaceType(), 0);
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

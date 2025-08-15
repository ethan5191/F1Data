package packets.events;

import utils.constants.Constants;

import java.nio.ByteBuffer;

public class SpeedTrapDataFactory {

    public static SpeedTrapData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2020:
                SpeedTrapData.SpeedTrapData20 s20 = new SpeedTrapData.SpeedTrapData20(byteBuffer);
                yield new SpeedTrapData(s20.vehicleId(), s20.speed(), 0, 0, 0, 0);
            case Constants.YEAR_2021:
                SpeedTrapData.SpeedTrapData21 s21 = new SpeedTrapData.SpeedTrapData21(byteBuffer);
                yield new SpeedTrapData(s21.vehicleId(), s21.speed(), s21.isOverallFastest(), s21.isDriverFastest(), 0, 0);
            case Constants.YEAR_2022, Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025:
                SpeedTrapData.SpeedTrapData22 s22 = new SpeedTrapData.SpeedTrapData22(byteBuffer);
                yield new SpeedTrapData(s22.vehicleId(), s22.speed(), s22.isOverallFastest(), s22.isDriverFastest(), s22.fastestVehicleId(), s22.fastestSpeed());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

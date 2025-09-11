package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class MotionDataFactory {

    public static MotionData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2019, Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022,
                 Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025 -> new MotionData(byteBuffer);
            default ->
                    throw new IllegalStateException("Games Packet Format did not match an accepted format (2019 - 2025)");
        };
    }
}

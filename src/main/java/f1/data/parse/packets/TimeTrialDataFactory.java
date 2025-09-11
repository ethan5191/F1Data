package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class TimeTrialDataFactory implements DataFactory<TimeTrialData> {

    private final SupportedYearsEnum packetFormat;

    public TimeTrialDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public TimeTrialData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2024, F1_2025 -> new TimeTrialData(byteBuffer);
            default ->
                    throw new IllegalStateException("Games Packet Format did not match an accepted format (2024 - 2025)");
        };
    }
}

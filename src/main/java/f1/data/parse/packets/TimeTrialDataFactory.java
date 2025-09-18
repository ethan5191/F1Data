package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;

import java.nio.ByteBuffer;

public class TimeTrialDataFactory implements DataFactory<TimeTrialData>, FirstYearProvided {

    private final SupportedYearsEnum packetFormat;

    public TimeTrialDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public TimeTrialData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2024, F1_2025 -> new TimeTrialData(byteBuffer);
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2024.getYear();
    }
}

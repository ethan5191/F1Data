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
            case F1_2024, F1_2025 -> buildData(new TimeTrialData.TimeTrialData24(byteBuffer));
            default ->
                    throw new IllegalStateException(SupportedYearsEnum.buildErrorMessageFromYear(getFirstYear()));
        };
    }

    @Override
    public int getFirstYear() {
        return SupportedYearsEnum.F1_2024.getYear();
    }

    private TimeTrialData buildData(TimeTrialData.TimeTrialData24 ttd24) {
        return new TimeTrialData(ttd24.carIndex(), ttd24.teamId(), ttd24.lapTimeInMS(), ttd24.sector1TimeInMS(), ttd24.sector2TimeInMS(), ttd24.sector3TimeInMS(), ttd24.tractionControl(), ttd24.gearboxAssist(), ttd24.antiLockBrakes(), ttd24.equalCarPerformance(), ttd24.customSetup(), ttd24.valid());
    }
}

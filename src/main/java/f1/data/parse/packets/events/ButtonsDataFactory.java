package f1.data.parse.packets.events;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;

import java.nio.ByteBuffer;

public class ButtonsDataFactory implements DataFactory<ButtonsData> {

    private final SupportedYearsEnum packetFormat;

    public ButtonsDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public ButtonsData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2019, F1_2020, F1_2021, F1_2022, F1_2023, F1_2024, F1_2025 -> new ButtonsData(byteBuffer);
        };
    }
}

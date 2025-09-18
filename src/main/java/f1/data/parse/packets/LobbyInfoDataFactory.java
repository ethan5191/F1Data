package f1.data.parse.packets;

import f1.data.enums.SupportedYearsEnum;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class LobbyInfoDataFactory implements DataFactory<LobbyInfoData> {

    private final SupportedYearsEnum packetFormat;

    public LobbyInfoDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public LobbyInfoData build(ByteBuffer byteBuffer) {
        int nameLength = (this.packetFormat.is2024OrEarlier()) ? 48 : 32;
        return switch (packetFormat) {
            case F1_2020 -> buildData(new LobbyInfoData.LobbyInfoData20(byteBuffer, nameLength));
            case F1_2021, F1_2022 -> buildData(new LobbyInfoData.LobbyInfoData21(byteBuffer, nameLength));
            case F1_2023 -> buildData(new LobbyInfoData.LobbyInfoData23(byteBuffer, nameLength));
            case F1_2024, F1_2025 -> buildData(new LobbyInfoData.LobbyInfoData24(byteBuffer, nameLength));
            default ->
                    throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }

    private LobbyInfoData buildData(LobbyInfoData.LobbyInfoData20 lid20) {
        return new LobbyInfoData(lid20.aiControlled(), lid20.teamId(), lid20.nationality(), lid20.name(), lid20.readyStatus(), 0, 0, 0, 0, 0);
    }

    private LobbyInfoData buildData(LobbyInfoData.LobbyInfoData21 lid21) {
        return new LobbyInfoData(lid21.aiControlled(), lid21.teamId(), lid21.nationality(), lid21.name(), lid21.readyStatus(), lid21.carNumber(), 0, 0, 0, 0);
    }

    private LobbyInfoData buildData(LobbyInfoData.LobbyInfoData23 lid23) {
        return new LobbyInfoData(lid23.aiControlled(), lid23.teamId(), lid23.nationality(), lid23.name(), lid23.readyStatus(), lid23.carNumber(), lid23.platform(), 0, 0, 0);
    }

    private LobbyInfoData buildData(LobbyInfoData.LobbyInfoData24 lid24) {
        return new LobbyInfoData(lid24.aiControlled(), lid24.teamId(), lid24.nationality(), lid24.name(), lid24.readyStatus(), lid24.carNumber(), lid24.platform(), lid24.yourTelemetry(), lid24.showOnlineNames(), lid24.techLevel());
    }
}

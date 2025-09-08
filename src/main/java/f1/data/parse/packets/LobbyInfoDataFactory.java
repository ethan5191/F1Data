package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class LobbyInfoDataFactory {

    public static LobbyInfoData build(int packetFormat, ByteBuffer byteBuffer) {
        int nameLength = (packetFormat < Constants.YEAR_2025) ? 48 : 32;
        return switch (packetFormat) {
            case Constants.YEAR_2020:
                LobbyInfoData.LobbyInfoData20 lid20 = new LobbyInfoData.LobbyInfoData20(byteBuffer, nameLength);
                yield new LobbyInfoData(lid20.aiControlled(), lid20.teamId(), lid20.nationality(), lid20.name(), lid20.readyStatus(), 0, 0);
            case Constants.YEAR_2021, Constants.YEAR_2022:
                LobbyInfoData.LobbyInfoData21 lid21 = new LobbyInfoData.LobbyInfoData21(byteBuffer, nameLength);
                yield new LobbyInfoData(lid21.aiControlled(), lid21.teamId(), lid21.nationality(), lid21.name(), lid21.readyStatus(), lid21.carNumber(), 0);
            case Constants.YEAR_2023:
                LobbyInfoData.LobbyInfoData23 lid23 = new LobbyInfoData.LobbyInfoData23(byteBuffer, nameLength);
                yield new LobbyInfoData(lid23.aiControlled(), lid23.teamId(), lid23.nationality(), lid23.name(), lid23.readyStatus(), lid23.carNumber(), lid23.platform());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

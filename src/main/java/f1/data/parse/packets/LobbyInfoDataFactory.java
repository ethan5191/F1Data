package f1.data.parse.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class LobbyInfoDataFactory {

    public static LobbyInfoData build(int packetFormat, ByteBuffer byteBuffer) {
        int nameLength = (packetFormat < Constants.YEAR_2025) ? 48 : 32;
        return switch (packetFormat) {
            case Constants.YEAR_2020:
                LobbyInfoData.LobbyInfoData20 lid20 = new LobbyInfoData.LobbyInfoData20(byteBuffer, nameLength);
                yield new LobbyInfoData(lid20.aiControlled(), lid20.teamId(), lid20.nationality(), lid20.name(), lid20.readyStatus(), 0);
            case Constants.YEAR_2021:
                LobbyInfoData.LobbyInfoData21 lid21 = new LobbyInfoData.LobbyInfoData21(byteBuffer, nameLength);
                yield new LobbyInfoData(lid21.aiControlled(), lid21.teamId(), lid21.nationality(), lid21.name(), lid21.readyStatus(), lid21.carNumber());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

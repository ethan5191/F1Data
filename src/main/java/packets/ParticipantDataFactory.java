package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ParticipantDataFactory {

    public static ParticipantData build(int packetFormat, ByteBuffer byteBuffer) {
        int nameLength = (packetFormat < Constants.YEAR_2025) ? 48 : 32;
        return switch (packetFormat) {
            case Constants.YEAR_2020:
                ParticipantData.ParticipantData20 p20 = new ParticipantData.ParticipantData20(nameLength, byteBuffer);
                yield new ParticipantData(p20.aiControlled(), p20.driverId(), p20.teamId(), p20.raceNumber(), p20.nationality(),
                        p20.name(), p20.yourTelemetry(), 0, 0, 0, 0, 0, buildLastName(p20.name()));
            case Constants.YEAR_2021, Constants.YEAR_2022:
                ParticipantData.ParticipantData21 p21 = new ParticipantData.ParticipantData21(nameLength, byteBuffer);
                yield new ParticipantData(p21.aiControlled(), p21.driverId(), p21.teamId(), p21.raceNumber(), p21.nationality(),
                        p21.name(), p21.yourTelemetry(), p21.networkId(), p21.myTeam(), 0, 0, 0, buildLastName(p21.name()));
            case Constants.YEAR_2023:
                ParticipantData.ParticipantData23 p23 = new ParticipantData.ParticipantData23(nameLength, byteBuffer);
                yield new ParticipantData(p23.aiControlled(), p23.driverId(), p23.teamId(), p23.raceNumber(), p23.nationality(),
                        p23.name(), p23.yourTelemetry(), p23.networkId(), p23.myTeam(), p23.showOnlineNames(), p23.platform(), 0, buildLastName(p23.name()));
            case Constants.YEAR_2024:
                ParticipantData.ParticipantData24 p24 = new ParticipantData.ParticipantData24(nameLength, byteBuffer);
                yield new ParticipantData(p24.aiControlled(), p24.driverId(), p24.teamId(), p24.raceNumber(), p24.nationality(),
                        p24.name(), p24.yourTelemetry(), p24.networkId(), p24.myTeam(), p24.showOnlineNames(), p24.platform(), p24.techLevel(), buildLastName(p24.name()));
            case Constants.YEAR_2025:
                ParticipantData.ParticipantData25 p25 = new ParticipantData.ParticipantData25(nameLength, byteBuffer);
                yield new ParticipantData(p25.aiControlled(), p25.driverId(), p25.teamId(), p25.raceNumber(), p25.nationality(),
                        p25.name(), p25.yourTelemetry(), p25.networkId(), p25.myTeam(), p25.showOnlineNames(), p25.platform(), p25.techLevel(), buildLastName(p25.name()));
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }

    private static String buildLastName(byte[] name) {
        int length = 0;
        while (length < name.length && name[length] != 0) {
            length++;
        }
        return new String(name, 0, length, StandardCharsets.UTF_8);
    }
}

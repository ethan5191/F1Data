package f1.data.parse.packets.participant;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ParticipantDataFactory implements DataFactory<ParticipantData> {

    private final SupportedYearsEnum packetFormat;
    private final int nameLength;

    public ParticipantDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
        this.nameLength = (this.packetFormat.is2024OrEarlier()) ? 48 : 32;
    }

    public ParticipantData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2019, F1_2020 -> buildData(new ParticipantData.ParticipantData19(this.nameLength, byteBuffer));
            case F1_2021, F1_2022 -> buildData(new ParticipantData.ParticipantData21(this.nameLength, byteBuffer));
            case F1_2023 -> buildData(new ParticipantData.ParticipantData23(this.nameLength, byteBuffer));
            case F1_2024 -> buildData(new ParticipantData.ParticipantData24(this.nameLength, byteBuffer));
            case F1_2025 -> buildData(new ParticipantData.ParticipantData25(this.nameLength, byteBuffer));
        };
    }

    private ParticipantData buildData(ParticipantData.ParticipantData19 p19) {
        return new ParticipantData(p19.aiControlled(), p19.driverId(), p19.teamId(), p19.raceNumber(), p19.nationality(),
                p19.name(), p19.yourTelemetry(), 0, 0, 0, 0, 0, buildLastName(p19.name()));
    }

    private ParticipantData buildData(ParticipantData.ParticipantData21 p21) {
        return new ParticipantData(p21.aiControlled(), p21.driverId(), p21.teamId(), p21.raceNumber(), p21.nationality(),
                p21.name(), p21.yourTelemetry(), p21.networkId(), p21.myTeam(), 0, 0, 0, buildLastName(p21.name()));
    }

    private ParticipantData buildData(ParticipantData.ParticipantData23 p23) {
        return new ParticipantData(p23.aiControlled(), p23.driverId(), p23.teamId(), p23.raceNumber(), p23.nationality(),
                p23.name(), p23.yourTelemetry(), p23.networkId(), p23.myTeam(), p23.showOnlineNames(), p23.platform(), 0, buildLastName(p23.name()));
    }

    private ParticipantData buildData(ParticipantData.ParticipantData24 p24) {
        return new ParticipantData(p24.aiControlled(), p24.driverId(), p24.teamId(), p24.raceNumber(), p24.nationality(),
                p24.name(), p24.yourTelemetry(), p24.networkId(), p24.myTeam(), p24.showOnlineNames(), p24.platform(), p24.techLevel(), buildLastName(p24.name()));
    }

    private ParticipantData buildData(ParticipantData.ParticipantData25 p25) {
        return new ParticipantData(p25.aiControlled(), p25.driverId(), p25.teamId(), p25.raceNumber(), p25.nationality(),
                p25.name(), p25.yourTelemetry(), p25.networkId(), p25.myTeam(), p25.showOnlineNames(), p25.platform(), p25.techLevel(), buildLastName(p25.name()));
    }

    private String buildLastName(byte[] name) {
        int length = 0;
        while (length < name.length && name[length] != 0) {
            length++;
        }
        return new String(name, 0, length, StandardCharsets.UTF_8);
    }
}

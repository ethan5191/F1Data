package packets.parsers;

import packets.ParticipantData;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class ParticipantPacketParser {

    public static ParticipantData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        ParticipantData.Builder builder = new ParticipantData.Builder().
                setAiControlled(ParserUtils.bitMask8(byteBuffer.get()))
                .setDriverId(ParserUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= 2021) builder.setNetworkId(ParserUtils.bitMask8(byteBuffer.get()))
                .setTeamId(ParserUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= 2021) builder.setMyTeam(ParserUtils.bitMask8(byteBuffer.get()))
                .setRaceNumber(ParserUtils.bitMask8(byteBuffer.get()))
                .setNationality(ParserUtils.bitMask8(byteBuffer.get()));
        int nameMaxLength = (packetFormat < 2025) ? 48 : 32;
        byte[] tempName = new byte[nameMaxLength];
        byteBuffer.get(tempName, 0, nameMaxLength);
        builder.setName(tempName)
                .setYourTelemetry(ParserUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= 2023) {
            builder.setShowOnlineNames(ParserUtils.bitMask8(byteBuffer.get()));
            if (packetFormat >= 2024) builder.setTechLevel(byteBuffer.getShort() & Constants.BIT_MASK_16);
            builder.setPlatform(ParserUtils.bitMask8(byteBuffer.get()));
        }
        if (packetFormat >= 2025) {
            //num colors and LiveryColor will be here, they were added in 2025, along with shrinking the name.
        }
        return builder.build();
    }
}

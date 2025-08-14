package packets.parsers;

import packets.ParticipantData;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class ParticipantPacketParser {

    public static ParticipantData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        ParticipantData.Builder builder = new ParticipantData.Builder()
                .setAiControlled(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setDriverId(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2021) builder.setNetworkId(BitMaskUtils.bitMask8(byteBuffer.get()));
        builder.setTeamId(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2021) builder.setMyTeam(BitMaskUtils.bitMask8(byteBuffer.get()));
        builder.setRaceNumber(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setNationality(BitMaskUtils.bitMask8(byteBuffer.get()));
        int nameMaxLength = (packetFormat < Constants.YEAR_2025) ? 48 : 32;
        byte[] tempName = new byte[nameMaxLength];
        byteBuffer.get(tempName, 0, nameMaxLength);
        builder.setName(tempName)
                .setYourTelemetry(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2023) {
            builder.setShowOnlineNames(BitMaskUtils.bitMask8(byteBuffer.get()));
            if (packetFormat >= Constants.YEAR_2024)
                builder.setTechLevel(byteBuffer.getShort() & Constants.BIT_MASK_16);
            builder.setPlatform(BitMaskUtils.bitMask8(byteBuffer.get()));
        }
        if (packetFormat >= Constants.YEAR_2025) {
            //num colors and LiveryColor will be here, they were added in 2025, along with shrinking the name.
        }
        return builder.build();
    }
}

package packets.parsers;

import packets.ParticipantData;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class ParticipantPacketParser {

    public static ParticipantData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        ParticipantData.Builder builder = new ParticipantData.Builder();
        builder.setAiControlled(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setDriverId(byteBuffer.get() & Constants.BIT_MASK_8);
        if (packetFormat >= 2021) builder.setNetworkId(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setTeamId(byteBuffer.get() & Constants.BIT_MASK_8);
        if (packetFormat >= 2021) builder.setMyTeam(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setRaceNumber(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setNationality(byteBuffer.get() & Constants.BIT_MASK_8);
        int nameMaxLength = (packetFormat < 2025) ? 48 : 32;
        byte[] tempName = new byte[nameMaxLength];
        byteBuffer.get(tempName, 0, nameMaxLength);
        builder.setName(tempName);
        builder.setYourTelemetry(byteBuffer.get() & Constants.BIT_MASK_8);
        if (packetFormat >= 2023) {
            builder.setShowOnlineNames(byteBuffer.get() & Constants.BIT_MASK_8);
            if (packetFormat >= 2024) builder.setTechLevel(byteBuffer.getShort() & Constants.BIT_MASK_16);
            builder.setPlatform(byteBuffer.get() & Constants.BIT_MASK_8);
        }
        if (packetFormat >= 2025) {
            //num colors and LiveryColor will be here, they were added in 2025, along with shrinking the name.
        }
        return builder.build();
    }
}

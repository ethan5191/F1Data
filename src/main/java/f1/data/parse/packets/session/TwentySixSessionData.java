package f1.data.parse.packets.session;

import f1.data.parse.packets.PacketUtils;
import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

//Not a struct, houses the new params that were added to the SessionData struct in the 2026 DLC.
public record TwentySixSessionData(int activeAeroTrackStatus, int numActiveAeroZonesFull, ActiveAeroZoneData[] activeAeroZonesFull,
                                   int numActiveAeroZonesPartial, ActiveAeroZoneData[] activeAeroZonesPartial, int numDrsZones,
                                   DrsZoneData[] drsZoneData, float startReactionTime, int antiLockBrakesAssist,
                                   int tractionControlAssist, int dynamicRacingLineHiVis, int dynamicRacingLineColourBlind,
                                   int recurringReviewPrompt) {

    public static ActiveAeroZoneData[] buildActiveAeroZoneData(int packetFormat, ByteBuffer byteBuffer) {
        return new ActiveAeroZoneDataFactory(packetFormat).build(byteBuffer);
    }

    public static DrsZoneData[] buildDrsZoneData(int packetFormat, ByteBuffer byteBuffer) {
        return new DrsZoneDataFactory(packetFormat).build(byteBuffer);
    }

    public TwentySixSessionData(int packetFormat, ByteBuffer byteBuffer) {
        this(
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                buildActiveAeroZoneData(packetFormat, byteBuffer),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                buildActiveAeroZoneData(packetFormat, byteBuffer),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                buildDrsZoneData(packetFormat, byteBuffer),
                PacketUtils.determineFloatValue(byteBuffer.getFloat()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get())
        );
    }
}

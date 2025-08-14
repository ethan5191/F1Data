package packets.parsers;

import packets.LapData;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class LapDataPacketParser {

    public static LapData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        //Params that changed datatype in 2021 packet updates.
        float lastLapTime20 = 0.0f;
        float currentLapTime20 = 0.0f;
        //Params that where removed in the 2021 packet updates.
        float bestLapTime = 0.0f;
        int bestLapNum = 0;
        int bestLapSector1InMS = 0;
        int bestLapSector2InMS = 0;
        int bestLapSector3InMS = 0;
        int bestOverallSector1InMS = 0;
        int bestOverallSector1LapNum = 0;
        int bestOverallSector2InMS = 0;
        int bestOverallSector2LapNum = 0;
        int bestOverallSector3InMS = 0;
        int bestOverallSector3LapNum = 0;
        //Replaced in 2023 by the totalWarnings and cornerCuttingWarnings params.
        int warnings = 0;
        LapData.Builder builder = new LapData.Builder();
        if (packetFormat <= Constants.YEAR_2020) {
            lastLapTime20 = byteBuffer.getFloat();
            currentLapTime20 = byteBuffer.getFloat();
        } else {
            builder.setLastLapTimeMs(BitMaskUtils.bitMask32(byteBuffer.getInt()))
                    .setCurrentLapTimeMs(BitMaskUtils.bitMask32(byteBuffer.getInt()));
        }
        builder.setSector1TimeMsPart(BitMaskUtils.bitMask16(byteBuffer.getShort()));
        if (packetFormat >= Constants.YEAR_2023) builder.setSector1TimeMinutesPart(BitMaskUtils.bitMask8(byteBuffer.get()));
        builder.setSector2TimeMsPart(BitMaskUtils.bitMask16(byteBuffer.getShort()));
        if (packetFormat >= Constants.YEAR_2023) {
            builder.setSector1TimeMinutesPart(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setDeltaCarInFrontMsPart(BitMaskUtils.bitMask16(byteBuffer.getShort()));
            if (packetFormat >= Constants.YEAR_2024) builder.setDeltaCarInFrontMinutesPart(BitMaskUtils.bitMask8(byteBuffer.get()));
            builder.setDeltaRaceLeaderMsPart(BitMaskUtils.bitMask16(byteBuffer.getShort()));
            if (packetFormat >= Constants.YEAR_2024) builder.setDeltaRaceLeaderMinutesPart(BitMaskUtils.bitMask8(byteBuffer.get()));
        }
        if (packetFormat <= Constants.YEAR_2020) {
            bestLapTime = byteBuffer.getFloat();
            bestLapNum = BitMaskUtils.bitMask8(byteBuffer.get());
            bestLapSector1InMS = BitMaskUtils.bitMask16(byteBuffer.getShort());
            bestLapSector2InMS = BitMaskUtils.bitMask16(byteBuffer.getShort());
            bestLapSector3InMS = BitMaskUtils.bitMask16(byteBuffer.getShort());
            bestOverallSector1InMS = BitMaskUtils.bitMask16(byteBuffer.getShort());
            bestOverallSector1LapNum = BitMaskUtils.bitMask8(byteBuffer.get());
            bestOverallSector2InMS = BitMaskUtils.bitMask16(byteBuffer.getShort());
            bestOverallSector2LapNum = BitMaskUtils.bitMask8(byteBuffer.get());
            bestOverallSector3InMS = BitMaskUtils.bitMask16(byteBuffer.getShort());
            bestOverallSector3LapNum = BitMaskUtils.bitMask8(byteBuffer.get());
        }
        builder.setLapDistance(byteBuffer.getFloat())
                .setTotalDistance(byteBuffer.getFloat())
                .setSafetyCarDelta(byteBuffer.getFloat())
                .setCarPosition(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setCurrentLapNum(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setPitStatus(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2021) builder.setNumPitStops(BitMaskUtils.bitMask8(byteBuffer.get()));
        builder.setSector(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setCurrentLapInvalid(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setPenalties(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat == Constants.YEAR_2021 || packetFormat == Constants.YEAR_2022) {
            warnings = BitMaskUtils.bitMask8(byteBuffer.get());
        } else if (packetFormat >= Constants.YEAR_2023) {
             builder.setTotalWarnings(BitMaskUtils.bitMask8(byteBuffer.get()))
                     .setCornerCuttingWarnings(BitMaskUtils.bitMask8(byteBuffer.get()));
        }
        if (packetFormat >= Constants.YEAR_2021) {
            builder.setNumUnservedDriveThroughPens(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setNumUnservedStopGoPens(BitMaskUtils.bitMask8(byteBuffer.get()));
        }
        builder.setGridPosition(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setDriverStatus(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setResultStatus(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2021) {
            builder.setPitLaneTimeActive(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setPitLaneTimerInLaneInMs(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                    .setPitStopTimerInMS(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                    .setPitStopShouldServePen(BitMaskUtils.bitMask8(byteBuffer.get()));
        }
        if (packetFormat >= Constants.YEAR_2023) {
            builder.setSpeedTrapFastestSpeed(byteBuffer.getFloat())
                    .setSpeedTrapFastestLap(BitMaskUtils.bitMask8(byteBuffer.get()));
        }

        //Populate the LegacyData object
        builder.setLegacyLapData(new LapData.LegacyLapData(lastLapTime20, currentLapTime20, bestLapTime, bestLapNum, bestLapSector1InMS,
                bestLapSector2InMS, bestLapSector3InMS, bestOverallSector1InMS, bestOverallSector1LapNum, bestOverallSector2InMS, bestOverallSector2LapNum,
                bestOverallSector3InMS, bestOverallSector3LapNum, warnings));
        return builder.build();
    }
}

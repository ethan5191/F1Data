package f1.data.parse.packets.session;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;

import java.nio.ByteBuffer;

public record GameModeData(int equalCarPerformance, int recoveryMode, int flashbackLimit, int surfaceType,
                           int lowFuelMode, int raceStarts, int tyreTemperature, int pitLaneTyreSim, int carDamage,
                           int carDamageRate, int collisions, int collisionsOffFirstLap, int mpUnsafePitRelease,
                           int mpOffForGriefing, int cornerCuttingStringency, int parcFermeRules, int pitStopExperience,
                           int safetyCar, int safetyCarExperience, int formationLap, int formationLapExperience,
                           int redFlags, int affectsLicenseLevelSolo, int affectsLicenseLevelMP,
                           int numSessionsInWeekend, int[] weekendStructure) {
    public GameModeData(ByteBuffer byteBuffer) {
        this(
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                ParseUtils.parseIntArray(byteBuffer, 12)
        );
    }
}

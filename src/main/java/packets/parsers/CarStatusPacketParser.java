package packets.parsers;

import packets.CarStatusData;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarStatusPacketParser {

    public static CarStatusData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        CarStatusData.Builder builder = new CarStatusData.Builder()
                .setTractionControl(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setAntiLockBrakes(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFuelMix(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFrontBrakeBias(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setPitLimitStatus(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFuelInTank(byteBuffer.getFloat())
                .setFuelCapacity(byteBuffer.getFloat())
                .setFuelRemainingLaps(byteBuffer.getFloat())
                .setMaxRPM(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setIdleRPM(BitMaskUtils.bitMask16(byteBuffer.getShort()))
                .setMaxGears(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setDrsAllowed(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setDrsActivationDistance(BitMaskUtils.bitMask16(byteBuffer.getShort()));
        if (packetFormat <= Constants.YEAR_2020) {
            float[] tireWear = new float[4];
            for (int i = 0; i < 4; i++) tireWear[i] = byteBuffer.getFloat();
            builder.setTyresWear(tireWear);
        }
        builder.setActualTireCompound(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setVisualTireCompound(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setTiresAgeLaps(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat <= Constants.YEAR_2020) {
            int[] tireDamage = new int[4];
            for (int i = 0; i < 4; i++) tireDamage[i] = BitMaskUtils.bitMask8(byteBuffer.get());
            builder.setTyresDamage(tireDamage)
                    .setFrontLeftWingDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setFrontRightWingDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setRearWingDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setDrsFault(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setEngineDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setGearBoxDamage(BitMaskUtils.bitMask8(byteBuffer.get()));
        }
        builder.setVehicleFiaFlags(byteBuffer.get());
        if (packetFormat >= Constants.YEAR_2023) {
            builder.setEnginePowerICE(byteBuffer.getFloat())
                    .setEnginePowerMGUK(byteBuffer.getFloat());
        }
        builder.setErsStoreEnergy(byteBuffer.getFloat())
                .setErsDeployMode(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setErsHarvestedThisLapMGUK(byteBuffer.getFloat())
                .setErsHarvestedThisLapMGUH(byteBuffer.getFloat())
                .setErsDeployedThisLap(byteBuffer.getFloat());
        if (packetFormat >= Constants.YEAR_2021) builder.setNetworkPaused(BitMaskUtils.bitMask8(byteBuffer.get()));

        return builder.build();
    }
}

package packets.parsers;

import packets.CarStatusData;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarStatusPacketParser {

    public static CarStatusData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        CarStatusData.Builder builder = new CarStatusData.Builder()
                .setTractionControl(ParserUtils.bitMask8(byteBuffer.get()))
                .setAntiLockBrakes(ParserUtils.bitMask8(byteBuffer.get()))
                .setFuelMix(ParserUtils.bitMask8(byteBuffer.get()))
                .setFrontBrakeBias(ParserUtils.bitMask8(byteBuffer.get()))
                .setPitLimitStatus(ParserUtils.bitMask8(byteBuffer.get()))
                .setFuelInTank(byteBuffer.getFloat())
                .setFuelCapacity(byteBuffer.getFloat())
                .setFuelRemainingLaps(byteBuffer.getFloat())
                .setMaxRPM(ParserUtils.bitMask16(byteBuffer.getShort()))
                .setIdleRPM(ParserUtils.bitMask16(byteBuffer.getShort()))
                .setMaxGears(ParserUtils.bitMask8(byteBuffer.get()))
                .setDrsAllowed(ParserUtils.bitMask8(byteBuffer.get()))
                .setDrsActivationDistance(ParserUtils.bitMask16(byteBuffer.getShort()));
        if (packetFormat <= Constants.YEAR_2020) {
            float[] tireWear = new float[4];
            for (int i = 0; i < 4; i++) tireWear[i] = byteBuffer.getFloat();
            builder.setTyresWear(tireWear);
        }
        builder.setActualTireCompound(ParserUtils.bitMask8(byteBuffer.get()))
                .setVisualTireCompound(ParserUtils.bitMask8(byteBuffer.get()))
                .setTiresAgeLaps(ParserUtils.bitMask8(byteBuffer.get()));
        if (packetFormat <= Constants.YEAR_2020) {
            int[] tireDamage = new int[4];
            for (int i = 0; i < 4; i++) tireDamage[i] = ParserUtils.bitMask8(byteBuffer.get());
            builder.setTyresDamage(tireDamage)
                    .setFrontLeftWingDamage(ParserUtils.bitMask8(byteBuffer.get()))
                    .setFrontRightWingDamage(ParserUtils.bitMask8(byteBuffer.get()))
                    .setRearWingDamage(ParserUtils.bitMask8(byteBuffer.get()))
                    .setDrsFault(ParserUtils.bitMask8(byteBuffer.get()))
                    .setEngineDamage(ParserUtils.bitMask8(byteBuffer.get()))
                    .setGearBoxDamage(ParserUtils.bitMask8(byteBuffer.get()));
        }
        builder.setVehicleFiaFlags(byteBuffer.get());
        if (packetFormat >= Constants.YEAR_2023) {
            builder.setEnginePowerICE(byteBuffer.getFloat())
                    .setEnginePowerMGUK(byteBuffer.getFloat());
        }
        builder.setErsStoreEnergy(byteBuffer.getFloat())
                .setErsDeployMode(ParserUtils.bitMask8(byteBuffer.get()))
                .setErsHarvestedThisLapMGUK(byteBuffer.getFloat())
                .setErsHarvestedThisLapMGUH(byteBuffer.getFloat())
                .setErsDeployedThisLap(byteBuffer.getFloat());
        if (packetFormat >= Constants.YEAR_2021) builder.setNetworkPaused(ParserUtils.bitMask8(byteBuffer.get()));

        return builder.build();
    }
}

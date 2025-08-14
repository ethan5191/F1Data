package packets.parsers;

import packets.CarDamageData;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarDamagePacketParser {

    public static CarDamageData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        float[] tireWear = new float[4];
        int[] tireDamage = new int[4];
        int[] brakesDamage = new int[4];
        for (int i = 0; i < 4; i++) tireWear[i] = byteBuffer.getFloat();
        for (int i = 0; i < 4; i++) tireDamage[i] = BitMaskUtils.bitMask8(byteBuffer.get());
        for (int i = 0; i < 4; i++) brakesDamage[i] = BitMaskUtils.bitMask8(byteBuffer.get());
        CarDamageData.Builder builder = new CarDamageData.Builder()
                .setTyresWear(tireWear)
                .setTyresDamage(tireDamage)
                .setBrakesDamage(brakesDamage)
                .setFrontLeftWingDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFrontRightWingDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setRearWingDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFloorDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setDiffuserDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setSidepodDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setDrsFault(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2022) builder.setErsFault(BitMaskUtils.bitMask8(byteBuffer.get()));
        builder.setGearBoxDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setEngineDamage(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setEngineMGUHWear(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setEngineESWear(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setEngineCEWear(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setEngineICEWear(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setEngineMGUKWear(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setEngineTCWear(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2022) {
            builder.setEngineBlown(BitMaskUtils.bitMask8(byteBuffer.get()))
                    .setEngineSeized(BitMaskUtils.bitMask8(byteBuffer.get()));
        }
        return builder.build();
    }
}

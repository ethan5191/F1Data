package packets.parsers;

import packets.CarDamageData;

import java.nio.ByteBuffer;

public class CarDamagePacketParser {

    public static CarDamageData parsePacket(int packetFormat, ByteBuffer byteBuffer) {
        float[] tireWear = new float[4];
        int[] tireDamage = new int[4];
        int[] brakesDamage = new int[4];
        for (int i = 0; i < 4; i++) tireWear[i] = byteBuffer.getFloat();
        for (int i = 0; i < 4; i++) tireDamage[i] = ParserUtils.bitMask8(byteBuffer.get());
        for (int i = 0; i < 4; i++) brakesDamage[i] = ParserUtils.bitMask8(byteBuffer.get());
        CarDamageData.Builder builder = new CarDamageData.Builder()
                .setTyresWear(tireWear)
                .setTyresDamage(tireDamage)
                .setBrakesDamage(brakesDamage)
                .setFrontLeftWingDamage(ParserUtils.bitMask8(byteBuffer.get()))
                .setFrontRightWingDamage(ParserUtils.bitMask8(byteBuffer.get()))
                .setRearWingDamage(ParserUtils.bitMask8(byteBuffer.get()))
                .setFloorDamage(ParserUtils.bitMask8(byteBuffer.get()))
                .setDiffuserDamage(ParserUtils.bitMask8(byteBuffer.get()))
                .setSidepodDamage(ParserUtils.bitMask8(byteBuffer.get()))
                .setDrsFault(ParserUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= 2022) builder.setErsFault(ParserUtils.bitMask8(byteBuffer.get()));
        builder.setGearBoxDamage(ParserUtils.bitMask8(byteBuffer.get()))
                .setEngineDamage(ParserUtils.bitMask8(byteBuffer.get()))
                .setEngineMGUHWear(ParserUtils.bitMask8(byteBuffer.get()))
                .setEngineESWear(ParserUtils.bitMask8(byteBuffer.get()))
                .setEngineCEWear(ParserUtils.bitMask8(byteBuffer.get()))
                .setEngineICEWear(ParserUtils.bitMask8(byteBuffer.get()))
                .setEngineMGUKWear(ParserUtils.bitMask8(byteBuffer.get()))
                .setEngineTCWear(ParserUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= 2022) {
            builder.setEngineBlown(ParserUtils.bitMask8(byteBuffer.get()))
                    .setEngineSeized(ParserUtils.bitMask8(byteBuffer.get()));
        }
        return builder.build();
    }
}

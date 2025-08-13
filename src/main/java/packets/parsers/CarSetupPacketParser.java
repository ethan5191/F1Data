package packets.parsers;

import packets.CarSetupData;

import java.nio.ByteBuffer;

public class CarSetupPacketParser {

    public static CarSetupData parsePacket(int packetFormat, ByteBuffer byteBuffer, String setupName) {
        CarSetupData.Builder builder = new CarSetupData.Builder()
                .setFrontWing(ParserUtils.bitMask8(byteBuffer.get()))
                .setRearWing(ParserUtils.bitMask8(byteBuffer.get()))
                .setOnThrottle(ParserUtils.bitMask8(byteBuffer.get()))
                .setOffThrottle(ParserUtils.bitMask8(byteBuffer.get()))
                .setFrontCamber(byteBuffer.getFloat())
                .setRearCamber(byteBuffer.getFloat())
                .setFrontToe(byteBuffer.getFloat())
                .setRearToe(byteBuffer.getFloat())
                .setFrontSusp(ParserUtils.bitMask8(byteBuffer.get()))
                .setRearSusp(ParserUtils.bitMask8(byteBuffer.get()))
                .setFrontARB(ParserUtils.bitMask8(byteBuffer.get()))
                .setRearARB(ParserUtils.bitMask8(byteBuffer.get()))
                .setFrontHeight(ParserUtils.bitMask8(byteBuffer.get()))
                .setRearHeight(ParserUtils.bitMask8(byteBuffer.get()))
                .setBrakePressure(ParserUtils.bitMask8(byteBuffer.get()))
                .setBrakeBias(ParserUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= 2024) builder.setEngineBraking(ParserUtils.bitMask8(byteBuffer.get()));
        builder.setRearLeftPressure(byteBuffer.getFloat())
                .setRearRightPressure(byteBuffer.getFloat())
                .setFrontLeftPressure(byteBuffer.getFloat())
                .setFrontRightPressure(byteBuffer.getFloat())
                .setBallast(ParserUtils.bitMask8(byteBuffer.get()))
                .setFuelLoad(byteBuffer.getFloat());

        builder.setSetupName(setupName);
        return builder.build();
    }
}

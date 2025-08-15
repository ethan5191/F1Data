package packets.parsers;

import packets.CarSetupData;
import utils.BitMaskUtils;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarSetupPacketParser {

    public static CarSetupData parsePacket(int packetFormat, ByteBuffer byteBuffer, String setupName) {
        CarSetupData.Builder builder = new CarSetupData.Builder()
                .setFrontWing(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setRearWing(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setOnThrottle(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setOffThrottle(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFrontCamber(byteBuffer.getFloat())
                .setRearCamber(byteBuffer.getFloat())
                .setFrontToe(byteBuffer.getFloat())
                .setRearToe(byteBuffer.getFloat())
                .setFrontSusp(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setRearSusp(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFrontARB(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setRearARB(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFrontHeight(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setRearHeight(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setBrakePressure(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setBrakeBias(BitMaskUtils.bitMask8(byteBuffer.get()));
        if (packetFormat >= Constants.YEAR_2024) builder.setEngineBraking(BitMaskUtils.bitMask8(byteBuffer.get()));
        builder.setRearLeftPressure(byteBuffer.getFloat())
                .setRearRightPressure(byteBuffer.getFloat())
                .setFrontLeftPressure(byteBuffer.getFloat())
                .setFrontRightPressure(byteBuffer.getFloat())
                .setBallast(BitMaskUtils.bitMask8(byteBuffer.get()))
                .setFuelLoad(byteBuffer.getFloat());

        builder.setSetupName(setupName);
        return builder.build();
    }
}

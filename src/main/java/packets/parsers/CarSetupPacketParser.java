package packets.parsers;

import packets.CarSetupData;
import utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarSetupPacketParser {

    public static CarSetupData parsePacket(int packetFormat, ByteBuffer byteBuffer, String setupName) {
        CarSetupData.Builder builder = new CarSetupData.Builder();
        builder.setFrontWing(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setRearWing(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setOnThrottle(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setOffThrottle(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setFrontCamber(byteBuffer.getFloat());
        builder.setRearCamber(byteBuffer.getFloat());
        builder.setFrontToe(byteBuffer.getFloat());
        builder.setRearToe(byteBuffer.getFloat());
        builder.setFrontSusp(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setRearSusp(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setFrontARB(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setRearARB(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setFrontHeight(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setRearHeight(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setBrakePressure(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setBrakeBias(byteBuffer.get() & Constants.BIT_MASK_8);
        if (packetFormat >= 2024) builder.setEngineBraking(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setRearLeftPressure(byteBuffer.getFloat());
        builder.setRearRightPressure(byteBuffer.getFloat());
        builder.setFrontLeftPressure(byteBuffer.getFloat());
        builder.setFrontRightPressure(byteBuffer.getFloat());
        builder.setBallast(byteBuffer.get() & Constants.BIT_MASK_8);
        builder.setFuelLoad(byteBuffer.getFloat());

        builder.setSetupName(setupName);
        return builder.build();
    }
}

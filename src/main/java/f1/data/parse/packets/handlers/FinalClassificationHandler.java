package f1.data.parse.packets.handlers;

import f1.data.parse.packets.FinalClassificationData;
import f1.data.parse.packets.FinalClassificationDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class FinalClassificationHandler implements PacketHandler {

    private final int packetFormat;
    private int numCars;
    private final List<FinalClassificationData> fcdList = new ArrayList<>();

    public FinalClassificationHandler(int packetFormat) {
        this.packetFormat = packetFormat;
    }

    public void processPacket(ByteBuffer byteBuffer) {
        this.numCars = BitMaskUtils.bitMask8(byteBuffer.get());
        int arraySize = Util.findArraySize(this.packetFormat);
        for (int i = 0; i < arraySize; i++) {
            FinalClassificationData fcd = FinalClassificationDataFactory.build(this.packetFormat, byteBuffer);
            this.fcdList.add(fcd);
        }
    }
}

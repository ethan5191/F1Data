package f1.data.parse.packets.handlers;

import f1.data.parse.packets.LobbyInfoData;
import f1.data.parse.packets.LobbyInfoDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class LobbyInfoPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final LobbyInfoDataFactory factory;
    private int numCars;
    private final List<LobbyInfoData> lidList = new ArrayList<>();

    public LobbyInfoPacketHandler(int packetFormat) {
        this.packetFormat = packetFormat;
        this.factory = new LobbyInfoDataFactory(this.packetFormat);
    }

    public void processPacket(ByteBuffer byteBuffer) {
        this.numCars = BitMaskUtils.bitMask8(byteBuffer.get());
        int arraySize = Util.findArraySize(this.packetFormat);
        for (int i = 0; i < arraySize; i++) {
            LobbyInfoData lid = factory.build(byteBuffer);
            lidList.add(lid);
        }
    }
}

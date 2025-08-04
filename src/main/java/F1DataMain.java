import packets.PacketHeader;
import packets.ParticipantData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class F1DataMain {

    public static void main(String[] args) {
        int port = Constants.PORT_NUM;
        byte[] buffer = new byte[2048];
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            Map<Integer, ParticipantData> participants = new HashMap<>();
            while (true) {
                socket.receive(packet);
                int length = packet.getLength();
                ByteBuffer byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(buffer, 0, length));
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                PacketHeader ph = new PacketHeader(byteBuffer);
//                if (ph.getPacketId() == Constants.MOTION_PACK) {
//                    packets.MotionData md = new packets.MotionData(byteBuffer, ph.getPlayerCarIndex());
//                    System.out.println("Forward X " + md.getWorldForwardDirX() + " Forward Y " + md.getWorldForwardDirY() + " Forward Z " + md.getWorldForwardDirZ());
//                }
                if (ph.getPacketId() == Constants.PARTICIPANTS_PACK && participants.isEmpty()) {
                    int numActiveCars = byteBuffer.get();
                    for (int i = 0; i < numActiveCars; i++) {
                        ParticipantData pd = new ParticipantData(byteBuffer);
                        pd.printName();
                        participants.put((int) pd.getDriverId(), pd);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

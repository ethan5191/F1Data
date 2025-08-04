import packets.CarSetupData;
import packets.LapData;
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
                if (ph.getPacketId() == Constants.LAP_DATA_PACK && !participants.isEmpty()) {
                    for (int i = 0; i < 22; i++) {
                        LapData ld = new LapData(byteBuffer);
                        if (validKey(participants, i)) {
                            System.out.println(ld.getCurrentLapNum() + " " + ld.getSpeedTrapFastestSpeed() + " " + ld.getCarPosition() + " " + ld.getLapDistance() + " " + ld.getTotalDistance());
                        }
                    }
                    //Time trail params at the end of the Lap Data packet. Only there a single time, therefore they are outside of the loop.
//                    byte timeTrailPBCarId = byteBuffer.get();
//                    byte timeTrailRivalPdCarId = byteBuffer.get();
                } else if (ph.getPacketId() == Constants.CAR_SETUP_PACK && !participants.isEmpty()) {
                    for (int i = 0; i < 22; i++) {
                        CarSetupData csd = new CarSetupData(byteBuffer);
                        if (validKey(participants, i)) {
                            System.out.println("I " + i + " Front Wing " + csd.getFrontWing() + " Rear " + csd.getRearWing());
                        }
                    }
                } else if (ph.getPacketId() == Constants.PARTICIPANTS_PACK && participants.isEmpty()) {
                    int index = 0;
                    //DO NOT DELETE THIS LINE, you will break the logic below it, we have to move the position with the .get() for the logic to work.
                    int numActiveCars = byteBuffer.get();
                    for (int i = 0; i < 22; i++) {
                        ParticipantData pd = new ParticipantData(byteBuffer);
                        if (pd.getRaceNumber() > 0) {
                            pd.printName();
                            participants.put(index, pd);
                        }
                        //CRUCIAL!!!!!!! index value ensures that the drivers will be mapped to the correct lap data.
                        index++;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Checks if the map of participants(drivers in session) contains the id we are looking for. Prevents extra ids for custom team from printing stuff when they have no data.
    private static boolean validKey(Map<Integer, ParticipantData> participants, int i) {
        return participants.containsKey(i);
    }
}

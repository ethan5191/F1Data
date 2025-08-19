package f1.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class F1PacketProcessor {

    private static final Logger logger = LoggerFactory.getLogger(F1PacketProcessor.class);

    private final BlockingQueue<DatagramPacket> packetQueue;
    private final DatagramSocket socket;
    private volatile boolean isRunning = true;
    private Thread receiverThread;

    public F1PacketProcessor(int port, int queueSize) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.packetQueue = new LinkedBlockingQueue<>(queueSize);
    }

    public void start() {
        receiverThread = new Thread(this::receivePackets, "F1-PacketReceiver");
        receiverThread.setDaemon(true);
        receiverThread.start();
    }

    public DatagramPacket getNextPacket() throws InterruptedException {
        return packetQueue.take(); // Blocks until packet available
    }

    private void receivePackets() {
        byte[] buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (isRunning) {
            try {
                socket.receive(packet);
                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
                DatagramPacket queuePacket = new DatagramPacket(data, data.length);
                if (!packetQueue.offer(queuePacket)) {
                    System.err.println("Packet queue full! Dropping packet");
                }
            } catch (IOException e) {
                if (isRunning) logger.error("Caught exception ", e);
            }
        }
    }

    public void stop() {
        isRunning = false;
        socket.close();
        if (receiverThread != null) receiverThread.interrupt();
    }
}

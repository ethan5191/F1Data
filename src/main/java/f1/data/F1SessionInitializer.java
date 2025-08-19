package f1.data;

import f1.data.packets.PacketHeader;
import f1.data.packets.PacketHeaderFactory;
import f1.data.packets.ParticipantData;
import f1.data.packets.ParticipantDataFactory;
import f1.data.packets.session.SessionData;
import f1.data.packets.session.SessionDataFactory;
import f1.data.ui.home.HomePanel;
import f1.data.utils.constants.Constants;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class F1SessionInitializer {

    private final F1PacketProcessor packetProcessor;
    private final Label infoLabel;

    public F1SessionInitializer(F1PacketProcessor packetProcessor, HomePanel homePanel) {
        this.packetProcessor = packetProcessor;
        this.infoLabel = (Label) homePanel.getContainer().getChildren().get(0);
    }

    public void startInitializationWithCallback(Consumer<SessionInitializationResult> callback) {
        //Create atomic references that are thread safe.
        AtomicReference<SessionData> sessionRef = new AtomicReference<>();
        AtomicReference<List<ParticipantData>> participantsRef = new AtomicReference<>();
        AtomicReference<Integer> numActiveCars = new AtomicReference<>();
        showInProgress();
        //create this logic on its independent thread to ensure that the UI logic still runs while waiting on
        //the session and participants packets to load.
        Thread initThread = new Thread(() -> {
            while (sessionRef.get() == null || participantsRef.get() == null) {
                try {
                    DatagramPacket packet = packetProcessor.getNextPacket();
                    ByteBuffer buffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
                    buffer.order(ByteOrder.LITTLE_ENDIAN);
                    PacketHeader ph = PacketHeaderFactory.build(buffer);
                    //If its the correct packet and that ref is null, we need to parse the packet, and store the object in the ref.
                    if (ph.packetId() == Constants.SESSION_PACK && sessionRef.get() == null) {
                        SessionData sd = SessionDataFactory.build(ph.packetFormat(), buffer);
                        sessionRef.set(sd);
                    } else if (ph.packetId() == Constants.PARTICIPANTS_PACK && participantsRef.get() == null) {
                        numActiveCars.set((int) buffer.get());
                        List<ParticipantData> participants = new ArrayList<>();
                        for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                            ParticipantData pd = ParticipantDataFactory.build(ph.packetFormat(), buffer);
                            if (pd.raceNumber() > 0) {
                                participants.add(pd);
                            }
                        }
                        participantsRef.set(participants);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            //We have both packets we need, build the result object
            SessionInitializationResult result = new SessionInitializationResult(
                    sessionRef.get(), participantsRef.get(), numActiveCars.get());
            //Hides the progress dialog, I might make it post an 'All Packets Loaded' message instead of hiding it.
            Platform.runLater(() -> {
                packetsLoaded();
                callback.accept(result);
            });
        });
        //Start the thread.
        initThread.setDaemon(true);
        initThread.start();
    }

    private void showInProgress() {
        this.infoLabel.setText("Waiting for Session and Participant packets.");
    }

    private void packetsLoaded() {
        this.infoLabel.setText("Session and Participants packets loaded.");
    }
}

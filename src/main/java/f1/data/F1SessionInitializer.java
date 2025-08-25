package f1.data;

import f1.data.packets.PacketHeader;
import f1.data.packets.PacketHeaderFactory;
import f1.data.packets.ParticipantData;
import f1.data.packets.ParticipantDataFactory;
import f1.data.enums.DriverPairingsEnum;
import f1.data.packets.session.SessionData;
import f1.data.packets.session.SessionDataFactory;
import f1.data.ui.home.HomePanel;
import f1.data.utils.constants.Constants;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class F1SessionInitializer {

    private final F1PacketProcessor packetProcessor;
    private final VBox container;

    public F1SessionInitializer(F1PacketProcessor packetProcessor, HomePanel homePanel) {
        this.packetProcessor = packetProcessor;
        this.container = homePanel.getContainer();
    }

    public void startInitializationWithCallback(Consumer<SessionInitializationResult> callback) {
        //Create atomic references that are thread safe.
        AtomicReference<SessionData> sessionRef = new AtomicReference<>();
        AtomicReference<List<ParticipantData>> participantsRef = new AtomicReference<>();
        AtomicReference<Integer> numActiveCars = new AtomicReference<>();
        AtomicReference<Integer> packetFormat = new AtomicReference<>();
        AtomicReference<Integer> playerCarIndex = new AtomicReference<>();
        AtomicReference<DriverPairingsEnum> driverPairings = new AtomicReference<>();
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
                    //This data should be on the very first packetHeader that we get regardless of what packet it is.
                    //Depending on the order of the packets, player car index can come across as 0, even when that's not its true value.
                    //So if it gets set to 0, but then the value is different on a subsequent packet, update it. Once it's a non-zero number it doesn't change in a session.
                    if (playerCarIndex.get() == null || playerCarIndex.get() != ph.playerCarIndex()) {
                        packetFormat.set(ph.packetFormat());
                        playerCarIndex.set(ph.playerCarIndex());
                        driverPairings.set(DriverPairingsEnum.fromYear(ph.packetFormat()));
                    }
                    //If its the correct packet and that ref is null, we need to parse the packet, and store the object in the ref.
                    if (ph.packetId() == Constants.SESSION_PACK && sessionRef.get() == null) {
                        SessionData sd = SessionDataFactory.build(ph.packetFormat(), buffer);
                        sessionRef.set(sd);
                    } else if (ph.packetId() == Constants.PARTICIPANTS_PACK && participantsRef.get() == null) {
                        //Must process this first as its always above the actual packet content, at least from 2020 onwards.
                        numActiveCars.set((int) buffer.get());
                        List<ParticipantData> participants = new ArrayList<>();
                        //Loop over the packet and create objects for each record in the array.
                        for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                            ParticipantData pd = ParticipantDataFactory.build(ph.packetFormat(), buffer);
                            //If race number isn't greater than 0 then its not an actual participant but a placeholder, so don't add to the list.
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
            SessionInitializationResult result = new SessionInitializationResult(sessionRef.get(), participantsRef.get(), driverPairings.get(), numActiveCars.get(), playerCarIndex.get(), packetFormat.get());
            //Hides the progress dialog, I might make it post an 'All Packets Loaded' message instead of hiding it.
            Platform.runLater(() -> {
                packetsLoaded(packetFormat.get());
                callback.accept(result);
            });
        });
        //Start the thread.
        initThread.setDaemon(true);
        initThread.start();
    }

    private void showInProgress() {
        ((Label) this.container.getChildren().get(0)).setText("Waiting for Session and Participant packets.");
    }

    private void packetsLoaded(Integer packetFormat) {
        ((Label) this.container.getChildren().get(0)).setText("F1 " + packetFormat);
        for (int i = 1; i < this.container.getChildren().size(); i++) {
            if (this.container.getChildren().get(i) instanceof CheckBox) {
                ((CheckBox) this.container.getChildren().get(i)).setDisable(false);
            }
        }
    }
}

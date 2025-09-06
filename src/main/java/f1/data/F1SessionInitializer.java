package f1.data;

import f1.data.mapKeys.DriverPair;
import f1.data.parse.F1PacketProcessor;
import f1.data.parse.packets.PacketHeader;
import f1.data.parse.packets.PacketHeaderFactory;
import f1.data.parse.packets.ParticipantData;
import f1.data.parse.packets.ParticipantDataFactory;
import f1.data.parse.packets.handlers.ParticipantPacketHandler;
import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.SessionDataFactory;
import f1.data.ui.panels.home.HomePanel;
import f1.data.utils.Util;
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
import java.util.Map;
import java.util.TreeMap;
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
        AtomicReference<Map<Integer, DriverPair>> driverPairPerTeam = new AtomicReference<>();
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
                    }
                    //If its the correct packet and that ref is null, we need to parse the packet, and store the object in the ref.
                    if (ph.packetId() == Constants.SESSION_PACK && sessionRef.get() == null) {
                        SessionData sd = SessionDataFactory.build(ph.packetFormat(), buffer);
                        sessionRef.set(sd);
                    } else if (ph.packetId() == Constants.PARTICIPANTS_PACK && participantsRef.get() == null) {
                        ParticipantPacketHandler handler = new ParticipantPacketHandler(packetFormat.get());
                        handler.processPacket(buffer);
                        numActiveCars.set(handler.getNumActiveCars());
                        driverPairPerTeam.set(handler.getDriverPairPerTeam());
                        participantsRef.set(handler.getParticipantDataList());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            //We have both packets we need, build the result object
            SessionInitializationResult result = new SessionInitializationResult(playerCarIndex.get(), packetFormat.get(), numActiveCars.get(), driverPairPerTeam.get(), participantsRef.get(), sessionRef.get());
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
        if (packetFormat <= Constants.YEAR_2019) {
            ((Label) this.container.getChildren().get(0)).setText("F1 " + packetFormat + " (No Speed Trap data in this year).");
        } else {
            ((Label) this.container.getChildren().get(0)).setText("F1 " + packetFormat);
        }
        for (int i = this.container.getChildren().size() - 1; i >= 0; i--) {
            if (this.container.getChildren().get(i) instanceof CheckBox current) {
                boolean isSpeedTrapBox = current.getText().contains("Speed Trap");
                if (packetFormat > Constants.YEAR_2019 || !isSpeedTrapBox) {
                    current.setDisable(false);
                }
            }
        }
    }
}

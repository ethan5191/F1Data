package f1.data;

import f1.data.mapKeys.DriverPair;
import f1.data.parse.F1PacketProcessor;
import f1.data.parse.packets.PacketHeader;
import f1.data.parse.packets.PacketHeaderFactory;
import f1.data.parse.packets.ParticipantData;
import f1.data.parse.packets.ParticipantDataFactory;
import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.SessionDataFactory;
import f1.data.ui.panels.home.HomePanel;
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
                        //Must process this first as its always above the actual packet content, at least from 2020 onwards.
                        numActiveCars.set((int) buffer.get());
                        List<ParticipantData> participants = new ArrayList<>();
                        //Loop over the packet and create objects for each record in the array.
                        for (int i = 0; i < Constants.F1_20_TO_25_CAR_COUNT; i++) {
                            ParticipantData pd = ParticipantDataFactory.build(ph.packetFormat(), buffer);
                            //If race number isn't greater than 0 then its not an actual participant but a placeholder, so don't add to the list.
                            if (pd.raceNumber() > 0) {
                                participants.add(pd);
                                if (driverPairPerTeam.get() != null) {
                                    Map<Integer, DriverPair> working = driverPairPerTeam.get();
                                    //if we have already added this team to the map, then just update the 2nd driver, the 1st driver is already there.
                                    if (working.containsKey(pd.teamId())) {
                                        working.get(pd.teamId()).setDriverTwo(pd.driverId());
                                    } else {
                                        //Haven't added this team yet, so create a new record in the map for team->driver pair object.
                                        working.put(pd.teamId(), new DriverPair(pd.driverId()));
                                    }
                                } else {
                                    //First pass through, create a new object and set it into the Atomic Reference.
                                    Map<Integer, DriverPair> temp = new TreeMap<>();
                                    temp.put(pd.teamId(), new DriverPair(pd.driverId()));
                                    driverPairPerTeam.set(temp);
                                }
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
        ((Label) this.container.getChildren().get(0)).setText("F1 " + packetFormat);
        for (int i = 1; i < this.container.getChildren().size(); i++) {
            if (this.container.getChildren().get(i) instanceof CheckBox) {
                ((CheckBox) this.container.getChildren().get(i)).setDisable(false);
            }
        }
    }
}

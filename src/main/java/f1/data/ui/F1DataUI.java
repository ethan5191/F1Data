package f1.data.ui;

import f1.data.F1SessionInitializer;
import f1.data.SessionInitializationResult;
import f1.data.parse.F1DataMain;
import f1.data.parse.F1PacketProcessor;
import f1.data.ui.panels.dto.*;
import f1.data.ui.panels.home.HomePanel;
import f1.data.ui.panels.stages.*;
import f1.data.ui.panels.stages.managers.*;
import f1.data.utils.constants.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final static Logger logger = LoggerFactory.getLogger(F1DataUI.class);

    private F1PacketProcessor packetProcessor;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            packetProcessor = new F1PacketProcessor(Constants.PORT_NUM, Constants.PACKET_QUEUE_SIZE);
            packetProcessor.start();
        } catch (SocketException e) {
            logger.error("Caught exception ", e);
            if (packetProcessor != null) packetProcessor.stop();
        }

        //creates the initial panel that allows for toggling of other panels.
        HomePanel home = new HomePanel(packetProcessor);

        F1SessionInitializer initializer = new F1SessionInitializer(packetProcessor, home);
        initializer.startInitializationWithCallback(initResult -> {
            AtomicBoolean isF1 = new AtomicBoolean(initResult.isF1());
            AtomicInteger playerDriverId = new AtomicInteger(initResult.getPlayerDriverId());
            AtomicInteger teamMateDriverId = new AtomicInteger(initResult.getTeamMateDriverId());
            AtomicInteger numActiveCars = new AtomicInteger(initResult.getNumActiveCars());

            //Main content panels for the different views.
            LatestLapStageManager latestLap = new LatestLapStageManager(playerDriverId.get(), teamMateDriverId.get(), initResult.getParticipantData());
            AllLapStageManager allLaps = new AllLapStageManager(playerDriverId.get(), teamMateDriverId.get());
            SetupStageManager setupData = new SetupStageManager();
            RunDataStageManager runData = new RunDataStageManager(playerDriverId.get(), teamMateDriverId.get(), isF1.get());
            SpeedTrapDataManager speedTrapData = new SpeedTrapDataManager(playerDriverId.get(), teamMateDriverId.get(), numActiveCars.get());
            TeamSpeedTrapDataManager teamSpeedTrapData = new TeamSpeedTrapDataManager(playerDriverId.get(), teamMateDriverId.get());

            Consumer<SessionChangeDTO> sessionChangeDTOConsumer = snapshot ->
            {
                Platform.runLater(() -> {
                    //Update global params
                    playerDriverId.set(snapshot.playerDriverId());
                    teamMateDriverId.set(snapshot.teamMateDriverId());
                    numActiveCars.set(snapshot.numActiveCars());

                    //All the following calls also make a call to the onSessionReset method to clear the backing objects.
                    //This is due to there being no way to verify that this logic will run before the update logic runs after the panels reset after a session change.
                    //Call common interface logic for session change
                    latestLap.onSessionChange(playerDriverId.get(), teamMateDriverId.get());
                    allLaps.onSessionChange(playerDriverId.get(), teamMateDriverId.get());
                    speedTrapData.onSessionChange(playerDriverId.get(), teamMateDriverId.get());
                    teamSpeedTrapData.onSessionChange(playerDriverId.get(), teamMateDriverId.get());
                    runData.onSessionChange(playerDriverId.get(), teamMateDriverId.get());

                    //Call panel specific logic for session change.
                    speedTrapData.onSessionChangeNumActiveCars(numActiveCars.get());
                    latestLap.onSessionChange(snapshot.participantData());
                });
            };
            //Logic for the Setup, LatestLap, and AllLap panels.
            Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
            {
                Platform.runLater(() -> {
                    latestLap.updateStage(snapshot);
                    allLaps.updateStage(snapshot);
                    setupData.updateStage(snapshot);
                    runData.updateStage(snapshot);
                });
            };
            //Logic for the speed trap panels.
            Consumer<SpeedTrapDataDTO> speedTrapDataDTO = snapshot ->
            {
                Platform.runLater(() -> {
                    speedTrapData.updateStage(snapshot);
                    teamSpeedTrapData.updateStage(snapshot);
                });
            };
            Consumer<SessionResetDTO> sessionResetConsumer = snapshot ->
            {
                Platform.runLater(() -> {
                    if (snapshot.newSession()) {
                        //Update global param value
                        isF1.set(snapshot.isF1());

                        //Call panel specific logic for session reset.
                        runData.onSessionChangeIsF1(isF1.get());

                        //Call interface method for session reset
                        latestLap.onSessionReset();
                        allLaps.onSessionReset();
                        setupData.onSessionReset();
                        runData.onSessionReset();
                        speedTrapData.onSessionReset();
                        teamSpeedTrapData.onSessionReset();
                    }
                });
            };

            //Call the different stage constructors.
            new LatestLapStage(stage, latestLap.getContainer());
            new AllLapDataStage(new Stage(), allLaps.getContainer());
            new SetupStage(new Stage(), setupData.getContainer());
            new SpeedTrapStage(new Stage(), speedTrapData.getContainer());
            new TeamSpeedTrapStage(new Stage(), teamSpeedTrapData.getContainer());
            new RunDataStage(new Stage(), runData.getContainer());

            ParentConsumer parent = new ParentConsumer(driverDataConsumer, speedTrapDataDTO, sessionResetConsumer, sessionChangeDTOConsumer);
            //Calls the data thread.
            callTelemetryThread(parent, initResult);
        });
    }

    //Calls the telemetry thread, which handles parsing the packets.
    private void callTelemetryThread(ParentConsumer parent, SessionInitializationResult result) {
        Thread telemetryThread = new Thread(() -> {
            new F1DataMain(packetProcessor, parent, result).run();
        });
        telemetryThread.setDaemon(true);
        telemetryThread.start();
    }

    public void run(String[] args) {
        logger.info("Starting");
        launch(args);
    }
}

package f1.data;

import f1.data.ui.dto.DriverDataDTO;
import f1.data.ui.dto.SpeedTrapDataDTO;
import f1.data.ui.home.HomePanel;
import f1.data.ui.stages.*;
import f1.data.ui.stages.managers.*;
import f1.data.utils.constants.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
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
            boolean isF1 = initResult.isF1();
            int playerDriverId = initResult.getPlayerDriverId();
            int teamMateDriverId = initResult.getTeamMateDriverId();
            int numActiveCars = initResult.getNumActiveCars();

            //Main content panels for the different views.
            LatestLapStageManager latestLap = new LatestLapStageManager(playerDriverId, teamMateDriverId);
            AllLapStageManager allLaps = new AllLapStageManager(playerDriverId, teamMateDriverId);
            SetupStageManager setupData = new SetupStageManager();
            RunDataStageManager runData = new RunDataStageManager(playerDriverId, teamMateDriverId, isF1);
            SpeedTrapDataManager speedTrapData = new SpeedTrapDataManager(playerDriverId, teamMateDriverId, numActiveCars);
            TeamSpeedTrapDataManager teamSpeedTrapData = new TeamSpeedTrapDataManager(playerDriverId, teamMateDriverId);

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

            //Call the different stage constructors.
            new LatestLapStage(stage, latestLap.getContainer());
            new AllLapDataStage(new Stage(), allLaps.getContainer());
            new SetupStage(new Stage(), setupData.getContainer());
            new SpeedTrapStage(new Stage(), speedTrapData.getContainer());
            new TeamSpeedTrapStage(new Stage(), teamSpeedTrapData.getContainer());
            new RunDataStage(new Stage(), runData.getContainer());

            //Calls the data thread.
            callTelemetryThread(driverDataConsumer, speedTrapDataDTO);
        });
    }

    //Calls the telemetry thread, which handles parsing the packets.
    private void callTelemetryThread(Consumer<DriverDataDTO> driverDataConsumer, Consumer<SpeedTrapDataDTO> speedTrapDataDTO) {
        Thread telemetryThread = new Thread(() -> {
            new F1DataMain().run(packetProcessor, driverDataConsumer, speedTrapDataDTO);
        });
        telemetryThread.setDaemon(true);
        telemetryThread.start();
    }

    public void run(String[] args) {
        logger.info("Starting");
        launch(args);
    }
}

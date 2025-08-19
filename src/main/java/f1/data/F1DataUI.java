package f1.data;

import f1.data.ui.dashboards.SpeedTrapDashboard;
import f1.data.ui.dashboards.TeamSpeedTrapDashboard;
import f1.data.ui.dto.DriverDataDTO;
import f1.data.ui.dto.SpeedTrapDataDTO;
import f1.data.ui.home.HomePanel;
import f1.data.ui.stages.*;
import f1.data.ui.stages.managers.AllLapStageManager;
import f1.data.ui.stages.managers.LatestLapStageManager;
import f1.data.ui.stages.managers.RunDataStageManager;
import f1.data.ui.stages.managers.SetupStageManager;
import f1.data.utils.constants.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final static Logger logger = LoggerFactory.getLogger(F1DataUI.class);

    private F1PacketProcessor packetProcessor;

    private final Map<Integer, SpeedTrapDashboard> speedTrapDashboard = new HashMap<>();
    private final Map<Integer, Map<Integer, TeamSpeedTrapDashboard>> latestTeamSpeedTrapDash = new HashMap<>(2);
    private final List<SpeedTrapDataDTO> speedTrapRankings = new ArrayList<>();

    private int playerDriverId = -1;
    private int teamMateId = -1;
    private boolean isF1 = false;

    @Override
    public void start(Stage stage) throws Exception {
        //creates the initial panel that allows for toggling of other panels.
        new HomePanel(packetProcessor);

        //Main content panels for the different views.
        LatestLapStageManager latestLap = new LatestLapStageManager(new VBox());
        AllLapStageManager allLaps = new AllLapStageManager(new VBox());
        SetupStageManager setupData = new SetupStageManager(new VBox());
        RunDataStageManager runData = new RunDataStageManager(new VBox());
        VBox speedTrapData = new VBox(5);
        VBox teamSpeedTrapData = new VBox(5);

        //Logic for the Setup, LatestLap, and AllLap panels.
        Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
        {
            Platform.runLater(() -> {
                latestLap.updateStage(snapshot);
                allLaps.updateStage(snapshot, latestLap.getPlayerDriverId(), latestLap.getTeamMateId());
                setupData.updateStage(snapshot);
                runData.updateStage(snapshot, latestLap.getPlayerDriverId(), latestLap.getTeamMateId(), latestLap.isF1());
            });
        };
        //Logic for the speed trap panels.
        Consumer<SpeedTrapDataDTO> speedTrapDataDTO = snapshot ->
        {
            Platform.runLater(() -> {
                buildSpeedTrapDashboard(snapshot, speedTrapData);
                buildTeamSpeedTrapDashboard(snapshot, teamSpeedTrapData);
            });
        };

        //Call the different stage constructors.
        new LatestLapStage(stage, latestLap.getContainer());
        new AllLapDataStage(new Stage(), allLaps.getContainer());
        new SetupStage(new Stage(), setupData.getContainer());
        new SpeedTrapStage(new Stage(), speedTrapData);
        new TeamSpeedTrapStage(new Stage(), teamSpeedTrapData);
        new RunDataStage(new Stage(), runData.getContainer());

        try {
            packetProcessor = new F1PacketProcessor(Constants.PORT_NUM, Constants.PACKET_QUEUE_SIZE);
            packetProcessor.start();
        } catch (SocketException e) {
            logger.error("Caught exception ", e);
            packetProcessor.stop();
        }

        //Calls the data thread.
        callTelemetryThread(driverDataConsumer, speedTrapDataDTO);
    }

    //Creates the all speed trap panel, keeps track of the order based on the fastest lap by each driver
    private void buildSpeedTrapDashboard(SpeedTrapDataDTO snapshot, VBox speedTrapData) {
        //If this is the first car through the speed trap then we need to create the initial group of containers for the data.
        //Based on the number of cars in the session will determine how many dashboards are created.
        if (speedTrapRankings.isEmpty() && speedTrapDashboard.isEmpty()) {
            //This is the only panel so far that creates all the dashboards up front, then just updates the values with each new speed trap value.
            for (int i = 0; i < snapshot.getNumActiveCars(); i++) {
                SpeedTrapDashboard dashboard = new SpeedTrapDashboard(i + 1);
                speedTrapData.getChildren().add(dashboard);
                speedTrapDashboard.put(i, dashboard);
            }
            //Add this to the list and update its map object as its the first one in.
            speedTrapRankings.add(snapshot);
            speedTrapDashboard.get(0).updateRank(snapshot);
        }
        if (!speedTrapRankings.isEmpty()) {
            //Does a check on the driver name to see if this driver already has a top speed recorded.
            int index = speedTrapRankings.indexOf(snapshot);
            //boolean to indicate if we need to reSort and redraw the data.
            boolean reSort = false;
            //If index < 0 this driver doesn't have a speed in the list, so we just add it.
            if (index < 0) {
                speedTrapRankings.add(snapshot);
                reSort = true;
            } else {
                //else he has a speed and we need to see if he went faster, if he did remove the old record and add the new one to the end.
                SpeedTrapDataDTO currentRanking = speedTrapRankings.get(index);
                if (currentRanking.getSpeed() < snapshot.getSpeed()) {
                    speedTrapRankings.remove(currentRanking);
                    speedTrapRankings.add(snapshot);
                    reSort = true;
                }
            }
            //If we need to reSort and redraw then we do it now.
            if (reSort) {
                //sort the List by the speed values before we redraw the data.
                SpeedTrapDataDTO.sortBySpeed(speedTrapRankings);
                for (int n = 0; n < speedTrapRankings.size(); n++) {
                    SpeedTrapDataDTO current = speedTrapRankings.get(n);
                    SpeedTrapDashboard currentDash = speedTrapDashboard.get(n);
                    //If its the player or there teammate, update the bacckground so they are easy to identify.
                    if (current.getDriverId() == playerDriverId || current.getDriverId() == teamMateId) {
                        currentDash.setStyle("-fx-background-color: #3e3e3e;");
                    } else {
                        currentDash.setStyle(null);
                    }
                    currentDash.updateRank(current);
                }
            }
        }
    }

    //Creates the player team speed trap panel. This panel logs every speed trap registered by the teams 2 drivers, ordered by lap#.
    private void buildTeamSpeedTrapDashboard(SpeedTrapDataDTO snapshot, VBox teamSpeedTrapData) {
        //This panel is only for the player and there teammate.
        if (snapshot.getDriverId() == playerDriverId || snapshot.getDriverId() == teamMateId) {
            TeamSpeedTrapDashboard dashboard = new TeamSpeedTrapDashboard(snapshot);
            boolean updated = false;
            //If the map doesn't contain a record for this driver then we are doin gan initial create.
            if (!latestTeamSpeedTrapDash.containsKey(snapshot.getDriverId())) {
                Map<Integer, TeamSpeedTrapDashboard> temp = new HashMap<>(1);
                temp.put(snapshot.getLapNum(), dashboard);
                latestTeamSpeedTrapDash.put(snapshot.getDriverId(), temp);
            } else {
                //Else we need to make sure that the current lap doesn't already exist for this driver.
                Map<Integer, TeamSpeedTrapDashboard> latestSpeedDash = latestTeamSpeedTrapDash.get(snapshot.getDriverId());
                //If the lap already exists in the map (driver pitted) then we just update the dashboard's trap speedd
                if (latestSpeedDash.containsKey(snapshot.getLapNum())) {
                    dashboard = latestSpeedDash.get(snapshot.getLapNum());
                    dashboard.updateSpeed(snapshot);
                    //Flip this flag, so we don't try to add this child element, as its already on the view.
                    updated = true;
                }
                latestSpeedDash.put(snapshot.getLapNum(), dashboard);
                latestTeamSpeedTrapDash.put(snapshot.getDriverId(), latestSpeedDash);
            }
            if (!updated) teamSpeedTrapData.getChildren().add(dashboard);
        }
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

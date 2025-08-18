package f1.data;

import f1.data.individualLap.IndividualLapInfo;
import f1.data.packets.enums.FormulaTypeEnum;
import f1.data.ui.RunDataAverage;
import f1.data.ui.dashboards.*;
import f1.data.ui.dto.DriverDataDTO;
import f1.data.ui.dto.SpeedTrapDataDTO;
import f1.data.ui.home.AppState;
import f1.data.ui.stages.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final static Logger logger = LoggerFactory.getLogger(F1DataUI.class);

    private final Map<Integer, LatestLapDashboard> latestLapDashboard = new HashMap<>();
    private final Map<Integer, VBox> allLapDataDashboard = new HashMap<>();
    private final Map<Integer, Map<Integer, VBox>> setupDataDashboard = new HashMap<>();
    private final Map<Integer, Map<Integer, List<RunDataDashboard>>> runDataDashboard = new HashMap<>();
    private final Map<Integer, SpeedTrapDashboard> speedTrapDashboard = new HashMap<>();
    private final Map<Integer, Map<Integer, TeamSpeedTrapDashboard>> latestTeamSpeedTrapDash = new HashMap<>(2);
    private final List<SpeedTrapDataDTO> speedTrapRankings = new ArrayList<>();

    private int playerDriverId = -1;
    private int teamMateId = -1;
    private Map<Integer, Integer> driverPairings = new HashMap<>();
    private boolean isF1 = false;

    @Override
    public void start(Stage stage) throws Exception {
        //Creates the panel that allows you to show/hide different data panels.
        createTogglePanel();

        //Main content panels for the different views.
        VBox latestLap = new VBox(5);
        VBox allLaps = new VBox(5);
        VBox setupData = new VBox(5);
        VBox speedTrapData = new VBox(5);
        VBox teamSpeedTrapData = new VBox(5);
        VBox runData = new VBox(5);

        //Logic for the Setup, LatestLap, and AllLap panels.
        Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
        {
            Platform.runLater(() -> {
                buildLatestLapBoard(snapshot, latestLap);
                buildAllLapBoard(snapshot, allLaps);
                buildSetupBoard(snapshot, setupData);
                buildRunDataBoard(snapshot, runData);
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
        new LatestLapStage(stage, latestLap);
        new AllLapDataStage(new Stage(), allLaps);
        new SetupStage(new Stage(), setupData);
        new SpeedTrapStage(new Stage(), speedTrapData);
        new TeamSpeedTrapStage(new Stage(), teamSpeedTrapData);
        new RunDataStage(new Stage(), runData);

        //Calls the data thread.
        callTelemetryThread(driverDataConsumer, speedTrapDataDTO);
    }

    //Creates the initial toggle panel that allows for showing hiding the individual panels.
    private void createTogglePanel() {
        CheckBox latestLapCheckbox = new CheckBox("Show Latest Lap Panel");
        CheckBox lapsDataCheckbox = new CheckBox("Show Laps Data Panel");
        CheckBox setupDataCheckbox = new CheckBox("Show Setup Data Panel");
        CheckBox speedTrapDataCheckbox = new CheckBox("Show All Speed Trap Panel");
        CheckBox teamSpeedTrapDataCheckbox = new CheckBox("Show Team Speed Trap Panel");
        CheckBox runDataPanelCheckbox = new CheckBox("Show the Run Data Panel");

        latestLapCheckbox.selectedProperty().bindBidirectional(AppState.latestLapPanelVisible);
        lapsDataCheckbox.selectedProperty().bindBidirectional(AppState.allLapsDataPanelVisible);
        setupDataCheckbox.selectedProperty().bindBidirectional(AppState.setupDataPanelVisible);
        speedTrapDataCheckbox.selectedProperty().bindBidirectional(AppState.speedTrapPanelVisible);
        teamSpeedTrapDataCheckbox.selectedProperty().bindBidirectional(AppState.teamSpeedTrapPanelVisible);
        runDataPanelCheckbox.selectedProperty().bindBidirectional(AppState.runDataPanelVisible);

        VBox statePanel = new VBox(10, latestLapCheckbox, lapsDataCheckbox, setupDataCheckbox, speedTrapDataCheckbox,
                teamSpeedTrapDataCheckbox, runDataPanelCheckbox);

        Scene scene = new Scene(statePanel, 200, 200);
        Stage panel = new Stage();
        panel.setScene(scene);
        panel.setOnCloseRequest(e -> {
            logger.info("Shutting Down");
            Platform.exit();
        });
        panel.show();
    }

    //Builds the latest lap panel. This is each cars last lap that they have crossed the start finish line. Not ordered.
    private void buildLatestLapBoard(DriverDataDTO snapshot, VBox latestLap) {
        //If the global map of driver pairings is empty, then we need to populate it from the DTO. It should have the map we need.
        //if driver pairings is empty then we haven't populated the formulaType either.
        if (driverPairings.isEmpty()) {
            driverPairings = snapshot.getDriverPairings();
            isF1 = FormulaTypeEnum.isF1(snapshot.getFormulaTypeEnum());
        }
        LatestLapDashboard latestLapDash = latestLapDashboard.computeIfAbsent(snapshot.getId(), id -> {
            //Creates the new dashboard
            LatestLapDashboard newDashboard = new LatestLapDashboard(snapshot.getLastName());
            //add it to the view.
            latestLap.getChildren().add(newDashboard);
            //If this is the players driver, then update the background color of this box.
            if (snapshot.isPlayer()) {
                playerDriverId = snapshot.getId();
                //Use the driverPairings param to ensure we can accommodate F1/F2/F2 previous year driver lineups.
                teamMateId = driverPairings.get(playerDriverId);
                newDashboard.setStyle("-fx-background-color: #3e3e3e;");
                //If we have already created the teammates view, update the background color
                if (latestLapDashboard.containsKey(teamMateId)) {
                    LatestLapDashboard teamMateDash = latestLapDashboard.get(teamMateId);
                    teamMateDash.setStyle("-fx-background-color: #3e3e3e;");
                }
            }
            return newDashboard;
        });
        //Make sure we have the info object, if we do then we can actually update the dashboard with data.
        if (snapshot.getInfo() != null) {
            latestLapDash.updateValues(snapshot.getInfo());
        }
    }

    //Builds the all lap panel. This panel groups all the laps each driver does in order of the lapnum.
    private void buildAllLapBoard(DriverDataDTO snapshot, VBox allLaps) {
        if (snapshot.getInfo() != null) {
            //builds out the labels for the lapdata panel (panel 2 at the moment)
            VBox driver = allLapDataDashboard.computeIfAbsent(snapshot.getId(), id -> {
                VBox temp = new VBox();
                //Add the box to the parent view.
                allLaps.getChildren().add(temp);
                //If its the driver or there teammate then update the background color.
                if (id == playerDriverId || id == teamMateId) {
                    temp.setStyle("-fx-background-color: #3e3e3e;");
                }
                return temp;
            });
            //Creates the actual dashboard
            AllLapDataDashboard allLapsDashboard = new AllLapDataDashboard(snapshot.getLastName(), snapshot.getInfo());
            //container for the laps information
            VBox lapsContainer = new VBox();
            lapsContainer.getChildren().add(allLapsDashboard);
            //add to the overall panel.
            driver.getChildren().add(lapsContainer);
        }
    }

    //Builds the carsetup panel. Right now it only shows the first setup that the driver finishes a lap with.
    private void buildSetupBoard(DriverDataDTO snapshot, VBox setupData) {
        if (snapshot.getInfo() != null) {
            //Ensures we don't duplicate records, as we only want 1 record per driver.
            if (!setupDataDashboard.containsKey(snapshot.getId())) {
                commonSetupLogic(snapshot, setupData, new HashMap<>(), snapshot.getInfo().getCarSetupData().setupName());
                //If this driver has already completed a lap with a different setup, we are adding this new setup to the map and using the lap # in the name.
            } else if (snapshot.getInfo().isSetupChange()) {
                String setupName = snapshot.getInfo().getCarSetupData().setupName() + " Lap #" + snapshot.getInfo().getLapNum();
                Map<Integer, VBox> existingSetups = setupDataDashboard.get(snapshot.getId());
                commonSetupLogic(snapshot, setupData, existingSetups, setupName);
            }
        }
    }

    //builds the runData panel. This panel shows the setup, and all laps completed with that setup.
    private void buildRunDataBoard(DriverDataDTO snapshot, VBox runData) {
        IndividualLapInfo info = snapshot.getInfo();
        if (info != null) {
            if (snapshot.getId() == playerDriverId || snapshot.getId() == teamMateId) {
                //If this is the first pass through or the setup has changed we need to do all of this.
                if (!runDataDashboard.containsKey(snapshot.getId()) || info.isSetupChange()) {
                    VBox driver = new VBox();
                    runData.getChildren().add(driver);
                    //Creates the actual dashboard
                    SetupInfoDashboard setupInfo = new SetupInfoDashboard(info.getCarSetupData().setupName(), info.getCarSetupData(), info.getCarStatusInfo().getVisualTireCompound());
                    VBox container = new VBox(3);
                    RunDataDashboard lapInfoBoard = new RunDataDashboard(snapshot, isF1);
                    Map<Integer, List<RunDataDashboard>> initial = new HashMap<>();
                    //calculate the averages and add them as a new dashboard to the end of the list.
                    RunDataAverage average = new RunDataAverage(info.getLapNum(), snapshot, isF1);
                    RunDataDashboard averages = new RunDataDashboard(average, info.isUseLegacy());
                    initial.put(info.getLapNum(), List.of(lapInfoBoard, averages));
                    runDataDashboard.put(snapshot.getId(), initial);
                    container.getChildren().add(setupInfo);
                    lapInfoBoard.createHeaderRow(container);
                    container.getChildren().add(lapInfoBoard);
                    container.getChildren().add(averages);
                    driver.getChildren().add(container);
                    //else we have this setup already done a lap, so we just need to create a new lap info
                } else {
                    Map<Integer, List<RunDataDashboard>> currentData = runDataDashboard.get(snapshot.getId());
                    //The lapnum is the key if a setup change has happened, so get the max key to get the latest setup change.
                    //a setup change will be handled by the if part of this if/else statement.
                    Optional<Integer> maxNewSetupLap = currentData.keySet().stream().max(Integer::compare);
                    Integer maxLap = maxNewSetupLap.get();
                    //Create a copy of the value in the map for this lapnum so we can add a new dashboard to the end.
                    List<RunDataDashboard> lapsForSetupCopy = new ArrayList<>(currentData.get(maxLap));
                    //get the current averages and use it to update the averages to account for a new lap completed.
                    RunDataDashboard currentAverages = lapsForSetupCopy.get(lapsForSetupCopy.size() - 1);
                    RunDataAverage updatedAverages = new RunDataAverage(maxLap, snapshot, currentAverages.getAverage());
                    RunDataDashboard newAvgDash = new RunDataDashboard(updatedAverages, info.isUseLegacy());
                    lapsForSetupCopy.add(newAvgDash);
                    //Update the previous averages line to have the new laps data in it instead of averages.
                    currentAverages.updateValues(snapshot);
                    currentData.put(maxLap, lapsForSetupCopy);
                    VBox container = (VBox) currentAverages.getParent();
                    container.getChildren().add(newAvgDash);
                }
            }
        }
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
            new F1DataMain().run(driverDataConsumer, speedTrapDataDTO);
        });
        telemetryThread.setDaemon(true);
        telemetryThread.start();
    }

    //Common logic used by the setup panel for both new entries and new setups for drivers who already have a setup listed.
    private void commonSetupLogic(DriverDataDTO snapshot, VBox setupData, Map<Integer, VBox> mapToUpdate, String setupName) {
        VBox driver = new VBox();
        setupData.getChildren().add(driver);
        //Add the box to the map so we can ensure we don't dupliate it.
        mapToUpdate.put(snapshot.getInfo().getLapNum(), driver);
        setupDataDashboard.put(snapshot.getId(), mapToUpdate);
        //Creates the actual dashboard
        SetupInfoDashboard setupInfo = new SetupInfoDashboard(setupName, snapshot.getInfo().getCarSetupData(), snapshot.getInfo().getCarStatusInfo().getVisualTireCompound());
        VBox container = new VBox(3);
        container.getChildren().add(setupInfo);
        driver.getChildren().add(container);
    }

    public void run(String[] args) {
        logger.info("Starting");
        launch(args);
    }
}

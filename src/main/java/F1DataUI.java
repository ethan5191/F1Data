import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.dashboards.AllLapDataDashboard;
import ui.dashboards.LatestLapDashboard;
import ui.dashboards.SetupInfoDashboard;
import ui.dashboards.SpeedTrapDashboard;
import ui.dto.DriverDataDTO;
import ui.dto.SpeedTrapDataDTO;
import ui.home.AppState;
import ui.stages.AllLapDataStage;
import ui.stages.LatestLapStage;
import ui.stages.SetupStage;
import ui.stages.SpeedTrapStage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final Map<Integer, LatestLapDashboard> latestLapDashboard = new HashMap<>();
    private final Map<Integer, VBox> allLapDataDashboard = new HashMap<>();
    private final Map<Integer, VBox> setupDataDashboard = new HashMap<>();
    private final Map<Integer, SpeedTrapDashboard> speedTrapDashboard = new HashMap<>();
    private final List<SpeedTrapDataDTO> speedTrapRankings = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        //Creates the panel that allows you to show/hide different data panels.
        createTogglePanel();

        VBox latestLap = new VBox(5);
        VBox allLaps = new VBox(5);
        VBox setupData = new VBox(5);
        VBox speedTrapData = new VBox(5);

        Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
        {
            Platform.runLater(() -> {
                buildLatestLapBoard(snapshot, latestLap);
                buildAllLapBoard(snapshot, allLaps);
                buildSetupBoard(snapshot, setupData);
            });
        };
        Consumer<SpeedTrapDataDTO> speedTrapDataDTO = snapshot ->
        {
            Platform.runLater(() -> {
                buildSpeedTrapDashboard(snapshot, speedTrapData);
            });
        };

        new LatestLapStage(stage, latestLap);
        new AllLapDataStage(new Stage(), allLaps);
        new SetupStage(new Stage(), setupData);
        new SpeedTrapStage(new Stage(), speedTrapData);

        callTelemetryThread(driverDataConsumer, speedTrapDataDTO);
    }

    private void createTogglePanel() {
        CheckBox latestLapCheckbox = new CheckBox("Show Latest Lap Panel");
        CheckBox lapsDataCheckbox = new CheckBox("Show Laps Data Panel");
        CheckBox setupDataCheckbox = new CheckBox("Show Setup Data Panel");
        CheckBox speedTrapDataCheckbox = new CheckBox("Show Speed Trap Panel");

        latestLapCheckbox.selectedProperty().bindBidirectional(AppState.latestLapPanelVisible);
        lapsDataCheckbox.selectedProperty().bindBidirectional(AppState.allLapsDataPanelVisible);
        setupDataCheckbox.selectedProperty().bindBidirectional(AppState.setupDataPanelVisible);
        speedTrapDataCheckbox.selectedProperty().bindBidirectional(AppState.speedTrapPanelVisible);

        VBox statePanel = new VBox(10, latestLapCheckbox, lapsDataCheckbox, setupDataCheckbox, speedTrapDataCheckbox);

        Scene scene = new Scene(statePanel, 200, 200);
        Stage panel = new Stage();
        panel.setScene(scene);
        panel.show();
    }

    private void buildLatestLapBoard(DriverDataDTO snapshot, VBox latestLap) {
        LatestLapDashboard latestLapDash = latestLapDashboard.computeIfAbsent(snapshot.getId(), id -> {
            LatestLapDashboard newDashboard = new LatestLapDashboard(snapshot.getLastName());
            latestLap.getChildren().add(newDashboard);
            return newDashboard;
        });
        if (snapshot.getInfo() != null) {
            latestLapDash.updateValues(snapshot.getInfo());
        }
    }

    private void buildAllLapBoard(DriverDataDTO snapshot, VBox allLaps) {
        if (snapshot.getInfo() != null) {
            //builds out the labels for the lapdata panel (panel 2 at the moment)
            VBox driver = allLapDataDashboard.computeIfAbsent(snapshot.getId(), id -> {
                VBox temp = new VBox();
                allLaps.getChildren().add(temp);
                return temp;
            });
            AllLapDataDashboard allLapsDashboard = new AllLapDataDashboard(snapshot.getLastName(), snapshot.getInfo());
            VBox lapsContainer = new VBox();
            lapsContainer.getChildren().add(allLapsDashboard);
            driver.getChildren().add(lapsContainer);
        }
    }

    private void buildSetupBoard(DriverDataDTO snapshot, VBox setupData) {
        if (snapshot.getInfo() != null) {
            if (!setupDataDashboard.containsKey(snapshot.getId())) {
                VBox driver = new VBox();
                setupData.getChildren().add(driver);
                setupDataDashboard.put(snapshot.getId(), driver);
                String setupName = snapshot.getInfo().getCarSetupData().getSetupName();
                SetupInfoDashboard setupInfo = new SetupInfoDashboard(setupName, snapshot.getInfo().getCarSetupData());
                VBox container = new VBox(3);
                container.getChildren().add(setupInfo);
                driver.getChildren().add(container);
            }
        }
    }

    private void buildSpeedTrapDashboard(SpeedTrapDataDTO snapshot, VBox speedTrapData) {
        //If this is the first car through the speed trap then we need to create the initial group of containers for the data.
        //Based on the number of cars in the session will determine how many dashboards are created.
        if (speedTrapRankings.isEmpty() && speedTrapDashboard.isEmpty()) {
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
            //boolean to indicate if we need to resort and redraw the data.
            boolean resort = false;
            //If index < 0 this driver doesn't have a speed in the list, so we just add it.
            if (index < 0) {
                speedTrapRankings.add(snapshot);
                resort = true;
            } else {
                //else he has a speed and we need to see if he went faster, if he did remove the old record and add the new one to the end.
                SpeedTrapDataDTO currentRanking = speedTrapRankings.get(index);
                if (currentRanking.getSpeed() < snapshot.getSpeed()) {
                    speedTrapRankings.remove(currentRanking);
                    speedTrapRankings.add(snapshot);
                    resort = true;
                }
            }
            //If we need to resort and redraw then we do it now.
            if (resort) {
                SpeedTrapDataDTO.sortBySpeed(speedTrapRankings);
                for (int n = 0; n < speedTrapRankings.size(); n++) {
                    SpeedTrapDataDTO current = speedTrapRankings.get(n);
                    speedTrapDashboard.get(n).updateRank(current);
                }
            }
        }
    }

    private void callTelemetryThread(Consumer<DriverDataDTO> driverDataConsumer, Consumer<SpeedTrapDataDTO> speedTrapDataDTO) {
        Thread telemetryThread = new Thread(() -> {
            new F1DataMain().run(driverDataConsumer, speedTrapDataDTO);
        });
        telemetryThread.setDaemon(true);
        telemetryThread.start();
    }

    public void run(String[] args) {
        launch(args);
    }
}

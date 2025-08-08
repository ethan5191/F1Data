import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import telemetry.TelemetryRunData;
import ui.LatestLapDashboard;
import ui.DriverDataDTO;
import ui.AllLapDataDashboard;
import ui.SetupInfoDashboard;
import ui.stages.AllLapDataStage;
import ui.stages.LatestLapStage;
import ui.stages.SetupStage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final Map<Integer, LatestLapDashboard> latestLapDashboard = new HashMap<>();
    private final Map<Integer, VBox> allLapDataDashboard = new HashMap<>();
    private final Map<Integer, VBox> setupDataDashboard = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        VBox latestLap = new VBox(5);
        VBox allLaps = new VBox(5);
        VBox setupData = new VBox(5);

        Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
        {
            Platform.runLater(() -> {
                buildLatestLapBoard(snapshot, latestLap);
                buildAllLapBoard(snapshot, allLaps);
                buildSetupBoard(snapshot, setupData);
            });
        };

//        new LatestLapStage(stage, latestLap);
//        new AllLapDataStage(new Stage(), allLaps);
        new SetupStage(new Stage(), setupData);

        callTelemetryThread(driverDataConsumer);
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

    private void callTelemetryThread(Consumer<DriverDataDTO> driverDataConsumer) {
        Thread telemetryThread = new Thread(() -> {
            new F1DataMain().run(driverDataConsumer);
        });
        telemetryThread.setDaemon(true);
        telemetryThread.start();
    }

    public void run(String[] args) {
        launch(args);
    }
}

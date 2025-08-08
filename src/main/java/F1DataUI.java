import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.DriverDashboard;
import ui.DriverDataDTO;
import ui.LapDataDashboard;
import ui.stages.LapDataStage;
import ui.stages.LatestLapStage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final Map<Integer, DriverDashboard> driverDashboards = new HashMap<>();
    private final Map<Integer, VBox> lapDataDashboard = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        VBox allDrivers = new VBox(5);
        VBox allLaps = new VBox(5);

        Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
        {
            Platform.runLater(() -> {
                buildLatestLapBoard(snapshot, allDrivers);
                buildAllLapBoard(snapshot, allLaps);
            });
        };

        new LatestLapStage(stage, allDrivers);
        new LapDataStage(new Stage(), allLaps);

        callTelemetryThread(driverDataConsumer);
    }

    private void buildLatestLapBoard(DriverDataDTO snapshot, VBox allDrivers) {
        DriverDashboard latestLap = driverDashboards.computeIfAbsent(snapshot.getId(), id -> {
            DriverDashboard newDashboard = new DriverDashboard(snapshot.getLastName());
            allDrivers.getChildren().add(newDashboard);
            return newDashboard;
        });
        if (snapshot.getInfo() != null) {
            latestLap.updateValues(snapshot.getInfo());
        }
    }

    private void buildAllLapBoard(DriverDataDTO snapshot, VBox allLaps) {
        if (snapshot.getInfo() != null) {
            //builds out the labels for the lapdata panel (panel 2 at the moment)
            VBox driver = lapDataDashboard.computeIfAbsent(snapshot.getId(), id -> {
                VBox temp = new VBox();
                allLaps.getChildren().add(temp);
                return temp;
            });
            LapDataDashboard allLapsDashboard = new LapDataDashboard(snapshot.getLastName(), snapshot.getInfo());
            VBox lapsContainer = new VBox();
            lapsContainer.getChildren().add(allLapsDashboard);
            driver.getChildren().add(lapsContainer);
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

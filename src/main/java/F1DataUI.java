import individualLap.IndividualLapInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.DriverDashboard;
import ui.DriverDataDTO;
import ui.LapDataDashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final Map<Integer, DriverDashboard> driverDashboards = new HashMap<>();
    private final Map<Integer, TreeMap<Integer, LapDataDashboard>> lapDataDashboard = new HashMap<>();

    private final String[] HEADERS = {"NAME (Tire)", "#", "S1", "S2", "S3", "TIME"};
    private final String[] LAP_HEADERS = {"NAME", "TIRE", "#", "TIME"};

    @Override
    public void start(Stage stage) throws Exception {
        VBox content = new VBox();
        HBox headers = new HBox(3);
        for (String s : HEADERS) {
            Label header = new Label(s);
            header.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(header, Priority.ALWAYS);
            headers.getChildren().add(header);
        }
        headers.setMaxWidth(Double.MAX_VALUE);
        content.getChildren().add(headers);
        VBox allDrivers = new VBox(5);

        VBox allLaps = new VBox(5);

        Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
        {
            Platform.runLater(() -> {
                DriverDashboard dashboard = driverDashboards.computeIfAbsent(snapshot.getId(), id -> {
                    DriverDashboard newDashboard = new DriverDashboard(snapshot.getLastName());
                    allDrivers.getChildren().add(newDashboard);
                    return newDashboard;
                });
                IndividualLapInfo info = snapshot.getInfo();
                if (info != null) {
                    dashboard.updateValues(info);

                    //builds out the labels for the lapdata panel (panel 2 at the moment)
                    TreeMap<Integer, LapDataDashboard> laps = lapDataDashboard.computeIfAbsent(snapshot.getId(), id -> new TreeMap<>());
                    LapDataDashboard dataDashboard = laps.computeIfAbsent(snapshot.getInfo().getLapNum(), lapNum -> {
                        LapDataDashboard newDashboard = new LapDataDashboard(snapshot.getLastName(), info);
                        allLaps.getChildren().add(newDashboard);
                        return newDashboard;
                    });
                }
            });
        };
        Thread telemetryThread = new Thread(() -> {
            new F1DataMain().run(driverDataConsumer);
        });
        telemetryThread.setDaemon(true);
        telemetryThread.start();

        //TODO: remove this eventually, for now with everything still printing to the console, it stays.
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(event -> {
            stage.hide();
            System.out.println("Window closed, app still lives");
        });

        content.getChildren().add(allDrivers);
        Scene scene = new Scene(content, 500, 500);
        stage.setScene(scene);
        stage.show();

        buildLapDataStage(allLaps);
    }

    private void buildLapDataStage(VBox allLaps) {
        Stage lapData = new Stage();
        VBox content = new VBox();
        HBox headerBox = new HBox();
        for (String s : LAP_HEADERS) {
            Label header = new Label(s);
            header.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(header, Priority.ALWAYS);
            headerBox.getChildren().add(header);
        }
        headerBox.setMaxWidth(Double.MAX_VALUE);
        content.getChildren().add(headerBox);
        content.getChildren().add(allLaps);

        Platform.setImplicitExit(false);
        lapData.setOnCloseRequest(event -> {
            lapData.hide();
            System.out.println("Lap Data closed, app still lives");
        });

        Scene lapDataScene = new Scene(content, 700, 300);
        lapData.setScene(lapDataScene);
        lapData.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}

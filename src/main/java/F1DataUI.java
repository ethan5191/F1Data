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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final Map<Integer, DriverDashboard> driverDashboards = new HashMap<>();

    private final String[] HEADERS = {"NAME", "#", "S1", "S2", "S3", "TIME"};

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
    }

    public void run(String[] args) {
        launch(args);
    }
}

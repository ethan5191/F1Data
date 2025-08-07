import individualLap.IndividualLapInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.DriverDashboard;
import ui.DriverDataDTO;
import ui.LapDataDashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static ui.DriverDashboard.HEADERS;
import static ui.DriverDashboard.HEADERS_WIDTH;
import static ui.LapDataDashboard.LAP_HEADERS;
import static ui.LapDataDashboard.LAP_HEADERS_WIDTH;

public class F1DataUI extends Application {

    private final Map<Integer, DriverDashboard> driverDashboards = new HashMap<>();
    private final Map<Integer, VBox> lapDataDashboard = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        VBox content = new VBox();
        HBox headers = new HBox(3);
        for (int i = 0; i < HEADERS.length; i++) {
            Label header = new Label(HEADERS[i]);
            header.setMinWidth(HEADERS_WIDTH[i]);
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
                    VBox driver = lapDataDashboard.computeIfAbsent(snapshot.getId(), id -> {
                        VBox temp = new VBox();
                        allLaps.getChildren().add(temp);
                        return temp;
                    });
                    LapDataDashboard lap = new LapDataDashboard(snapshot.getLastName(), snapshot.getInfo());
                    VBox lapsContainer = new VBox();
                    lapsContainer.getChildren().add(lap);
                    driver.getChildren().add(lapsContainer);
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
        Scene scene = new Scene(content, 650, 475);
        stage.setScene(scene);
        stage.show();

        buildLapDataStage(allLaps);
    }

    private void buildLapDataStage(VBox allLaps) {
        Stage lapData = new Stage();
        VBox content = new VBox();
        HBox headerBox = new HBox();
        for (int i = 0; i < LAP_HEADERS.length; i++) {
            Label header = new Label(LAP_HEADERS[i]);
            header.setMinWidth(LAP_HEADERS_WIDTH[i]);
            headerBox.getChildren().add(header);
        }
        content.getChildren().add(headerBox);
        content.getChildren().add(allLaps);

        Platform.setImplicitExit(false);
        lapData.setOnCloseRequest(event -> {
            lapData.hide();
            System.out.println("Lap Data closed, app still lives");
        });
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(content);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Scene lapDataScene = new Scene(scroll, 300, 700);
        lapData.setScene(lapDataScene);
        lapData.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}

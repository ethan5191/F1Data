import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.DriverDataDTO;
import utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final Map<Integer, Label> labelMap = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox(5);
        for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
            labelMap.put(i, new Label(null));
        }

        Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
        {
            Platform.runLater(() -> {
                Label l = labelMap.get(snapshot.getId());
                if (l.getText() == null) {
                    l.setText(snapshot.getLastName());
                    vBox.getChildren().add(l);
                }
            });
        };
        Thread telemetryThread = new Thread(() -> {
            new F1DataMain().run(driverDataConsumer);
        });
        telemetryThread.setDaemon(true);
        telemetryThread.start();
        Scene scene = new Scene(vBox, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}

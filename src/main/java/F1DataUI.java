import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.DriverDataDTO;
import utils.Constants;

import java.awt.font.TextMeasurer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataUI extends Application {

    private final Map<Integer, Label> labelMap = new HashMap<>();

    private static String[] HEADERS = new String[6];

    static {
        String[] temp = new String[6];
        temp[0] = "NAME";
        temp[1] = "#";
        temp[2] = "S1";
        temp[3] = "S2";
        temp[4] = "S3";
        temp[5] = "TIME";
        HEADERS = temp;
    }

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
        VBox names = new VBox(5);
        for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
            labelMap.put(i, new Label(null));
        }

        Consumer<DriverDataDTO> driverDataConsumer = snapshot ->
        {
            Platform.runLater(() -> {
                Label l = labelMap.get(snapshot.getId());
                if (l.getText() == null) {
                    l.setText(snapshot.getLastName());
                    names.getChildren().add(l);
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

        content.getChildren().add(names);
        Scene scene = new Scene(content, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}

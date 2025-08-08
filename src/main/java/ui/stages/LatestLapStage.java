package ui.stages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.stages.helper.Delta;
import ui.stages.helper.StageUtils;

import static ui.DriverDashboard.HEADERS;
import static ui.DriverDashboard.HEADERS_WIDTH;

public class LatestLapStage implements F1Stages<VBox> {

    public LatestLapStage(Stage stage, VBox allDrivers) {
        this.stage = stage;
        this.allDrivers = allDrivers;
        this.content = createParentContent(this.stage);
        init();
    }

    private final Stage stage;
    private final VBox content;
    private final VBox allDrivers;

    private void init() {
        buildHeader();
        StageUtils.enableHideWindows(this.stage);
        this.content.getChildren().add(this.allDrivers);
        showStage();
    }

    public VBox createParentContent(Stage stage) {
        VBox content = new VBox();
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.25);");
        Delta dragDelta = new Delta();
        content.setOnMousePressed(e -> {
            dragDelta.x = stage.getX() - e.getScreenX();
            dragDelta.y = stage.getY() - e.getScreenY();
        });
        content.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + dragDelta.x);
            stage.setY(e.getScreenY() + dragDelta.y);
        });
        return content;
    }

    public void buildHeader() {
        HBox headers = new HBox(3);
        for (int i = 0; i < HEADERS.length; i++) {
            Label header = new Label(HEADERS[i]);
            header.setMinWidth(HEADERS_WIDTH[i]);
            headers.getChildren().add(header);
            header.setTextFill(Color.WHITE);
        }
        headers.setMaxWidth(Double.MAX_VALUE);
        this.content.getChildren().add(headers);
    }

    public void showStage() {
        Scene scene = new Scene(this.content, 650, 475);
        scene.setFill(Color.TRANSPARENT);
        this.stage.setScene(scene);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.show();
    }
}

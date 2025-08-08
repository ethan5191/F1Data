package ui.stages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.stages.helper.Delta;
import ui.stages.helper.StageUtils;

import static ui.LapDataDashboard.LAP_HEADERS;
import static ui.LapDataDashboard.LAP_HEADERS_WIDTH;

public class LapDataStage implements F1Stages<VBox> {

    public LapDataStage(Stage stage, VBox allLaps) {
        this.stage = stage;
        this.allLaps = allLaps;
        this.content = createParentContent(this.stage);
        init();
    }

    private final Stage stage;
    private final VBox content;
    private final VBox allLaps;

    private void init() {
        buildHeader();
        StageUtils.enableHideWindows(this.stage);
        content.getChildren().add(allLaps);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(content);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        showStage();
    }

    @Override
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

    @Override
    public void buildHeader() {
        HBox headerBox = new HBox(3);
        for (int i = 0; i < LAP_HEADERS.length; i++) {
            Label header = new Label(LAP_HEADERS[i]);
            header.setMinWidth(LAP_HEADERS_WIDTH[i]);
            headerBox.getChildren().add(header);
            header.setTextFill(Color.WHITE);
        }
        content.getChildren().add(headerBox);
    }

    @Override
    public void showStage() {
        Scene scene = new Scene(this.content, 300, 700);
        scene.setFill(Color.TRANSPARENT);
        this.stage.setScene(scene);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.show();
    }
}

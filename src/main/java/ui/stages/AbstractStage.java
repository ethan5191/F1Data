package ui.stages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.stages.helper.Delta;
import ui.stages.helper.StageUtils;

public abstract class AbstractStage<T extends Pane> {

    public AbstractStage(Stage stage, String[] headers, int[] headersWidth) {
        this.stage = stage;
        this.headers = headers;
        this.headersWidth = headersWidth;
        this.content = createParentContent();
        addDragAndDrop();
        StageUtils.enableHideWindows(this.stage);
        buildHeader();
    }

    protected final Stage stage;
    protected final T content;
    private final String[] headers;
    private final int[] headersWidth;

    public abstract T createParentContent();

    protected abstract void init();

    protected VBox createParentVbox() {
        VBox content = new VBox();
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.33);");
        return content;
    }

    protected void addDragAndDrop() {
        Delta dragDelta = new Delta();
        content.setOnMousePressed(e -> {
            dragDelta.x = stage.getX() - e.getScreenX();
            dragDelta.y = stage.getY() - e.getScreenY();
        });
        content.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + dragDelta.x);
            stage.setY(e.getScreenY() + dragDelta.y);
        });
    }

    public void buildHeader() {
        HBox headersBox = new HBox(3);
        for (int i = 0; i < this.headers.length; i++) {
            Label header = new Label(this.headers[i]);
            header.setMinWidth(this.headersWidth[i]);
            headersBox.getChildren().add(header);
            header.setTextFill(Color.WHITE);
        }
        this.content.getChildren().add(headersBox);
    }

    public void showStage(double width, double height) {
        Scene scene = new Scene(this.content, width, height);
        scene.setFill(Color.TRANSPARENT);
        this.stage.setScene(scene);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.show();
    }
}

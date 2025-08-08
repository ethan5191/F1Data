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

public abstract class AbstractStage<T extends Pane> implements F1Stages {

    public AbstractStage(Stage stage) {
        this.stage = stage;
        this.content = createParentContent();
    }

    protected final Stage stage;
    protected final T content;

    public abstract T createParentContent();

    protected abstract void init();

    protected VBox createParentVbox() {
        VBox content = new VBox();
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.25);");
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

    public void buildHeader(String[] headers, int[] headersWidth) {
        HBox headersBox = new HBox(3);
        for (int i = 0; i < headers.length; i++) {
            Label header = new Label(headers[i]);
            header.setMinWidth(headersWidth[i]);
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

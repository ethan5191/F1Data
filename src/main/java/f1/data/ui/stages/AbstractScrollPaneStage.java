package f1.data.ui.stages;

import f1.data.ui.stages.helper.Delta;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class AbstractScrollPaneStage {

    private final Stage stage;
    private final VBox content;
    private final String[] headers;
    private final int[] headersWidth;

    public AbstractScrollPaneStage(Stage stage, VBox content, String[] headers, int[] headersWidth) {
        this.stage = stage;
        this.stage.setAlwaysOnTop(true);
        this.content = content;
        this.content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.33);");
        this.headers = headers;
        this.headersWidth = headersWidth;
        init();
    }

    private void init() {
        int spacing = 3;
        VBox container = new VBox(spacing);
        buildHeaders(container);
        double height = getSceneHeight();
        container.getChildren().add(createScrollPane(height));
        container.setStyle("-fx-background-color: rgba(0, 0, 0, 0.33);");
        addDragAndDrop(container);
        createScene(container, getSceneWidth(), height);
    }

    private void addDragAndDrop(Pane element) {
        Delta dragDelta = new Delta();
        element.setOnMousePressed(e -> {
            dragDelta.x = stage.getX() - e.getScreenX();
            dragDelta.y = stage.getY() - e.getScreenY();
        });
        element.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + dragDelta.x);
            stage.setY(e.getScreenY() + dragDelta.y);
        });
    }

    private ScrollPane createScrollPane(double height) {
        ScrollPane pane = new ScrollPane();
        pane.setMinHeight(height);
        pane.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-control-inner-background: transparent;" +
                        "-fx-background: transparent;" +
                        "-fx-box-border: transparent;" +
                        "-fx-border-color: transparent;"
        );
        pane.setContent(this.content);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return pane;
    }

    private void buildHeaders(VBox container) {
        HBox headersBox = new HBox(3);
        for (int i = 0; i < this.headers.length; i++) {
            Label header = new Label(this.headers[i]);
            header.setMinWidth(this.headersWidth[i]);
            headersBox.getChildren().add(header);
            header.setTextFill(Color.WHITE);
        }
        headersBox.setOnMouseEntered(e -> {
            headersBox.getScene().setCursor(Cursor.MOVE);
        });
        headersBox.setOnMouseExited(e -> {
            headersBox.getScene().setCursor(Cursor.DEFAULT);
        });
        container.getChildren().add(headersBox);
    }

    private void createScene(Pane container, double width, double height) {
        Scene scene = new Scene(container, width, height);
        scene.setFill(Color.TRANSPARENT);
        this.stage.setScene(scene);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        getAppState().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.stage.show();
            } else if (oldValue) {
                this.stage.hide();
            }
        });
    }

    protected double useFullScreenHeight() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        return bounds.getHeight();
    }

    protected abstract double getSceneWidth();

    protected abstract double getSceneHeight();

    protected abstract BooleanProperty getAppState();
}

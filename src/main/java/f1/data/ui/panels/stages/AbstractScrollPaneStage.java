package f1.data.ui.panels.stages;

import f1.data.ui.panels.stages.helper.Delta;
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

    private final int SPACING = 5;


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
        VBox container = new VBox(SPACING);
        buildHeaders(container);
        double height = getSceneHeight();
        container.getChildren().add(createScrollPane(height));
        container.setStyle("-fx-background-color: rgba(0, 0, 0, 0.33);");
        addDragAndDrop(container);
        createScene(container, getSceneWidth(), height);
    }

    //Adds drag and drop to the element passed in. Currently only the parent container for the scroll pane is passed in.
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

    //Creates the scroll pane, ensuring that it is transparent, instead of having a white background.
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

    //If the stage we are building has passed us headers then we are building them here.
    //Regardless of if we have headers, we still want to create and add the header box as it has the Cursor.Move logic on it.
    private void buildHeaders(VBox container) {
        HBox headersBox = new HBox(SPACING);
        if (this.headers != null) {
            for (int i = 0; i < this.headers.length; i++) {
                Label header = new Label(this.headers[i]);
                header.setMinWidth(this.headersWidth[i]);
                headersBox.getChildren().add(header);
                header.setTextFill(Color.WHITE);
            }
        }
        headersBox.setOnMouseEntered(e -> {
            headersBox.getScene().setCursor(Cursor.MOVE);
        });
        headersBox.setOnMouseExited(e -> {
            headersBox.getScene().setCursor(Cursor.DEFAULT);
        });
        container.getChildren().add(headersBox);
    }

    //Creates the actual scene, with the container, width, and height passed in.
    private void createScene(Pane container, double width, double height) {
        Scene scene = new Scene(container, width, height);
        scene.setFill(Color.TRANSPARENT);
        this.stage.setScene(scene);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        //allows for toggling of the panel via the checkboxes on the home screen.
        getAppState().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.stage.show();
            } else if (oldValue) {
                this.stage.hide();
            }
        });
    }

    //Default logic to use the full screen height based on the screen it is being displayed on.
    protected double useFullScreenHeight() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        return bounds.getHeight();
    }

    protected abstract double getSceneWidth();

    protected abstract double getSceneHeight();

    protected abstract BooleanProperty getAppState();
}

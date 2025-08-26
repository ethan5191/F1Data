package f1.data.ui.stages;

import f1.data.ui.dashboards.TeamSpeedTrapDashboard;
import f1.data.ui.home.AppState;
import f1.data.ui.stages.helper.Delta;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Rectangle2D;
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

public class TeamSpeedTrapStage {

    private final Stage stage;
    private final VBox content;

    public TeamSpeedTrapStage(Stage stage, VBox content) {
        this.stage = stage;
        this.content = content;
        this.content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.33);");
        init();
    }

    private void init() {
        addDragAndDrop(this.content);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        VBox container = new VBox(3);
        createHeader(container);
        container.getChildren().add(createScrollPane(bounds.getHeight()));
        container.setStyle("-fx-background-color: rgba(0, 0, 0, 0.33);");
        createScene(container, 250, bounds.getHeight());
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
        pane.setPannable(true);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return pane;
    }

    private void createHeader(VBox container) {
        HBox headersBox = new HBox(3);
        for (int i = 0; i < TeamSpeedTrapDashboard.HEADERS.length; i++) {
            Label header = new Label(TeamSpeedTrapDashboard.HEADERS[i]);
            header.setMinWidth(TeamSpeedTrapDashboard.HEADERS_WIDTH[i]);
            headersBox.getChildren().add(header);
            header.setTextFill(Color.WHITE);
        }
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

    protected BooleanProperty getAppState() {
        return AppState.teamSpeedTrapPanelVisible;
    }
}

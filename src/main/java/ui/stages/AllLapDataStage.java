package ui.stages;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ui.home.AppState;

import static ui.dashboards.AllLapDataDashboard.LAP_HEADERS;
import static ui.dashboards.AllLapDataDashboard.LAP_HEADERS_WIDTH;

public class AllLapDataStage extends AbstractStage<VBox> {

    public AllLapDataStage(Stage stage, VBox allLaps) {
        super(stage, LAP_HEADERS, LAP_HEADERS_WIDTH);
        this.allLaps = allLaps;
        init();
    }

    private final VBox allLaps;

    protected void init() {
        this.content.getChildren().add(allLaps);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(this.content);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(300);
        stage.setHeight(bounds.getHeight());
        setScene(this.stage.getWidth(), this.stage.getHeight());
    }

    protected VBox createParentContent() {
        return createParentVbox();
    }

    protected BooleanProperty getAppState() {
        return AppState.allLapsDataPanelVisible;
    }
}

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
        setFullHeightScene(300);
    }

    protected VBox createParentContent() {
        return createParentVbox();
    }

    protected BooleanProperty getAppState() {
        return AppState.allLapsDataPanelVisible;
    }
}

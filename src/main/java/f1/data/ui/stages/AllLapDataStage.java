package f1.data.ui.stages;

import f1.data.ui.dashboards.AllLapDataDashboard;
import f1.data.ui.home.AppState;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AllLapDataStage extends AbstractScrollPaneStage {

    public AllLapDataStage(Stage stage, VBox content) {
        super(stage, content, AllLapDataDashboard.LAP_HEADERS, AllLapDataDashboard.LAP_HEADERS_WIDTH);
    }

    protected double getSceneWidth() {
        return 300;
    }

    protected double getSceneHeight() {
        return useFullScreenHeight();
    }

    protected BooleanProperty getAppState() {
        return AppState.allLapsDataPanelVisible;
    }
}

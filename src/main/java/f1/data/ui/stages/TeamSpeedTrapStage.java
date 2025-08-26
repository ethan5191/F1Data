package f1.data.ui.stages;

import f1.data.ui.dashboards.TeamSpeedTrapDashboard;
import f1.data.ui.home.AppState;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeamSpeedTrapStage extends AbstractScrollPaneStage {

    public TeamSpeedTrapStage(Stage stage, VBox content) {
        super(stage, content, TeamSpeedTrapDashboard.HEADERS, TeamSpeedTrapDashboard.HEADERS_WIDTH);
    }

    protected double getSceneWidth() {
        return 250;
    }

    protected double getSceneHeight() {
        return useFullScreenHeight();
    }

    protected BooleanProperty getAppState() {
        return AppState.teamSpeedTrapPanelVisible;
    }
}

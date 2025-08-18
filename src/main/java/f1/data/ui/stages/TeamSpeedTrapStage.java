package f1.data.ui.stages;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import f1.data.ui.dashboards.TeamSpeedTrapDashboard;
import f1.data.ui.home.AppState;

public class TeamSpeedTrapStage extends AbstractStage<VBox> {

    public TeamSpeedTrapStage(Stage stage, VBox teamSpeedTrapData) {
        super(stage, TeamSpeedTrapDashboard.HEADERS, TeamSpeedTrapDashboard.HEADERS_WIDTH);
        this.teamSpeedTrapData = teamSpeedTrapData;
        init();
    }

    private final VBox teamSpeedTrapData;

    @Override
    protected VBox createParentContent() {
        return createParentVbox();
    }

    @Override
    protected void init() {
        this.content.getChildren().add(teamSpeedTrapData);
        setFullHeightScene(250);
    }

    @Override
    protected BooleanProperty getAppState() {
        return AppState.teamSpeedTrapPanelVisible;
    }
}

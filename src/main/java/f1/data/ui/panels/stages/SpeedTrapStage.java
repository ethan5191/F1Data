package f1.data.ui.panels.stages;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import f1.data.ui.panels.dashboards.SpeedTrapDashboard;
import f1.data.ui.panels.home.AppState;

public class SpeedTrapStage extends AbstractStage<VBox> {

    public SpeedTrapStage(Stage stage, VBox speedTrap) {
        super(stage, SpeedTrapDashboard.HEADERS, SpeedTrapDashboard.HEADERS_WIDTH);
        this.speedTrap = speedTrap;
        init();
    }

    private final VBox speedTrap;

    @Override
    protected VBox createParentContent() {
        return createParentVbox();
    }

    @Override
    protected void init() {
        this.content.getChildren().add(speedTrap);
        setScene(350, 505);
    }

    @Override
    protected BooleanProperty getAppState() {
        return AppState.speedTrapPanelVisible;
    }
}

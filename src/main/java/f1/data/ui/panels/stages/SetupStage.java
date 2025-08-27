package f1.data.ui.panels.stages;

import f1.data.ui.panels.home.AppState;
import f1.data.utils.constants.Constants;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetupStage extends AbstractScrollPaneStage {

    public SetupStage(Stage stage, VBox content) {
        super(stage, content, null, null);
    }

    @Override
    protected double getSceneWidth() {
        return Constants.SETUP_PANEL_WIDTH;
    }

    @Override
    protected double getSceneHeight() {
        return useFullScreenHeight();
    }

    @Override
    protected BooleanProperty getAppState() {
        return AppState.setupDataPanelVisible;
    }
}

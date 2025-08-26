package f1.data.ui.stages;

import f1.data.ui.home.AppState;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RunDataStage extends AbstractScrollPaneStage {

    public RunDataStage(Stage stage, VBox content) {
        super(stage, content, null, null);
    }

    @Override
    protected double getSceneWidth() {
        return 950;
    }

    @Override
    protected double getSceneHeight() {
        return useFullScreenHeight();
    }

    @Override
    protected BooleanProperty getAppState() {
        return AppState.runDataPanelVisible;
    }
}

package f1.data.ui.stages;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import f1.data.ui.home.AppState;

public class RunDataStage extends AbstractStage<VBox> {

    public RunDataStage(Stage stage, VBox runData) {
        super(stage, null, null);
        this.runData = runData;
        init();
    }

    private final VBox runData;

    @Override
    protected VBox createParentContent() {
        return createParentVbox();
    }

    @Override
    protected void init() {
        this.content.getChildren().add(runData);
        setFullHeightScene(950);
    }

    @Override
    protected BooleanProperty getAppState() {
        return AppState.runDataPanelVisible;
    }
}

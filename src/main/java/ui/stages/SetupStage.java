package ui.stages;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ui.home.AppState;
import utils.Constants;

public class SetupStage extends AbstractStage<VBox> {

    public SetupStage(Stage stage, VBox setupData) {
        super(stage, null, null);
        this.setupData = setupData;
        init();
    }

    private final VBox setupData;

    @Override
    protected VBox createParentContent() {
        return createParentVbox();
    }

    protected BooleanProperty getAppState() {
        return AppState.setupDataPanelVisible;
    }

    @Override
    protected void init() {
        this.content.getChildren().add(setupData);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(Constants.SETUP_PANEL_WIDTH);
        stage.setHeight(bounds.getHeight());
        setScene(this.stage.getWidth(), this.stage.getHeight());
    }
}

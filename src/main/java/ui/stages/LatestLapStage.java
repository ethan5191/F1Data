package ui.stages;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.home.AppState;

import static ui.LatestLapDashboard.HEADERS;
import static ui.LatestLapDashboard.HEADERS_WIDTH;

public class LatestLapStage extends AbstractStage<VBox> {

    public LatestLapStage(Stage stage, VBox allDrivers) {
        super(stage, HEADERS, HEADERS_WIDTH);
        this.allDrivers = allDrivers;
        init();
    }

    private final VBox allDrivers;

    protected void init() {
        this.content.getChildren().add(this.allDrivers);
        setScene(650, 475);
    }

    protected VBox createParentContent() {
        return createParentVbox();
    }

    protected BooleanProperty getAppState() {
        return AppState.latestLapPanelVisible;
    }
}

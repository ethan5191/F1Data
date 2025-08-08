package ui.stages;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.stages.helper.StageUtils;

import static ui.DriverDashboard.HEADERS;
import static ui.DriverDashboard.HEADERS_WIDTH;

public class LatestLapStage extends AbstractStage<VBox> {

    public LatestLapStage(Stage stage, VBox allDrivers) {
        super(stage);
        this.allDrivers = allDrivers;
        init();
    }

    private final VBox allDrivers;

    protected void init() {
        addDragAndDrop();
        buildHeader(HEADERS, HEADERS_WIDTH);
        StageUtils.enableHideWindows(this.stage);
        this.content.getChildren().add(this.allDrivers);
        showStage(650, 475);
    }

    public VBox createParentContent() {
        return createParentVbox();
    }
}

package ui.stages;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static ui.DriverDashboard.HEADERS;
import static ui.DriverDashboard.HEADERS_WIDTH;

public class LatestLapStage extends AbstractStage<VBox> {

    public LatestLapStage(Stage stage, VBox allDrivers) {
        super(stage, HEADERS, HEADERS_WIDTH);
        this.allDrivers = allDrivers;
        init();
    }

    private final VBox allDrivers;

    protected void init() {
        this.content.getChildren().add(this.allDrivers);
        showStage(650, 475);
    }

    public VBox createParentContent() {
        return createParentVbox();
    }
}

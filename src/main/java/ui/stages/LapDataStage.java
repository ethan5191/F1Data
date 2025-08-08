package ui.stages;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.stages.helper.StageUtils;

import static ui.LapDataDashboard.LAP_HEADERS;
import static ui.LapDataDashboard.LAP_HEADERS_WIDTH;

public class LapDataStage extends AbstractStage<VBox> {

    public LapDataStage(Stage stage, VBox allLaps) {
        super(stage);
        this.allLaps = allLaps;
        init();
    }

    private final VBox allLaps;

    protected void init() {
        addDragAndDrop();
        buildHeader(LAP_HEADERS, LAP_HEADERS_WIDTH);
        StageUtils.enableHideWindows(this.stage);
        this.content.getChildren().add(allLaps);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(this.content);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        showStage(300, 700);
    }

    public VBox createParentContent() {
        return createParentVbox();
    }
}

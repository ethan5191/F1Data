package f1.data.ui.stages.managers;

import f1.data.ui.Panel;
import f1.data.ui.dashboards.AllLapDataDashboard;
import f1.data.ui.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class AllLapStageManager implements Panel {

    private final VBox container;
    private final Map<Integer, VBox> dashboards = new HashMap<>();
    private final int playerDriverId;
    private final int teamMateId;

    public AllLapStageManager(int playerDriverId, int teamMateId) {
        this.container = new VBox(getSpacing());
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
    }

    public void updateStage(DriverDataDTO dto) {
        if (dto.getInfo() != null) {
            //builds out the labels for the lapdata panel (panel 2 at the moment)
            VBox driver = this.dashboards.computeIfAbsent(dto.getId(), id -> {
                VBox temp = new VBox();
                //Add the box to the parent view.
                this.container.getChildren().add(temp);
                //If it's the driver or there teammate then update the background color.
                if (id == this.playerDriverId || id == this.teamMateId) {
                    temp.setStyle("-fx-background-color: #3e3e3e;");
                }
                return temp;
            });
            //Creates the actual dashboard
            AllLapDataDashboard allLapsDashboard = new AllLapDataDashboard(dto.getLastName(), dto.getInfo());
            //container for the laps information
            VBox lapsContainer = new VBox();
            lapsContainer.getChildren().add(allLapsDashboard);
            //add to the overall panel.
            driver.getChildren().add(lapsContainer);
        }
    }

    public VBox getContainer() {
        return container;
    }

    public double getSpacing() {
        return 5;
    }
}

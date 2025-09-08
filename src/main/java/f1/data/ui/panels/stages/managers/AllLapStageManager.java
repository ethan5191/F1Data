package f1.data.ui.panels.stages.managers;

import f1.data.ui.panels.OnSessionChange;
import f1.data.ui.panels.OnSessionReset;
import f1.data.ui.panels.Panel;
import f1.data.ui.panels.dashboards.AllLapDataDashboard;
import f1.data.ui.panels.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class AllLapStageManager implements Panel, OnSessionReset, OnSessionChange {

    private final VBox container;
    private final Map<Integer, VBox> dashboards = new HashMap<>();
    private int playerDriverId;
    private int teamMateId;

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

    public void onSessionReset() {
        this.container.getChildren().clear();
        this.dashboards.clear();
    }

    public void onSessionChange(int playerDriverId, int teamMateId) {
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
        onSessionReset();
    }

    public VBox getContainer() {
        return container;
    }

    public double getSpacing() {
        return 5;
    }
}

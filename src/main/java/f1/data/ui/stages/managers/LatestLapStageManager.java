package f1.data.ui.stages.managers;

import f1.data.ui.Panel;
import f1.data.ui.dashboards.LatestLapDashboard;
import f1.data.ui.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class LatestLapStageManager implements Panel {

    private final VBox container;
    private final Map<Integer, LatestLapDashboard> dashboards = new HashMap<>();

    private int playerDriverId = -1;
    private int teamMateId = -1;
    private Map<Integer, Integer> driverPairings = new HashMap<>();

    public LatestLapStageManager() {
        this.container = new VBox(getSpacing());
    }

    public void updateStage(DriverDataDTO dto) {
        //If the global map of driver pairings is empty, then we need to populate it from the DTO. It should have the map we need.
        //if driver pairings is empty then we haven't populated the formulaType either.
        if (this.driverPairings.isEmpty()) {
            this.driverPairings = dto.getDriverPairings();
        }
        LatestLapDashboard latestLapDash = this.dashboards.computeIfAbsent(dto.getId(), id -> {
            //Creates the new dashboard
            LatestLapDashboard newDashboard = new LatestLapDashboard(dto.getLastName());
            //add it to the view.
            this.container.getChildren().add(newDashboard);
            //If this is the players driver, then update the background color of this box.
            if (dto.isPlayer()) {
                this.playerDriverId = dto.getId();
                //Use the driverPairings param to ensure we can accommodate F1/F2/F2 previous year driver lineups.
                this.teamMateId = this.driverPairings.get(this.playerDriverId);
                newDashboard.setStyle("-fx-background-color: #3e3e3e;");
                //If we have already created the teammates view, update the background color
                if (this.dashboards.containsKey(this.teamMateId)) {
                    LatestLapDashboard teamMateDash = this.dashboards.get(this.teamMateId);
                    teamMateDash.setStyle("-fx-background-color: #3e3e3e;");
                }
            }
            return newDashboard;
        });
        //Make sure we have the info object, if we do then we can actually update the dashboard with data.
        if (dto.getInfo() != null) {
            latestLapDash.updateValues(dto.getInfo());
        }
    }

    public VBox getContainer() {
        return container;
    }

    public double getSpacing() {
        return 5;
    }
}

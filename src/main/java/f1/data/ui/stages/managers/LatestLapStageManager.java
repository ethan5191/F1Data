package f1.data.ui.stages.managers;

import f1.data.ui.OnSessionReset;
import f1.data.ui.Panel;
import f1.data.ui.dashboards.LatestLapDashboard;
import f1.data.ui.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class LatestLapStageManager implements Panel, OnSessionReset {

    private final VBox container;
    private final Map<Integer, LatestLapDashboard> dashboards = new HashMap<>();
    private final int playerDriverId;
    private final int teamMateId;

    public LatestLapStageManager(int playerDriverId, int teamMateId) {
        this.container = new VBox(getSpacing());
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
    }

    public void updateStage(DriverDataDTO dto) {
        LatestLapDashboard latestLapDash = this.dashboards.computeIfAbsent(dto.getId(), id -> {
            //Creates the new dashboard
            LatestLapDashboard newDashboard = new LatestLapDashboard(dto.getLastName());
            //add it to the view.
            this.container.getChildren().add(newDashboard);
            //If this is the players driver, then update the background color of this box.
            if (dto.getId() == this.playerDriverId || dto.getId() == this.teamMateId) {
                newDashboard.setStyle("-fx-background-color: #3e3e3e;");
            }
            return newDashboard;
        });
        //Make sure we have the info object, if we do then we can actually update the dashboard with data.
        if (dto.getInfo() != null) {
            latestLapDash.updateValues(dto.getInfo());
        }
    }

    public void onSessionReset() {
        this.container.getChildren().clear();
        this.dashboards.clear();
    }

    public VBox getContainer() {
        return container;
    }

    public double getSpacing() {
        return 5;
    }
}

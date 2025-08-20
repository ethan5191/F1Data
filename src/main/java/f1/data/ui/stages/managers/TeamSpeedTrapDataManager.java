package f1.data.ui.stages.managers;

import f1.data.ui.Panel;
import f1.data.ui.dashboards.TeamSpeedTrapDashboard;
import f1.data.ui.dto.SpeedTrapDataDTO;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class TeamSpeedTrapDataManager implements Panel {

    private final VBox container;
    private final Map<Integer, Map<Integer, TeamSpeedTrapDashboard>> dashboards = new HashMap<>(2);
    private final int playerDriverId;
    private final int teamMateId;

    public TeamSpeedTrapDataManager(int playerDriverId, int teamMateId) {
        this.container = new VBox(getSpacing());
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
    }

    //Creates the player team speed trap panel. This panel logs every speed trap registered by the teams 2 drivers, ordered by lap#.
    public void updateStage(SpeedTrapDataDTO dto) {
        //This panel is only for the player and their teammate.
        if (dto.driverId() == this.playerDriverId || dto.driverId() == this.teamMateId) {
            TeamSpeedTrapDashboard dashboard = new TeamSpeedTrapDashboard(dto);
            boolean updated = false;
            //If the map doesn't contain a record for this driver then we are doing an initial create.
            if (!this.dashboards.containsKey(dto.driverId())) {
                Map<Integer, TeamSpeedTrapDashboard> temp = new HashMap<>(1);
                temp.put(dto.lapNum(), dashboard);
                this.dashboards.put(dto.driverId(), temp);
            } else {
                //Else we need to make sure that the current lap doesn't already exist for this driver.
                Map<Integer, TeamSpeedTrapDashboard> latestSpeedDash = this.dashboards.get(dto.driverId());
                //If the lap already exists in the map (driver pitted) then we just update the dashboard's trap speed
                if (latestSpeedDash.containsKey(dto.lapNum())) {
                    dashboard = latestSpeedDash.get(dto.lapNum());
                    dashboard.updateSpeed(dto);
                    //Flip this flag, so we don't try to add this child element, as its already on the view.
                    updated = true;
                }
                latestSpeedDash.put(dto.lapNum(), dashboard);
                this.dashboards.put(dto.driverId(), latestSpeedDash);
            }
            if (!updated) this.container.getChildren().add(dashboard);
        }
    }

    public VBox getContainer() {
        return container;
    }

    @Override
    public double getSpacing() {
        return 5;
    }
}

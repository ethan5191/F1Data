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

    public TeamSpeedTrapDataManager(VBox container) {
        this.container = container;
        this.container.setSpacing(getSpacing());
    }

    //Creates the player team speed trap panel. This panel logs every speed trap registered by the teams 2 drivers, ordered by lap#.
    public void updateStage(SpeedTrapDataDTO dto, int playerId, int teamMateId) {
        //This panel is only for the player and their teammate.
        if (dto.getDriverId() == playerId || dto.getDriverId() == teamMateId) {
            TeamSpeedTrapDashboard dashboard = new TeamSpeedTrapDashboard(dto);
            boolean updated = false;
            //If the map doesn't contain a record for this driver then we are doing an initial create.
            if (!this.dashboards.containsKey(dto.getDriverId())) {
                Map<Integer, TeamSpeedTrapDashboard> temp = new HashMap<>(1);
                temp.put(dto.getLapNum(), dashboard);
                this.dashboards.put(dto.getDriverId(), temp);
            } else {
                //Else we need to make sure that the current lap doesn't already exist for this driver.
                Map<Integer, TeamSpeedTrapDashboard> latestSpeedDash = this.dashboards.get(dto.getDriverId());
                //If the lap already exists in the map (driver pitted) then we just update the dashboard's trap speed
                if (latestSpeedDash.containsKey(dto.getLapNum())) {
                    dashboard = latestSpeedDash.get(dto.getLapNum());
                    dashboard.updateSpeed(dto);
                    //Flip this flag, so we don't try to add this child element, as its already on the view.
                    updated = true;
                }
                latestSpeedDash.put(dto.getLapNum(), dashboard);
                this.dashboards.put(dto.getDriverId(), latestSpeedDash);
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

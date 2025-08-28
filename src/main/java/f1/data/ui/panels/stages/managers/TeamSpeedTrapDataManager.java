package f1.data.ui.panels.stages.managers;

import f1.data.mapKeys.DriverIdLapNum;
import f1.data.ui.panels.OnSessionReset;
import f1.data.ui.panels.Panel;
import f1.data.ui.panels.dashboards.TeamSpeedTrapDashboard;
import f1.data.ui.panels.dto.SpeedTrapDataDTO;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class TeamSpeedTrapDataManager implements Panel, OnSessionReset {

    private final VBox container;
    private final int playerDriverId;
    private final int teamMateId;
    private final Map<DriverIdLapNum, TeamSpeedTrapDashboard> dashboards = new HashMap<>();

    public TeamSpeedTrapDataManager(int playerDriverId, int teamMateId) {
        this.container = new VBox(getSpacing());
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
    }

    public void updateStage(SpeedTrapDataDTO dto) {
        //For this panel we only care about the player driver and their teammate.
        if (dto.driverId() == this.playerDriverId || dto.driverId() == this.teamMateId) {
            //Create the driverId/LapNum key object.
            DriverIdLapNum idLapNum = new DriverIdLapNum(dto.driverId(), dto.lapNum());
            //If that key already exists, then we just need to update the speed. We only care about the latest and greatest speed.
            //If you trip the speed trap then pit, that speed will be overwritten the next time you trigger the speed trap.
            if (this.dashboards.containsKey(idLapNum)) {
                this.dashboards.get(idLapNum).updateSpeed(dto);
            //if that key doesn't exist, then we create a new dashboard that will get displayed.
            //add that dashboard to the map.
            } else {
                TeamSpeedTrapDashboard trapDashboard = new TeamSpeedTrapDashboard(dto);
                this.dashboards.put(idLapNum, trapDashboard);
                this.container.getChildren().add(trapDashboard);
            }
        }
    }

    public VBox getContainer() {
        return container;
    }

    @Override
    public void onSessionReset() {
        this.container.getChildren().clear();
        this.dashboards.clear();
    }

    @Override
    public double getSpacing() {
        return 5;
    }
}

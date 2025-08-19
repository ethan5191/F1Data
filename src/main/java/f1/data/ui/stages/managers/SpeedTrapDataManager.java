package f1.data.ui.stages.managers;

import f1.data.ui.Panel;
import f1.data.ui.dashboards.SpeedTrapDashboard;
import f1.data.ui.dto.SpeedTrapDataDTO;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeedTrapDataManager implements Panel {

    private final VBox container;
    private final Map<Integer, SpeedTrapDashboard> dashboards = new HashMap<>();
    private final List<SpeedTrapDataDTO> rankings = new ArrayList<>();

    public SpeedTrapDataManager(VBox container) {
        this.container = container;
        this.container.setSpacing(getSpacing());
    }

    //Creates the all speed trap panel, keeps track of the order based on the fastest lap by each driver
    public void updateStage(SpeedTrapDataDTO dto, int playerId, int teamMateId) {
        //If this is the first car through the speed trap then we need to create the initial group of containers for the data.
        //Based on the number of cars in the session will determine how many dashboards are created.
        if (this.rankings.isEmpty() && this.dashboards.isEmpty()) {
            //This is the only panel so far that creates all the dashboards up front, then just updates the values with each new speed trap value.
            for (int i = 0; i < dto.getNumActiveCars(); i++) {
                SpeedTrapDashboard dashboard = new SpeedTrapDashboard(i + 1);
                this.container.getChildren().add(dashboard);
                this.dashboards.put(i, dashboard);
            }
            //Add this to the list and update its map object as its the first one in.
            this.rankings.add(dto);
            this.dashboards.get(0).updateRank(dto);
        }
        if (!this.rankings.isEmpty()) {
            //Does a check on the driver name to see if this driver already has a top speed recorded.
            int index = this.rankings.indexOf(dto);
            //boolean to indicate if we need to reSort and redraw the data.
            boolean reSort = false;
            //If index < 0 this driver doesn't have a speed in the list, so we just add it.
            if (index < 0) {
                this.rankings.add(dto);
                reSort = true;
            } else {
                //else he has a speed and we need to see if he went faster, if he did remove the old record and add the new one to the end.
                SpeedTrapDataDTO currentRanking = this.rankings.get(index);
                if (currentRanking.getSpeed() < dto.getSpeed()) {
                    this.rankings.remove(currentRanking);
                    this.rankings.add(dto);
                    reSort = true;
                }
            }
            //If we need to reSort and redraw then we do it now.
            if (reSort) {
                //sort the List by the speed values before we redraw the data.
                SpeedTrapDataDTO.sortBySpeed(this.rankings);
                for (int n = 0; n < this.rankings.size(); n++) {
                    SpeedTrapDataDTO current = this.rankings.get(n);
                    SpeedTrapDashboard currentDash = this.dashboards.get(n);
                    //If its the player or there teammate, update the background so they are easy to identify.
                    if (current.getDriverId() == playerId || current.getDriverId() == teamMateId) {
                        currentDash.setStyle("-fx-background-color: #3e3e3e;");
                    } else {
                        currentDash.setStyle(null);
                    }
                    currentDash.updateRank(current);
                }
            }
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

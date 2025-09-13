package f1.data.ui.panels.stages.managers;

import f1.data.ui.panels.OnSessionChange;
import f1.data.ui.panels.OnSessionChangeNumActiveCars;
import f1.data.ui.panels.OnSessionReset;
import f1.data.ui.panels.Panel;
import f1.data.ui.panels.dashboards.SpeedTrapDashboard;
import f1.data.ui.panels.dto.SpeedTrapDataDTO;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeedTrapDataManager implements Panel, OnSessionReset, OnSessionChange, OnSessionChangeNumActiveCars {

    private final VBox container;
    private final Map<Integer, SpeedTrapDashboard> dashboards = new HashMap<>();
    private final List<SpeedTrapDataDTO> rankings = new ArrayList<>();
    private int playerDriverId;
    private int teamMateId;
    private int numActiveCars;

    public SpeedTrapDataManager(int playerDriverId, int teamMateId, int numActiveCars) {
        this.container = new VBox(getSpacing());
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
        this.numActiveCars = numActiveCars;
        buildInitialDisplay();
    }

    //Builds the initial dashboards for each speed trap ranking, with placeholder values.
    private void buildInitialDisplay() {
        for (int i = 0; i < this.numActiveCars; i++) {
            SpeedTrapDashboard dashboard = new SpeedTrapDashboard(i + 1);
            this.container.getChildren().add(dashboard);
            this.dashboards.put(i, dashboard);
        }
    }

    //Creates the all speed trap panel, keeps track of the order based on the fastest lap by each driver
    public void updateStage(SpeedTrapDataDTO dto) {
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
                //else he has a speed, and we need to see if he went faster, if he did remove the old record and add the new one to the end.
                SpeedTrapDataDTO currentRanking = this.rankings.get(index);
                if (currentRanking.speed() < dto.speed()) {
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
                    updateStyle(currentDash, current.driverId());
                    currentDash.updateRank(current);
                }
            }
        } else {
            //Add this to the list and update its map object as it's the first one in.
            this.rankings.add(dto);
            this.dashboards.get(0).updateRank(dto);
            updateStyle(this.dashboards.get(0), dto.driverId());
        }
    }

    //Adds the colored background if its the player or there teammate, otherwise removes the style, in case it was previously added.
    private void updateStyle(SpeedTrapDashboard currentDash, int driverId) {
        //If it's the player or there teammate, update the background so they are easy to identify.
        if (driverId == this.playerDriverId || driverId == this.teamMateId) {
            currentDash.setStyle("-fx-background-color: #3e3e3e;");
        } else {
            currentDash.setStyle(null);
        }
    }

    public void onSessionReset() {
        this.container.getChildren().clear();
        this.dashboards.clear();
        this.rankings.clear();
        buildInitialDisplay();
    }

    public void onSessionChange(int playerDriverId, int teamMateId) {
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
        onSessionReset();
    }

    public void onSessionChangeNumActiveCars(int numActiveCars) {
        this.numActiveCars = numActiveCars;
        onSessionReset();
    }

    public VBox getContainer() {
        return container;
    }

    @Override
    public double getSpacing() {
        return 5;
    }
}

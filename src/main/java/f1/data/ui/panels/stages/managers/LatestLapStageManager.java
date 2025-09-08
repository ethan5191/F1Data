package f1.data.ui.panels.stages.managers;

import f1.data.ui.panels.OnSessionChange;
import f1.data.ui.panels.OnSessionReset;
import f1.data.ui.panels.Panel;
import f1.data.ui.panels.dashboards.LatestLapDashboard;
import f1.data.ui.panels.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatestLapStageManager implements Panel, OnSessionReset, OnSessionChange {

    private final VBox container;
    private final Map<Integer, LatestLapDashboard> dashboards = new HashMap<>();
    private int playerDriverId;
    private int teamMateId;

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

    public void onSessionChange(int playerDriverId, int teamMateId) {
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
        //Before we clear this specific dashboard we want to build a list of the objects.
        List<DriverDataDTO> dtos = new ArrayList<>(this.dashboards.size());
        //build the DTO object that we will use to rehydrate the default values for this specific panel.
        for (Map.Entry<Integer, LatestLapDashboard> record : this.dashboards.entrySet()) {
            dtos.add(new DriverDataDTO(record.getKey(), record.getValue().getName().getText()));
        }
        onSessionReset();
        //for each DTO create a new dashboard and put it in the map.
        for (DriverDataDTO dto : dtos) {
            updateStage(dto);
        }
    }

    public VBox getContainer() {
        return container;
    }

    public double getSpacing() {
        return 5;
    }
}

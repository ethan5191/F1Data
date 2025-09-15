package f1.data.ui.panels.stages.managers;

import f1.data.parse.packets.participant.ParticipantData;
import f1.data.ui.panels.OnSessionChange;
import f1.data.ui.panels.OnSessionReset;
import f1.data.ui.panels.Panel;
import f1.data.ui.panels.dashboards.LatestLapDashboard;
import f1.data.ui.panels.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.*;

public class LatestLapStageManager implements Panel, OnSessionReset, OnSessionChange {

    private final VBox container;
    private final Map<Integer, LatestLapDashboard> dashboards = new HashMap<>();
    private int playerDriverId;
    private int teamMateId;

    public LatestLapStageManager(int playerDriverId, int teamMateId, List<ParticipantData> participantData) {
        this.container = new VBox(getSpacing());
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
        buildInitialMap(participantData);
        buildInitialDisplay();
    }

    public void updateStage(DriverDataDTO dto) {
        //Make sure we have the info object, if we do then we can actually update the dashboard with data.
        if (dto.getInfo() != null) {
            this.dashboards.get(dto.getId()).updateValues(dto.getInfo());
        }
    }

    //Builds the map of driver ID->LatestLapDashboard
    private void buildInitialMap(List<ParticipantData> participantData) {
        for (ParticipantData pd : participantData) {
            LatestLapDashboard newDash = new LatestLapDashboard(pd.lastName());
            if (pd.driverId() == this.playerDriverId || pd.driverId() == this.teamMateId) {
                newDash.setStyle("-fx-background-color: #3e3e3e;");
            }
            this.dashboards.put(pd.driverId(), newDash);
        }
    }

    //Takes the map, sorts it based on the last name and then adds the elements to the actual panel.
    private void buildInitialDisplay() {
        List<LatestLapDashboard> dashboardRecs = new ArrayList<>(this.dashboards.values());
        dashboardRecs.sort(Comparator.comparing(LatestLapDashboard::getLastName));
        this.container.getChildren().addAll(dashboardRecs);
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

    public void onSessionChange(List<ParticipantData> participantData) {
        buildInitialMap(participantData);
        buildInitialDisplay();
    }

    public VBox getContainer() {
        return container;
    }

    public double getSpacing() {
        return 5;
    }
}

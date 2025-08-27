package f1.data.ui.panels.stages.managers;

import f1.data.ui.panels.OnSessionReset;
import f1.data.ui.panels.Panel;
import f1.data.ui.panels.dashboards.SetupInfoDashboard;
import f1.data.ui.panels.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SetupStageManager implements Panel, OnSessionReset {

    private final VBox container;
    private final Map<Integer, Map<Integer, VBox>> dashboards = new HashMap<>();
    private final Map<Integer, Set<Integer>> driversSetupsIds = new HashMap<>();

    public SetupStageManager() {
        this.container = new VBox(getSpacing());
    }

    public void updateStage(DriverDataDTO dto) {
        if (dto.getInfo() != null) {
            //Ensures we don't duplicate records, as we only want 1 record per driver.
            if (!this.dashboards.containsKey(dto.getId())) {
                commonSetupLogic(dto, this.container, new HashMap<>(), new HashSet<>(), dto.getLastName());
                //If this driver has already completed a lap with a different setup, we are adding this new setup to the map and using the lap # in the name.
            } else if (dto.getInfo().isSetupChange()) {
                //If this setupNumber doesn't exist in the map, then we need to actually update the UI.
                Set<Integer> setupIds = this.driversSetupsIds.get(dto.getId());
                if (!setupIds.contains(dto.getInfo().getCurrentSetupNumber())) {
                    String setupName = dto.getLastName() + " Lap #" + dto.getInfo().getLapNum();
                    Map<Integer, VBox> existingSetups = this.dashboards.get(dto.getId());
                    commonSetupLogic(dto, this.container, existingSetups, setupIds, setupName);
                }
            }
        }
    }

    //Common logic used by the setup panel for both new entries and new setups for drivers who already have a setup listed.
    private void commonSetupLogic(DriverDataDTO snapshot, VBox setupData, Map<Integer, VBox> mapToUpdate, Set<Integer> setupIds, String setupName) {
        VBox driver = new VBox();
        setupData.getChildren().add(driver);
        //Add the box to the map so we can ensure we don't duplicate it.
        mapToUpdate.put(snapshot.getInfo().getLapNum(), driver);
        this.dashboards.put(snapshot.getId(), mapToUpdate);
        //Creates the actual dashboard
        SetupInfoDashboard setupInfo = new SetupInfoDashboard(setupName, snapshot.getInfo().getCarSetupData(), snapshot.getInfo().getCarStatusInfo().getVisualTireCompound());
        VBox container = new VBox(5);
        //Add the current setupNumber to the map of <DriverID, Set<SetupNumbers>>
        setupIds.add(snapshot.getInfo().getCurrentSetupNumber());
        this.driversSetupsIds.put(snapshot.getId(), setupIds);
        container.getChildren().add(setupInfo);
        driver.getChildren().add(container);
    }

    public void onSessionReset() {
        this.container.getChildren().clear();
        this.dashboards.clear();
    }

    public VBox getContainer() {
        return container;
    }

    @Override
    public double getSpacing() {
        return 5;
    }
}

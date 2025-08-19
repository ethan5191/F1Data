package f1.data.ui.stages.managers;

import f1.data.ui.Panel;
import f1.data.ui.dashboards.SetupInfoDashboard;
import f1.data.ui.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class SetupStageManager implements Panel {

    private final VBox container;
    private final Map<Integer, Map<Integer, VBox>> dashboards = new HashMap<>();

    public SetupStageManager() {
        this.container = new VBox(getSpacing());
    }

    public void updateStage(DriverDataDTO dto) {
        if (dto.getInfo() != null) {
            //Ensures we don't duplicate records, as we only want 1 record per driver.
            if (!this.dashboards.containsKey(dto.getId())) {
                commonSetupLogic(dto, this.container, new HashMap<>(), dto.getInfo().getCarSetupData().setupName());
                //If this driver has already completed a lap with a different setup, we are adding this new setup to the map and using the lap # in the name.
            } else if (dto.getInfo().isSetupChange()) {
                String setupName = dto.getInfo().getCarSetupData().setupName() + " Lap #" + dto.getInfo().getLapNum();
                Map<Integer, VBox> existingSetups = this.dashboards.get(dto.getId());
                commonSetupLogic(dto, this.container, existingSetups, setupName);
            }
        }
    }

    //Common logic used by the setup panel for both new entries and new setups for drivers who already have a setup listed.
    private void commonSetupLogic(DriverDataDTO snapshot, VBox setupData, Map<Integer, VBox> mapToUpdate, String setupName) {
        VBox driver = new VBox();
        setupData.getChildren().add(driver);
        //Add the box to the map so we can ensure we don't duplicate it.
        mapToUpdate.put(snapshot.getInfo().getLapNum(), driver);
        this.dashboards.put(snapshot.getId(), mapToUpdate);
        //Creates the actual dashboard
        SetupInfoDashboard setupInfo = new SetupInfoDashboard(setupName, snapshot.getInfo().getCarSetupData(), snapshot.getInfo().getCarStatusInfo().getVisualTireCompound());
        VBox container = new VBox(3);
        container.getChildren().add(setupInfo);
        driver.getChildren().add(container);
    }

    public VBox getContainer() {
        return container;
    }

    @Override
    public double getSpacing() {
        return 5;
    }
}

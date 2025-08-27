package f1.data.ui.stages.managers;

import f1.data.individualLap.IndividualLapInfo;
import f1.data.telemetry.SetupTireKey;
import f1.data.ui.OnSessionReset;
import f1.data.ui.Panel;
import f1.data.ui.RunDataAverage;
import f1.data.ui.dashboards.RunDataDashboard;
import f1.data.ui.dashboards.SetupInfoDashboard;
import f1.data.ui.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunDataStageManager implements Panel, OnSessionReset {

    private final VBox container;
    private final Map<Integer, Map<SetupTireKey, List<RunDataDashboard>>> dashboards = new HashMap<>();
    private final int playerDriverId;
    private final int teamMateId;
    private final boolean isF1;

    public RunDataStageManager(int playerDriverId, int teamMateId, boolean isF1) {
        this.container = new VBox(getSpacing());
        this.playerDriverId = playerDriverId;
        this.teamMateId = teamMateId;
        this.isF1 = isF1;
    }

    //builds the runData panel. This panel shows the setup, and all laps completed with that setup.
    public void updateStage(DriverDataDTO dto) {
        IndividualLapInfo info = dto.getInfo();
        if (info != null) {
            if (dto.getId() == this.playerDriverId || dto.getId() == this.teamMateId) {
                //does this drive id exist in the dashboard yet?
                boolean containsKey = this.dashboards.containsKey(dto.getId());
                //Does this setup/fitted tire id exist for this driver?
                boolean setupAlreadyUsed = containsKey && isSetupUsed(dto.getInfo().getCurrentSetupKey(), dto.getId());
                //If this is the first pass through or (the setup has changed, and we haven't used this setup) we need to do all of this.
                if (!containsKey || (info.isSetupChange() && !setupAlreadyUsed)) {
                    VBox driver = new VBox();
                    this.container.getChildren().add(driver);
                    //Creates the actual dashboard
                    SetupInfoDashboard setupInfo = new SetupInfoDashboard(info.getCarSetupData().setupName(), info.getCarSetupData(), info.getCarStatusInfo().getVisualTireCompound());
                    VBox newBox = new VBox(3);
                    RunDataDashboard lapInfoBoard = new RunDataDashboard(dto, this.isF1);
                    Map<SetupTireKey, List<RunDataDashboard>> initial = new HashMap<>();
                    //calculate the averages and add them as a new dashboard to the end of the list.
                    RunDataAverage average = new RunDataAverage(info.getLapNum(), info.getTotalLapsThisSetup(), dto, this.isF1);
                    RunDataDashboard averages = new RunDataDashboard(average, info.isUseLegacy());
                    initial.put(info.getCurrentSetupKey(), List.of(lapInfoBoard, averages));
                    this.dashboards.put(dto.getId(), initial);
                    newBox.getChildren().add(setupInfo);
                    lapInfoBoard.createHeaderRow(newBox);
                    newBox.getChildren().add(lapInfoBoard);
                    newBox.getChildren().add(averages);
                    driver.getChildren().add(newBox);
                    //else we have this setup already done a lap, so we just need to create a new lap info
                } else {
                    Map<SetupTireKey, List<RunDataDashboard>> currentData = this.dashboards.get(dto.getId());
                    SetupTireKey currentSetupKey = info.getCurrentSetupKey();
                    List<RunDataDashboard> lapsForSetupCopy = new ArrayList<>(currentData.get(currentSetupKey));
                    //get the current averages and use it to update the averages to account for a new lap completed.
                    RunDataDashboard currentAverages = lapsForSetupCopy.get(lapsForSetupCopy.size() - 1);
                    RunDataAverage updatedAverages = new RunDataAverage(currentSetupKey.setupNumber(), info.getTotalLapsThisSetup(), dto, currentAverages.getAverage());
                    RunDataDashboard newAvgDash = new RunDataDashboard(updatedAverages, info.isUseLegacy());
                    lapsForSetupCopy.add(newAvgDash);
                    //Update the previous averages line to have the new laps data in it instead of averages.
                    currentAverages.updateValues(dto);
                    currentData.put(currentSetupKey, lapsForSetupCopy);
                    VBox parent = (VBox) currentAverages.getParent();
                    parent.getChildren().add(newAvgDash);
                }
            }
        }
    }

    //Returns true if the setup we are using, has already completed at least one lap in practice.
    private boolean isSetupUsed(SetupTireKey currentSetupKey, int driverId) {
        return this.dashboards.get(driverId).containsKey(currentSetupKey);
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

package f1.data.ui.stages.managers;

import f1.data.individualLap.IndividualLapInfo;
import f1.data.ui.Panel;
import f1.data.ui.RunDataAverage;
import f1.data.ui.dashboards.RunDataDashboard;
import f1.data.ui.dashboards.SetupInfoDashboard;
import f1.data.ui.dto.DriverDataDTO;
import javafx.scene.layout.VBox;

import java.util.*;

public class RunDataStageManager implements Panel {

    private final VBox container;
    private final Map<Integer, Map<Integer, List<RunDataDashboard>>> dashboards = new HashMap<>();
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
                //If this is the first pass through or the setup has changed we need to do all of this.
                if (!this.dashboards.containsKey(dto.getId()) || info.isSetupChange()) {
                    VBox driver = new VBox();
                    this.container.getChildren().add(driver);
                    //Creates the actual dashboard
                    SetupInfoDashboard setupInfo = new SetupInfoDashboard(info.getCarSetupData().setupName(), info.getCarSetupData(), info.getCarStatusInfo().getVisualTireCompound());
                    VBox newBox = new VBox(3);
                    RunDataDashboard lapInfoBoard = new RunDataDashboard(dto, this.isF1);
                    Map<Integer, List<RunDataDashboard>> initial = new HashMap<>();
                    //calculate the averages and add them as a new dashboard to the end of the list.
                    RunDataAverage average = new RunDataAverage(info.getLapNum(), dto, this.isF1);
                    RunDataDashboard averages = new RunDataDashboard(average, info.isUseLegacy());
                    initial.put(info.getLapNum(), List.of(lapInfoBoard, averages));
                    this.dashboards.put(dto.getId(), initial);
                    newBox.getChildren().add(setupInfo);
                    lapInfoBoard.createHeaderRow(newBox);
                    newBox.getChildren().add(lapInfoBoard);
                    newBox.getChildren().add(averages);
                    driver.getChildren().add(newBox);
                    //else we have this setup already done a lap, so we just need to create a new lap info
                } else {
                    Map<Integer, List<RunDataDashboard>> currentData = this.dashboards.get(dto.getId());
                    //The lapnum is the key if a setup change has happened, so get the max key to get the latest setup change.
                    //a setup change will be handled by the if part of this if/else statement.
                    Optional<Integer> maxNewSetupLap = currentData.keySet().stream().max(Integer::compare);
                    Integer maxLap = maxNewSetupLap.get();
                    //Create a copy of the value in the map for this lapnum so we can add a new dashboard to the end.
                    List<RunDataDashboard> lapsForSetupCopy = new ArrayList<>(currentData.get(maxLap));
                    //get the current averages and use it to update the averages to account for a new lap completed.
                    RunDataDashboard currentAverages = lapsForSetupCopy.get(lapsForSetupCopy.size() - 1);
                    RunDataAverage updatedAverages = new RunDataAverage(maxLap, dto, currentAverages.getAverage());
                    RunDataDashboard newAvgDash = new RunDataDashboard(updatedAverages, info.isUseLegacy());
                    lapsForSetupCopy.add(newAvgDash);
                    //Update the previous averages line to have the new laps data in it instead of averages.
                    currentAverages.updateValues(dto);
                    currentData.put(maxLap, lapsForSetupCopy);
                    VBox parent = (VBox) currentAverages.getParent();
                    parent.getChildren().add(newAvgDash);
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

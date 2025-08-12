package ui.dashboards;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ui.RunDataAverage;
import ui.dto.DriverDataDTO;
import utils.constants.Constants;
import utils.Util;

import java.math.BigDecimal;

public class RunDataDashboard extends HBox {

    public static final String[] HEADERS = {"#", "TIME", "RL", "RR", "FL", "FR", "Fuel", "Trap", "Store", "MGUK", "MGUH", "Deployed"};
    public static final int[] HEADERS_WIDTH = {50, 100, 65, 65, 65, 65, 75, 75, 75, 75, 75, 75};

    //Used to create a new lap record.
    public RunDataDashboard(DriverDataDTO dto) {
        this.dto = dto;
        this.lapNum = new Label(String.valueOf(dto.getInfo().getLapNum()));
        this.lapNum.setTextFill(Color.WHITE);
        this.lapNum.setMinWidth(HEADERS_WIDTH[0]);
        this.lapTime = new Label(DashboardUtils.buildTimeText(dto.getInfo().getLapTimeInMs()));
        this.lapTime.setTextFill(Color.WHITE);
        this.lapTime.setMinWidth(HEADERS_WIDTH[1]);
        this.rearLeftWear = new Label(DashboardUtils.formatTwoDecimals(dto.getInfo().getTireWearThisLap()[0]) + Constants.PERCENT_SIGN);
        this.rearLeftWear.setTextFill(Color.WHITE);
        this.rearLeftWear.setMinWidth(HEADERS_WIDTH[2]);
        this.rearRightWear = new Label(DashboardUtils.formatTwoDecimals(dto.getInfo().getTireWearThisLap()[1]) + Constants.PERCENT_SIGN);
        this.rearRightWear.setTextFill(Color.WHITE);
        this.rearRightWear.setMinWidth(HEADERS_WIDTH[3]);
        this.frontLeftWear = new Label(DashboardUtils.formatTwoDecimals(dto.getInfo().getTireWearThisLap()[2]) + Constants.PERCENT_SIGN);
        this.frontLeftWear.setTextFill(Color.WHITE);
        this.frontLeftWear.setMinWidth(HEADERS_WIDTH[4]);
        this.frontRightWear = new Label(DashboardUtils.formatTwoDecimals(dto.getInfo().getTireWearThisLap()[3]) + Constants.PERCENT_SIGN);
        this.frontRightWear.setTextFill(Color.WHITE);
        this.frontRightWear.setMinWidth(HEADERS_WIDTH[5]);
        this.fuelUsed = new Label(DashboardUtils.formatTwoDecimals(dto.getInfo().getFuelUsedThisLap()) + Constants.KG);
        this.fuelUsed.setTextFill(Color.WHITE);
        this.fuelUsed.setMinWidth(HEADERS_WIDTH[6]);
        this.speedTrap = new Label(DashboardUtils.formatTwoDecimals(dto.getInfo().getSpeedTrap()));
        this.speedTrap.setTextFill(Color.WHITE);
        this.speedTrap.setMinWidth(HEADERS_WIDTH[7]);
        this.energyStore = new Label(String.valueOf(dto.getInfo().getCarStatusInfo().getErsStoreEnergy()));
        this.energyStore.setTextFill(Color.WHITE);
        this.energyStore.setMinWidth(HEADERS_WIDTH[8]);
        this.mgukHarvested = new Label(String.valueOf(dto.getInfo().getCarStatusInfo().getErsHarvestedThisLapMGUK()));
        this.mgukHarvested.setTextFill(Color.WHITE);
        this.mgukHarvested.setMinWidth(HEADERS_WIDTH[9]);
        this.mguhHarvested = new Label(String.valueOf(dto.getInfo().getCarStatusInfo().getErsHarvestedThisLapMGUH()));
        this.mguhHarvested.setTextFill(Color.WHITE);
        this.mguhHarvested.setMinWidth(HEADERS_WIDTH[10]);
        this.ersDeployed = new Label(String.valueOf(dto.getInfo().getCarStatusInfo().getErsDeployedThisLap()));
        this.ersDeployed.setTextFill(Color.WHITE);
        this.ersDeployed.setMinWidth(HEADERS_WIDTH[11]);

        this.getChildren().addAll(this.lapNum, this.lapTime, this.rearLeftWear, this.rearRightWear, this.frontLeftWear, this.frontRightWear,
                this.fuelUsed, this.speedTrap, this.energyStore, this.mgukHarvested, this.mguhHarvested, this.ersDeployed);
    }

    //Used to create the average lap box.
    public RunDataDashboard(RunDataAverage average) {
        this.average = average;
        this.lapNum = new Label(average.getTotalLaps() + " Laps");
        this.lapNum.setTextFill(Color.WHITE);
        this.lapNum.setMinWidth(HEADERS_WIDTH[0]);
        BigDecimal avgTimeBd = BigDecimal.valueOf(average.getAvgLapTimeInMs());
        this.lapTime = new Label(DashboardUtils.buildTimeText(Util.roundDecimal(avgTimeBd)));
        this.lapTime.setTextFill(Color.WHITE);
        this.lapTime.setMinWidth(HEADERS_WIDTH[1]);
        this.rearLeftWear = new Label(average.getAvgTireWear()[0] + Constants.PERCENT_SIGN);
        this.rearLeftWear.setTextFill(Color.WHITE);
        this.rearLeftWear.setMinWidth(HEADERS_WIDTH[2]);
        this.rearRightWear = new Label(average.getAvgTireWear()[1] + Constants.PERCENT_SIGN);
        this.rearRightWear.setTextFill(Color.WHITE);
        this.rearRightWear.setMinWidth(HEADERS_WIDTH[3]);
        this.frontLeftWear = new Label(average.getAvgTireWear()[2] + Constants.PERCENT_SIGN);
        this.frontLeftWear.setTextFill(Color.WHITE);
        this.frontLeftWear.setMinWidth(HEADERS_WIDTH[4]);
        this.frontRightWear = new Label(average.getAvgTireWear()[3] + Constants.PERCENT_SIGN);
        this.frontRightWear.setTextFill(Color.WHITE);
        this.frontRightWear.setMinWidth(HEADERS_WIDTH[5]);
        this.fuelUsed = new Label(average.getAvgFuelUsed() + Constants.KG);
        this.fuelUsed.setTextFill(Color.WHITE);
        this.fuelUsed.setMinWidth(HEADERS_WIDTH[6]);
        this.speedTrap = new Label(average.getAvgSpeedTrap());
        this.speedTrap.setTextFill(Color.WHITE);
        this.speedTrap.setMinWidth(HEADERS_WIDTH[7]);
        this.energyStore = new Label(String.valueOf(average.getAvgErsEnergyStore()));
        this.energyStore.setTextFill(Color.WHITE);
        this.energyStore.setMinWidth(HEADERS_WIDTH[8]);
        this.mgukHarvested = new Label(String.valueOf(average.getAvgErsHarvestedMGUK()));
        this.mgukHarvested.setTextFill(Color.WHITE);
        this.mgukHarvested.setMinWidth(HEADERS_WIDTH[9]);
        this.mguhHarvested = new Label(String.valueOf(average.getAvgErsHarvestedMGUH()));
        this.mguhHarvested.setTextFill(Color.WHITE);
        this.mguhHarvested.setMinWidth(HEADERS_WIDTH[10]);
        this.ersDeployed = new Label(String.valueOf(average.getAvgErsDeployed()));
        this.ersDeployed.setTextFill(Color.WHITE);
        this.ersDeployed.setMinWidth(HEADERS_WIDTH[11]);

        this.getChildren().addAll(this.lapNum, this.lapTime, this.rearLeftWear, this.rearRightWear, this.frontLeftWear, this.frontRightWear,
                this.fuelUsed, this.speedTrap, this.energyStore, this.mgukHarvested, this.mguhHarvested, this.ersDeployed);
    }

    private final Label lapNum;
    private final Label lapTime;
    private final Label rearLeftWear;
    private final Label rearRightWear;
    private final Label frontLeftWear;
    private final Label frontRightWear;
    private final Label fuelUsed;
    private final Label speedTrap;
    private final Label energyStore;
    private final Label mgukHarvested;
    private final Label mguhHarvested;
    private final Label ersDeployed;

    private DriverDataDTO dto;
    private RunDataAverage average;

    public Label getLapNum() {
        return lapNum;
    }

    public Label getLapTime() {
        return lapTime;
    }

    public DriverDataDTO getDto() {
        return dto;
    }

    public RunDataAverage getAverage() {
        return average;
    }

    //This is a special dashboard as it has data above what would be the header, so it doesn't use the AbstractStage headers.
    public static void createHeaderRow(VBox container) {
        HBox headersBox = new HBox(3);
        for (int i = 0; i < HEADERS.length; i++) {
            Label header = new Label(HEADERS[i]);
            header.setMinWidth(HEADERS_WIDTH[i]);
            headersBox.getChildren().add(header);
            header.setTextFill(Color.WHITE);
        }
        container.getChildren().add(headersBox);
    }

    //Used to update the values of the previous averages record to be an actual lap record when a new lap is registered in a run.
    public void updateValues(DriverDataDTO dto) {
        this.dto = dto;
        this.lapNum.setText(String.valueOf(dto.getInfo().getLapNum()));
        this.lapTime.setText(DashboardUtils.buildTimeText(dto.getInfo().getLapTimeInMs()));
        this.rearLeftWear.setText(DashboardUtils.formatTwoDecimals(dto.getInfo().getTireWearThisLap()[0]) + Constants.PERCENT_SIGN);
        this.rearRightWear.setText(DashboardUtils.formatTwoDecimals(dto.getInfo().getTireWearThisLap()[1]) + Constants.PERCENT_SIGN);
        this.frontLeftWear.setText(DashboardUtils.formatTwoDecimals(dto.getInfo().getTireWearThisLap()[2]) + Constants.PERCENT_SIGN);
        this.frontRightWear.setText(DashboardUtils.formatTwoDecimals(dto.getInfo().getTireWearThisLap()[3]) + Constants.PERCENT_SIGN);
        this.fuelUsed.setText(DashboardUtils.formatTwoDecimals(dto.getInfo().getFuelUsedThisLap()) + Constants.KG);
        this.speedTrap.setText(DashboardUtils.formatTwoDecimals(dto.getInfo().getSpeedTrap()));
        this.energyStore.setText(String.valueOf(dto.getInfo().getCarStatusInfo().getErsStoreEnergy()));
        this.mgukHarvested.setText(String.valueOf(dto.getInfo().getCarStatusInfo().getErsHarvestedThisLapMGUK()));
        this.mguhHarvested.setText(String.valueOf(dto.getInfo().getCarStatusInfo().getErsHarvestedThisLapMGUH()));
        this.ersDeployed.setText(String.valueOf(dto.getInfo().getCarStatusInfo().getErsDeployedThisLap()));
    }
}

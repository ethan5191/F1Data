package ui.dashboards;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ui.dto.DriverDataDTO;
import utils.Constants;

public class RunDataDashboard extends HBox {

    public static final String[] HEADERS = {"#", "TIRE", "RL", "RR", "FL", "FR", "Fuel", "Trap"};
    public static final int[] HEADERS_WIDTH = {50, 100, 75, 75, 75, 75, 75, 75};

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
        this.fuelRemaining = new Label(DashboardUtils.formatTwoDecimals(dto.getInfo().getFuelUsedThisLap()) + Constants.KG);
        this.fuelRemaining.setTextFill(Color.WHITE);
        this.fuelRemaining.setMinWidth(HEADERS_WIDTH[6]);
        this.speedTrapThisLap = new Label(DashboardUtils.formatTwoDecimals(dto.getInfo().getSpeedTrap()));
        this.speedTrapThisLap.setTextFill(Color.WHITE);
        this.speedTrapThisLap.setMinWidth(HEADERS_WIDTH[7]);


        this.getChildren().addAll(this.lapNum, this.lapTime, this.rearLeftWear, this.rearRightWear, this.frontLeftWear, this.frontRightWear, this.fuelRemaining, this.speedTrapThisLap);
    }

    private final Label lapNum;
    private final Label lapTime;
    private final Label rearLeftWear;
    private final Label rearRightWear;
    private final Label frontLeftWear;
    private final Label frontRightWear;
    private final Label fuelRemaining;
    private final Label speedTrapThisLap;

    private final DriverDataDTO dto;

    public Label getLapNum() {
        return lapNum;
    }

    public Label getLapTime() {
        return lapTime;
    }

    public DriverDataDTO getDto() {
        return dto;
    }

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
}

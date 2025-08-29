package f1.data.ui.panels.dashboards;

import f1.data.parse.individualLap.IndividualLapInfo;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class AllLapDataDashboard extends HBox {

    public static final String[] LAP_HEADERS = {"NAME", "TIRE", "#", "TIME"};
    public static final int[] LAP_HEADERS_WIDTH = {100, 75, 50, 75};

    public AllLapDataDashboard(String lastName, IndividualLapInfo info) {
        this.name = new Label(lastName);
        this.name.setMinWidth(LAP_HEADERS_WIDTH[0]);
        this.name.setTextFill(Color.WHITE);
        this.tire = new Label(info.getCarStatusInfo().getVisualTire().getDisplay());
        this.tire.setMinWidth(LAP_HEADERS_WIDTH[1]);
        this.tire.setTextFill(Color.WHITE);
        this.lapNum = new Label(String.valueOf(info.getIndividualLap().getLapNum()));
        this.lapNum.setMinWidth(LAP_HEADERS_WIDTH[2]);
        this.lapNum.setTextFill(Color.WHITE);
        this.lapTime = new Label(DashboardUtils.buildTimeText(info.getIndividualLap().getLapTimeInMs()));
        this.lapTime.setMinWidth(LAP_HEADERS_WIDTH[3]);
        this.lapTime.setTextFill(Color.WHITE);

        this.getChildren().addAll(name, tire, lapNum, lapTime);
    }

    private final Label name;
    private final Label tire;
    private final Label lapNum;
    private final Label lapTime;

    public Label getName() {
        return name;
    }

    public Label getTire() {
        return tire;
    }

    public Label getLapNum() {
        return lapNum;
    }

    public Label getLapTime() {
        return lapTime;
    }
}

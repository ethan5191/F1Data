package ui;

import individualLap.IndividualLapInfo;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LapDataDashboard extends HBox {

    public LapDataDashboard(String lastName, IndividualLapInfo info) {
        this.name = new Label(lastName);
        this.name.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.name, Priority.ALWAYS);
        this.tire = new Label(info.getCarStatusInfo().getVisualTire().getDisplay());
        this.tire.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.tire, Priority.ALWAYS);
        this.lapNum = new Label(String.valueOf(info.getLapNum()));
        this.lapNum.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.lapNum, Priority.ALWAYS);
        this.lapTime = new Label(String.valueOf(info.getLapTimeInMs()));
        this.lapTime.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.lapTime, Priority.ALWAYS);

        this.setMaxWidth(Double.MAX_VALUE);
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

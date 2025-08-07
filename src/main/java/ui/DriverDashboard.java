package ui;

import individualLap.IndividualLapInfo;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class DriverDashboard extends HBox {

    public DriverDashboard(String lastName) {
        this.name = new Label(lastName);
        this.name.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.name, Priority.ALWAYS);
        this.lapNum = new Label("0");
        this.lapNum.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.lapNum, Priority.ALWAYS);
        this.s1 = new Label("-");
        this.s1.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.s1, Priority.ALWAYS);
        this.s2 = new Label("-");
        this.s2.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.s2, Priority.ALWAYS);
        this.s3 = new Label("-");
        this.s3.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.s3, Priority.ALWAYS);
        this.lapTime = new Label("-");
        this.lapTime.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this.lapTime, Priority.ALWAYS);

        this.setMaxWidth(Double.MAX_VALUE);
        this.getChildren().addAll(name, lapNum, s1, s2, s3, lapTime);
    }

    private final Label name;
    private final Label lapNum;
    private final Label s1;
    private final Label s2;
    private final Label s3;
    private final Label lapTime;

    public Label getName() {
        return name;
    }

    public Label getLapNum() {
        return lapNum;
    }

    public Label getS1() {
        return s1;
    }

    public Label getS2() {
        return s2;
    }

    public Label getS3() {
        return s3;
    }

    public Label getLapTime() {
        return lapTime;
    }

    public void updateValues(IndividualLapInfo info) {
        if (info.getLapNum() > Integer.parseInt(this.lapNum.getText())) {
            this.lapNum.setText(String.valueOf(info.getLapNum()));
            this.s1.setText(String.valueOf(info.getSector1InMs()));
            this.s2.setText(String.valueOf(info.getSector2InMs()));
            this.s3.setText(String.valueOf(info.getSector3InMs()));
            this.lapTime.setText(String.valueOf(info.getLapTimeInMs()));
        }
    }
}

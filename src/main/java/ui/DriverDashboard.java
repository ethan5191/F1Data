package ui;

import individualLap.IndividualLapInfo;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DriverDashboard extends HBox {

    public static final String[] HEADERS = {"NAME (Tire)", "#", "S1", "S2", "S3", "TIME"};
    public static final int[] HEADERS_WIDTH = {155, 55, 100, 100, 100, 100};

    public DriverDashboard(String lastName) {
        this.name = new Label(lastName);
        this.name.setMinWidth(HEADERS_WIDTH[0]);
        this.name.setTextFill(Color.WHITE);
        this.lapNum = new Label("0");
        this.lapNum.setMinWidth(HEADERS_WIDTH[1]);
        this.lapNum.setTextFill(Color.WHITE);
        this.s1 = new Label("-");
        this.s1.setMinWidth(HEADERS_WIDTH[2]);
        this.s1.setTextFill(Color.WHITE);
        this.s2 = new Label("-");
        this.s2.setMinWidth(HEADERS_WIDTH[3]);
        this.s2.setTextFill(Color.WHITE);
        this.s3 = new Label("-");
        this.s3.setMinWidth(HEADERS_WIDTH[4]);
        this.s3.setTextFill(Color.WHITE);
        this.lapTime = new Label("-");
        this.lapTime.setMinWidth(HEADERS_WIDTH[5]);
        this.lapTime.setTextFill(Color.WHITE);

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
            if (!this.name.getText().contains("(")) {
                String text = this.name.getText() + " (" + info.getCarStatusInfo().getVisualTire() + ")";
                this.name.setText(text);
            }
            this.lapNum.setText(String.valueOf(info.getLapNum()));
            this.s1.setText(String.valueOf(info.getSector1InMs()));
            this.s2.setText(String.valueOf(info.getSector2InMs()));
            this.s3.setText(String.valueOf(info.getSector3InMs()));
            this.lapTime.setText(String.valueOf(info.getLapTimeInMs()));
        }
    }
}

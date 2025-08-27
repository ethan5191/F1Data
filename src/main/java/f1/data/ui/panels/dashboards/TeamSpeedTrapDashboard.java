package f1.data.ui.panels.dashboards;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import f1.data.ui.panels.dto.SpeedTrapDataDTO;
import f1.data.utils.constants.Constants;

public class TeamSpeedTrapDashboard extends HBox {

    public static final String[] HEADERS = {"NAME", "#", "SPEED"};
    public static final int[] HEADERS_WIDTH = {100, 50, 100};

    public TeamSpeedTrapDashboard(SpeedTrapDataDTO dto) {
        this.name = new Label(dto.name());
        this.name.setMinWidth(HEADERS_WIDTH[0]);
        this.name.setTextFill(Color.WHITE);
        this.lapNum = new Label(String.valueOf(dto.lapNum()));
        this.lapNum.setMinWidth(HEADERS_WIDTH[1]);
        this.lapNum.setTextFill(Color.WHITE);
        this.speed = new Label(String.format(Constants.TWO_DECIMAL, dto.speed()));
        this.speed.setMinWidth(HEADERS_WIDTH[2]);
        this.speed.setTextFill(Color.WHITE);

        this.getChildren().addAll(this.name, this.lapNum, this.speed);
    }

    private final Label name;
    private final Label lapNum;
    private final Label speed;

    public void updateSpeed(SpeedTrapDataDTO dto) {
        this.speed.setText(String.format(Constants.TWO_DECIMAL, dto.speed()));
    }

}

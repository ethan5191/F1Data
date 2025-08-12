package ui.dashboards;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ui.dto.SpeedTrapDataDTO;
import utils.constants.Constants;


public class SpeedTrapDashboard extends HBox {

    public static final String[] HEADERS = {"RANK", "NAME", "SPEED", "#"};
    public static final int[] HEADERS_WIDTH = {55, 150, 100, 55};

    public SpeedTrapDashboard(int ranking) {
        this.rank = new Label(String.valueOf(ranking));
        this.rank.setMinWidth(HEADERS_WIDTH[0]);
        this.rank.setTextFill(Color.WHITE);
        this.name = new Label("-");
        this.name.setMinWidth(HEADERS_WIDTH[1]);
        this.name.setTextFill(Color.WHITE);
        this.speed = new Label("-");
        this.speed.setMinWidth(HEADERS_WIDTH[2]);
        this.speed.setTextFill(Color.WHITE);
        this.lapNum = new Label("-");
        this.lapNum.setMinWidth(HEADERS_WIDTH[3]);
        this.lapNum.setTextFill(Color.WHITE);

        this.getChildren().addAll(this.rank, this.name, this.speed, this.lapNum);
    }

    private final Label rank;
    private final Label name;
    private final Label speed;
    private final Label lapNum;

    public Label getRank() {
        return rank;
    }

    public Label getName() {
        return name;
    }

    public Label getSpeed() {
        return speed;
    }

    public Label getLapNum() {
        return lapNum;
    }

    public void updateRank(SpeedTrapDataDTO dto) {
        this.name.setText(dto.getName());
        this.speed.setText(String.format(Constants.TWO_DECIMAL, dto.getSpeed()));
        this.lapNum.setText(String.valueOf(dto.getLapNum()));
    }
}

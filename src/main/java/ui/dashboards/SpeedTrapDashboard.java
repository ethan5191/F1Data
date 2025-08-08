package ui.dashboards;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ui.dto.SpeedTrapDataDTO;
import utils.Constants;


public class SpeedTrapDashboard extends HBox {

    public static final String[] HEADERS = {"RANK", "NAME", "SPEED", "#"};
    public static final int[] HEADERS_WIDTH = {55, 150, 100, 55};

    public SpeedTrapDashboard(int ranking, SpeedTrapDataDTO dto) {
        this.rank = new Label(String.valueOf(ranking));
        this.rank.setMinWidth(HEADERS_WIDTH[0]);
        this.rank.setTextFill(Color.WHITE);
        this.name = new Label(dto.getName());
        this.name.setMinWidth(HEADERS_WIDTH[1]);
        this.name.setTextFill(Color.WHITE);
        this.speed = new Label(String.format(Constants.TWO_DECIMAL, dto.getSpeed()));
        this.speed.setMinWidth(HEADERS_WIDTH[2]);
        this.speed.setTextFill(Color.WHITE);
        this.lapNum = new Label(String.valueOf(dto.getLapNum()));
        this.lapNum.setMinWidth(HEADERS_WIDTH[3]);
        this.lapNum.setTextFill(Color.WHITE);

        this.getChildren().addAll(this.rank, this.name, this.speed, this.lapNum);
        this.dto = dto;
    }

    private final Label rank;
    private final Label name;
    private final Label speed;
    private final Label lapNum;
    private final SpeedTrapDataDTO dto;

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

    public SpeedTrapDataDTO getDto() {
        return dto;
    }

    public void updateRank(int newRank, SpeedTrapDataDTO dto) {
        this.rank.setText(String.valueOf(newRank));
        this.speed.setText(String.format(Constants.TWO_DECIMAL, dto.getSpeed()));
        this.lapNum.setText(String.valueOf(dto.getLapNum()));
    }
}

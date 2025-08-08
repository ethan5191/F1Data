package ui;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import packets.CarSetupData;


public class SetupInfoDashboard extends VBox {

    public SetupInfoDashboard(String setupName, CarSetupData setupData) {
        GridPane setupDetails = new GridPane();
        for (int i = 0; i < setupData.getSetupDashboardData().length; i++) {
            Label[] inner = setupData.getSetupDashboardData()[i];
            for (int j = 0; j < inner.length; j++) {
                setupDetails.add(inner[j], i, j);
            }
        }

        this.setupName = new TitledPane(setupName, setupDetails);

        this.getChildren().add(this.setupName);
    }

    private final TitledPane setupName;

    public TitledPane getSetupName() {
        return setupName;
    }
}

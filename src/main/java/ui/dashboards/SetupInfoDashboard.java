package ui.dashboards;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import packets.CarSetupData;
import packets.enums.VisualTireEnum;


public class SetupInfoDashboard extends VBox {

    public SetupInfoDashboard(String setupName, CarSetupData setupData, int visualCompound) {
        GridPane setupDetails = new GridPane();
        //Prints the data out in the order that the setup elements exist in the setup menu in the game.
        for (int i = 0; i < setupData.getSetupDashboardData().length; i++) {
            Label[] inner = setupData.getSetupDashboardData()[i];
            for (int j = 0; j < inner.length; j++) {
                setupDetails.add(inner[j], i, j);
            }
        }
        //Prints the tire compound and its label out below the Fuel Load.
        setupDetails.add(new Label("Tire "), 0, 1);
        setupDetails.add(new Label(VisualTireEnum.fromValue(visualCompound).getDisplay()), 1, 1);

        this.setupName = new TitledPane(setupName, setupDetails);

        this.getChildren().add(this.setupName);
    }

    private final TitledPane setupName;

    public TitledPane getSetupName() {
        return setupName;
    }
}

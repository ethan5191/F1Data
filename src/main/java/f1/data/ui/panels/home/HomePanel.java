package f1.data.ui.panels.home;

import f1.data.parse.F1PacketProcessor;
import f1.data.ui.panels.Panel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class HomePanel implements Panel {

    private static final Logger logger = LoggerFactory.getLogger(HomePanel.class);
    private static final double WIDTH = 250;
    private static final double HEIGHT = 250;

    private final F1PacketProcessor packetProcessor;
    private final VBox container;

    private static final Map<CheckBox, BooleanProperty> PANEL_CHECKS;

    static {
        PANEL_CHECKS = new TreeMap<>(Comparator.comparing(Labeled::getText));

        PANEL_CHECKS.put(new CheckBox("Show Run Data Panel"), AppState.runDataPanelVisible);
        PANEL_CHECKS.put(new CheckBox("Show Team Speed Trap Panel"), AppState.teamSpeedTrapPanelVisible);
        PANEL_CHECKS.put(new CheckBox("Show All Speed Trap Panel"), AppState.speedTrapPanelVisible);
        PANEL_CHECKS.put(new CheckBox("Show Setup Data Panel"), AppState.setupDataPanelVisible);
        PANEL_CHECKS.put(new CheckBox("Show Laps Data Panel"), AppState.allLapsDataPanelVisible);
        PANEL_CHECKS.put(new CheckBox("Show Latest Lap Panel"), AppState.latestLapPanelVisible);
    }

    public HomePanel(F1PacketProcessor packetProcessor) {
        this.packetProcessor = packetProcessor;
        this.container = new VBox(getSpacing());
        createHomePanel();
    }

    private void createHomePanel() {
        //placeholder for the label to indicate status of loading initial packets.
        this.container.getChildren().add(0, new Label());
        for (Map.Entry<CheckBox, BooleanProperty> entry : PANEL_CHECKS.entrySet()) {
            CheckBox temp = entry.getKey();
            temp.selectedProperty().bindBidirectional(entry.getValue());
            temp.setDisable(true);
            this.container.getChildren().add(temp);
        }
        Separator separator = new Separator(Orientation.HORIZONTAL);
        this.container.getChildren().add(separator);
        CheckBox saveSession = new CheckBox("Save Session Data?");
        saveSession.selectedProperty().bindBidirectional(AppState.saveSessionData);
        saveSession.setDisable(true);
        this.container.getChildren().add(saveSession);
        Scene scene = new Scene(this.container, WIDTH, HEIGHT);
        Stage panel = new Stage();
        panel.setScene(scene);
        panel.setOnCloseRequest(e -> {
            logger.info("Shutting Down");
            if (packetProcessor != null) packetProcessor.stop();
            Platform.exit();
        });
        panel.show();
    }

    public VBox getContainer() {
        return container;
    }

    @Override
    public double getSpacing() {
        return 10;
    }
}

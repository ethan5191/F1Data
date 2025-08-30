package f1.data.save;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ViewSavedSessionDataHandler {

    public static void viewSavedSessionData(String fileName, SaveSessionDataWrapper data) {
        Map<String, ViewSavedSessionData> savedSessionData = buildViewData(data);
        VBox container = new VBox(5);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        ObservableList<String> drivers = FXCollections.observableArrayList(savedSessionData.keySet());
        ListView<String> driverList = new ListView<>(drivers);
        grid.add(driverList, 0, 0);
        driverList.setFixedCellSize(25);
        driverList.setPrefHeight(driverList.getFixedCellSize() * savedSessionData.size());
        driverList.setPrefWidth(200);
        GridPane speedPane = new GridPane();
        speedPane.setHgap(5);
        speedPane.setVgap(5);
        VBox speedPaneLap = new VBox(5);
        VBox speedPaneSpeed = new VBox(5);
        speedPane.add(new Label("#"), 0, 0);
        speedPane.add(speedPaneLap, 0, 1);
        speedPane.add(new Label("Speed"), 1, 0);
        speedPane.add(speedPaneSpeed, 1, 1);
        GridPane lapPane = new GridPane();
        lapPane.setHgap(5);
        lapPane.setVgap(5);
        VBox lapPanelLap = new VBox(5);
        VBox setupPanelLap = new VBox(5);
        VBox lapTimeLap = new VBox(5);
        lapPane.add(new Label("#"), 0, 0);
        lapPane.add(lapPanelLap, 0, 1);
        lapPane.add(new Label("Setup/Tire #"), 1, 0);
        lapPane.add(setupPanelLap, 1, 1);
        lapPane.add(new Label("Lap Time"), 2, 0);
        lapPane.add(lapTimeLap, 2, 1);
        grid.add(speedPane, 1, 0);
        grid.add(new Separator(Orientation.VERTICAL), 2, 0);
        grid.add(lapPane, 3, 0);
        driverList.getSelectionModel().selectedItemProperty().addListener((selectedItem, oldValue, newValue) -> {
            if (newValue != null) {
                ViewSavedSessionData saved = savedSessionData.get(newValue);
                ObservableList<Node> speedChildren = speedPane.getChildren();
                for (Node node : speedChildren) {
                    int rowIndex = GridPane.getRowIndex(node);
                    if (rowIndex == 1) {
                        if (node instanceof VBox) ((VBox) node).getChildren().clear();
                    }
                }
                for (Integer lap : saved.getSpeedTrapByLap().keySet()) {
                    speedPaneLap.getChildren().add(new Label(lap.toString()));
                    speedPaneSpeed.getChildren().add(new Label(saved.getSpeedTrapByLap().get(lap).toString()));
                }
                ObservableList<Node> lapChildren = lapPane.getChildren();
                for (Node node : lapChildren) {
                    int rowIndex = GridPane.getRowIndex(node);
                    if (rowIndex == 1) {
                        if (node instanceof VBox) ((VBox) node).getChildren().clear();
                    }
                }
                for (RunDataMapRecord run : saved.getLapsForSetup()) {
                    for (IndividualLapSessionData lap : run.laps()) {
                        lapPanelLap.getChildren().add(new Label(String.valueOf(lap.getLapNum())));
                        setupPanelLap.getChildren().add(new Label(run.key().setupNumber() + "/" + run.key().fittedTireId()));
                        lapTimeLap.getChildren().add(new Label(lap.getLapTimeInMs().toString()));
                    }
                }
            }
        });
        container.getChildren().add(grid);
        Stage viewSavedStage = new Stage();
        viewSavedStage.setTitle(fileName);
        Scene scene = new Scene(container, 500, driverList.getPrefHeight() + 5);
        viewSavedStage.setScene(scene);
        viewSavedStage.show();
    }

    private static Map<String, ViewSavedSessionData> buildViewData(SaveSessionDataWrapper data) {
        Map<String, ViewSavedSessionData> savedSessionData = new HashMap<>(data.speedTraps().size());
        for (SpeedTrapSessionData speedTrap : data.speedTraps()) {
            ViewSavedSessionData saved = new ViewSavedSessionData(speedTrap.lastName());
            saved.setSpeedTrapByLap(speedTrap.speedTrapByLap());
            savedSessionData.put(speedTrap.lastName(), saved);
        }
        for (RunDataSessionData runData : data.runData()) {
            ViewSavedSessionData saved = savedSessionData.get(runData.lastName());
            saved.setSetups(runData.setups());
            saved.setLapsForSetup(runData.lapsForSetup());
        }
        return savedSessionData;
    }
}

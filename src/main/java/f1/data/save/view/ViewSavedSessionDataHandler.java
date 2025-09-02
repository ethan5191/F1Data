package f1.data.save.view;

import f1.data.save.*;
import f1.data.utils.constants.Constants;
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
        boolean hasSpeedTrap = data.speedTraps() != null;
        Map<String, ViewSavedSessionData> savedSessionData = buildViewData(data);
        ObservableList<String> drivers = FXCollections.observableArrayList(savedSessionData.keySet());
        VBox container = new VBox(Constants.SPACING);
        ListView<String> driverList = initializeListView(drivers, savedSessionData.size());
        GridPane grid = initializeGrid(driverList);

        GridPane lapPane = initializeGrid();
        VBox lapPanelLap = new VBox(Constants.SPACING);
        VBox setupPanelLap = new VBox(Constants.SPACING);
        VBox lapTimeLap = new VBox(Constants.SPACING);
        lapPane.add(new Label("#"), 0, 0);
        lapPane.add(lapPanelLap, 0, 1);
        lapPane.add(new Label("Setup/Tire #"), 1, 0);
        lapPane.add(setupPanelLap, 1, 1);
        lapPane.add(new Label("Lap Time"), 2, 0);
        lapPane.add(lapTimeLap, 2, 1);
        grid.add(lapPane, 1, 0);

        GridPane speedPane = initializeGrid();
        VBox speedPaneLap = new VBox(Constants.SPACING);
        VBox speedPaneSpeed = new VBox(Constants.SPACING);
        if (hasSpeedTrap) {
            speedPane.add(new Label("#"), 0, 0);
            speedPane.add(speedPaneLap, 0, 1);
            speedPane.add(new Label("Speed"), 1, 0);
            speedPane.add(speedPaneSpeed, 1, 1);
            grid.add(new Separator(Orientation.VERTICAL), 2, 0);
            grid.add(speedPane, 3, 0);
        }

        driverList.getSelectionModel().selectedItemProperty().addListener((selectedItem, oldValue, newValue) -> {
            if (newValue != null) {
                ViewSavedSessionData saved = savedSessionData.get(newValue);
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
                if (hasSpeedTrap) {
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
        Map<String, ViewSavedSessionData> savedSessionData = new HashMap<>(data.runData().size());
        for (RunDataSessionData runData : data.runData()) {
            ViewSavedSessionData saved = new ViewSavedSessionData(runData.lastName());
            saved.setSetups(runData.setups());
            saved.setLapsForSetup(runData.lapsForSetup());
            savedSessionData.put(runData.lastName(), saved);
        }
        if (data.speedTraps() != null) {
            for (SpeedTrapSessionData speedTrap : data.speedTraps()) {
                savedSessionData.get(speedTrap.lastName()).setSpeedTrapByLap(speedTrap.speedTrapByLap());
            }
        }
        return savedSessionData;
    }

    private static ListView<String> initializeListView(ObservableList<String> drivers, int size) {
        ListView<String> driverList = new ListView<>(drivers);
        driverList.setFixedCellSize(25);
        driverList.setPrefHeight(driverList.getFixedCellSize() * size);
        driverList.setPrefWidth(200);
        return driverList;
    }

    private static GridPane initializeGrid(ListView<String> drivers) {
        GridPane grid = new GridPane();
        grid.setHgap(Constants.SPACING);
        grid.setVgap(Constants.SPACING);
        grid.add(drivers, 0, 0);
        return grid;
    }

    private static GridPane initializeGrid() {
        GridPane pane = new GridPane();
        pane.setHgap(Constants.SPACING);
        pane.setVgap(Constants.SPACING);
        return pane;
    }
}

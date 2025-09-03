package f1.data.save.view;

import f1.data.save.IndividualLapSessionData;
import f1.data.save.RunDataMapRecord;
import f1.data.utils.constants.Constants;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Objects;

public class ViewSavedSessionDataUI {

    private final VBox container = new VBox(Constants.SPACING);
    private final ViewSavedSessionDataService service;

    private final Label[] runDataHeaders = {new Label("#"), new Label("Setup/Tire #"), new Label("Lap Time")};
    private final VBox[] runDataBoxes = {new VBox(Constants.SPACING), new VBox(Constants.SPACING), new VBox(Constants.SPACING)};
    private final Label[] speedTrapHeaders = {new Label("#"), new Label("Speed")};
    private final VBox[] speedTrapBoxes = {new VBox(Constants.SPACING), new VBox(Constants.SPACING)};

    public ViewSavedSessionDataUI(ViewSavedSessionDataService service) {
        this.service = service;
        buildUI();
    }

    private void buildUI() {
        this.container.getChildren().add(buildSearchOptions());
        ListView<String> driverList = initializeListView();
        GridPane grid = initializeGrid(driverList);
        this.container.getChildren().add(grid);
        GridPane runData = initializeGrid(runDataHeaders, runDataBoxes);
        int colIndex = 1;
        grid.add(runData, colIndex++, 0);
        if (service.isHasSpeedTrapData()) {
            grid.add(new Separator(Orientation.VERTICAL), colIndex++, 0);
            GridPane speedTrap = initializeGrid(speedTrapHeaders, speedTrapBoxes);
            grid.add(speedTrap, colIndex, 0);
        }
        showScene();
    }

    private HBox buildSearchOptions() {
        return new ViewSavedSessionDataSearchUI(this.service).getSearchOptions();
    }

    private ListView<String> initializeListView() {
        ListView<String> driverList = new ListView<>(service.getDrivers());
        driverList.setFixedCellSize(25);
        driverList.setPrefHeight((driverList.getFixedCellSize() * service.getSavedSessionData().size()) + Constants.SPACING);
        driverList.setPrefWidth(200);
        addListener(driverList);
        return driverList;
    }

    private GridPane initializeGrid(ListView<String> drivers) {
        GridPane grid = new GridPane();
        grid.setHgap(Constants.SPACING);
        grid.setVgap(Constants.SPACING);
        grid.add(drivers, 0, 0);
        return grid;
    }

    private GridPane initializeGrid(Label[] headers, VBox[] boxes) {
        GridPane pane = new GridPane();
        pane.setHgap(Constants.SPACING);
        pane.setVgap(Constants.SPACING);
        for (int i = 0; i < headers.length; i++) {
            pane.add(headers[i], i, 0);
            pane.add(boxes[i], i, 1);
        }
        return pane;
    }

    private void showScene() {
        Stage viewSavedStage = new Stage();
        viewSavedStage.setTitle(service.getFileName().substring(0, service.getFileName().lastIndexOf('_')));
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Scene scene = new Scene(container, bounds.getWidth() - 10, bounds.getHeight() - 15);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        viewSavedStage.setScene(scene);
        viewSavedStage.show();
    }

    private void clearContent(VBox[] boxes) {
        for (VBox box : boxes) {
            box.getChildren().clear();
        }
    }

    private void addListener(ListView<String> driverList) {
        driverList.getSelectionModel().selectedItemProperty().addListener((selectedItem, oldValue, newValue) -> {
            if (newValue != null) {
                clearContent(runDataBoxes);
                ViewSavedSessionData data = service.findSessionDataByName(newValue);
                for (RunDataMapRecord run : data.getLapsForSetup()) {
                    for (IndividualLapSessionData lap : run.laps()) {
                        runDataBoxes[0].getChildren().add(new Label(String.valueOf(lap.getLapNum())));
                        runDataBoxes[1].getChildren().add(new Label(run.key().setupNumber() + "/" + run.key().fittedTireId()));
                        runDataBoxes[2].getChildren().add(new Label(String.valueOf(lap.getLapTimeInMs())));
                    }
                }
                if (service.isHasSpeedTrapData()) {
                    clearContent(speedTrapBoxes);
                    for (Map.Entry<Integer, Float> entry : data.getSpeedTrapByLap().entrySet()) {
                        speedTrapBoxes[0].getChildren().add(new Label(String.valueOf(entry.getKey())));
                        speedTrapBoxes[1].getChildren().add(new Label(String.valueOf(entry.getValue())));
                    }
                }
            }
        });
    }
}

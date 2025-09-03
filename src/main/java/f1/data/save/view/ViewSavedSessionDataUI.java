package f1.data.save.view;

import f1.data.utils.constants.Constants;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewSavedSessionDataUI {

    private final VBox container = new VBox(Constants.SPACING);
    private final ViewSaveSessionDataService service;

    public ViewSavedSessionDataUI(ViewSaveSessionDataService service) {
        this.service = service;
        buildUI();
    }

    private void buildUI() {
        this.container.getChildren().add(buildSearchOptions());

        GridPane grid = initializeGrid(initializeListView());
        this.container.getChildren().add(grid);

        Stage viewSavedStage = new Stage();
        viewSavedStage.setTitle(service.getFileName().substring(0, service.getFileName().lastIndexOf('_')));
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Scene scene = new Scene(container, bounds.getWidth() - 10, bounds.getHeight() - 15);
        scene.getStylesheets().add(Objects.requireNonNull(ViewSavedSessionDataHandler.class.getResource("/styles.css")).toExternalForm());
        viewSavedStage.setScene(scene);
        viewSavedStage.show();
    }

    private HBox buildSearchOptions() {
        return new ViewSavedSessionDataSearchUI(this.service).getSearchOptions();
    }

    private ListView<String> initializeListView() {
        ListView<String> driverList = new ListView<>(service.getDrivers());
        driverList.setFixedCellSize(25);
        driverList.setPrefHeight((driverList.getFixedCellSize() * service.getSavedSessionData().size()) + Constants.SPACING);
        driverList.setPrefWidth(200);
        return driverList;
    }

    private GridPane initializeGrid(ListView<String> drivers) {
        GridPane grid = new GridPane();
        grid.setHgap(Constants.SPACING);
        grid.setVgap(Constants.SPACING);
        grid.add(drivers, 0, 0);
        return grid;
    }
}

package f1.data.view;

import f1.data.enums.VisualTireEnum;
import f1.data.save.IndividualLapSessionData;
import f1.data.save.RunDataMapRecord;
import f1.data.utils.constants.Constants;
import f1.data.utils.constants.StyleConstants;
import f1.data.view.gridColumns.GridPaneColumn;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

public class ViewSavedSessionDataUI {

    private final VBox container = new VBox(Constants.SPACING);
    private LineChart<Number, Number> lineChart;
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final ViewSavedSessionDataService service;

    private final GridPaneColumn[] RUN_DATA_COLUMNS = {
            new GridPaneColumn(new Label("#"), new VBox(Constants.SPACING)),
            new GridPaneColumn(new Label("Setup #/Compound (ID)"), new VBox(Constants.SPACING)),
            new GridPaneColumn(new Label("Lap Time"), new VBox(Constants.SPACING))
    };
    private final GridPaneColumn[] SPEED_TRAP_COLUMNS = {
            new GridPaneColumn(new Label("#"), new VBox(Constants.SPACING)),
            new GridPaneColumn(new Label("Speed"), new VBox(Constants.SPACING))
    };

    public ViewSavedSessionDataUI(ViewSavedSessionDataService service) {
        this.service = service;
        buildUI();
    }

    private void buildUI() {
        this.container.getChildren().add(buildSearchOptions());
        ListView<String> driverList = initializeListView();
        GridPane grid = initializeGrid(driverList);
        this.container.getChildren().add(grid);
        GridPane runData = initializeGrid(RUN_DATA_COLUMNS);
        int colIndex = 1;
        grid.add(runData, colIndex++, 0);
        if (service.isHasSpeedTrapData()) {
            grid.add(new Separator(Orientation.VERTICAL), colIndex++, 0);
            GridPane speedTrap = initializeGrid(SPEED_TRAP_COLUMNS);
            grid.add(speedTrap, colIndex++, 0);
        }
        grid.add(new Separator(Orientation.VERTICAL), colIndex++, 0);
        defaultAxis(xAxis, "Lap #");
        defaultAxis(yAxis, "Lap Time");
        lineChart = new LineChart<>(xAxis, yAxis);
        final double LINE_CHART_WIDTH = 1000;
        lineChart.setPrefWidth(LINE_CHART_WIDTH);
        grid.add(lineChart, colIndex, 0);
        showScene();
    }

    //Builds the search options panel at the top of the view.
    private HBox buildSearchOptions() {
        ViewSavedSessionDataSearchUI searchUI = new ViewSavedSessionDataSearchUI(this.service);
        BooleanProperty interProp = service.getSearch().interProperty();
        BooleanProperty wetProp = service.getSearch().wetProperty();
        BooleanProperty superProp = service.getSearch().superSoftProperty();
        BooleanProperty softProp = service.getSearch().softProperty();
        BooleanProperty mediumProp = service.getSearch().mediumProperty();
        BooleanProperty hardProp = service.getSearch().hardProperty();
        //Links the individual checkboxes with an associated boolean property on the search object.
        searchUI.getInterCheck().selectedProperty().bindBidirectional(interProp);
        searchUI.getWetCheck().selectedProperty().bindBidirectional(wetProp);
        searchUI.getSuperSoftCheck().selectedProperty().bindBidirectional(superProp);
        searchUI.getSoftCheck().selectedProperty().bindBidirectional(softProp);
        searchUI.getMediumCheck().selectedProperty().bindBidirectional(mediumProp);
        searchUI.getHardCheck().selectedProperty().bindBidirectional(hardProp);
        //Create a list of the boolean properties to streamline the call to add the listener.
        List<BooleanProperty> booleanProps = Arrays.asList(interProp, wetProp, superProp, softProp, mediumProp, hardProp);
        for (BooleanProperty p : booleanProps) {
            addCheckBoxListener(p);
        }
        searchUI.getSetupNums().valueProperty().addListener((observable, oldValue, newValue) -> {
            service.getSearch().setSetupId((!newValue.isEmpty()) ? newValue : null);
            ViewSavedSessionData data = service.findSessionDataByName();
            if (data != null) {
                updateRunData(data);
                updateLapChart(data);
            }
        });
        return searchUI.getSearchOptions();
    }

    //Creates the initial list view object with the different drivers names in it.
    private ListView<String> initializeListView() {
        ListView<String> driverList = new ListView<>(service.getDrivers());
        driverList.setFixedCellSize(25);
        driverList.setPrefHeight((driverList.getFixedCellSize() * service.getSavedSessionData().size()) + Constants.SPACING);
        driverList.setPrefWidth(200);
        addListener(driverList);
        return driverList;
    }

    //Initializes the overall grid that contains the listview and other panes.
    private GridPane initializeGrid(ListView<String> drivers) {
        GridPane grid = new GridPane();
        grid.setHgap(Constants.SPACING);
        grid.setVgap(Constants.SPACING);
        grid.add(drivers, 0, 0);
        return grid;
    }

    //Used to initialize the smaller data specific panes.
    private GridPane initializeGrid(GridPaneColumn[] columns) {
        GridPane pane = new GridPane();
        pane.setHgap(Constants.SPACING);
        pane.setVgap(Constants.SPACING);
        for (int i = 0; i < columns.length; i++) {
            pane.add(columns[i].columnHeader(), i, 0);
            pane.add(columns[i].content(), i, 1);
        }
        return pane;
    }

    //Sets the scene height and width and displays the panel
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

    private void defaultAxis(NumberAxis axis, String label) {
        axis.setLowerBound(1);
        axis.setAutoRanging(false);
        axis.setUpperBound(10);
        axis.setLabel(label);
    }

    //Clears all the VBOX children for the given array of columns passed in.
    private void clearContent(GridPaneColumn[] columns) {
        for (GridPaneColumn column : columns) {
            column.content().getChildren().clear();
        }
    }

    //Adds listeners to each of the boolean properties associated with the tire compound search checkboxes.
    private void addCheckBoxListener(BooleanProperty property) {
        property.addListener((observable, oldValue, newValue) -> {
            ViewSavedSessionData data = service.findSessionDataByName();
            if (data != null) {
                updateRunData(data);
                updateLapChart(data);
            }
        });
    }

    //Adds the listener to the list view object to change the data shown in the data panels.
    private void addListener(ListView<String> driverList) {
        driverList.getSelectionModel().selectedItemProperty().addListener((selectedItem, oldValue, newValue) -> {
            if (newValue != null) {
                ViewSavedSessionData data = service.findSessionDataByName(newValue);
                updateRunData(data);
                updateLapChart(data);
                if (service.isHasSpeedTrapData()) {
                    clearContent(SPEED_TRAP_COLUMNS);
                    for (Map.Entry<Integer, Float> entry : data.getSpeedTrapByLap().entrySet()) {
                        SPEED_TRAP_COLUMNS[0].content().getChildren().add(new Label(String.valueOf(entry.getKey())));
                        SPEED_TRAP_COLUMNS[1].content().getChildren().add(new Label(String.valueOf(entry.getValue())));
                    }
                }
            }
        });
    }

    private void updateRunData(ViewSavedSessionData data) {
        clearContent(RUN_DATA_COLUMNS);
        for (RunDataMapRecord run : data.getLapsForSetup()) {
            for (IndividualLapSessionData lap : run.laps()) {
                RUN_DATA_COLUMNS[0].content().getChildren().add(new Label(String.valueOf(lap.getLapNum())));
                String setupTireValue = run.key().setupNumber() + "/" + VisualTireEnum.fromValue(lap.getVisualTire()).getDisplay();
                if (run.key().fittedTireId() >= 0) setupTireValue += " (" + run.key().fittedTireId() + ") ";
                RUN_DATA_COLUMNS[1].content().getChildren().add(new Label(setupTireValue));
                RUN_DATA_COLUMNS[2].content().getChildren().add(new Label(String.valueOf(lap.getLapTimeInMs())));
            }
        }
    }

    private void updateLapChart(ViewSavedSessionData data) {
        this.lineChart.getData().clear();
        this.lineChart.setTitle(service.getSearch().getLastName());
        List<XYChart.Series<Number, Number>> runs = new ArrayList<>();
        int lapCount = 0;
        int minTime = 0;
        int maxTime = 0;
        for (RunDataMapRecord run : data.getLapsForSetup()) {
            XYChart.Series<Number, Number> laps = new XYChart.Series<>();
            for (int i = 0; i < run.laps().size(); i++) {
                IndividualLapSessionData lap = run.laps().get(i);
                if (lap.getLapTimeInMs().intValue() < minTime || minTime == 0)
                    minTime = lap.getLapTimeInMs().intValue();
                if (lap.getLapTimeInMs().intValue() > maxTime || maxTime == 0)
                    maxTime = lap.getLapTimeInMs().intValue();
                XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(i + 1, lap.getLapTimeInMs());
                dataPoint.setExtraValue(VisualTireEnum.fromValue(lap.getVisualTire()).getDisplay());
                laps.getData().add(dataPoint);
            }
            if (run.laps().size() > lapCount) lapCount = run.laps().size();
            runs.add(laps);
        }
        xAxis.setUpperBound(lapCount + 1);
        yAxis.setLowerBound(minTime - .5);
        yAxis.setUpperBound(maxTime + 1);
        for (XYChart.Series<Number, Number> individual : runs) {
            this.lineChart.getData().add(individual);
        }
        Platform.runLater(() -> {
            for (XYChart.Series<Number, Number> individual : runs) {
                XYChart.Data<Number, Number> dataPoint0 = individual.getData().get(0);
                String styleClass = switch (dataPoint0.getExtraValue().toString()) {
                    case Constants.SUPER -> StyleConstants.RUN_DATA_SS.toLowerCase(Locale.ENGLISH);
                    case Constants.SOFT -> StyleConstants.RUN_DATA_S.toLowerCase(Locale.ENGLISH);
                    case Constants.MEDIUM -> StyleConstants.RUN_DATA_M.toLowerCase(Locale.ENGLISH);
                    case Constants.HARD -> StyleConstants.RUN_DATA_H.toLowerCase(Locale.ENGLISH);
                    case Constants.INTERMEDIATE -> StyleConstants.RUN_DATA_INTER.toLowerCase(Locale.ENGLISH);
                    case Constants.X_WET -> StyleConstants.RUN_DATA_WET.toLowerCase(Locale.ENGLISH);
                    default -> null;
                };
                if (styleClass != null) {
                    individual.getNode().getStyleClass().add(styleClass);
                    for (XYChart.Data<Number, Number> dataPoint : individual.getData()) {
                        dataPoint.getNode().getStyleClass().add(styleClass + StyleConstants.DASH + StyleConstants.DATA_POINT);
                    }
                }
            }
        });
    }
}

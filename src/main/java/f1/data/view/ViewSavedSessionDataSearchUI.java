package f1.data.view;

import f1.data.utils.constants.Constants;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewSavedSessionDataSearchUI {

    private final ViewSavedSessionDataService service;
    private final HBox searchOptions = new HBox(Constants.SPACING);
    private final ComboBox<String> setupNums = new ComboBox<>();

    private final String[] dryTires = {Constants.SUPER, Constants.SOFT, Constants.MEDIUM, Constants.HARD};
    private final String[] wetTires = {Constants.INTER, Constants.WET};

    public ViewSavedSessionDataSearchUI(ViewSavedSessionDataService service) {
        this.service = service;
        buildUI();
    }

    private void buildUI() {
        searchOptions.getChildren().add(buildDriverSearch());
        searchOptions.getChildren().add(buildTireSearch());
        searchOptions.getChildren().add(buildSetupSearch());
        searchOptions.getStyleClass().add("searchOptions");
    }

    //builds the driver search dropdown section of the search area.
    private VBox buildDriverSearch() {
        VBox driverSearch = new VBox(Constants.SPACING);
        driverSearch.getChildren().add(new Label("Driver"));
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(service.getDropdownOptions());
        comboBox.setValue("");
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                service.updateListView(newValue);
                comboBox.setValue(newValue);
            }
        });
        driverSearch.getChildren().add(comboBox);
        return driverSearch;
    }

    //Builds the tire search options
    private VBox buildTireSearch() {
        VBox tireSearch = new VBox(Constants.SPACING);
        tireSearch.getChildren().add(new Label("Tire Compound"));
        HBox tireTypes = new HBox(Constants.SPACING);
        VBox dry = new VBox(Constants.SPACING);
        for (String s : dryTires) {
            dry.getChildren().add(new CheckBox(s));
        }
        tireTypes.getChildren().add(dry);
        VBox wet = new VBox(Constants.SPACING);
        for (String s : wetTires) {
            wet.getChildren().add(new CheckBox(s));
        }
        tireTypes.getChildren().add(wet);
        tireSearch.getChildren().add(tireTypes);
        return tireSearch;
    }

    //Builds the setup search option
    private VBox buildSetupSearch() {
        VBox setup = new VBox(Constants.SPACING);
        setup.getChildren().add(new Label("Setup #"));
        setupNums.setValue("");
        setupNums.setItems(service.getSetupOptions());
        setup.getChildren().add(setupNums);
        return setup;
    }

    //returns the search options container.
    public HBox getSearchOptions() {
        return searchOptions;
    }

    public ComboBox<String> getSetupNums() {
        return setupNums;
    }
}

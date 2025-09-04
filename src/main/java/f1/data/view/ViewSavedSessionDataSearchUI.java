package f1.data.view;

import f1.data.utils.constants.Constants;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ViewSavedSessionDataSearchUI {

    private final ViewSavedSessionDataService service;
    private final HBox searchOptions = new HBox(Constants.SPACING);
    private final ComboBox<String> setupNums = new ComboBox<>();

    private final CheckBox superSoftCheck = new CheckBox(Constants.SUPER);
    private final CheckBox softCheck = new CheckBox(Constants.SOFT);
    private final CheckBox mediumCheck = new CheckBox(Constants.MEDIUM);
    private final CheckBox hardCheck = new CheckBox(Constants.HARD);

    private final CheckBox interCheck = new CheckBox(Constants.INTER);
    private final CheckBox wetCheck = new CheckBox(Constants.WET);

    private final ArrayList<CheckBox> dryBoxes = new ArrayList<>(4);
    private final ArrayList<CheckBox> wetBoxes = new ArrayList<>(2);

    public ViewSavedSessionDataSearchUI(ViewSavedSessionDataService service) {
        this.service = service;
        dryBoxes.add(superSoftCheck);
        dryBoxes.add(softCheck);
        dryBoxes.add(mediumCheck);
        dryBoxes.add(hardCheck);
        wetBoxes.add(interCheck);
        wetBoxes.add(wetCheck);
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
        for (CheckBox c : dryBoxes) {
            dry.getChildren().add(c);
        }
        tireTypes.getChildren().add(dry);
        VBox wet = new VBox(Constants.SPACING);
        for (CheckBox c : wetBoxes) {
            wet.getChildren().add(c);
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

    public CheckBox getWetCheck() {
        return wetCheck;
    }

    public CheckBox getInterCheck() {
        return interCheck;
    }

    public CheckBox getHardCheck() {
        return hardCheck;
    }

    public CheckBox getMediumCheck() {
        return mediumCheck;
    }

    public CheckBox getSoftCheck() {
        return softCheck;
    }

    public CheckBox getSuperSoftCheck() {
        return superSoftCheck;
    }
}

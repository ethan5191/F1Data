package f1.data.save.view;

import f1.data.utils.constants.Constants;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewSavedSessionDataSearchUI {

    private final ViewSaveSessionDataService service;
    private final HBox searchOptions = new HBox(Constants.SPACING);

    public ViewSavedSessionDataSearchUI(ViewSaveSessionDataService service) {
        this.service = service;
        buildUI();
    }

    private void buildUI() {
        VBox driverSearch = new VBox(Constants.SPACING);
        driverSearch.getChildren().add(new Label("Driver"));
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(service.getDropdownOptions());
        comboBox.setValue("");
//        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                drivers.clear();
//                if (newValue.isEmpty()) {
//                    drivers.addAll(savedSessionData.keySet());
//                } else {
//                    drivers.add(newValue);
//                }
//                comboBox.setValue(newValue);
//            }
//        });
        driverSearch.getChildren().add(comboBox);
        searchOptions.getChildren().add(driverSearch);

        VBox tireSearch = new VBox(Constants.SPACING);
        tireSearch.getChildren().add(new Label("Tire Compound"));
        HBox tireTypes = new HBox(Constants.SPACING);
        VBox dry = new VBox(Constants.SPACING);
        dry.getChildren().add(new CheckBox(Constants.SUPER));
        dry.getChildren().add(new CheckBox(Constants.SOFT));
        dry.getChildren().add(new CheckBox(Constants.MEDIUM));
        dry.getChildren().add(new CheckBox(Constants.HARD));
        tireTypes.getChildren().add(dry);
        VBox wet = new VBox(Constants.SPACING);
        wet.getChildren().add(new CheckBox(Constants.INTER));
        wet.getChildren().add(new CheckBox(Constants.WET));
        tireTypes.getChildren().add(wet);
        tireSearch.getChildren().add(tireTypes);
        searchOptions.getChildren().add(tireSearch);

        VBox setup = new VBox(Constants.SPACING);
        setup.getChildren().add(new Label("Setup #"));
        ComboBox<String> setupNums = new ComboBox<>();
        setupNums.setValue("");
        setupNums.getItems().add("");
        for (int i = 0; i <= service.getMaxSetups(); i++) {
            setupNums.getItems().add(String.valueOf(i));
        }
        setup.getChildren().add(setupNums);
        searchOptions.getChildren().add(setup);

        searchOptions.getStyleClass().add("searchOptions");
    }

    public HBox getSearchOptions() {
        return searchOptions;
    }
}

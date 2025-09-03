package f1.data.view;

import f1.data.save.RunDataSessionData;
import f1.data.save.SaveSessionDataWrapper;
import f1.data.save.SpeedTrapSessionData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class ViewSavedSessionDataService {

    private final String fileName;
    private final SaveSessionDataWrapper data;
    private final boolean hasSpeedTrapData;
    private final Map<String, ViewSavedSessionData> savedSessionData;
    private final ObservableList<String> drivers;
    private final ObservableList<String> dropdownOptions = FXCollections.observableArrayList("");
    private final int maxSetups;

    public ViewSavedSessionDataService(SaveSessionDataWrapper data, String fileName) {
        this.data = data;
        this.fileName = fileName;
        this.hasSpeedTrapData = data.speedTraps() != null;
        this.savedSessionData = buildViewData(data);
        this.drivers = FXCollections.observableArrayList(this.savedSessionData.keySet());
        this.dropdownOptions.addAll(this.drivers);
        this.maxSetups = findNumMaxSetups();
    }

    public String getFileName() {
        return fileName;
    }

    public SaveSessionDataWrapper getData() {
        return data;
    }

    public boolean isHasSpeedTrapData() {
        return hasSpeedTrapData;
    }

    public Map<String, ViewSavedSessionData> getSavedSessionData() {
        return savedSessionData;
    }

    public ObservableList<String> getDrivers() {
        return drivers;
    }

    public ObservableList<String> getDropdownOptions() {
        return dropdownOptions;
    }

    public int getMaxSetups() {
        return maxSetups;
    }

    private Map<String, ViewSavedSessionData> buildViewData(SaveSessionDataWrapper data) {
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

    private int findNumMaxSetups() {
        int result = -1;
        for (RunDataSessionData runData : data.runData()) {
            if (runData.setups().size() - 1 > result) result = runData.setups().size() - 1;
        }
        return result;
    }

    public void updateListView(String newValue) {
        this.drivers.clear();
        if (newValue.isEmpty()) {
            this.drivers.addAll(this.savedSessionData.keySet());
        } else {
            this.drivers.add(newValue);
        }
    }

    public ViewSavedSessionData findSessionDataByName(String newValue) {
        return this.savedSessionData.get(newValue);
    }
}

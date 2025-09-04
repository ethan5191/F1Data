package f1.data.view;

import f1.data.enums.VisualTireEnum;
import f1.data.save.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class ViewSavedSessionDataService {

    private final String fileName;
    private final SaveSessionDataWrapper data;
    private final boolean hasSpeedTrapData;
    private final Map<String, ViewSavedSessionData> savedSessionData;
    private final ObservableList<String> drivers;
    private final ObservableList<String> dropdownOptions = FXCollections.observableArrayList("");
    private final ObservableList<String> setupOptions = FXCollections.observableArrayList("");
    private final int maxSetups;

    private final SaveSessionSearchOptions search = new SaveSessionSearchOptions();

    //Valid enum values for each of the individual check boxes for tire search.
    private final Set<Integer> INTERS = Set.of(VisualTireEnum.INTER.getValue());
    private final Set<Integer> WETS = Set.of(VisualTireEnum.W.getValue(), VisualTireEnum.X_WET.getValue());
    private final Set<Integer> SUPER_SOFT = Set.of(VisualTireEnum.SS.getValue(), VisualTireEnum.SS_F2_20.getValue(), VisualTireEnum.SS_F2_19.getValue());
    private final Set<Integer> SOFT = Set.of(VisualTireEnum.SOFT.getValue(), VisualTireEnum.S.getValue(), VisualTireEnum.S_F2_20.getValue(), VisualTireEnum.S_F2_19.getValue());
    private final Set<Integer> MEDIUM = Set.of(VisualTireEnum.MEDIUM.getValue(), VisualTireEnum.M.getValue(), VisualTireEnum.M_F2_20.getValue(), VisualTireEnum.M_F2_19.getValue());
    private final Set<Integer> HARD = Set.of(VisualTireEnum.HARD.getValue(), VisualTireEnum.H.getValue(), VisualTireEnum.H_F2_20.getValue(), VisualTireEnum.H_F2_19.getValue());

    public ViewSavedSessionDataService(SaveSessionDataWrapper data, String fileName) {
        this.data = data;
        this.fileName = fileName;
        this.hasSpeedTrapData = data.speedTraps() != null;
        this.savedSessionData = buildViewData(data);
        this.drivers = FXCollections.observableArrayList(this.savedSessionData.keySet());
        this.dropdownOptions.addAll(this.drivers);
        this.maxSetups = findNumMaxSetups();
        for (int i = 0; i <= this.maxSetups; i++) {
            this.setupOptions.add(String.valueOf(i));
        }
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

    public ObservableList<String> getSetupOptions() {
        return setupOptions;
    }

    public int getMaxSetups() {
        return maxSetups;
    }

    public SaveSessionSearchOptions getSearch() {
        return search;
    }

    //Takes the wrapper object and builds out a map so you can find data based on the drivers last name.
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

    //Finds the maximum number of setups used by a single driver.
    private int findNumMaxSetups() {
        int result = -1;
        for (RunDataSessionData runData : data.runData()) {
            if (runData.setups().size() - 1 > result) result = runData.setups().size() - 1;
        }
        return result;
    }

    //used to update the list view based on the selection made in the driver dropdown.
    public void updateListView(String newValue) {
        this.drivers.clear();
        if (newValue.isEmpty()) {
            this.drivers.addAll(this.savedSessionData.keySet());
        } else {
            this.drivers.add(newValue);
        }
    }

    //Called by the setup dropdown change and the checkbox events. The name isn't known by the front end, so it uses the service's value, if it has one.
    public ViewSavedSessionData findSessionDataByName() {
        return findSessionDataByName(search.getLastName());
    }

    //Used to find the save session data based on the last name clicked in the list view.
    public ViewSavedSessionData findSessionDataByName(String newValue) {
        if (newValue == null || newValue.isEmpty()) return null;
        search.setLastName(newValue);
        if (this.search.noSearchOptions()) {
            return this.savedSessionData.get(newValue);
        } else {
            ViewSavedSessionData copy = new ViewSavedSessionData(this.savedSessionData.get(newValue));
            if (this.search.getSetupId() != null) {
                copy.getLapsForSetup().removeIf(record -> record.key().setupNumber() != Integer.parseInt(this.search.getSetupId()));
            }
            //Are any of the tire search options checked, if so we need to ensure only that compound is shown in the result set.
            if (this.search.hasTireSearch()) {
                Set<Integer> validCompounds = buildValidCompoundsSet();
                List<RunDataMapRecord> recsToRemove = findRunDataToRemove(copy, validCompounds);
                //Remove any runs from the list here outside the loop.
                if (!recsToRemove.isEmpty()) copy.getLapsForSetup().removeAll(recsToRemove);
            }
            return copy;
        }
    }

    private Set<Integer> buildValidCompoundsSet() {
        Set<Integer> validCompounds = new HashSet<>();
        //for each compound option that is checked, add its valid compounds to the set.
        if (this.search.isInter()) validCompounds.addAll(INTERS);
        if (this.search.isWet()) validCompounds.addAll(WETS);
        if (this.search.isSuperSoft()) validCompounds.addAll(SUPER_SOFT);
        if (this.search.isSoft()) validCompounds.addAll(SOFT);
        if (this.search.isMedium()) validCompounds.addAll(MEDIUM);
        if (this.search.isHard()) validCompounds.addAll(HARD);
        return validCompounds;
    }

    private List<RunDataMapRecord> findRunDataToRemove(ViewSavedSessionData copy, Set<Integer> validCompounds) {
        List<RunDataMapRecord> recsToRemove = new ArrayList<>();
        //Loop over each run. For each run all the laps are on the same compound so the first lap will tell us if the run meets the compound requirement or not.
        for (RunDataMapRecord run : copy.getLapsForSetup()) {
            IndividualLapSessionData lap = run.laps().get(0);
            //If the valid compounds set doesn't contain this compound then we need to remove this run so it doesn't det displayed.
            if (!validCompounds.contains(lap.getVisualTire())) recsToRemove.add(run);
        }
        return recsToRemove;
    }
}

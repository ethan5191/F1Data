package f1.data.save.view;

import f1.data.parse.packets.CarSetupData;
import f1.data.save.RunDataMapRecord;

import java.util.List;
import java.util.Map;

public class ViewSavedSessionData {

    private final String lastName;
    private Map<Integer, Float> speedTrapByLap;
    private List<CarSetupData> setups;
    private List<RunDataMapRecord> lapsForSetup;

    public ViewSavedSessionData(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<Integer, Float> getSpeedTrapByLap() {
        return speedTrapByLap;
    }

    public void setSpeedTrapByLap(Map<Integer, Float> speedTrapByLap) {
        this.speedTrapByLap = speedTrapByLap;
    }

    public List<CarSetupData> getSetups() {
        return setups;
    }

    public void setSetups(List<CarSetupData> setups) {
        this.setups = setups;
    }

    public List<RunDataMapRecord> getLapsForSetup() {
        return lapsForSetup;
    }

    public void setLapsForSetup(List<RunDataMapRecord> lapsForSetup) {
        this.lapsForSetup = lapsForSetup;
    }
}

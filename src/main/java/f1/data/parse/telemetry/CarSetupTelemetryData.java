package f1.data.parse.telemetry;

import f1.data.parse.individualLap.IndividualLapInfo;
import f1.data.parse.packets.CarSetupData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarSetupTelemetryData {

    private Integer currentSetupNumber = 0;
    private Integer fittedTireId = -1;
    private CarSetupData currentSetup;
    private SetupTireKey currentLapsPerSetupKey;
    private final List<CarSetupData> setups = new ArrayList<>();
    private final Map<SetupTireKey, List<IndividualLapInfo>> lapsPerSetup = new HashMap<>();
    private boolean setupChange;

    public CarSetupTelemetryData() {
    }

    public Integer getCurrentSetupNumber() {
        return currentSetupNumber;
    }

    public void setFittedTireId(Integer fittedTireId) {
        this.fittedTireId = fittedTireId;
    }

    public Integer getFittedTireId() {
        return fittedTireId;
    }

    public CarSetupData getCurrentSetup() {
        return currentSetup;
    }

    public void setCurrentSetup(CarSetupData currentSetup) {
        this.currentSetup = currentSetup;
    }

    public SetupTireKey getCurrentLapsPerSetupKey() {
        return currentLapsPerSetupKey;
    }

    public void setCurrentLapsPerSetupKey(SetupTireKey currentLapsPerSetupKey) {
        this.currentLapsPerSetupKey = currentLapsPerSetupKey;
    }

    public List<CarSetupData> getSetups() {
        return setups;
    }

    public Map<SetupTireKey, List<IndividualLapInfo>> getLapsPerSetup() {
        return lapsPerSetup;
    }

    public boolean isSetupChange() {
        return setupChange;
    }

    public void setSetupChange(boolean setupChange) {
        this.setupChange = setupChange;
    }

    //Creates the initial params for this object on the first setup that needs to be saved.
    protected void initialSetup(CarSetupData currentSetup) {
        this.currentSetup = currentSetup;
        this.setups.add(this.currentSetup);
        //Create this key as it's a key to ensure the setup and tire info are correctly recorded.
        this.currentLapsPerSetupKey = new SetupTireKey(this.currentSetupNumber, this.fittedTireId);
        this.lapsPerSetup.put(this.currentLapsPerSetupKey, new ArrayList<>());
    }

    //Update the car setup information.
    protected void updateCarSetupData(CarSetupData currentSetup) {
        boolean foundSetup = false;
        boolean sameTire = false;
        for (int i = 0; i < this.setups.size(); i++) {
            //Does this setup already exist. If so then we need to update the params to use that setups' info.
            if (this.setups.get(i).equals(currentSetup)) {
                this.currentSetupNumber = i;
                this.currentSetup = this.setups.get(i);
                //Key to ensure a tire change with the same setup is treated as a separate run.
                SetupTireKey temp = new SetupTireKey(this.currentSetupNumber, this.fittedTireId);
                //If this key exists already then update the objects key so we add the info the correct list.
                if (this.lapsPerSetup.containsKey(temp)) {
                    this.currentLapsPerSetupKey = temp;
                    sameTire = true;
                }
                foundSetup = true;
                break;
            }
        }
        //If we didn't find a setup, or it's not the same tire, then some of this logic needs to happen.
        if (!foundSetup || !sameTire) {
            //if we didn't find a setup then we need to update the currentSetupNumber and then add the setup to the list.
            if (!foundSetup) {
                this.currentSetupNumber = this.setups.size();
                this.setups.add(currentSetup);
            }
            //for either statement we need to do the following lines.
            this.currentSetup = currentSetup;
            this.currentLapsPerSetupKey = new SetupTireKey(this.currentSetupNumber, this.fittedTireId);
            this.lapsPerSetup.put(this.currentLapsPerSetupKey, new ArrayList<>());
        }
    }
}

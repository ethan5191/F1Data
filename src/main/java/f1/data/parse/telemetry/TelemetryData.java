package f1.data.parse.telemetry;

import f1.data.enums.DriverStatusEnum;
import f1.data.parse.individualLap.IndividualLapInfo;
import f1.data.parse.packets.*;
import f1.data.utils.constants.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelemetryData {

    public TelemetryData(ParticipantData participantData) {
        this.participantData = participantData;
    }

    private final ParticipantData participantData;
    private Integer currentSetupNumber = 0;
    private CarSetupData currentSetup;
    private Integer lastLapNum;
    private BigDecimal lastLapTimeInMs;
    private float[] currentTireWear = {0, 0, 0, 0};
    private float[] startOfLapTireWear = {0, 0, 0, 0};
    private float currentFuelInTank;
    private float startOfLapFuelInTank = 0;
    private int fittedTireId;
    private boolean isSetupChange;

    private LapData currentLap;
    private CarTelemetryData currentTelemetry;
    private CarStatusData currentStatus;
    private CarDamageData currentDamage;
    private TireSetsData[] tireSetsData = new TireSetsData[Constants.TIRE_SETS_PACKET_COUNT];

    private final List<CarSetupData> setups = new ArrayList<>();
    private SetupTireKey currentLapsPerSetupKey;
    private final Map<SetupTireKey, List<IndividualLapInfo>> lapsPerSetup = new HashMap<>();

    private SpeedTrapTelemetryData speedTrapData;

    public ParticipantData getParticipantData() {
        return participantData;
    }

    public Integer getCurrentSetupNumber() {
        return currentSetupNumber;
    }

    public CarSetupData getCurrentSetup() {
        return currentSetup;
    }

    public void setCurrentSetup(CarSetupData currentSetup) {
        //Only do the setup logic when the car is on a flying lap. Otherwise, you can end up with a bunch of different setups saved with no data.
        if (this.currentLap != null && this.currentLap.driverStatus() == DriverStatusEnum.FLYING_LAP.getValue()) {
            //If we haven't saved a setup yet, then save it to the list and the map.
            if (this.setups.isEmpty()) {
                this.setups.add(currentSetup);
                this.currentSetup = currentSetup;
                this.currentLapsPerSetupKey = new SetupTireKey(this.currentSetupNumber, this.fittedTireId);
                this.getLapsPerSetup().put(this.currentLapsPerSetupKey, new ArrayList<>());
            } else {
                boolean foundSetup = false;
                boolean sameTire = false;
                //We have saved setups, make sure this one isn't one fo them. If it is, update the setupNumber and set that setup as current.
                for (int i = 0; i < setups.size(); i++) {
                    if (setups.get(i).equals(currentSetup)) {
                        this.currentSetupNumber = i;
                        this.currentSetup = setups.get(i);
                        //create a new map key.
                        SetupTireKey temp = new SetupTireKey(this.currentSetupNumber, this.fittedTireId);
                        //If that map key is in the map already, then set it to the object param and flip the boolean.
                        if (this.lapsPerSetup.containsKey(temp)) {
                            sameTire = true;
                            this.currentLapsPerSetupKey = temp;
                        }
                        foundSetup = true;
                        break;
                    }
                }
                //If we didn't find a setup, then we need to set the number = to the size of the array, ensuring we don't overwrite ourselves.
                //Then add this new setup to the list, create a record in the amp, and set it as the active setup.
                if (!foundSetup || !sameTire) {
                    //If we didn't find a setup then we need to update the setupNumber and add this setup to the list.
                    if (!foundSetup) {
                        this.currentSetupNumber = this.setups.size();
                        this.setups.add(currentSetup);
                    }
                    //if either of those booleans are false, we always do these 3 lines.
                    this.currentSetup = currentSetup;
                    this.currentLapsPerSetupKey = new SetupTireKey(this.currentSetupNumber, this.fittedTireId);
                    this.getLapsPerSetup().put(this.currentLapsPerSetupKey, new ArrayList<>());
                }
            }
        }
    }

    public Integer getLastLapNum() {
        return lastLapNum;
    }

    public void setLastLapNum(Integer lastLapNum) {
        this.lastLapNum = lastLapNum;
    }

    public BigDecimal getLastLapTimeInMs() {
        return lastLapTimeInMs;
    }

    public void setLastLapTimeInMs(BigDecimal lastLapTimeInMs) {
        this.lastLapTimeInMs = lastLapTimeInMs;
    }

    public float[] getCurrentTireWear() {
        return currentTireWear;
    }

    public float[] getStartOfLapTireWear() {
        return startOfLapTireWear;
    }

    public void setStartOfLapTireWear(float[] startOfLapTireWear) {
        this.startOfLapTireWear = (startOfLapTireWear == null) ? new float[4] : startOfLapTireWear;
    }

    public float getCurrentFuelInTank() {
        return currentFuelInTank;
    }

    public float getStartOfLapFuelInTank() {
        return startOfLapFuelInTank;
    }

    public void setStartOfLapFuelInTank(float startOfLapFuelInTank) {
        this.startOfLapFuelInTank = startOfLapFuelInTank;
    }

    public int getFittedTireId() {
        return fittedTireId;
    }

    public void setFittedTireId(int fittedTireId) {
        this.fittedTireId = fittedTireId;
    }

    public boolean isSetupChange() {
        return isSetupChange;
    }

    public void setSetupChange(boolean setupChange) {
        isSetupChange = setupChange;
    }

    public LapData getCurrentLap() {
        return currentLap;
    }

    public void setCurrentLap(LapData currentLap) {
        this.currentLap = currentLap;
    }

    public CarTelemetryData getCurrentTelemetry() {
        return currentTelemetry;
    }

    public void setCurrentTelemetry(CarTelemetryData currentTelemetry) {
        this.currentTelemetry = currentTelemetry;
    }

    public CarStatusData getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CarStatusData currentStatus) {
        this.currentStatus = currentStatus;
        //Only update these values when we are on an actual flying lap.
        if (this.currentLap != null) {
            if (this.currentLap.driverStatus() == DriverStatusEnum.FLYING_LAP.getValue()) {
                this.currentFuelInTank = currentStatus.fuelInTank();
                //default value is -1, if it is that then we need to seed the initial with the current fuel in the tank.
                if (this.startOfLapFuelInTank == -1) {
                    this.startOfLapFuelInTank = this.currentFuelInTank;
                }
            } else if (this.currentLap.driverStatus() == DriverStatusEnum.IN_GARAGE.getValue() || this.currentLap.driverStatus() == DriverStatusEnum.IN_LAP.getValue()) {
                this.startOfLapFuelInTank = 0;
            } else if (this.currentLap.driverStatus() == DriverStatusEnum.OUT_LAP.getValue()) {
                //while on an out lap, theses two values should remain the same, that way the first 'official'
                //lap of the run has the proper start fuel value.
                this.startOfLapFuelInTank = currentStatus.fuelInTank();
                this.currentFuelInTank = currentStatus.fuelInTank();
            }
        }
    }

    public CarDamageData getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(CarDamageData currentDamage) {
        this.currentDamage = currentDamage;
        //Only update these values when we are on an actual flying lap.
        if (this.currentLap != null) {
            if (this.currentLap.driverStatus() == DriverStatusEnum.FLYING_LAP.getValue()) {
                this.currentTireWear = currentDamage.tyresWear();
            } else if (this.currentLap.driverStatus() == DriverStatusEnum.IN_GARAGE.getValue() || this.currentLap.driverStatus() == DriverStatusEnum.IN_LAP.getValue()) {
                this.startOfLapTireWear = new float[]{0, 0, 0, 0};
            } else if (this.currentLap.driverStatus() == DriverStatusEnum.OUT_LAP.getValue()) {
                //while on an out lap, theses two values should remain the same, that way the first 'official'
                //lap of the run has the proper start fuel value.
                this.startOfLapTireWear = currentDamage.tyresWear();
                this.currentTireWear = currentDamage.tyresWear();
            }
        }
    }

    public TireSetsData[] getTireSetsData() {
        return tireSetsData;
    }

    public void setTireSetsData(TireSetsData[] tireSetsData) {
        this.tireSetsData = tireSetsData;
    }

    public List<CarSetupData> getSetups() {
        return setups;
    }

    public SetupTireKey getCurrentLapsPerSetupKey() {
        return currentLapsPerSetupKey;
    }

    public Map<SetupTireKey, List<IndividualLapInfo>> getLapsPerSetup() {
        return lapsPerSetup;
    }

    public SpeedTrapTelemetryData getSpeedTrapData() {
        return speedTrapData;
    }

    public void setSpeedTrapData(SpeedTrapTelemetryData speedTrapData) {
        this.speedTrapData = speedTrapData;
    }
}

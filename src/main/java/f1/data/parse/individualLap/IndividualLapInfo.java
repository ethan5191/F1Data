package f1.data.parse.individualLap;

import f1.data.parse.packets.LapData;
import f1.data.parse.telemetry.SetupTireKey;
import f1.data.save.IndividualLapSessionData;

//Used to represent an individual laps data for an individual car. Idea is this will be populated at the end of the lap.
//Used to display data to the different panels, uses the save data object as its seed for the majority of its data.
public class IndividualLapInfo {

    //ld is the current lap, which should be a newly started lap.
    //prevLap is the last LapData from the telemetry object, which has the sector 1 and 2 times in it.
    public IndividualLapInfo(IndividualLapSessionData individualLap, LapData ld) {
        this.individualLap = individualLap;
        this.useLegacy = (ld.lastLapTimeMs() == 0 && ld.lastLapTime20() > 0);
        this.nonRoundedLapTime = (this.useLegacy) ? ld.lastLapTime20() : ld.lastLapTimeMs();
    }

    private final IndividualLapSessionData individualLap;

    private final float nonRoundedLapTime;

    //Indicates this is from 2020 or earlier, which doesn't require a division to determine full lap time.
    private final boolean useLegacy;

    private int totalLapsThisSetup;
    private int currentSetupNumber;
    private SetupTireKey currentSetupKey;
    private CarSetupInfo carSetupInfo;
    private CarTelemetryInfo carTelemetryInfo;

    private boolean isSetupChange;

    private CarStatusInfo carStatusInfo;

    private CarDamageInfo carDamageInfo;

    public IndividualLapSessionData getIndividualLap() {
        return individualLap;
    }

    public float getNonRoundedLapTime() {
        return nonRoundedLapTime;
    }

    public boolean isUseLegacy() {
        return useLegacy;
    }

    public int getTotalLapsThisSetup() {
        return totalLapsThisSetup;
    }

    public void setTotalLapsThisSetup(int totalLapsThisSetup) {
        this.totalLapsThisSetup = totalLapsThisSetup;
    }

    public int getCurrentSetupNumber() {
        return currentSetupNumber;
    }

    public void setCurrentSetupNumber(int currentSetupNumber) {
        this.currentSetupNumber = currentSetupNumber;
    }

    public SetupTireKey getCurrentSetupKey() {
        return currentSetupKey;
    }

    public void setCurrentSetupKey(SetupTireKey currentSetupKey) {
        this.currentSetupKey = currentSetupKey;
    }

    public CarSetupInfo getCarSetupInfo() {
        return carSetupInfo;
    }

    public void setCarSetupInfo(CarSetupInfo carSetupInfo) {
        this.carSetupInfo = carSetupInfo;
    }

    public CarTelemetryInfo getCarTelemetryInfo() {
        return carTelemetryInfo;
    }

    public void setCarTelemetryInfo(CarTelemetryInfo carTelemetryInfo) {
        this.carTelemetryInfo = carTelemetryInfo;
    }

    public CarStatusInfo getCarStatusInfo() {
        return carStatusInfo;
    }

    public void setCarStatusInfo(CarStatusInfo carStatusInfo) {
        this.carStatusInfo = carStatusInfo;
    }

    public CarDamageInfo getCarDamageInfo() {
        return carDamageInfo;
    }

    public void setCarDamageInfo(CarDamageInfo carDamageInfo) {
        this.carDamageInfo = carDamageInfo;
    }

    public boolean isSetupChange() {
        return isSetupChange;
    }

    public void setSetupChange(boolean setupChange) {
        isSetupChange = setupChange;
    }
}

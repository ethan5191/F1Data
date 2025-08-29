package f1.data.ui.panels;

import f1.data.ui.panels.dashboards.DashboardUtils;
import f1.data.ui.panels.dto.DriverDataDTO;

public class RunDataAverage {

    //Used to create a new average object off of just the DTO and start lap.
    //Either the first lap or a new setup change will cause this.
    public RunDataAverage(Integer startLap, Integer totalLaps, DriverDataDTO dto, boolean isF1) {
        this.isF1 = isF1;
        this.startLap = startLap;
        this.completedLap = dto.getInfo().getIndividualLap().getLapNum();
        //Add 1 to this value to ensure we are always including the first lap we completed. Otherwise our total laps will be off by 1.
        this.totalLaps = totalLaps;
        this.totalTimeInMS = dto.getInfo().getNonRoundedLapTime();
        this.totalTireWear = dto.getInfo().getIndividualLap().getTireWear();
        this.totalFuelUsed = dto.getInfo().getIndividualLap().getFuelUsed();
        this.totalTrapSpeed = dto.getInfo().getIndividualLap().getSpeedTrap();
        if (this.isF1) {
            this.totalErsStoreEnergy = dto.getInfo().getCarStatusInfo().getErsStoreEnergy();
            this.totalErsHarvestedThisLapMGUK = dto.getInfo().getCarStatusInfo().getErsHarvestedThisLapMGUK();
            this.totalErsHarvestedThisLapMGUH = dto.getInfo().getCarStatusInfo().getErsHarvestedThisLapMGUH();
            this.totalErsDeployedThisLap = dto.getInfo().getCarStatusInfo().getErsDeployedThisLap();
        }
        calculateAvg();
    }

    //Used to create a new average object off of the startLap, dto, and existing averages.
    //This will happen anytime a setup has already been used and has laps completed.
    public RunDataAverage(Integer startLap, Integer totalLaps, DriverDataDTO dto, RunDataAverage current) {
        this.isF1 = current.isF1;
        this.startLap = startLap;
        this.completedLap = dto.getInfo().getIndividualLap().getLapNum();
        //Add 1 to this value to ensure we are always including the first lap we completed. Otherwise our total laps will be off by 1.
        this.totalLaps = totalLaps;
        this.totalTimeInMS = dto.getInfo().getNonRoundedLapTime() + current.totalTimeInMS;
        for (int i = 0; i < current.totalTireWear.length; i++) {
            this.totalTireWear[i] = current.totalTireWear[i] + dto.getInfo().getIndividualLap().getTireWear()[i];
        }
        this.totalFuelUsed = current.totalFuelUsed + dto.getInfo().getIndividualLap().getFuelUsed();
        this.totalTrapSpeed = current.getTotalTrapSpeed() + dto.getInfo().getIndividualLap().getSpeedTrap();
        if (this.isF1) {
            this.totalErsStoreEnergy = current.getTotalErsStoreEnergy() + dto.getInfo().getCarStatusInfo().getErsStoreEnergy();
            this.totalErsHarvestedThisLapMGUK = current.getTotalErsHarvestedThisLapMGUK() + dto.getInfo().getCarStatusInfo().getErsHarvestedThisLapMGUK();
            this.totalErsHarvestedThisLapMGUH = current.getTotalErsHarvestedThisLapMGUH() + dto.getInfo().getCarStatusInfo().getErsHarvestedThisLapMGUH();
            this.totalErsDeployedThisLap = current.getTotalErsDeployedThisLap() + dto.getInfo().getCarStatusInfo().getErsDeployedThisLap();
        }
        calculateAvg();
    }

    private final int completedLap;
    private final int startLap;
    private final int totalLaps;
    private final float totalTimeInMS;
    private float[] totalTireWear = new float[4];
    private final float totalFuelUsed;
    private final float totalTrapSpeed;
    private float totalErsStoreEnergy;
    private float totalErsHarvestedThisLapMGUK;
    private float totalErsHarvestedThisLapMGUH;
    private float totalErsDeployedThisLap;

    private final boolean isF1;

    private float avgLapTimeInMs;
    private final String[] avgTireWear = new String[4];
    private String avgFuelUsed;
    private String avgSpeedTrap;
    private float avgErsEnergyStore;
    private float avgErsHarvestedMGUK;
    private float avgErsHarvestedMGUH;
    private float avgErsDeployed;

    public int getCompletedLap() {
        return completedLap;
    }

    public int getStartLap() {
        return startLap;
    }

    public int getTotalLaps() {
        return totalLaps;
    }

    public float getTotalTimeInMS() {
        return totalTimeInMS;
    }

    public float[] getTotalTireWear() {
        return totalTireWear;
    }

    public float getTotalFuelUsed() {
        return totalFuelUsed;
    }

    public float getTotalTrapSpeed() {
        return totalTrapSpeed;
    }

    public float getTotalErsStoreEnergy() {
        return totalErsStoreEnergy;
    }

    public float getTotalErsHarvestedThisLapMGUK() {
        return totalErsHarvestedThisLapMGUK;
    }

    public float getTotalErsHarvestedThisLapMGUH() {
        return totalErsHarvestedThisLapMGUH;
    }

    public float getTotalErsDeployedThisLap() {
        return totalErsDeployedThisLap;
    }

    public float getAvgLapTimeInMs() {
        return avgLapTimeInMs;
    }

    public String[] getAvgTireWear() {
        return avgTireWear;
    }

    public String getAvgFuelUsed() {
        return avgFuelUsed;
    }

    public String getAvgSpeedTrap() {
        return avgSpeedTrap;
    }

    public float getAvgErsEnergyStore() {
        return avgErsEnergyStore;
    }

    public float getAvgErsHarvestedMGUK() {
        return avgErsHarvestedMGUK;
    }

    public float getAvgErsHarvestedMGUH() {
        return avgErsHarvestedMGUH;
    }

    public float getAvgErsDeployed() {
        return avgErsDeployed;
    }

    public boolean isF1() {
        return isF1;
    }

    //Calculates the average for the different data elements.
    private void calculateAvg() {
        this.avgLapTimeInMs = this.totalTimeInMS / this.totalLaps;
        for (int i = 0; i < this.totalTireWear.length; i++) {
            this.avgTireWear[i] = DashboardUtils.formatTwoDecimals((this.totalTireWear[i] / this.totalLaps));
        }
        this.avgFuelUsed = DashboardUtils.formatTwoDecimals((this.totalFuelUsed / this.totalLaps));
        this.avgSpeedTrap = DashboardUtils.formatTwoDecimals((this.totalTrapSpeed / this.totalLaps));
        if (this.isF1) {
            this.avgErsEnergyStore = this.totalErsStoreEnergy / this.totalLaps;
            this.avgErsHarvestedMGUK = this.totalErsHarvestedThisLapMGUK / this.totalLaps;
            this.avgErsHarvestedMGUH = this.totalErsHarvestedThisLapMGUH / this.totalLaps;
            this.avgErsDeployed = this.totalErsDeployedThisLap / this.totalLaps;
        }
    }
}

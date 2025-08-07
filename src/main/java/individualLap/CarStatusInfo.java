package individualLap;

import packets.CarStatusData;
import packets.enums.ActualTireEnum;
import packets.enums.VisualTireEnum;
import utils.Constants;

public class CarStatusInfo {

    public CarStatusInfo(CarStatusData csd) {
        this.fuelInTank = String.format(Constants.TWO_DECIMAL, csd.getFuelInTank());
        this.fuelRemainingLaps = String.format(Constants.TWO_DECIMAL, csd.getFuelRemainingLaps());
        this.actualTireCompound = csd.getActualTireCompound();
        this.visualTireCompound = csd.getVisualTireCompound();
        this.tiresAgeLaps = csd.getTiresAgeLaps();
        this.enginePowerICE = csd.getEnginePowerICE();
        this.enginePowerMGUK = csd.getEnginePowerMGUK();
        this.ersStoreEnergy = csd.getErsStoreEnergy();
        this.ersDeployMode = csd.getErsDeployMode();
        this.ersHarvestedThisLapMGUK = csd.getErsHarvestedThisLapMGUK();
        this.ersHarvestedThisLapMGUH = csd.getErsHarvestedThisLapMGUH();
        this.ersDeployedThisLap = csd.getErsDeployedThisLap();
    }

    private final String fuelInTank;
    private final String fuelRemainingLaps;
    private final int actualTireCompound;
    private final int visualTireCompound;
    private final int tiresAgeLaps;
    private final float enginePowerICE;
    private final float enginePowerMGUK;
    private final float ersStoreEnergy;
    private final int ersDeployMode;
    private final float ersHarvestedThisLapMGUK;
    private final float ersHarvestedThisLapMGUH;
    private final float ersDeployedThisLap;

    public String getFuelInTank() {
        return fuelInTank;
    }

    public String getFuelRemainingLaps() {
        return fuelRemainingLaps;
    }

    public int getActualTireCompound() {
        return actualTireCompound;
    }

    public int getVisualTireCompound() {
        return visualTireCompound;
    }

    public int getTiresAgeLaps() {
        return tiresAgeLaps;
    }

    public float getEnginePowerICE() {
        return enginePowerICE;
    }

    public float getEnginePowerMGUK() {
        return enginePowerMGUK;
    }

    public float getErsStoreEnergy() {
        return ersStoreEnergy;
    }

    public int getErsDeployMode() {
        return ersDeployMode;
    }

    public float getErsHarvestedThisLapMGUK() {
        return ersHarvestedThisLapMGUK;
    }

    public float getErsHarvestedThisLapMGUH() {
        return ersHarvestedThisLapMGUH;
    }

    public float getErsDeployedThisLap() {
        return ersDeployedThisLap;
    }

    public ActualTireEnum getActualTire() {
        return ActualTireEnum.fromValue(this.actualTireCompound);
    }

    public VisualTireEnum getVisualTire() {
        return VisualTireEnum.fromValue(this.visualTireCompound);
    }
}

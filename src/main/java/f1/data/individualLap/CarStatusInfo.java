package f1.data.individualLap;

import f1.data.packets.CarStatusData;
import f1.data.enums.ActualTireEnum;
import f1.data.enums.VisualTireEnum;
import f1.data.utils.constants.Constants;

public class CarStatusInfo {

    public CarStatusInfo(CarStatusData csd) {
        this.fuelInTank = String.format(Constants.TWO_DECIMAL, csd.fuelInTank());
        this.fuelRemainingLaps = String.format(Constants.TWO_DECIMAL, csd.fuelRemainingLaps());
        this.actualTireCompound = csd.actualTireCompound();
        this.visualTireCompound = csd.visualTireCompound();
        this.tiresAgeLaps = csd.tiresAgeLaps();
        this.enginePowerICE = csd.enginePowerICE();
        this.enginePowerMGUK = csd.enginePowerMGUK();
        this.ersStoreEnergy = csd.ersStoreEnergy();
        this.ersDeployMode = csd.ersDeployMode();
        this.ersHarvestedThisLapMGUK = csd.ersHarvestedThisLapMGUK();
        this.ersHarvestedThisLapMGUH = csd.ersHarvestedThisLapMGUH();
        this.ersDeployedThisLap = csd.ersDeployedThisLap();
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

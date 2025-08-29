package f1.data.save;

import f1.data.parse.packets.CarStatusData;
import f1.data.parse.packets.LapData;
import f1.data.utils.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;

//Used to save data after a session ends.
public class IndividualLapSessionData {

    private int lapNum;
    private BigDecimal sector1InMs;
    private BigDecimal sector2InMs;
    private BigDecimal sector3InMs;
    private BigDecimal lapTimeInMs;
    private float fuelUsed;
    private float[] tireWear;
    private float speedTrap;
    private int visualTire;
    private float ersStoreEnergy;
    private float ersHarvestedMGUK;
    private float ersHarvestedMGUH;
    private float ersDeployed;

    public IndividualLapSessionData() {
    }

    public IndividualLapSessionData(LapData ld, LapData prevLap, float speedTrap, float fuelUsedThisLap, float[] tireWearThisLap, CarStatusData csd) {
        this.lapNum = prevLap.currentLapNum();
        this.lapTimeInMs = determineLapTimeInMs(ld);
        this.sector1InMs = convertSectorToFullMs(prevLap.sector1TimeMinutesPart(), prevLap.sector1TimeMsPart());
        this.sector2InMs = convertSectorToFullMs(prevLap.sector2TimeMinutesPart(), prevLap.sector2TimeMsPart());
        this.sector3InMs = determineSector3TimeInMs(this.lapTimeInMs, this.sector1InMs.add(this.sector2InMs));
        this.speedTrap = speedTrap;
        this.fuelUsed = fuelUsedThisLap;
        this.tireWear = tireWearThisLap;
        this.visualTire = csd.visualTireCompound();
        this.ersStoreEnergy = csd.ersStoreEnergy();
        this.ersHarvestedMGUK = csd.ersHarvestedThisLapMGUK();
        this.ersHarvestedMGUH = csd.ersHarvestedThisLapMGUH();
        this.ersDeployed = csd.ersDeployedThisLap();
    }

    public int getLapNum() {
        return lapNum;
    }

    public BigDecimal getSector1InMs() {
        return sector1InMs;
    }

    public BigDecimal getSector2InMs() {
        return sector2InMs;
    }

    public BigDecimal getSector3InMs() {
        return sector3InMs;
    }

    public BigDecimal getLapTimeInMs() {
        return lapTimeInMs;
    }

    public float getFuelUsed() {
        return fuelUsed;
    }

    public float[] getTireWear() {
        return tireWear;
    }

    public float getSpeedTrap() {
        return speedTrap;
    }

    public int getVisualTire() {
        return visualTire;
    }

    public float getErsStoreEnergy() {
        return ersStoreEnergy;
    }

    public float getErsHarvestedMGUK() {
        return ersHarvestedMGUK;
    }

    public float getErsHarvestedMGUH() {
        return ersHarvestedMGUH;
    }

    public float getErsDeployed() {
        return ersDeployed;
    }

    private BigDecimal determineLapTimeInMs(LapData ld) {
        if (ld.lastLapTimeMs() == 0 && ld.lastLapTime20() > 0) {
            return BigDecimal.valueOf(ld.lastLapTime20()).setScale(3, RoundingMode.HALF_UP);
        } else {
            return Util.roundDecimal(BigDecimal.valueOf(ld.lastLapTimeMs()));
        }
    }

    private BigDecimal convertSectorToFullMs(int minutesPart, int msPart) {
        int sectorMinPart = minutesPart * 60;
        int totalSeconds = msPart + sectorMinPart;
        return Util.roundDecimal(new BigDecimal(totalSeconds));
    }

    private BigDecimal determineSector3TimeInMs(BigDecimal lapTimeInMs, BigDecimal sumS1AndS2) {
        return lapTimeInMs.subtract(sumS1AndS2);
    }
}

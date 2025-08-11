package individualLap;

import packets.CarSetupData;
import packets.LapData;
import packets.enums.TireBrakesOrderEnum;
import utils.Util;

import java.math.BigDecimal;

//Used to represent an individual laps data for an individual car. Idea is this will be populated at the end of the lap.
public class IndividualLapInfo {

    //ld is the current lap, which should be a newly started lap.
    //prevLap is the last LapData from the telemetry object, which has the sector 1 and 2 times in it.
    public IndividualLapInfo(LapData ld, LapData prevLap, float speedTrap, float fuelUsedThisLap, float[] tireWearThisLap) {
        this.lapNum = prevLap.getCurrentLapNum();
        this.nonRoundedLapTime = ld.getLastLapTimeMs();
        this.lapTimeInMs = Util.roundDecimal(new BigDecimal(ld.getLastLapTimeMs()));
        int sector1MinPart = prevLap.getSector1TimeMinutesPart() * 60;
        int s1 = prevLap.getSector1TimeMsPart() + sector1MinPart;
        this.sector1InMs = Util.roundDecimal(new BigDecimal(s1));
        int sector2MinPart = prevLap.getSector2TimeMinutesPart() * 60;
        int s2 = prevLap.getSector2TimeMsPart() + sector2MinPart;
        this.sector2InMs = Util.roundDecimal(new BigDecimal(s2));
        BigDecimal sumSectors = this.sector1InMs.add(this.sector2InMs);
        this.sector3InMs = this.lapTimeInMs.subtract(sumSectors);
        this.speedTrap = speedTrap;
        this.fuelUsedThisLap = fuelUsedThisLap;
        this.tireWearThisLap = tireWearThisLap;
    }

    //From LapData
    private final int lapNum;
    private final float nonRoundedLapTime;
    private final BigDecimal lapTimeInMs;
    private final BigDecimal sector1InMs;
    private final BigDecimal sector2InMs;
    //Calculated from lapTimeMs - (sector2InMs + sector1InMs)
    private final BigDecimal sector3InMs;

    //From SpeedTrap event
    private final float speedTrap;

    private final float fuelUsedThisLap;
    private final float[] tireWearThisLap;

    private CarSetupData carSetupData;
    private CarTelemetryInfo carTelemetryInfo;

    private boolean isSetupChange;

    private CarStatusInfo carStatusInfo;

    private CarDamageInfo carDamageInfo;

    public int getLapNum() {
        return lapNum;
    }

    public float getNonRoundedLapTime() {
        return nonRoundedLapTime;
    }

    public BigDecimal getLapTimeInMs() {
        return lapTimeInMs;
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

    public float getSpeedTrap() {
        return speedTrap;
    }

    public float getFuelUsedThisLap() {
        return fuelUsedThisLap;
    }

    public float[] getTireWearThisLap() {
        return tireWearThisLap;
    }

    public CarSetupData getCarSetupData() {
        return carSetupData;
    }

    public void setCarSetupData(CarSetupData carSetupData) {
        this.carSetupData = carSetupData;
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

    public void printInfo(String lastName) {
        CarTelemetryInfo cti = this.carTelemetryInfo;
        System.out.println();
        System.out.println(lastName + " Lap # " + this.lapNum + " Time " + this.lapTimeInMs +
                " 1st " + this.sector1InMs + " 2nd " + this.sector2InMs + " 3rd " + this.sector3InMs
                + " Speed Trap " + this.speedTrap + " Engine temp " + cti.getEngineTemp() + "\n");
        printLoop(cti.getBrakeTemps(), "Brakes");
        System.out.println("\n-----------------");
        printLoop(cti.getTireSurfaceTemps(), "Tire Surface");
        System.out.println("\n-----------------");
        printLoop(cti.getTireInnerTemps(), "Tire Inner");
        System.out.println("\n-----------------");
        System.out.println("Tire Pressure");
        printLoop(cti.getTirePressures());
    }

    public void printStatus(String lastName) {
        CarStatusInfo csi = this.carStatusInfo;
        System.out.println();
        System.out.println(lastName + " In Tank " + csi.getFuelInTank() + " Remain Lap " + csi.getFuelRemainingLaps()
                + " Actual Tire " + csi.getActualTire().getDisplay() + " Visual tire " + csi.getVisualTire().getDisplay() + " Tire Age " + csi.getTiresAgeLaps());
        System.out.println("ICE " + csi.getEnginePowerICE() + " MGUK " + csi.getEnginePowerMGUK() + " Store " + csi.getErsStoreEnergy() +
                " MGUK Harvest " + csi.getErsHarvestedThisLapMGUK() + " MGUH Harvested " + csi.getErsHarvestedThisLapMGUH() + " Deployed " + csi.getErsDeployedThisLap());
    }

    public void printDamage(String lastName) {
        CarDamageInfo cdi = this.carDamageInfo;
        System.out.println();
        System.out.println(lastName + " Floor " + cdi.getFloorDamage() + " Diffuser " + cdi.getDiffuserDamage());
        printLoop(cdi.getTyresWear());
        System.out.println("\n-----------------");
    }

    private void printLoop(int[] array, String header) {
        System.out.println(header);
        for (int i = 0; i < array.length; i++) {
            TireBrakesOrderEnum elem = TireBrakesOrderEnum.values()[i];
            System.out.print(elem.name() + " " + array[i] + " ");
        }
    }

    private void printLoop(float[] array) {
        for (int i = 0; i < array.length; i++) {
            TireBrakesOrderEnum elem = TireBrakesOrderEnum.values()[i];
            System.out.print(" " + elem + " " + array[i] + " ");
        }
    }

    private void printLoop(String[] array) {
        for (int i = 0; i < array.length; i++) {
            TireBrakesOrderEnum elem = TireBrakesOrderEnum.values()[i];
            System.out.print(" " + elem + " " + array[i] + " ");
        }
    }
}

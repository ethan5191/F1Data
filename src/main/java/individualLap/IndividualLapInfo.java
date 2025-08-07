package individualLap;

import packets.LapData;
import packets.enums.TireBrakesOrderEnum;

//Used to represent an individual laps data for an individual car. Idea is this will be populated at the end of the lap.
public class IndividualLapInfo {

    //ld is the current lap, which should be a newly started lap.
    //prevLap is the last LapData from the telemetry object, which has the sector 1 and 2 times in it.
    public IndividualLapInfo(LapData ld, LapData prevLap, float speedTrap) {
        this.lapNum = prevLap.getCurrentLapNum();
        this.lapTimeInMs = ld.getLastLapTimeMs();
        int sector1MinPart = prevLap.getSector1TimeMinutesPart() * 60;
        this.sector1InMs = prevLap.getSector1TimeMsPart() + sector1MinPart;
        int sector2MinPart = prevLap.getSector2TimeMinutesPart() * 60;
        this.sector2InMs = prevLap.getSector2TimeMsPart() + sector2MinPart;
        this.sector3InMs = this.lapTimeInMs - (this.sector1InMs + this.sector2InMs);
        this.speedTrap = speedTrap;
    }

    //From LapData
    private final int lapNum;
    private final long lapTimeInMs;
    private final long sector1InMs;
    private final long sector2InMs;
    //Calculated from lapTimeMs - (sector2InMs + sector1InMs)
    private final long sector3InMs;

    //From SpeedTrap event
    private final float speedTrap;

    private CarTelemetryInfo carTelemetryInfo;

    private CarStatusInfo carStatusInfo;

    private CarDamageInfo carDamageInfo;

    public int getLapNum() {
        return lapNum;
    }

    public long getLapTimeInMs() {
        return lapTimeInMs;
    }

    public long getSector1InMs() {
        return sector1InMs;
    }

    public long getSector2InMs() {
        return sector2InMs;
    }

    public long getSector3InMs() {
        return sector3InMs;
    }

    public float getSpeedTrap() {
        return speedTrap;
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
                + " Actual Tire " + csi.getActualTireCompound() + " Visual tire " + csi.getVisualTireCompound() + " Tire Age " + csi.getTiresAgeLaps());
        System.out.println("ICE " + csi.getEnginePowerICE() + " MGUK " + csi.getEnginePowerMGUK() + " Store " + csi.getErsStoreEnergy() +
                " MGUK Harvest " + csi.getErsHarvestedThisLapMGUK() + " MGUH Harvested " + csi.getErsHarvestedThisLapMGUH() + " Deployed " + csi.getErsDeployedThisLap());
    }

    public void printDamage(String lastName) {
        CarDamageInfo cdi = this.carDamageInfo;
        System.out.println();
        System.out.println(lastName + " Floor " + cdi.getFloorDamage() + " Diffuser " + cdi.getDiffuserDamage());
        printLoop(cdi.getTyresWear());
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
}

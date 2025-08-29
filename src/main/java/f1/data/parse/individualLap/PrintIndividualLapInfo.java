package f1.data.parse.individualLap;

import f1.data.enums.TireBrakesOrderEnum;

import java.math.BigDecimal;

public class PrintIndividualLapInfo {

    public static void printTelemetry(CarTelemetryInfo cti, String lastName, int lapNum, BigDecimal lapTimeInMs, BigDecimal sector1InMs, BigDecimal sector2InMs, BigDecimal sector3InMs, float speedTrap) {
        System.out.println();
        System.out.println(lastName + " Lap # " + lapNum + " Time " + lapTimeInMs +
                " 1st " + sector1InMs + " 2nd " + sector2InMs + " 3rd " + sector3InMs
                + " Speed Trap " + speedTrap + " Engine temp " + cti.getEngineTemp() + "\n");
        printLoop(cti.getBrakeTemps(), "Brakes");
        System.out.println("\n-----------------");
        printLoop(cti.getTireSurfaceTemps(), "Tire Surface");
        System.out.println("\n-----------------");
        printLoop(cti.getTireInnerTemps(), "Tire Inner");
        System.out.println("\n-----------------");
        System.out.println("Tire Pressure");
        printLoop(cti.getTirePressures());
    }

    public static void printStatus(CarStatusInfo csi, String lastName) {
        System.out.println();
        System.out.println(lastName + " In Tank " + csi.getFuelInTank() + " Remain Lap " + csi.getFuelRemainingLaps()
                + " Actual Tire " + csi.getActualTire().getDisplay() + " Visual tire " + csi.getVisualTire().getDisplay() + " Tire Age " + csi.getTiresAgeLaps());
        System.out.println("ICE " + csi.getEnginePowerICE() + " MGUK " + csi.getEnginePowerMGUK() + " Store " + csi.getErsStoreEnergy() +
                " MGUK Harvest " + csi.getErsHarvestedThisLapMGUK() + " MGUH Harvested " + csi.getErsHarvestedThisLapMGUH() + " Deployed " + csi.getErsDeployedThisLap());
    }

    public static void printDamage(CarDamageInfo cdi, String lastName) {
        System.out.println();
        System.out.println(lastName + " Floor " + cdi.getFloorDamage() + " Diffuser " + cdi.getDiffuserDamage());
        printLoop(cdi.getTyresWear());
        System.out.println("\n-----------------");
    }

    private static void printLoop(int[] array, String header) {
        System.out.println(header);
        for (int i = 0; i < array.length; i++) {
            TireBrakesOrderEnum elem = TireBrakesOrderEnum.values()[i];
            System.out.print(elem.name() + " " + array[i] + " ");
        }
    }

    private static void printLoop(float[] array) {
        for (int i = 0; i < array.length; i++) {
            TireBrakesOrderEnum elem = TireBrakesOrderEnum.values()[i];
            System.out.print(" " + elem + " " + array[i] + " ");
        }
    }

    private static void printLoop(String[] array) {
        for (int i = 0; i < array.length; i++) {
            TireBrakesOrderEnum elem = TireBrakesOrderEnum.values()[i];
            System.out.print(" " + elem + " " + array[i] + " ");
        }
    }
}

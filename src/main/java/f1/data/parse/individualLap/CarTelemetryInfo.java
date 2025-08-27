package f1.data.parse.individualLap;

import f1.data.parse.packets.CarTelemetryData;

public class CarTelemetryInfo {

    public CarTelemetryInfo(CarTelemetryData ctd) {
        this.brakeTemps = ctd.brakeTemps();
        this.tireSurfaceTemps = ctd.tireSurfaceTemps();
        this.tireInnerTemps = ctd.tireInnerTemps();
        this.engineTemp = ctd.engineTemp();
        this.tirePressures = ctd.tirePressure();
    }

    private final int[] brakeTemps;
    private final int[] tireSurfaceTemps;
    private final int[] tireInnerTemps;
    private final int engineTemp;
    private final float[] tirePressures;

    public int[] getBrakeTemps() {
        return brakeTemps;
    }

    public int[] getTireSurfaceTemps() {
        return tireSurfaceTemps;
    }

    public int[] getTireInnerTemps() {
        return tireInnerTemps;
    }

    public int getEngineTemp() {
        return engineTemp;
    }

    public float[] getTirePressures() {
        return tirePressures;
    }
}

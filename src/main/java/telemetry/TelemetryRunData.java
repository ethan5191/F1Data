package telemetry;

import individualLap.IndividualLapInfo;
import packets.CarSetupData;

import java.util.Map;
import java.util.TreeMap;

public class TelemetryRunData {

    private final CarSetupData carSetupData;
    private final TreeMap<Integer, IndividualLapInfo> lapData = new TreeMap<>();

    private final long startTime;

    public TelemetryRunData(CarSetupData carSetupData) {
        this.carSetupData = carSetupData;
        this.startTime = System.currentTimeMillis();
    }

    public CarSetupData getCarSetupData() {
        return carSetupData;
    }

    public TreeMap<Integer, IndividualLapInfo> getLapData() {
        return lapData;
    }

    public long getStartTime() {
        return startTime;
    }
}

package telemetry;

import individualLap.IndividualLapInfo;
import packets.CarSetupData;

import java.util.HashMap;
import java.util.Map;

public class TelemetryRunData {

    private final CarSetupData carSetupData;
    private final Map<Integer, IndividualLapInfo> lapData = new HashMap<>();

    private final long startTime;

    public TelemetryRunData(CarSetupData carSetupData) {
        this.carSetupData = carSetupData;
        this.startTime = System.currentTimeMillis();
    }

    public CarSetupData getCarSetupData() {
        return carSetupData;
    }

    public Map<Integer, IndividualLapInfo> getLapData() {
        return lapData;
    }

    public long getStartTime() {
        return startTime;
    }
}

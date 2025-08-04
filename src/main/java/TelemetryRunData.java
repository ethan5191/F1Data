import packets.CarSetupData;
import packets.LapData;

import java.util.ArrayList;
import java.util.List;

public class TelemetryRunData {

    private final CarSetupData carSetupData;
    private final List<LapData> lapDataList = new ArrayList<>();

    private final long startTime;

    public TelemetryRunData(CarSetupData carSetupData) {
        this.carSetupData = carSetupData;
        this.startTime = System.currentTimeMillis();
    }

    public CarSetupData getCarSetupData() {
        return carSetupData;
    }

    public List<LapData> getLapDataList() {
        return lapDataList;
    }

    public long getStartTime() {
        return startTime;
    }
}

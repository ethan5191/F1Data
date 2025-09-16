package f1.data.parse.packets.handlers;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.CarSetupData;
import f1.data.parse.packets.CarSetupDataFactory;
import f1.data.parse.packets.PacketUtils;
import f1.data.parse.telemetry.CarSetupTelemetryData;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.Util;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarSetupPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final int playerCarIndex;
    private final Map<Integer, TelemetryData> participants;
    private final CarSetupDataFactory factory;
    private final SupportedYearsEnum supportedYearsEnum;

    public CarSetupPacketHandler(int packetFormat, int playerCarIndex, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.playerCarIndex = playerCarIndex;
        this.participants = participants;
        this.factory = new CarSetupDataFactory(this.packetFormat);
        this.supportedYearsEnum = SupportedYearsEnum.fromYear(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int arraySize = Util.findArraySize(this.packetFormat, this.playerCarIndex);
            for (int i = 0; i < arraySize; i++) {
                CarSetupData csd = factory.build(byteBuffer);
                if (PacketUtils.validKey(participants, i)) {
                    TelemetryData td = participants.get(i);
                    CarSetupTelemetryData cstd = td.getCarSetupData();
                    boolean isNullOrChanged = (cstd.getCurrentSetup() == null || !csd.equals(cstd.getCurrentSetup()));
                    boolean isDiffTire = cstd.getCurrentLapsPerSetupKey() != null && cstd.getFittedTireId() != cstd.getCurrentLapsPerSetupKey().fittedTireId();
                    //if the setup is null (hasn't loaded yet), changed (material difference between saved and current setup data).
                    //OR the fuel load has changed (with no material difference).
                    //OR a tire change occurred (fitted id vs fitted id, so Soft 1 != soft 2)
                    //Then we need a new setup.
                    if (isNullOrChanged || !csd.isSameFuelLoad(cstd.getCurrentSetup()) || isDiffTire) {
                        td.setCurrentSetup(csd);
                        //Only these two checks actually flip this flag. Fuel load change doesn't trigger a run data change, for now anyway.
                        if (isNullOrChanged || isDiffTire) cstd.setSetupChange(true);
                    }
                }
            }
            //Trailing value, must be here to ensure the packet is fully parsed.
            //nextFrontWingVal was added in the 24 data as a param AFTER the 22 car setups had been processed.
            if (this.supportedYearsEnum.is2024OrLater()) {
                float nextFronWingVal = byteBuffer.getFloat();
            }
        }
    }
}

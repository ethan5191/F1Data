package f1.data.parse.packets.handlers;

import f1.data.parse.packets.CarSetupData;
import f1.data.parse.packets.CarSetupDataFactory;
import f1.data.parse.packets.PacketUtils;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarSetupPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;

    public CarSetupPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.participants = participants;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.F1_25_AND_EARLIER_CAR_COUNT; i++) {
                boolean isValidKey = PacketUtils.validKey(participants, i);
                //If this isn't a valid key, we still need to parse the packet to ensure the position in the parser is updated.
                //Pass an empty string as this setup isn't going to be saved anywhere, so we don't care about the value.
                String setupName = (isValidKey) ? participants.get(i).getParticipantData().lastName() : "";
                CarSetupData csd = CarSetupDataFactory.build(packetFormat, byteBuffer, setupName);
                if (isValidKey) {
                    TelemetryData td = participants.get(i);
                    boolean isNullOrChanged = (td.getCurrentSetup() == null || !csd.equals(td.getCurrentSetup()));
                    boolean isDiffTire = td.getCurrentLapsPerSetupKey() != null && td.getFittedTireId() != td.getCurrentLapsPerSetupKey().fittedTireId();
                    //if the setup is null (hasn't loaded yet), changed (material difference between saved and current setup data).
                    //OR the fuel load has changed (with no material difference).
                    //OR a tire change occurred (fitted id vs fitted id, so Soft 1 != soft 2)
                    //Then we need a new setup.
                    if (isNullOrChanged || !csd.isSameFuelLoad(td.getCurrentSetup()) || isDiffTire) {
                        td.setCurrentSetup(csd);
                        //Only these two checks actually flip this flag. Fuel load change doesn't trigger a run data change, for now anyway.
                        if (isNullOrChanged || isDiffTire) td.setSetupChange(true);
                    }
                }
            }
            //Trailing value, must be here to ensure the packet is fully parsed.
            //nextFrontWingVal was added in the 24 data as a param AFTER the 22 car setups had been processed.
            if (packetFormat >= Constants.YEAR_2024) {
                float nextFronWingVal = byteBuffer.getFloat();
            }
        }
    }
}

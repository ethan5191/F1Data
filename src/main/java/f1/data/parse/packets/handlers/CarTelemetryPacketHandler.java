package f1.data.parse.packets.handlers;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.CarTelemetryData;
import f1.data.parse.packets.CarTelemetryDataFactory;
import f1.data.parse.packets.PacketUtils;
import f1.data.parse.packets.events.ButtonsData;
import f1.data.parse.packets.events.ButtonsDataFactory;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.Util;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.util.Map;

public class CarTelemetryPacketHandler implements PacketHandler, PauseActionHandler {

    private final int packetFormat;
    private final int playerCarIndex;
    private final Map<Integer, TelemetryData> participants;
    private final CarTelemetryDataFactory factory;
    private final ButtonsDataFactory buttonsDataFactory;
    private final SupportedYearsEnum supportedYearsEnum;

    private boolean isPause = false;

    public CarTelemetryPacketHandler(int packetFormat, int playerCarIndex, Map<Integer, TelemetryData> participants) {
        this.packetFormat = packetFormat;
        this.playerCarIndex = playerCarIndex;
        this.participants = participants;
        this.factory = new CarTelemetryDataFactory(this.packetFormat);
        this.buttonsDataFactory = new ButtonsDataFactory(this.packetFormat);
        this.supportedYearsEnum = SupportedYearsEnum.fromYear(this.packetFormat);
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            int arraySize = Util.findArraySize(this.packetFormat, this.playerCarIndex);
            for (int i = 0; i < arraySize; i++) {
                CarTelemetryData ctd = factory.build(byteBuffer);
                if (PacketUtils.validKey(participants, i)) {
                    participants.get(i).setCurrentTelemetry(ctd);
                }
            }
        }
        //Params at the end of the Telemetry packet, not associated with each car. Keep here to ensure the byteBuffer position is moved correctly.
        //For 2020 the button event was attached at the end of the telemetry packet, not the event packet.
        if (this.supportedYearsEnum.is2020OrEarlier()) {
            ButtonsData bd = buttonsDataFactory.build(byteBuffer);
            //2020 special button press mapped to button P on the McLaren wheel.
            //2019 special button mapped to the top left button the McLaren Wheel.
            if ((bd.buttonsStatus() == Constants.F1_2020_GT3_WHEEL_P_BUTTON && this.supportedYearsEnum == SupportedYearsEnum.F1_2020)
                    || (bd.buttonsStatus() == Constants.F1_2019_TOP_LEFT_BTN && this.supportedYearsEnum == SupportedYearsEnum.F1_2019)) {
                EventPacketHandler.handle2020ButtonEvent(this.packetFormat, this.participants);
                this.isPause = true;
            }
        }
        if (this.supportedYearsEnum.is2020OrLater()) {
            int mfdPanelIdx = BitMaskUtils.bitMask8(byteBuffer.get());
            int mfdPanelIdxSecondPlayer = BitMaskUtils.bitMask8(byteBuffer.get());
            int suggestedGear = byteBuffer.get();
        }
    }

    @Override
    public boolean isPause() {
        return this.isPause;
    }

    @Override
    public void setPause(boolean pause) {
        this.isPause = pause;
    }
}

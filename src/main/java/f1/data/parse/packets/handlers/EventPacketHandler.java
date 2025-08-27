package f1.data.parse.packets.handlers;

import f1.data.parse.individualLap.IndividualLapInfo;
import f1.data.parse.packets.events.ButtonsData;
import f1.data.parse.packets.events.SpeedTrapData;
import f1.data.parse.packets.events.SpeedTrapDataFactory;
import f1.data.parse.packets.events.SpeedTrapDistance;
import f1.data.parse.telemetry.SetupTireKey;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.ui.panels.dto.SpeedTrapDataDTO;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventPacketHandler implements PacketHandler {

    private final int packetFormat;
    private final Map<Integer, TelemetryData> participants;
    private final Consumer<SpeedTrapDataDTO> speedTrapData;
    private final SpeedTrapDistance speedTrapDistance;


    public EventPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, Consumer<SpeedTrapDataDTO> speedTrapData, SpeedTrapDistance speedTrapDistance) {
        this.packetFormat = packetFormat;
        this.participants = participants;
        this.speedTrapData = speedTrapData;
        this.speedTrapDistance = speedTrapDistance;
    }

    @Override
    public void processPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            byte[] codeArray = new byte[4];
            byteBuffer.get(codeArray, 0, 4);
            String value = new String(codeArray, StandardCharsets.US_ASCII);
            if (Constants.BUTTON_PRESSED_EVENT.equals(value)) {
                handleButtonEvent(byteBuffer);
            } else if (Constants.SPEED_TRAP_TRIGGERED_EVENT.equals(value)) {
                handleSpeedTrapEvent(byteBuffer);
            }
        }
    }

    private void handleButtonEvent(ByteBuffer byteBuffer) {
        ButtonsData bd = new ButtonsData(byteBuffer);
        //These are the 2 values that are the pause buttons on the McLaren GT3 wheel.
        if (Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN == bd.buttonsStatus()
                || Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN2 == bd.buttonsStatus()
        ) {
            for (Map.Entry<Integer, TelemetryData> id : this.participants.entrySet()) {
                TelemetryData td = id.getValue();
                System.out.println(td.getParticipantData().lastName());
                if (td.getCurrentSetup() != null) System.out.println(td.getCurrentSetup());
                if (!td.getLapsPerSetup().isEmpty()) {
                    for (Map.Entry<SetupTireKey, List<IndividualLapInfo>> laps : td.getLapsPerSetup().entrySet()) {
                        System.out.println(td.getSetups().get(laps.getKey().setupNumber()) + "\n" + laps.getKey().fittedTireId());
                        if (!laps.getValue().isEmpty()) {
                            for (IndividualLapInfo lap : laps.getValue()) {
                                System.out.println("#" + lap.getLapNum() + " " + lap.getLapTimeInMs());
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleSpeedTrapEvent(ByteBuffer byteBuffer) {
        SpeedTrapData trap = SpeedTrapDataFactory.build(packetFormat, byteBuffer);
        //Vehicle ID is the id of the driver based on the order they were presented for the participants' data.
        TelemetryData td = participants.get(trap.vehicleId());
        td.setSpeedTrap(trap.speed());
        if (packetFormat <= Constants.YEAR_2020 && speedTrapDistance.getDistance() < 0) {
            speedTrapDistance.setDistance(td.getCurrentLap().lapDistance());
        }
        //Populate the speedTrap consumer so that the panels get updated with the latest data.
        this.speedTrapData.accept(new SpeedTrapDataDTO(td.getParticipantData().driverId(), td.getParticipantData().lastName(), trap.speed(), td.getCurrentLap().currentLapNum()));
    }

    public static void handle2020ButtonEvent(ByteBuffer byteBuffer, Map<Integer, TelemetryData> participants) {
        long buttonEvent = BitMaskUtils.bitMask32(byteBuffer.getInt());
        //2020 special button press mapped to button P on the McLaren wheel.
        if (buttonEvent == Constants.F1_2020_GT3_WHEEL_P_BUTTON) {
            for (Map.Entry<Integer, TelemetryData> id : participants.entrySet()) {
                TelemetryData td = id.getValue();
                System.out.println(td.getParticipantData().lastName());
                if (td.getCurrentSetup() != null) System.out.println(td.getCurrentSetup());
                if (!td.getLapsPerSetup().isEmpty()) {
                    for (Map.Entry<SetupTireKey, List<IndividualLapInfo>> laps : td.getLapsPerSetup().entrySet()) {
                        System.out.println(td.getSetups().get(laps.getKey().setupNumber()) + "\n" + laps.getKey().fittedTireId());
                        if (!laps.getValue().isEmpty()) {
                            for (IndividualLapInfo lap : laps.getValue()) {
                                System.out.println("#" + lap.getLapNum() + " " + lap.getLapTimeInMs());
                            }
                        }
                    }
                }
            }
        }
    }
}

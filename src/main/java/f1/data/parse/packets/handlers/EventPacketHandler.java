package f1.data.parse.packets.handlers;

import f1.data.parse.packets.events.*;
import f1.data.parse.telemetry.CarSetupTelemetryData;
import f1.data.parse.telemetry.SetupTireKey;
import f1.data.parse.telemetry.SpeedTrapTelemetryData;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.save.IndividualLapSessionData;
import f1.data.save.SaveSessionDataHandler;
import f1.data.ui.panels.dto.SpeedTrapDataDTO;
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
    private final ButtonsDataFactory buttonsDataFactory;
    private final SpeedTrapDataFactory speedTrapFactory;

    private boolean isPause = false;


    public EventPacketHandler(int packetFormat, Map<Integer, TelemetryData> participants, Consumer<SpeedTrapDataDTO> speedTrapData, SpeedTrapDistance speedTrapDistance) {
        this.packetFormat = packetFormat;
        this.participants = participants;
        this.speedTrapData = speedTrapData;
        this.speedTrapDistance = speedTrapDistance;
        this.buttonsDataFactory = new ButtonsDataFactory(this.packetFormat);
        this.speedTrapFactory = new SpeedTrapDataFactory(this.packetFormat);
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
        ButtonsData bd = buttonsDataFactory.build(byteBuffer);
        //These are the 2 values that are the pause buttons on the McLaren GT3 wheel.
        if (Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN == bd.buttonsStatus()
                || Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN2 == bd.buttonsStatus()
        ) {
            handleTestSave(this.packetFormat, this.participants);
            printLapAndSetupData(this.participants);
            this.isPause = true;
        }
    }

    private void handleSpeedTrapEvent(ByteBuffer byteBuffer) {
        SpeedTrapData trap = speedTrapFactory.build(byteBuffer);
        //Vehicle ID is the id of the driver based on the order they were presented for the participants' data.
        TelemetryData td = participants.get(trap.vehicleId());
        if (packetFormat <= Constants.YEAR_2020 && speedTrapDistance.getDistance() < 0) {
            speedTrapDistance.setDistance(td.getCurrentLap().lapDistance());
        }
        SpeedTrapTelemetryData.updateConsumer(this.speedTrapData, td, trap.speed());
    }

    private static void printLapAndSetupData(Map<Integer, TelemetryData> participants) {
        for (Map.Entry<Integer, TelemetryData> id : participants.entrySet()) {
            TelemetryData td = id.getValue();
            CarSetupTelemetryData cstd = td.getCarSetupData();
            if (cstd.getCurrentSetup() != null) {
                System.out.println(td.getParticipantData().lastName());
                System.out.println(cstd.getCurrentSetup());
                if (!cstd.getLapsPerSetup().isEmpty()) {
                    for (Map.Entry<SetupTireKey, List<IndividualLapSessionData>> laps : cstd.getLapsPerSetup().entrySet()) {
                        System.out.println(cstd.getSetups().get(laps.getKey().setupNumber()) + "\n" + laps.getKey().fittedTireId());
                        if (!laps.getValue().isEmpty()) {
                            for (IndividualLapSessionData lap : laps.getValue()) {
                                System.out.println("#" + lap.getLapNum() + " " + lap.getLapTimeInMs());
                            }
                        }
                    }
                }
            }
        }
    }

    private static void handleTestSave(int packetFormat, Map<Integer, TelemetryData> participants) {
        //Builds the save data, if enabled and calls the method to actually create the save file.
        SaveSessionDataHandler.buildSaveData(packetFormat, "Testing", participants, false);
    }

    public static void handle2020ButtonEvent(int packetFormat, Map<Integer, TelemetryData> participants) {
        handleTestSave(packetFormat, participants);
        printLapAndSetupData(participants);
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public boolean isPause() {
        return isPause;
    }
}

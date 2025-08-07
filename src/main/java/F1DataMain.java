import individualLap.CarDamageInfo;
import individualLap.CarStatusInfo;
import individualLap.CarTelemetryInfo;
import individualLap.IndividualLapInfo;
import packets.*;
import packets.events.ButtonsData;
import packets.events.SpeedTrapData;
import telemetry.TelemetryRunData;
import utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class F1DataMain {

    private final Map<Integer, TelemetryData> participants = new HashMap<>();

    public void run() {
        int port = Constants.PORT_NUM;
        byte[] buffer = new byte[2048];
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(packet);
                int length = packet.getLength();
                ByteBuffer byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(buffer, 0, length));
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                PacketHeader ph = new PacketHeader(byteBuffer);
                switch (ph.getPacketId()) {
                    case Constants.MOTION_PACK:
                        break;
                    case Constants.EVENT_PACK:
                        handleEventPacket(byteBuffer);
                        break;
                    case Constants.LAP_DATA_PACK:
                        handleLapDataPacket(byteBuffer);
                        break;
                    case Constants.CAR_SETUP_PACK:
                        handleCarSetupPacket(byteBuffer);
                        break;
                    case Constants.PARTICIPANTS_PACK:
                        handleParticipantsPacket(byteBuffer);
                        break;
                    case Constants.CAR_TELEMETRY_PACK:
                        handleCarTelemetryPacket(byteBuffer);
                        break;
                    case Constants.CAR_STATUS_PACK:
                        handleCarStatusPacket(byteBuffer);
                        break;
                    case Constants.CAR_DAMAGE_PACK:
                        handleCarDamagePacket(byteBuffer);
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Checks if the map of participants(drivers in session) contains the id we are looking for. Prevents extra ids for custom team from printing stuff when they have no data.
    private boolean validKey(int i) {
        return participants.containsKey(i);
    }

    private void handleEventPacket(ByteBuffer byteBuffer) {
        byte[] codeArray = new byte[4];
        byteBuffer.get(codeArray, 0, 4);
        String value = new String(codeArray, StandardCharsets.US_ASCII);
        if (Constants.BUTTON_PRESSED_EVENT.equals(value)) {
            ButtonsData bd = new ButtonsData(byteBuffer);
            if (Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN == bd.getButtonsStatus()
                    || Constants.MCLAREN_GT3_WHEEL_PAUSE_BTN2 == bd.getButtonsStatus()) {
                for (Map.Entry<Integer, TelemetryData> entry : participants.entrySet()) {
                    Integer key = entry.getKey();
                    TelemetryData td = entry.getValue();
                    System.out.println();
                    System.out.println("ID " + key);
                    td.getParticipantData().printName();
                    System.out.println("Setup: " + td.getCurrentSetup().getSetupName() + " Lap #:" + td.getLastLapNum() + " Lap Time " + td.getLastLapTimeInMs());
                    for (TelemetryRunData trd : td.getTelemetryRunDataList()) {
                        System.out.println("Time " + trd.getStartTime() + " Setup " + trd.getCarSetupData().getSetupName());
                        for (Map.Entry<Integer, IndividualLapInfo> entry1 : trd.getLapData().entrySet()) {
                            IndividualLapInfo info = entry1.getValue();
                            System.out.println("Lap #:" + entry1.getKey() + " Time " + info.getLapTimeInMs());
                        }
                    }
                    System.out.println("-------------------------------------------");
                }
            }
        } else if (Constants.SPEED_TRAP_TRIGGERED_EVENT.equals(value)) {
            SpeedTrapData trap = new SpeedTrapData(byteBuffer);
            //Vehicle ID is the id of the driver based on the order they were presented for the participants' data.
            TelemetryData td = participants.get(trap.getVehicleId());
            td.setSpeedTrap(trap.getSpeed());
        }
    }

    private void handleLapDataPacket(ByteBuffer byteBuffer) {
        for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
            LapData ld = new LapData(byteBuffer);
            if (validKey(i)) {
                TelemetryData td = participants.get(i);
                if (td.getCurrentLap() != null) {
                    //If we have started a new lap, we need to create the info record, before we overnight the telemetry's ld object.
                    if (ld.getCurrentLapNum() > td.getCurrentLap().getCurrentLapNum()) {
                        IndividualLapInfo info = new IndividualLapInfo(ld, td.getCurrentLap(), td.getSpeedTrap());
                        td.setLastLapNum(info.getLapNum());
                        td.setLastLapTimeInMs(info.getLapTimeInMs());
                        if (td.getCurrentTelemetry() != null) {
                            info.setCarTelemetryInfo(new CarTelemetryInfo(td.getCurrentTelemetry()));
                        }
                        if (td.getCurrentStatus() != null) {
                            info.setCarStatusInfo(new CarStatusInfo(td.getCurrentStatus()));
                        }
                        if (td.getCurrentDamage() != null) {
                            info.setCarDamageInfo(new CarDamageInfo(td.getCurrentDamage()));
                        }
                        if (!td.getTelemetryRunDataList().isEmpty()) {
                            TelemetryRunData trd = td.getTelemetryRunDataList().get(td.getTelemetryRunDataList().size() - 1);
                            trd.getLapData().put(info.getLapNum(), info);
                            participants.put(i, td);
                        }
                        info.printInfo(td.getParticipantData().getLastName());
                        info.printStatus(td.getParticipantData().getLastName());
                        info.printDamage(td.getParticipantData().getLastName());
                    }
                    td.setCurrentLap(ld);
                } else {
                    td.setCurrentLap(ld);
                }
            }
        }
        //Time trail params at the end of the Lap Data packet. Only there a single time, therefore they are outside of the loop.
        int timeTrailPBCarId = byteBuffer.get() & Constants.BIT_MASK_8;
        int timeTrailRivalPdCarId = byteBuffer.get() & Constants.BIT_MASK_8;
    }

    private void handleCarSetupPacket(ByteBuffer byteBuffer) {
        for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
            CarSetupData csd = new CarSetupData(byteBuffer);
            if (validKey(i)) {
                TelemetryData td = participants.get(i);
                csd.setSetupName(td.getParticipantData().getLastName());
                if (td.getCurrentSetup() == null || !csd.equals(td.getCurrentSetup())) {
//                    System.out.println("i " + i + " Name " + csd.getSetupName() + " Inside td.getCurrentSetup == null. Current Setup Val " + td.getCurrentSetup());
                    td.setCurrentSetup(csd);
                    TelemetryRunData trd = new TelemetryRunData(csd);
                    td.getTelemetryRunDataList().add(trd);
                    participants.put(i, td);
                }
//                System.out.println("I " + i + " Front Wing " + csd.getFrontWing() + " Rear " + csd.getRearWing());
            }
        }
        //Trailing value, must be here to ensure the packet is fully parsed.
        float nextFronWingVal = byteBuffer.getFloat();
    }

    private void handleCarTelemetryPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                CarTelemetryData ctd = new CarTelemetryData(byteBuffer);
                if (validKey(i)) {
                    participants.get(i).setCurrentTelemetry(ctd);
                }
            }
        }
        //Params at the end of the Telemetry packet, not associated with each car. Keep here to ensure the byteBuffer position is moved correctly.
        int mfdPanelIdx = byteBuffer.get() & Constants.BIT_MASK_8;
        int mfdPanelIdxSecondPlayer = byteBuffer.get() & Constants.BIT_MASK_8;
        int suggestedGear = byteBuffer.get();
    }

    private void handleCarStatusPacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                CarStatusData csd = new CarStatusData(byteBuffer);
                if (validKey(i)) {
                    participants.get(i).setCurrentStatus(csd);
                }
            }
        }
    }

    private void handleCarDamagePacket(ByteBuffer byteBuffer) {
        if (!participants.isEmpty()) {
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                CarDamageData cdd = new CarDamageData(byteBuffer);
                if (validKey(i)) {
                    participants.get(i).setCurrentDamage(cdd);
                }
            }
        }
    }

    private void handleParticipantsPacket(ByteBuffer byteBuffer) {
        if (participants.isEmpty()) {
            //DO NOT DELETE THIS LINE, you will break the logic below it, we have to move the position with the .get() for the logic to work.
            int numActiveCars = byteBuffer.get();
            for (int i = 0; i < Constants.PACKET_CAR_COUNT; i++) {
                ParticipantData pd = new ParticipantData(byteBuffer);
                if (pd.getRaceNumber() > 0) {
                    pd.printName();
                    TelemetryData td = new TelemetryData(pd);
                    participants.put(i, td);
                }
            }
        }
    }

    public static void main(String[] args) {
        new F1DataMain().run();
    }
}

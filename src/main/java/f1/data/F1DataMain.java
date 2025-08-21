package f1.data;

import f1.data.packets.PacketHeader;
import f1.data.packets.PacketHeaderFactory;
import f1.data.packets.ParticipantData;
import f1.data.packets.events.SpeedTrapDistance;
import f1.data.packets.handlers.*;
import f1.data.telemetry.TelemetryData;
import f1.data.ui.dto.DriverDataDTO;
import f1.data.ui.dto.SpeedTrapDataDTO;
import f1.data.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class F1DataMain {

    private static final Logger logger = LoggerFactory.getLogger(F1DataMain.class);

    private final F1PacketProcessor packetProcessor;

    private final MotionPacketHandler motionPacketHandler;
    private final SessionPacketHandler sessionPacketHandler;
    private final EventPacketHandler eventPacketHandler;
    private final CarSetupPacketHandler carSetupPacketHandler;
    private final CarTelemetryPacketHandler carTelemetryPacketHandler;
    private final CarStatusPacketHandler carStatusPacketHandler;
    private final CarDamagePacketHandler carDamagePacketHandler;
    private final TireSetsPacketHandler tireSetsPacketHandler;
    private final LapDataPacketHandler lapDataPacketHandler;

    public F1DataMain(F1PacketProcessor packetProcessor, Consumer<DriverDataDTO> driverData, Consumer<SpeedTrapDataDTO> speedTrapData, List<ParticipantData> participantDataList, int packetFormat) {
        this.packetProcessor = packetProcessor;
        final Map<Integer, TelemetryData> participants = new HashMap<>();
        for (int i = 0; i < participantDataList.size(); i++) {
            ParticipantData pd = participantDataList.get(i);
            participants.put(i, new TelemetryData(pd));
            driverData.accept(new DriverDataDTO(pd.driverId(), pd.lastName()));
        }

        //Object used to ensure that when the speed trap even triggers an updated distance, the lapData object gets that update automatically.
        SpeedTrapDistance speedTrapDistance = new SpeedTrapDistance();
        this.motionPacketHandler = new MotionPacketHandler(packetFormat, participants);
        this.sessionPacketHandler = new SessionPacketHandler(packetFormat, participants);
        this.eventPacketHandler = new EventPacketHandler(packetFormat, participants, speedTrapData, speedTrapDistance);
        this.carSetupPacketHandler = new CarSetupPacketHandler(packetFormat, participants);
        this.carTelemetryPacketHandler = new CarTelemetryPacketHandler(packetFormat, participants);
        this.carStatusPacketHandler = new CarStatusPacketHandler(packetFormat, participants);
        this.carDamagePacketHandler = new CarDamagePacketHandler(packetFormat, participants);
        this.tireSetsPacketHandler = new TireSetsPacketHandler(participants);
        this.lapDataPacketHandler = new LapDataPacketHandler(packetFormat, participants, driverData, speedTrapData, speedTrapDistance);
    }

    private final int[][] packetCounts = new int[15][1];

    public void run() {
        logger.info("In DataMain");
        try {
            while (true) {
                DatagramPacket packet = this.packetProcessor.getNextPacket();
                int length = packet.getLength();
                ByteBuffer byteBuffer = ByteBuffer.wrap(packet.getData(), 0, length);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                //Parse the packetheader that comes in on every packet.
                PacketHeader ph = PacketHeaderFactory.build(byteBuffer);
                //Switch to handle the correct logic based on what packet has been sent.
                switch (ph.packetId()) {
                    case Constants.MOTION_PACK:
                        motionPacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.MOTION_PACK][0]++;
                        break;
                    case Constants.SESSION_PACK:
                        sessionPacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.SESSION_PACK][0]++;
                        break;
                    case Constants.EVENT_PACK:
                        eventPacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.EVENT_PACK][0]++;
                        break;
                    case Constants.LAP_DATA_PACK:
                        lapDataPacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.LAP_DATA_PACK][0]++;
                        break;
                    case Constants.CAR_SETUP_PACK:
                        carSetupPacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.CAR_SETUP_PACK][0]++;
                        break;
                    case Constants.PARTICIPANTS_PACK:
                        packetCounts[Constants.PARTICIPANTS_PACK][0]++;
                        break;
                    case Constants.CAR_TELEMETRY_PACK:
                        carTelemetryPacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.CAR_TELEMETRY_PACK][0]++;
                        break;
                    case Constants.CAR_STATUS_PACK:
                        carStatusPacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.CAR_STATUS_PACK][0]++;
                        break;
                    case Constants.CAR_DAMAGE_PACK:
                        carDamagePacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.CAR_DAMAGE_PACK][0]++;
                        break;
                    case Constants.TYRE_SETS_PACK:
                        tireSetsPacketHandler.processPacket(byteBuffer);
                        packetCounts[Constants.TYRE_SETS_PACK][0]++;
                        break;
                }
            }
        } catch (InterruptedException e) {
            logger.error("e ", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("Caught Exception ", e);
            throw new RuntimeException(e);
        }
    }
}

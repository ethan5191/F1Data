package f1.data;

import f1.data.packets.PacketHeader;
import f1.data.packets.PacketHeaderFactory;
import f1.data.packets.ParticipantData;
import f1.data.packets.events.SpeedTrapDistance;
import f1.data.packets.handlers.*;
import f1.data.packets.session.SessionData;
import f1.data.packets.session.SessionName;
import f1.data.telemetry.TelemetryData;
import f1.data.ui.dto.DriverDataDTO;
import f1.data.ui.dto.ParentConsumer;
import f1.data.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<Integer, PacketHandler> handlerMap = new HashMap<>();

    public F1DataMain(F1PacketProcessor packetProcessor, ParentConsumer parent, SessionData initialSession, List<ParticipantData> participantDataList, int packetFormat) {
        this.packetProcessor = packetProcessor;
        final Map<Integer, TelemetryData> participants = new HashMap<>();
        for (int i = 0; i < participantDataList.size(); i++) {
            ParticipantData pd = participantDataList.get(i);
            participants.put(i, new TelemetryData(pd));
            parent.driverDataDTOConsumer().accept(new DriverDataDTO(pd.driverId(), pd.lastName()));
        }

        //Object used to ensure that when the speed trap even triggers an updated distance, the lapData object gets that update automatically.
        SpeedTrapDistance speedTrapDistance = new SpeedTrapDistance();
        SessionName sessionName = new SessionName(initialSession.sessionType(), initialSession.trackId(), initialSession.formula());
        this.motionPacketHandler = new MotionPacketHandler(packetFormat, participants);
        this.sessionPacketHandler = new SessionPacketHandler(packetFormat, parent.sessionResetDTOConsumer(), sessionName);
        this.eventPacketHandler = new EventPacketHandler(packetFormat, participants, parent.speedTrapDataDTOConsumer(), speedTrapDistance);
        this.carSetupPacketHandler = new CarSetupPacketHandler(packetFormat, participants);
        this.carTelemetryPacketHandler = new CarTelemetryPacketHandler(packetFormat, participants);
        this.carStatusPacketHandler = new CarStatusPacketHandler(packetFormat, participants);
        this.carDamagePacketHandler = new CarDamagePacketHandler(packetFormat, participants);
        this.tireSetsPacketHandler = new TireSetsPacketHandler(participants);
        this.lapDataPacketHandler = new LapDataPacketHandler(packetFormat, participants, parent, speedTrapDistance);

        initializeHandlerMap();
    }

    private final int[][] packetCounts = new int[16][1];

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
                PacketHandler handler = handlerMap.get(ph.packetId());
                if (handler != null) handler.processPacket(byteBuffer);
                packetCounts[ph.packetId()][0]++;
            }
        } catch (InterruptedException e) {
            logger.error("e ", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("Caught Exception ", e);
            throw new RuntimeException(e);
        }
    }

    private void initializeHandlerMap() {
        handlerMap.put(Constants.MOTION_PACK, motionPacketHandler);
        handlerMap.put(Constants.SESSION_PACK, sessionPacketHandler);
        handlerMap.put(Constants.LAP_DATA_PACK, lapDataPacketHandler);
        handlerMap.put(Constants.EVENT_PACK, eventPacketHandler);
        handlerMap.put(Constants.PARTICIPANTS_PACK, null);
        handlerMap.put(Constants.CAR_SETUP_PACK, carSetupPacketHandler);
        handlerMap.put(Constants.CAR_TELEMETRY_PACK, carTelemetryPacketHandler);
        handlerMap.put(Constants.CAR_STATUS_PACK, carStatusPacketHandler);
        handlerMap.put(Constants.FINAL_CLASS_PACK, null);
        handlerMap.put(Constants.LOBBY_INFO_PACK, null);
        handlerMap.put(Constants.CAR_DAMAGE_PACK, carDamagePacketHandler);
        handlerMap.put(Constants.SESSION_HIST_PACK, null);
        handlerMap.put(Constants.TYRE_SETS_PACK, tireSetsPacketHandler);
        handlerMap.put(Constants.MOTION_EX_PACK, null);
        handlerMap.put(Constants.TIME_TRIAL_PACK, null);
        handlerMap.put(Constants.LAP_POSITIONS_PACK, null);
    }
}

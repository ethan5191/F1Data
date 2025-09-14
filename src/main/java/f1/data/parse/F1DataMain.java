package f1.data.parse;

import f1.data.SessionInitializationResult;
import f1.data.enums.PacketTypeEnum;
import f1.data.parse.packets.PacketHeader;
import f1.data.parse.packets.PacketHeaderFactory;
import f1.data.parse.packets.ParticipantData;
import f1.data.parse.packets.events.SpeedTrapDistance;
import f1.data.parse.packets.handlers.*;
import f1.data.parse.packets.session.SessionData;
import f1.data.parse.packets.session.SessionInformation;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.ui.panels.dto.DriverDataDTO;
import f1.data.ui.panels.dto.ParentConsumer;
import f1.data.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class F1DataMain {

    private static final Logger logger = LoggerFactory.getLogger(F1DataMain.class);

    private final F1PacketProcessor packetProcessor;

    private final MotionPacketHandler motionPacketHandler;
    private final SessionPacketHandler sessionPacketHandler;
    private final LapDataPacketHandler lapDataPacketHandler;
    private final EventPacketHandler eventPacketHandler;
    private final ParticipantPacketHandler participantPacketHandler;
    private final CarSetupPacketHandler carSetupPacketHandler;
    private final CarTelemetryPacketHandler carTelemetryPacketHandler;
    private final CarStatusPacketHandler carStatusPacketHandler;
    private final FinalClassificationHandler finalClassificationHandler;
    private final LobbyInfoPacketHandler lobbyInfoPacketHandler;
    private final CarDamagePacketHandler carDamagePacketHandler;
    private final SessionHistoryPacketHandler sessionHistoryPacketHandler;
    private final TireSetsPacketHandler tireSetsPacketHandler;
    private final MotionExPacketHandler motionExPacketHandler;
    private final TimeTrialPacketHandler timeTrialPacketHandler;
    private final LapPositionsPacketHandler lapPositionsPacketHandler;

    private final SessionInformation sessionInformation;
    private final Map<Integer, PacketHandler> handlerMap = new HashMap<>();

    private int packetFormat;
    private int playerCarIndex;

    public F1DataMain(F1PacketProcessor packetProcessor, ParentConsumer parent, SessionInitializationResult result) {
        this.packetFormat = result.getPacketFormat();
        this.playerCarIndex = result.getPlayerCarIndex();
        this.packetProcessor = packetProcessor;
        final Map<Integer, TelemetryData> participants = new HashMap<>();
        for (int i = 0; i < result.getParticipantData().size(); i++) {
            ParticipantData pd = result.getParticipantData().get(i);
            participants.put(i, new TelemetryData(pd));
            parent.driverDataDTOConsumer().accept(new DriverDataDTO(pd.driverId(), pd.lastName()));
        }

        //Object used to ensure that when the speed trap even triggers an updated distance, the lapData object gets that update automatically.
        SpeedTrapDistance speedTrapDistance = new SpeedTrapDistance();
        SessionData initialSession = result.getSessionData();
        SessionInformation sessionInformation = new SessionInformation(initialSession.sessionType(), initialSession.trackId(), initialSession.formula(),
                result.getPlayerDriverId(), result.getTeamMateDriverId(), result.getPlayerTeamId());
        this.sessionInformation = sessionInformation;
        final int packetFormat = result.getPacketFormat();
        this.motionPacketHandler = new MotionPacketHandler(packetFormat, this.playerCarIndex, participants);
        this.sessionPacketHandler = new SessionPacketHandler(packetFormat, participants, parent.sessionResetDTOConsumer(), sessionInformation);
        this.lapDataPacketHandler = new LapDataPacketHandler(packetFormat, this.playerCarIndex, participants, parent, speedTrapDistance);
        this.eventPacketHandler = new EventPacketHandler(packetFormat, participants, parent.speedTrapDataDTOConsumer(), speedTrapDistance);
        this.participantPacketHandler = new ParticipantPacketHandler(packetFormat, this.playerCarIndex, participants, parent.driverDataDTOConsumer(), parent.sessionChangeDTOConsumer(), this.sessionInformation);
        this.carSetupPacketHandler = new CarSetupPacketHandler(packetFormat, this.playerCarIndex, participants);
        this.carTelemetryPacketHandler = new CarTelemetryPacketHandler(packetFormat, this.playerCarIndex, participants);
        this.carStatusPacketHandler = new CarStatusPacketHandler(packetFormat, this.playerCarIndex, participants);
        this.finalClassificationHandler = new FinalClassificationHandler(packetFormat, this.playerCarIndex);
        this.lobbyInfoPacketHandler = new LobbyInfoPacketHandler(packetFormat, this.playerCarIndex);
        this.carDamagePacketHandler = new CarDamagePacketHandler(packetFormat, this.playerCarIndex, participants);
        this.sessionHistoryPacketHandler = new SessionHistoryPacketHandler(packetFormat);
        this.tireSetsPacketHandler = new TireSetsPacketHandler(packetFormat, participants);
        this.motionExPacketHandler = new MotionExPacketHandler(packetFormat);
        this.timeTrialPacketHandler = new TimeTrialPacketHandler(packetFormat);
        this.lapPositionsPacketHandler = new LapPositionsPacketHandler(packetFormat);

        initializeHandlerMap();
    }

    private final int[][] packetCounts = new int[PacketTypeEnum.values().length][1];

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
                updateHeaderParams(ph);
                PacketHandler handler = handlerMap.get(ph.packetId());
                if (handler != null) handler.processPacket(byteBuffer);
                packetCounts[ph.packetId()][0]++;
                logPacketCounts(handler);
            }
        } catch (InterruptedException e) {
            logger.error("e ", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("Caught Exception ", e);
            throw new RuntimeException(e);
        }
    }

    //If either of these values are different, we need to update them so the handler's logic works correctly.
    private void updateHeaderParams(PacketHeader ph) {
        if (ph.packetFormat() != this.packetFormat) this.packetFormat = ph.packetFormat();
        if (ph.playerCarIndex() != this.playerCarIndex) {
            this.playerCarIndex = ph.playerCarIndex();
            this.participantPacketHandler.setPlayerCarIndex(ph.playerCarIndex());
        }
    }

    private void initializeHandlerMap() {
        handlerMap.put(Constants.MOTION_PACK, motionPacketHandler);
        handlerMap.put(Constants.SESSION_PACK, sessionPacketHandler);
        handlerMap.put(Constants.LAP_DATA_PACK, lapDataPacketHandler);
        handlerMap.put(Constants.EVENT_PACK, eventPacketHandler);
        handlerMap.put(Constants.PARTICIPANTS_PACK, participantPacketHandler);
        handlerMap.put(Constants.CAR_SETUP_PACK, carSetupPacketHandler);
        handlerMap.put(Constants.CAR_TELEMETRY_PACK, carTelemetryPacketHandler);
        handlerMap.put(Constants.CAR_STATUS_PACK, carStatusPacketHandler);
        handlerMap.put(Constants.FINAL_CLASS_PACK, finalClassificationHandler);
        handlerMap.put(Constants.LOBBY_INFO_PACK, lobbyInfoPacketHandler);
        handlerMap.put(Constants.CAR_DAMAGE_PACK, carDamagePacketHandler);
        handlerMap.put(Constants.SESSION_HIST_PACK, sessionHistoryPacketHandler);
        handlerMap.put(Constants.TYRE_SETS_PACK, tireSetsPacketHandler);
        handlerMap.put(Constants.MOTION_EX_PACK, motionExPacketHandler);
        handlerMap.put(Constants.TIME_TRIAL_PACK, timeTrialPacketHandler);
        handlerMap.put(Constants.LAP_POSITIONS_PACK, lapPositionsPacketHandler);
    }

    private void logPacketCounts(PacketHandler handler) {
        if (handler instanceof PauseActionHandler) {
            if (((PauseActionHandler) handler).isPause()) {
                logger.info(sessionInformation.getName());
                for (int i = 0; i < this.packetCounts.length; i++) {
                    logger.info("Packet {} Count {}", PacketTypeEnum.findByValue(i).name(), Arrays.toString(this.packetCounts[i]));
                }
                ((PauseActionHandler) handler).setPause(false);
            }
        }
    }
}

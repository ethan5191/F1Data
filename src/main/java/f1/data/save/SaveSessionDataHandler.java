package f1.data.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.participant.ParticipantData;
import f1.data.parse.telemetry.SetupTireKey;
import f1.data.parse.telemetry.TelemetryData;
import f1.data.ui.panels.home.AppState;
import f1.data.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SaveSessionDataHandler {

    private static final Logger logger = LoggerFactory.getLogger(SaveSessionDataHandler.class);

    private static void saveSessionData(int packetFormat, String sessionName, List<SpeedTrapSessionData> speedTrapSessionWrapper, List<RunDataSessionData> runDataSessionDataWrapper) {
        SupportedYearsEnum supportedYearsEnum = SupportedYearsEnum.fromYear(packetFormat);
        SaveSessionWrapper saveSessionDataWrapper = (supportedYearsEnum.is2020OrLater()) ? new SaveSessionDataWrapper(speedTrapSessionWrapper, runDataSessionDataWrapper) : new SaveSessionDataWrapper2019(runDataSessionDataWrapper);
        String workingDir = System.getProperty(Constants.USER_HOME);
        Path workingPath = Paths.get(workingDir);
        Path savePath = workingPath.resolve(Constants.F1_DATA).resolve(Constants.SAVE_SESSIONS).resolve("F1_" + packetFormat);
        try {
            if (Files.notExists(savePath)) {
                Files.createDirectories(savePath);
            }
        } catch (IOException e) {
            logger.error("Caught IO Exception creating SaveSessions directory ", e);
        }
        Path filePath = savePath.resolve(sessionName + "_" + System.currentTimeMillis() + ".json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), saveSessionDataWrapper);
            logger.info("Data saved successfully to {}", filePath.toFile().getName());
        } catch (IOException e) {
            logger.error("Caught IO Exception ", e);
        }
    }

    public static void buildSaveData(int packetFormat, String sessionName, Map<Integer, TelemetryData> participants, boolean isNewSession) {
        if (AppState.saveSessionData.get()) {
            List<SpeedTrapSessionData> speedTrapSessionDataList = new ArrayList<>(participants.size());
            List<RunDataSessionData> runDataSessionData = new ArrayList<>(participants.size());
            //Loop over the participant map and create new telemetry data to reset the data on the backend.
            for (Integer i : participants.keySet()) {
                TelemetryData td = participants.get(i);
                ParticipantData pd = td.getParticipantData();
                List<RunDataMapRecord> records = new ArrayList<>(td.getCarSetupData().getLapsPerSetup().size());
                //If a driver hasn't set a speed trap yet in the session, it will show as 0 as that is the default object for SpeedTrapData on the td object.
                speedTrapSessionDataList.add(new SpeedTrapSessionData(pd.lastName(), td.getSpeedTrapData().getSpeedTrapByLap()));
                for (SetupTireKey key : td.getCarSetupData().getLapsPerSetup().keySet()) {
                    records.add(new RunDataMapRecord(key, td.getCarSetupData().getLapsPerSetup().get(key)));
                }
                runDataSessionData.add(new RunDataSessionData(pd.lastName(), td.getCarSetupData().getSetups(), records));
                //Only on a new session do we want to clear the participants data.
                if (isNewSession) participants.put(i, new TelemetryData(pd));
            }
            if (shouldSave(speedTrapSessionDataList, runDataSessionData))
                SaveSessionDataHandler.saveSessionData(packetFormat, sessionName, speedTrapSessionDataList, runDataSessionData);
        }
    }

    private static boolean shouldSave(List<SpeedTrapSessionData> speedTrapSessionDataList, List<RunDataSessionData> runDataSessionData) {
        boolean result = false;
        for (SpeedTrapSessionData trap : speedTrapSessionDataList) {
            if (!trap.speedTrapByLap().isEmpty()) {
                result = true;
                break;
            }
        }
        if (!result) {
            for (RunDataSessionData data : runDataSessionData) {
                if (!data.setups().isEmpty() || !data.lapsForSetup().isEmpty()) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}

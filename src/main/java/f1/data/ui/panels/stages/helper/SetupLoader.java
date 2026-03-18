package f1.data.ui.panels.stages.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import f1.data.enums.TrackEnum;
import f1.data.parse.individualLap.CarSetupInfo;
import f1.data.parse.packets.CarSetupData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SetupLoader {

    private static final Logger logger = LoggerFactory.getLogger(SetupLoader.class);

    private static final String TRACK_DOWNFORCE_PATH = "/%d/track_downforce_levels.json";
    private static final String FILE_PATH = "/%d/ai_setups.json";

    public static CarSetupInfo getSetup(int trackId, int packetFormat, String formula) {
        String setupType = loadTrackSetupType(trackId, packetFormat, formula);
        if (setupType == null) return null;
        Map<String, CarSetupData> aiSetups = loadSetups(packetFormat);
        CarSetupData loadedSetup = aiSetups.get(setupType);
        if (loadedSetup == null) return null;
        return new CarSetupInfo(loadedSetup);
    }

    private static String loadTrackSetupType(int trackId, int packetFormat, String formula) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, String>> trackSetupTypes;
        String fullPath = String.format(TRACK_DOWNFORCE_PATH, packetFormat);
        try (InputStream inputStream = SetupLoader.class.getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + fullPath);
            }
            trackSetupTypes = mapper.readValue(inputStream, new TypeReference<>() {
            });
            Map<String, String> trackMap = trackSetupTypes.get(formula);
            if (trackMap != null) {
                return trackMap.get(TrackEnum.fromId(trackId).name());
            }
        } catch (IOException e) {
            logger.error("Caught Exception ", e);
        }
        return null;
    }

    private static Map<String, CarSetupData> loadSetups(int packetFormat) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, CarSetupData> aiSetups = new HashMap<>();
        String fullPath = String.format(FILE_PATH, packetFormat);
        try (InputStream inputStream = SetupLoader.class.getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + fullPath);
            }
            aiSetups = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            logger.error("Caught Exception ", e);
        }
        return aiSetups;
    }
}

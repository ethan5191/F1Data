package f1.data.ui.panels.stages.helper;

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

    private static final String FILE_PATH = "/%d/ai_setups.json";

    public static CarSetupInfo getSetup(int trackId, int packetFormat, String formula) {
        Map<String, CarSetupData> aiSetups = loadSetups(packetFormat);
        String key = TrackEnum.fromId(trackId).getSetupName();
        CarSetupData loadedSetup = aiSetups.get(key);
        if (loadedSetup == null) return null;
        return new CarSetupInfo(loadedSetup);
    }

    private static Map<String, CarSetupData> loadSetups(int packetFormat) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, CarSetupData> aiSetups = new HashMap<>();
        String fullPath = String.format(FILE_PATH, packetFormat);
        try (InputStream inputStream = SetupLoader.class.getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + fullPath);
            }
            aiSetups = objectMapper.readValue(inputStream, new com.fasterxml.jackson.core.type.TypeReference<Map<String, CarSetupData>>() {
            });
        } catch (IOException e) {
            logger.error("Caught Exception ", e);
        }
        return aiSetups;
    }
}

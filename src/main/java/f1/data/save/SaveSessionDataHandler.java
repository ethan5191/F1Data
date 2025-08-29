package f1.data.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveSessionDataHandler {

    private static final Logger logger = LoggerFactory.getLogger(SaveSessionDataHandler.class);

    public static void saveSessionData(String sessionName, List<SpeedTrapSessionData> speedTrapSessionWrapper, List<RunDataSessionData> runDataSessionDataWrapper) {
        SaveSessionDataWrapper saveSessionDataWrapper = new SaveSessionDataWrapper(speedTrapSessionWrapper, runDataSessionDataWrapper);
        String workingDir = System.getProperty("user.dir");
        Path savePath = Paths.get(workingDir, "SessionSaves");
        try {
            if (Files.notExists(savePath)) {
                Files.createDirectory(savePath);
            }
        } catch (IOException e) {
            logger.error("Caught IO Exception creating SaveSessions directory ", e);
        }
        Path filePath = savePath.resolve(sessionName + ".json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), saveSessionDataWrapper);
            logger.info("Data saved successfully to {}", filePath.toFile().getName());
        } catch (IOException e) {
            logger.error("Caught IO Exception ", e);
        }
    }
}

package f1.data.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveSessionDataHandler {

    private static final Logger logger = LoggerFactory.getLogger(SaveSessionDataHandler.class);

    public static void saveSessionData(String sessionName, List<SaveSessionData> saveSessionDataList) {
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop";
        File myFile = new File(desktopPath, sessionName + ".json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(myFile, saveSessionDataList);
            logger.info("Data saved successfully to {}", myFile.getName());
        } catch (IOException e) {
            logger.error("Caught IO Exception ", e);
        }
    }
}

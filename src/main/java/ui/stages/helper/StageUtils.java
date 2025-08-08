package ui.stages.helper;

import javafx.application.Platform;
import javafx.stage.Stage;

public class StageUtils {

    public static void enableHideWindows(Stage stage) {
        //TODO: remove this eventually, for now with everything still printing to the console, it stays.
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(event -> {
            stage.hide();
            System.out.println("Window closed, app still lives");
        });
    }
}

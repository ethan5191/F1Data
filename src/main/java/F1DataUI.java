import javafx.application.Application;
import javafx.stage.Stage;

public class F1DataUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {




        Thread telemetryThread = new Thread(() -> {
            new F1DataMain().run();
        });
        telemetryThread.setDaemon(true);
        telemetryThread.start();
//        stage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}

package ui.stages;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SetupStage extends AbstractStage<VBox> {

    public SetupStage(Stage stage, VBox setupData) {
        super(stage, null, null);
        this.setupData = setupData;
        init();
    }

    private final VBox setupData;

    @Override
    public VBox createParentContent() {
        return createParentVbox();
    }

    @Override
    protected void init() {
        this.content.getChildren().add(setupData);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(725);
        stage.setHeight(bounds.getHeight());
        Scene scene = new Scene(this.content);
        scene.setFill(Color.TRANSPARENT);
        this.stage.setScene(scene);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.show();
    }
}

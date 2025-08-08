package ui.stages;

import javafx.scene.Parent;

public interface F1Stages<T extends Parent> {

    T createParentContent(javafx.stage.Stage stage);
    void buildHeader();
    void showStage();
}

package ui.stages;

import javafx.scene.layout.Pane;

public interface F1Stages<T extends Pane> {

    T createParentContent();

    void buildHeader(String[] headers, int[] headersWidth);

    void showStage(double width, double height);
}

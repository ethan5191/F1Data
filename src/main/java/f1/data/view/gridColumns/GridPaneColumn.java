package f1.data.view.gridColumns;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public record GridPaneColumn(Label columnHeader, VBox content) {
}

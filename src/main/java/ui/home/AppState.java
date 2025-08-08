package ui.home;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AppState {

    public static final BooleanProperty latestLapPanelVisible = new SimpleBooleanProperty(false);
    public static final BooleanProperty allLapsDataPanelVisible = new SimpleBooleanProperty(false);
    public static final BooleanProperty setupDataPanelVisible = new SimpleBooleanProperty(false);
}

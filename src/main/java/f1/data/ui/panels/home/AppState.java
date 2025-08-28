package f1.data.ui.panels.home;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AppState {

    public static final BooleanProperty latestLapPanelVisible = new SimpleBooleanProperty(false);
    public static final BooleanProperty allLapsDataPanelVisible = new SimpleBooleanProperty(false);
    public static final BooleanProperty setupDataPanelVisible = new SimpleBooleanProperty(false);
    public static final BooleanProperty speedTrapPanelVisible = new SimpleBooleanProperty(false);
    public static final BooleanProperty teamSpeedTrapPanelVisible = new SimpleBooleanProperty(false);
    public static final BooleanProperty runDataPanelVisible = new SimpleBooleanProperty(false);
    public static final BooleanProperty saveSessionData = new SimpleBooleanProperty(false);
}

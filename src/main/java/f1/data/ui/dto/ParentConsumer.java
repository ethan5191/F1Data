package f1.data.ui.dto;

import java.util.function.Consumer;

public record ParentConsumer(Consumer<DriverDataDTO> driverDataDTOConsumer,
                             Consumer<SpeedTrapDataDTO> speedTrapDataDTOConsumer,
                             Consumer<SessionResetDTO> sessionResetDTOConsumer) {

}

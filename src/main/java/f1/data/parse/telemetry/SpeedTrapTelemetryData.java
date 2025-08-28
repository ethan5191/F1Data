package f1.data.parse.telemetry;

import f1.data.ui.panels.dto.SpeedTrapDataDTO;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

//Object used to track the speed trap and lap # for each individual car.
public class SpeedTrapTelemetryData {

    private float speed;
    private final Map<Integer, Float> speedTrapByLap = new TreeMap<>();

    public SpeedTrapTelemetryData(float speed, Integer lapNum) {
        this.speed = speed;
        //Default is 0 speed on lap 0 to prevent NPEs. I don't want lap 0 in the map.
        if (lapNum > 0 ) this.speedTrapByLap.put(lapNum, speed);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Map<Integer, Float> getSpeedTrapByLap() {
        return speedTrapByLap;
    }

    public void updateSpeedTrap(Integer lapNum, Float speed) {
        this.speed = speed;
        this.speedTrapByLap.put(lapNum, speed);
    }

    public static void updateConsumer(Consumer<SpeedTrapDataDTO> speedTrapData, TelemetryData td, float speed) {
        //If this driver has set a speed trap value then we need to add this lap to the existing objects map.
        if (td.getSpeedTrapData() != null) {
            td.getSpeedTrapData().updateSpeedTrap(td.getCurrentLap().currentLapNum(), speed);
        } else {
            //Else this is the first speed trap for this driver, so create the new object which will add this lap/speed to the map.
            td.setSpeedTrapData(new SpeedTrapTelemetryData(speed, td.getCurrentLap().currentLapNum()));
        }
        //Populate the speedTrap consumer so that the panels get updated with the latest data.
        speedTrapData.accept(new SpeedTrapDataDTO(td.getParticipantData().driverId(), td.getParticipantData().lastName(), speed, td.getCurrentLap().currentLapNum()));
    }
}

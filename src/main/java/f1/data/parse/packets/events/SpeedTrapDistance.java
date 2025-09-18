package f1.data.parse.packets.events;

import f1.data.utils.constants.Constants;

public class SpeedTrapDistance {

    private float distance;

    public SpeedTrapDistance() {
        this.distance = Constants.SPEED_TRAP_DEFAULT;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }
}

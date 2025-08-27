package f1.data.parse.packets.events;

public class SpeedTrapDistance {

    private float distance;

    public SpeedTrapDistance() {
        this.distance = -50F;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }
}

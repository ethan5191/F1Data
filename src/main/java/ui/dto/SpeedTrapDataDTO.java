package ui.dto;

public class SpeedTrapDataDTO {

    public SpeedTrapDataDTO(int id, String name, float speed, int lapNum) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.lapNum = lapNum;
    }

    private final int id;
    private final String name;
    private final float speed;
    private final int lapNum;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }

    public int getLapNum() {
        return lapNum;
    }
}

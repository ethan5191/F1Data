package ui.dto;

import individualLap.IndividualLapInfo;

public class DriverDataDTO {

    public DriverDataDTO(Integer id, String lastName, boolean isPlayer) {
        this.id = id;
        this.lastName = lastName;
        this.info = null;
        this.isPlayer = isPlayer;
    }

    public DriverDataDTO(Integer id, String lastName, IndividualLapInfo info, boolean isPlayer) {
        this.id = id;
        this.lastName = lastName;
        this.info = info;
        this.isPlayer = isPlayer;
    }

    private final Integer id;
    private final String lastName;
    private final IndividualLapInfo info;
    private final boolean isPlayer;

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public IndividualLapInfo getInfo() {
        return info;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}

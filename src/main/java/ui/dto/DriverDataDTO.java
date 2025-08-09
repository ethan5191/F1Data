package ui.dto;

import individualLap.IndividualLapInfo;

public class DriverDataDTO {

    public DriverDataDTO(Integer id, String lastName, int playerCarIndex) {
        this.id = id;
        this.lastName = lastName;
        this.info = null;
        this.playerCarIndex = playerCarIndex;
    }

    public DriverDataDTO(Integer id, String lastName, IndividualLapInfo info) {
        this.id = id;
        this.lastName = lastName;
        this.info = info;
        this.playerCarIndex = -1;
    }

    private final Integer id;
    private final String lastName;
    private final IndividualLapInfo info;
    private final int playerCarIndex;

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public IndividualLapInfo getInfo() {
        return info;
    }

    public int getPlayerCarIndex() {
        return playerCarIndex;
    }
}

package ui.dto;

import individualLap.IndividualLapInfo;

public class DriverDataDTO {

    public DriverDataDTO(Integer id, String lastName) {
        this.id = id;
        this.lastName = lastName;
        this.info = null;
    }

    public DriverDataDTO(Integer id, String lastName, IndividualLapInfo info) {
        this.id = id;
        this.lastName = lastName;
        this.info = info;
    }

    private final Integer id;
    private final String lastName;
    private final IndividualLapInfo info;

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public IndividualLapInfo getInfo() {
        return info;
    }
}

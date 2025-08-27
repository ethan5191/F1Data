package f1.data.ui.panels.dto;

import f1.data.parse.individualLap.IndividualLapInfo;

public class DriverDataDTO {

    //Used by the participants packet, only passed one time per driver. Responsible for setting up initial data.
    public DriverDataDTO(Integer id, String lastName) {
        this.id = id;
        this.lastName = lastName;
        this.info = null;
    }

    //Used by the lapData packet, passed each time a car completes a new timed lap.
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

package f1.data.ui.dto;

import f1.data.individualLap.IndividualLapInfo;
import f1.data.packets.enums.FormulaEnum;

import java.util.Map;

public class DriverDataDTO {

    //Used by the participants packet, only passed one time per driver. Responsible for setting up initial data.
    public DriverDataDTO(Integer id, String lastName, boolean isPlayer, Map<Integer, Integer> driverPairings, FormulaEnum formulaEnum) {
        this.id = id;
        this.lastName = lastName;
        this.info = null;
        this.isPlayer = isPlayer;
        this.driverPairings = driverPairings;
        this.formulaEnum = formulaEnum;
    }

    //Used by the lapData packet, passed each time a car completes a new timed lap.
    public DriverDataDTO(Integer id, String lastName, IndividualLapInfo info, boolean isPlayer) {
        this.id = id;
        this.lastName = lastName;
        this.info = info;
        this.isPlayer = isPlayer;
        this.driverPairings = null;
        this.formulaEnum = null;
    }

    private final Integer id;
    private final String lastName;
    private final IndividualLapInfo info;
    private final boolean isPlayer;
    private final Map<Integer, Integer> driverPairings;
    private final FormulaEnum formulaEnum;

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

    public Map<Integer, Integer> getDriverPairings() {
        return driverPairings;
    }

    public FormulaEnum getFormulaEnum() {
        return formulaEnum;
    }
}

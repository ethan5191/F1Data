package packets.enums;

import utils.constants.DriverConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//enum mapping the game year to the 3 different driver pairings that exist in that given game.
//this is based on F1 24 where they only have F1, F2 '23, and F2 '24. I may have to update this if I go for older years.
public enum DriverPairingsEnum {
    //Game year (first element) is a 2 digit integer, at least in F1 24 it is.
    TWENTY(2020, DriverConstants.F1_20_DRIVER_PAIRS, DriverConstants.F2_20_DRIVER_PARIS, DriverConstants.F2_19_DRIVER_PAIRS),
    TWENTY_FOUR(2024, DriverConstants.F1_24_DRIVER_PAIRS, DriverConstants.F2_24_DRIVER_PAIRS, DriverConstants.F2_23_DRIVER_PAIRS);

    private final Integer gameYear;
    private final Map<Integer, Integer> f1DriverPairs;
    private final Map<Integer, Integer> f2DriverPairs;
    private final Map<Integer, Integer> f2PrevYearDriverPairs;

    private static final Map<Integer, DriverPairingsEnum> LOOKUP = new HashMap<>();
    private final List<Map<Integer, Integer>> driverPairingLists = new ArrayList<>(3);
    static {
        for (DriverPairingsEnum e : DriverPairingsEnum.values()) {
            LOOKUP.put(e.gameYear, e);
        }
    }

    DriverPairingsEnum(Integer gameYear, Map<Integer, Integer> f1DriverPairs, Map<Integer, Integer> f2DriverPairs, Map<Integer, Integer> f2PrevYearDriverPairs) {
        this.gameYear = gameYear;
        this.f1DriverPairs = f1DriverPairs;
        this.f2DriverPairs = f2DriverPairs;
        this.f2PrevYearDriverPairs = f2PrevYearDriverPairs;
    }

    public Integer getGameYear() {
        return gameYear;
    }

    public Map<Integer, Integer> getF1DriverPairs() {
        return f1DriverPairs;
    }

    public Map<Integer, Integer> getF2DriverPairs() {
        return f2DriverPairs;
    }

    public Map<Integer, Integer> getF2PrevYearDriverPairs() {
        return f2PrevYearDriverPairs;
    }

    //Once we know what enum we are using, populate the list with the different driver pairings.
    //Always do it in this order: F1, F2, F2 previous as thats the order the FormulaTypeEnum is in.
    public static DriverPairingsEnum fromYear(int gameYear) {
        DriverPairingsEnum result = LOOKUP.get(gameYear);
        result.driverPairingLists.add(result.getF1DriverPairs());
        result.driverPairingLists.add(result.getF2DriverPairs());
        result.driverPairingLists.add(result.getF2PrevYearDriverPairs());
        return result;
    }

    public Map<Integer, Integer> getDriverPair(int index) {
        return this.driverPairingLists.get(index);
    }
}

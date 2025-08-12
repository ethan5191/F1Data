package individualLap;

import packets.CarDamageData;
import utils.constants.Constants;

public class CarDamageInfo {

    public CarDamageInfo(CarDamageData cdd) {
        String[] temp = new String[4];
        for (int i = 0; i < cdd.getTyresWear().length; i++) {
            temp[i] = String.format(Constants.TWO_DECIMAL, cdd.getTyresWear()[i]);
        }
        this.tyresWear = temp;
        this.floorDamage = cdd.getFloorDamage();
        this.diffuserDamage = cdd.getDiffuserDamage();
    }

    private final String[] tyresWear;
    private final int floorDamage;
    private final int diffuserDamage;

    public String[] getTyresWear() {
        return tyresWear;
    }

    public int getFloorDamage() {
        return floorDamage;
    }

    public int getDiffuserDamage() {
        return diffuserDamage;
    }
}

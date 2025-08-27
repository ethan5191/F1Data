package f1.data.parse.individualLap;

import f1.data.parse.packets.CarDamageData;
import f1.data.utils.constants.Constants;

public class CarDamageInfo {

    public CarDamageInfo(CarDamageData cdd) {
        String[] temp = new String[4];
        for (int i = 0; i < cdd.tyresWear().length; i++) {
            temp[i] = String.format(Constants.TWO_DECIMAL, cdd.tyresWear()[i]);
        }
        this.tyresWear = temp;
        this.floorDamage = cdd.floorDamage();
        this.diffuserDamage = cdd.diffuserDamage();
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

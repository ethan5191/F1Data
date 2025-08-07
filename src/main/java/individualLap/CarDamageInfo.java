package individualLap;

import packets.CarDamageData;

public class CarDamageInfo {

    public CarDamageInfo(CarDamageData cdd) {
        this.tyresWear = cdd.getTyresWear();
        this.floorDamage = cdd.getFloorDamage();
        this.diffuserDamage = cdd.getDiffuserDamage();
    }

    private final float[] tyresWear;
    private final int floorDamage;
    private final int diffuserDamage;

    public float[] getTyresWear() {
        return tyresWear;
    }

    public int getFloorDamage() {
        return floorDamage;
    }

    public int getDiffuserDamage() {
        return diffuserDamage;
    }
}

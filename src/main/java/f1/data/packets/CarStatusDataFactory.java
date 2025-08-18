package f1.data.packets;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class CarStatusDataFactory {

    public static CarStatusData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2020:
                CarStatusData.CarStatusData20 c20 = new CarStatusData.CarStatusData20(byteBuffer);
                yield new CarStatusData(c20.tractionControl(), c20.antiLockBrakes(), c20.fuelMix(), c20.frontBrakeBias(), c20.pitLimitStatus(),
                        c20.fuelInTank(), c20.fuelCapacity(), c20.fuelRemainingLaps(), c20.maxRPM(), c20.idleRPM(), c20.maxGears(), c20.drsAllowed(),
                        c20.drsActivationDistance(), c20.actualTireCompound(), c20.visualTireCompound(), c20.tiresAgeLaps(), c20.vehicleFiaFlags(),
                        c20.ersStoreEnergy(), c20.ersDeployMode(), c20.ersHarvestedThisLapMGUK(), c20.ersHarvestedThisLapMGUH(), c20.ersDeployedThisLap(),
                        0, 0, 0, c20.tyresWear(), c20.tyresDamage(), c20.frontLeftWingDamage(), c20.frontRightWingDamage(),
                        c20.rearWingDamage(), c20.drsFault(), c20.engineDamage(), c20.gearBoxDamage());
            case Constants.YEAR_2021, Constants.YEAR_2022:
                CarStatusData.CarStatusData21 c21 = new CarStatusData.CarStatusData21(byteBuffer);
                yield new CarStatusData(c21.tractionControl(), c21.antiLockBrakes(), c21.fuelMix(), c21.frontBrakeBias(), c21.pitLimitStatus(),
                        c21.fuelInTank(), c21.fuelCapacity(), c21.fuelRemainingLaps(), c21.maxRPM(), c21.idleRPM(), c21.maxGears(), c21.drsAllowed(),
                        c21.drsActivationDistance(), c21.actualTireCompound(), c21.visualTireCompound(), c21.tiresAgeLaps(), c21.vehicleFiaFlags(),
                        c21.ersStoreEnergy(), c21.ersDeployMode(), c21.ersHarvestedThisLapMGUK(), c21.ersHarvestedThisLapMGUH(), c21.ersDeployedThisLap(),
                        c21.networkPaused(), 0, 0, new float[0], new int[0], 0, 0, 0,
                        0, 0, 0);
            case Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025:
                CarStatusData.CarStatusData23 c23 = new CarStatusData.CarStatusData23(byteBuffer);
                yield new CarStatusData(c23.tractionControl(), c23.antiLockBrakes(), c23.fuelMix(), c23.frontBrakeBias(), c23.pitLimitStatus(),
                        c23.fuelInTank(), c23.fuelCapacity(), c23.fuelRemainingLaps(), c23.maxRPM(), c23.idleRPM(), c23.maxGears(), c23.drsAllowed(),
                        c23.drsActivationDistance(), c23.actualTireCompound(), c23.visualTireCompound(), c23.tiresAgeLaps(), c23.vehicleFiaFlags(),
                        c23.ersStoreEnergy(), c23.ersDeployMode(), c23.ersHarvestedThisLapMGUK(), c23.ersHarvestedThisLapMGUH(), c23.ersDeployedThisLap(),
                        c23.networkPaused(), c23.enginePowerICE(), c23.enginePowerMGUK(), new float[0], new int[0], 0, 0, 0,
                        0, 0, 0);
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

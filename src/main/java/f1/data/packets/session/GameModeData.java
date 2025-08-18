package f1.data.packets.session;

public record GameModeData(int equalCarPerformance, int recoveryMode, int flashbackLimit, int surfaceType,
                           int lowFuelMode, int raceStarts, int tyreTemperature, int pitLaneTyreSim, int carDamage,
                           int carDamageRate, int collisions, int collisionsOffFirstLap, int mpUnsafePitRelease,
                           int mpOffForGriefing, int cornerCuttingStringency, int parcFermeRules, int pitStopExperience,
                           int safetyCar, int safetyCarExperience, int formationLap, int formationLapExperience,
                           int redFlags, int affectsLicenseLevelSolo, int affectsLicenseLevelMP,
                           int numSessionsInWeekend, int[] weekendStructure) {
}

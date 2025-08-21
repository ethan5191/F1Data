package f1.data.packets;

import f1.data.telemetry.TelemetryData;

import java.util.Map;

public class PacketUtils {

    //Checks if the map of participants(drivers in session) contains the id we are looking for. Prevents extra ids for custom team from printing stuff when they have no data.
    public static boolean validKey(Map<Integer, TelemetryData> participants, int key) {
        return participants.containsKey(key);
    }
}

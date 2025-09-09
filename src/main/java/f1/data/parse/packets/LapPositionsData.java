package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 2025 PacketLapPositionsData Breakdown (Little Endian)
 * - F1 2025 Length: 1131 bytes (maxNumCars I believe is 22, will probably change to 24 in F1 2026)
 * This struct contains the lap-by-lap position history for all cars in the session.
 * It provides the position for each car over the past 50 laps.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * PacketLapPositionsData
 * -------------------
 * Member Name                    | Data Type | Size (bytes) | First Appeared | Notes
 * -------------------------------|-----------|--------------|----------------|-------------------
 * m_header                       | PacketHeader | 29        |                | Full packet header
 * m_numLaps                      | uint8     | 1            | 2025           | Number of laps in the data
 * m_lapStart                     | uint8     | 1            | 2025           | Index of the lap where the data starts
 * m_positionForVehicleIdx[50][maxNumCars]| uint8[50][22] | 1100         | 2025           | Position for each car, 0 if no record
 * <p>
 * Note:
 * - uint8 maps to a Java 'int' (byte cast to int).
 * - The 2D array must be read with nested loops for proper data conversion.
 * - The `cs_maxNumCarsInUDPData` is assumed to be 22, based on other packets.
 */

public record LapPositionsData(int numLaps, int lapStart, int[][] positionForVehicleIdx) {

    private static final int POSITION_SIZE = 50;

    private static int[][] buildPositionsForVehicleId(int packetFormat, ByteBuffer byteBuffer) {
        int innerArraySize = (packetFormat >= Constants.YEAR_2026) ? Constants.F1_26_AND_LATER_CAR_COUNT : Constants.F1_20_TO_25_CAR_COUNT;
        int[][] result = new int[POSITION_SIZE][innerArraySize];
        for (int i = 0; i < POSITION_SIZE; i++) {
            for (int j = 0; j < innerArraySize; j++) {
                result[i][j] = BitMaskUtils.bitMask8(byteBuffer.get());
            }
        }
        return result;
    }

    public LapPositionsData(int packetFormat, ByteBuffer byteBuffer) {
        this(BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                buildPositionsForVehicleId(packetFormat, byteBuffer)
        );
    }
}

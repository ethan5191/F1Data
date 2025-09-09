package f1.data.parse.packets.history;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * F1 2021 PacketSessionHistoryData Breakdown (Little Endian)
 * - F1 2021 Length: 1131 bytes
 * This struct contains the lap and tyre history for a single car.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * PacketSessionHistoryData
 * -------------------
 * Member Name           | Data Type            | Size (bytes) | First Appeared | Notes
 * ----------------------|----------------------|--------------|----------------|-------------------
 * m_header              | PacketHeader         | 24           |                | Full packet header
 * m_carIdx              | uint8                | 1            | 2021           | Index of the car
 * m_numLaps             | uint8                | 1            | 2021           | Num laps in the data
 * m_numTyreStints       | uint8                | 1            | 2021           | Number of tyre stints
 * m_bestLapTimeLapNum   | uint8                | 1            | 2021           | Lap the best lap was achieved
 * m_bestSector1LapNum   | uint8                | 1            | 2021           | Lap the best Sector 1 was achieved
 * m_bestSector2LapNum   | uint8                | 1            | 2021           | Lap the best Sector 2 was achieved
 * m_bestSector3LapNum   | uint8                | 1            | 2021           | Lap the best Sector 3 was achieved
 * m_lapHistoryData[100] | LapHistoryData       | 1100         | 2021           | Lap history for the car
 * m_tyreStintsHistoryData[8] | TyreStintHistoryData | 24           | 2021           | Tyre stint history
 *
 * Note:
 * - uint32 maps to a Java 'long'.
 * - uint16 maps to a Java 'int'.
 * - uint8 maps to a Java 'int' (byte cast to int).
 * - The lap history and tyre stint history arrays must be read in a loop for proper data conversion.
 */

public record SessionHistoryData(int carIndex, int numLaps, int numTyreStints, int bestLapTimeLapNum, int bestSector1LapNum,
                                 int bestSector2LapNum, int bestSector3LapNum, LapHistoryData[] lapHistoryData, TyreStintHistoryData[] tyreStintHistoryData) {

    private static final int LAP_HISTORY_SIZE = 100;
    private static final int TYRE_STINT_HISTORY_SIZE = 8;

    private static LapHistoryData[] buildLapHistoryData(int packetFormat, ByteBuffer byteBuffer) {
        LapHistoryData[] results = new LapHistoryData[LAP_HISTORY_SIZE];
        for (int i = 0; i < LAP_HISTORY_SIZE; i++) {
            results[i] = LapHistoryDataFactory.build(packetFormat, byteBuffer);
        }
        return results;
    }

    private static TyreStintHistoryData[] buildTyreStintsHistoryData(ByteBuffer byteBuffer) {
        TyreStintHistoryData[] results = new TyreStintHistoryData[TYRE_STINT_HISTORY_SIZE];
        for (int i = 0; i < TYRE_STINT_HISTORY_SIZE; i++) {
            results[i] = new TyreStintHistoryData(byteBuffer);
        }
        return results;
    }

    record SessionHistoryData21(int carIndex, int numLaps, int numTyreStints, int bestLapTimeLapNum, int bestSector1LapNum,
                              int bestSector2LapNum, int bestSector3LapNum, LapHistoryData[] lapHistoryData, TyreStintHistoryData[] tyreStintHistoryData) {
        public SessionHistoryData21(int packetFormat, ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask8(byteBuffer.get()),
                    buildLapHistoryData(packetFormat, byteBuffer),
                    buildTyreStintsHistoryData(byteBuffer)
            );
        }
    }
}

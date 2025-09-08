package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;

import java.nio.ByteBuffer;

/**
 * F1 2020 FinalClassificationData Breakdown (Little Endian)
 * - F1 2020/2021 Length: 37 bytes per car (2021 the type of bestLapTime changed from float to uint32)
 * - F1 2022 - 2024 Length: 45 bytes per car
 * This struct is 37 bytes long and contains details of a driver's final classification in a session.
 * This data is sent as an array for each car.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * PacketFinalClassificationData
 * -----------------------------
 * Member Name               | Data Type             | Size (bytes)  | First Appeared  | Notes
 * --------------------------|-----------------------|---------------|-----------------|-------------------------
 * m_header                  | PacketHeader          | 24            | 2018            | Full packet header
 * m_numCars                 | uint8                 | 1             | 2020            | Number of cars in this packet (22)
 * m_classificationData[22]  | FinalClassificationData | 814           | 2020            | Array for each car
 * - m_position              | uint8                 | 1             | 2020            |
 * - m_numLaps               | uint8                 | 1             | 2020            |
 * - m_gridPosition          | uint8                 | 1             | 2020            |
 * - m_points                | uint8                 | 1             | 2020            |
 * - m_numPitStops           | uint8                 | 1             | 2020            |
 * - m_resultStatus          | uint8                 | 1             | 2020            | Result status
 * - m_bestLapTime           | float (uint32 - 2021) | 4             | 2020            |
 * - m_totalRaceTime         | double                | 8             | 2020            | Total time without penalties
 * - m_penaltiesTime         | uint8                 | 1             | 2020            |
 * - m_numPenalties          | uint8                 | 1             | 2020            |
 * - m_numTyreStints         | uint8                 | 1             | 2020            | Number of tyre stints up to max
 * - m_tyreStintsActual[8]   | uint8[8]              | 8             | 2020            | Actual tyres used
 * - m_tyreStintsVisual[8]   | uint8[8]              | 8             | 2020            | Visual tyres used
 * - m_tyreStintsEndLaps[8]  | uint8[8]              | 8             | 2022            | End lap for each tire stint
 * <p>
 * Note:
 * - The uint8 type must be read as a byte and converted to an int to get the unsigned value.
 * - float maps directly to a Java 'float'.
 * - double maps directly to a Java 'double'.
 * - Arrays must be read in a loop for proper data conversion.
 */

public record FinalClassificationData(int position, int numLaps, int gridPosition, int points, int numPitsStops,
                                      int resultStatus, float bestLapTime20, double totalRaceTime, int penaltiesTime,
                                      int numPenalties, int numTyreStints, int[] tyreStintsActual,
                                      int[] tyreStintsVisual, long bestLapTime, int[] tyreStintsEndLaps) {

    record FinalClassificationData20(int position, int numLaps, int gridPosition, int points, int numPitsStops,
                                     int resultStatus, float bestLapTime20, double totalRaceTime, int penaltiesTime,
                                     int numPenalties, int numTyreStints, int[] tyreStintsActual,
                                     int[] tyreStintsVisual) {
        public FinalClassificationData20(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    byteBuffer.getFloat(), byteBuffer.getDouble(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    ParseUtils.parseIntArray(byteBuffer, 8), ParseUtils.parseIntArray(byteBuffer, 8)
            );
        }
    }

    record FinalClassificationData21(int position, int numLaps, int gridPosition, int points, int numPitsStops,
                                     int resultStatus, long bestLapTime, double totalRaceTime, int penaltiesTime,
                                     int numPenalties, int numTyreStints, int[] tyreStintsActual,
                                     int[] tyreStintsVisual) {
        public FinalClassificationData21(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask32(byteBuffer.getInt()), byteBuffer.getDouble(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    ParseUtils.parseIntArray(byteBuffer, 8), ParseUtils.parseIntArray(byteBuffer, 8)
            );
        }
    }

    record FinalClassificationData22(int position, int numLaps, int gridPosition, int points, int numPitsStops,
                                     int resultStatus, long bestLapTime, double totalRaceTime, int penaltiesTime,
                                     int numPenalties, int numTyreStints, int[] tyreStintsActual,
                                     int[] tyreStintsVisual, int[] tyreStintsEndLaps) {
        public FinalClassificationData22(ByteBuffer byteBuffer) {
            this(
                    BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    BitMaskUtils.bitMask32(byteBuffer.getInt()), byteBuffer.getDouble(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()),
                    ParseUtils.parseIntArray(byteBuffer, 8), ParseUtils.parseIntArray(byteBuffer, 8), ParseUtils.parseIntArray(byteBuffer, 8)
            );
        }
    }
}

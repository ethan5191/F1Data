package packets;

import utils.constants.Constants;

import java.nio.ByteBuffer;

/**
 * F1 24 TyreSetData Breakdown (Little Endian)
 *
 * This struct is 10 bytes long and contains details about a single tyre set.
 * This data is sent for all tyre sets in the session.
 *
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 *
 * Member Name                       | Data Type        | Size (bytes) | Starting Offset
 * ----------------------------------|------------------|--------------|-----------------
 * m_actualTyreCompound              | uint8            | 1            | 0
 * m_visualTyreCompound              | uint8            | 1            | 1
 * m_wear                            | uint8            | 1            | 2
 * m_available                       | uint8            | 1            | 3
 * m_recommendedSession              | uint8            | 1            | 4
 * m_lifeSpan                        | uint8            | 1            | 5
 * m_usableLife                      | uint8            | 1            | 6
 * m_lapDeltaTime                    | int16            | 2            | 7
 * m_fitted                          | uint8            | 1            | 9
 *
 * Note:
 * - The uint8 type must be read with bitmasking (e.g., byteBuffer.get() & Constants.BIT_MASK_8).
 * - The int16 type maps directly to a Java 'short'.
 */
public class TireSetsData {

    public TireSetsData(ByteBuffer byteBuffer) {
        this.actualTireCompound = byteBuffer.get() & Constants.BIT_MASK_8;
        this.visualTireCompound = byteBuffer.get() & Constants.BIT_MASK_8;
        this.wear = byteBuffer.get() & Constants.BIT_MASK_8;
        this.available = byteBuffer.get() & Constants.BIT_MASK_8;
        this.recommendedSession = byteBuffer.get() & Constants.BIT_MASK_8;
        this.lifeSpan = byteBuffer.get() & Constants.BIT_MASK_8;
        this.usableLife = byteBuffer.get() & Constants.BIT_MASK_8;
        this.lapDeltaTime = byteBuffer.getShort();
        this.fitted = byteBuffer.get() & Constants.BIT_MASK_8;
    }

    private final int actualTireCompound;
    private final int visualTireCompound;
    private final int wear;
    private final int available;
    private final int recommendedSession;
    private final int lifeSpan;
    private final int usableLife;
    private final short lapDeltaTime;
    private final int fitted;

    public int getActualTireCompound() {
        return actualTireCompound;
    }

    public int getVisualTireCompound() {
        return visualTireCompound;
    }

    public int getWear() {
        return wear;
    }

    public int getAvailable() {
        return available;
    }

    public int getRecommendedSession() {
        return recommendedSession;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public int getUsableLife() {
        return usableLife;
    }

    public short getLapDeltaTime() {
        return lapDeltaTime;
    }

    public int getFitted() {
        return fitted;
    }
}

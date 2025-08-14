package packets;

/**
 * F1 24 TyreSetData Breakdown (Little Endian)
 * Packet didn't exist prior to 2023's version of the game.
 * - F1 2023-2025 Length: 10 bytes
 * This struct is 10 bytes long and contains details about a single tyre set.
 * This data is sent for all tyre sets in the session.
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * -------------------------------
 * Member Name                     | Data Type       | Size (bytes) | First Appeared | Notes
 * --------------------------------|----------------|--------------|----------------|-------------------------
 * m_header                        | PacketHeader    | ...          | 2024           | Full packet header
 * m_carIdx                        | uint8           | 1            | 2024           | Index of the car this data relates to
 * m_tyreSetData[20]               | TyreSetData     | ...          | 2024           | Array for 20 tyre sets (13 dry, 7 wet)
 * - m_actualTyreCompound          | uint8           | 1            | 2024           | Actual tyre compound used
 * - m_visualTyreCompound          | uint8           | 1            | 2024           | Visual tyre compound used
 * - m_wear                        | uint8           | 1            | 2024           | Tyre wear (percentage)
 * - m_available                   | uint8           | 1            | 2024           | Whether this set is currently available
 * - m_recommendedSession          | uint8           | 1            | 2024           | Recommended session for tyre set
 * - m_lifeSpan                    | uint8           | 1            | 2024           | Laps left in this tyre set
 * - m_usableLife                  | uint8           | 1            | 2024           | Max number of laps recommended
 * - m_lapDeltaTime                | int16           | 2            | 2024           | Lap delta time in ms vs. fitted set
 * - m_fitted                      | uint8           | 1            | 2024           | Whether the set is fitted or not
 * m_fittedIdx                     | uint8           | 1            | 2024           | Index into array of the fitted tyre set
 * Note:
 * - The uint8 type must be read with bitmasking (e.g., byteBuffer.get() & Constants.BIT_MASK_8).
 * - The int16 type maps directly to a Java 'short'.
 */
public class TireSetsData {

    public TireSetsData(Builder builder) {
        this.actualTireCompound = builder.actualTireCompound;
        this.visualTireCompound = builder.visualTireCompound;
        this.wear = builder.wear;
        this.available = builder.available;
        this.recommendedSession = builder.recommendedSession;
        this.lifeSpan = builder.lifeSpan;
        this.usableLife = builder.usableLife;
        this.lapDeltaTime = builder.lapDeltaTime;
        this.fitted = builder.fitted;
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

    public static class Builder {

        private int actualTireCompound;
        private int visualTireCompound;
        private int wear;
        private int available;
        private int recommendedSession;
        private int lifeSpan;
        private int usableLife;
        private short lapDeltaTime;
        private int fitted;

        public Builder setActualTireCompound(int actualTireCompound) {
            this.actualTireCompound = actualTireCompound;
            return this;
        }

        public Builder setVisualTireCompound(int visualTireCompound) {
            this.visualTireCompound = visualTireCompound;
            return this;
        }

        public Builder setWear(int wear) {
            this.wear = wear;
            return this;
        }

        public Builder setAvailable(int available) {
            this.available = available;
            return this;
        }

        public Builder setRecommendedSession(int recommendedSession) {
            this.recommendedSession = recommendedSession;
            return this;
        }

        public Builder setLifeSpan(int lifeSpan) {
            this.lifeSpan = lifeSpan;
            return this;
        }

        public Builder setUsableLife(int usableLife) {
            this.usableLife = usableLife;
            return this;
        }

        public Builder setLapDeltaTime(short lapDeltaTime) {
            this.lapDeltaTime = lapDeltaTime;
            return this;
        }

        public Builder setFitted(int fitted) {
            this.fitted = fitted;
            return this;
        }

        public TireSetsData build() {
            return new TireSetsData(this);
        }
    }
}

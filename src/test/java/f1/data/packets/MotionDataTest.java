package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class MotionDataTest extends AbstractFactoryTest {

    @Test
    @DisplayName("Builds the Motion Data packet.")
    void testBuild_motionData() {
        int bitMask16Count = 6;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            MotionData result = new MotionData(mockByteBuffer);
            assertNotNull(result);
            assertEquals(0, result.worldPositionX());
            assertEquals(0, result.worldPositionY());
            assertEquals(0, result.worldPositionZ());
            assertEquals(0, result.worldVelocityX());
            assertEquals(0, result.worldVelocityY());
            assertEquals(0, result.worldVelocityZ());
            assertEquals(BIT_16_START, result.worldForwardDirX());
            assertEquals(BIT_16_START + 1, result.worldForwardDirY());
            assertEquals(BIT_16_START + 2, result.worldForwardDirZ());
            assertEquals(BIT_16_START + 3, result.worldRightDirX());
            assertEquals(BIT_16_START + 4, result.worldRightDirY());
            assertEquals(BIT_16_START + 5, result.worldRightDirZ());
            assertEquals(0, result.gForceLat());
            assertEquals(0, result.gForceLon());
            assertEquals(0, result.gForceVer());
            assertEquals(0, result.yaw());
            assertEquals(0, result.pitch());
            assertEquals(0, result.roll());
        }
    }
}

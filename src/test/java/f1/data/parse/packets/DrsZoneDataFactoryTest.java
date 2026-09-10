package f1.data.parse.packets;

import f1.data.parse.packets.session.*;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.ParseUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class DrsZoneDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("supportedYears2026")
    @DisplayName("Builds the Drs Zone Data for 2026 to Present.")
    void testBuild_drsZoneData2026ToPresent(int packetFormat) {
        int floatCount = 2;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class);
             MockedStatic<ParseUtils> parseUtils = mockStatic(ParseUtils.class)) {
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            FactoryTestHelper.parseFloatArray(mockByteBuffer, parseUtils);
            when(mockByteBuffer.getFloat()).thenReturn(Constants.DIVISOR);
            DrsZoneData[] result = new DrsZoneDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            Assertions.assertEquals(SessionData.DRS_ZONE_SIZE, result.length);
            for (DrsZoneData data : result) {
                assertEquals(1, data.zoneStart());
                assertEquals(1, data.zoneEnd());
            }
        }
    }
}

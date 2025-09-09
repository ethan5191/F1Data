package f1.data.parse.packets;

import f1.data.parse.packets.session.MarshalZoneData;
import f1.data.parse.packets.session.MarshalZoneDataFactory;
import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class MarshalZoneDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2019, Constants.YEAR_2020, Constants.YEAR_2021, Constants.YEAR_2022,
            Constants.YEAR_2023, Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Marshal Zone Data from 2019 to Present.")
    void testBuild_marshalZoneData2019ToPresent(int packetFormat) {
        int intCount = 1;
        int floatCount = 1;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockSingleGetValue(mockByteBuffer, intCount);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            MarshalZoneData result = MarshalZoneDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);

            assertEquals(FLOAT_START, result.zoneStart());
            assertEquals(intCount + 1, result.zoneFlag());
        }
    }
}

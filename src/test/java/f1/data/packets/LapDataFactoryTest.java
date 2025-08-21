package f1.data.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class LapDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2020)
    @DisplayName("Builds the Lap Data for 2020.")
    void testBuild_lapData2020(int packetFormat) {
        int bitMask8Count = 13;
        int bitMask16Count = 8;
        int floatCount = 6;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = LapDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(FLOAT_START, result.lastLapTime20());
            assertEquals(FLOAT_START + 1, result.currentLapTime20());
            assertEquals(BIT_16_START, result.sector1TimeMsPart());
            assertEquals(BIT_16_START + 1, result.sector2TimeMsPart());
            assertEquals(FLOAT_START + 2, result.bestLapTime());
            assertEquals(BIT_8_START, result.bestLapNum());
            assertEquals(BIT_16_START + 2, result.bestLapSector1InMS());
            assertEquals(BIT_16_START + 3, result.bestLapSector2InMS());
            assertEquals(BIT_16_START + 4, result.bestLapSector3InMS());
            assertEquals(BIT_16_START + 5, result.bestOverallSector1InMS());
            assertEquals(BIT_8_START + 1, result.bestOverallSector1LapNum());
            assertEquals(BIT_16_START + 6, result.bestOverallSector2InMS());
            assertEquals(BIT_8_START + 2, result.bestOverallSector2LapNum());
            assertEquals(BIT_16_START + 7, result.bestOverallSector3InMS());
            assertEquals(BIT_8_START + 3, result.bestOverallSector3LapNum());
            assertEquals(FLOAT_START + 3, result.lapDistance());
            assertEquals(FLOAT_START + 4, result.totalDistance());
            assertEquals(FLOAT_START + 5, result.safetyCarDelta());
            assertEquals(BIT_8_START + 4, result.carPosition());
            assertEquals(BIT_8_START + 5, result.currentLapNum());
            assertEquals(BIT_8_START + 6, result.pitStatus());
            assertEquals(BIT_8_START + 7, result.sector());
            assertEquals(BIT_8_START + 8, result.currentLapInvalid());
            assertEquals(BIT_8_START + 9, result.penalties());
            assertEquals(BIT_8_START + 10, result.gridPosition());
            assertEquals(BIT_8_START + 11, result.driverStatus());
            assertEquals(BIT_8_START + 12, result.resultStatus());

            //Params not used in 2020
            assertEquals(0, result.lastLapTimeMs());
            assertEquals(0, result.currentLapTimeMs());
            assertEquals(0, result.sector1TimeMinutesPart());
            assertEquals(0, result.sector2TimeMinutesPart());
            assertEquals(0, result.deltaCarInFrontMsPart());
            assertEquals(0, result.deltaCarInFrontMinutesPart());
            assertEquals(0, result.deltaRaceLeaderMsPart());
            assertEquals(0, result.deltaRaceLeaderMinutesPart());
            assertEquals(0, result.numPitStops());
            assertEquals(0, result.totalWarnings());
            assertEquals(0, result.cornerCuttingWarnings());
            assertEquals(0, result.numUnservedDriveThroughPens());
            assertEquals(0, result.numUnservedStopGoPens());
            assertEquals(0, result.pitLaneTimeActive());
            assertEquals(0, result.pitLaneTimerInLaneInMs());
            assertEquals(0, result.pitStopTimerInMS());
            assertEquals(0, result.pitStopShouldServePen());
            assertEquals(0, result.speedTrapFastestSpeed());
            assertEquals(0, result.speedTrapFastestLap());
            assertEquals(0, result.warnings());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2021, Constants.YEAR_2022})
    @DisplayName("Builds the Lap Data for 2021 and 2022.")
    void testBuild_lapData2021And2022(int packetFormat) {
        int bitMask8Count = 15;
        int bitMask16Count = 4;
        int bitMask32Count = 2;
        int floatCount = 3;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = LapDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_32_START, result.lastLapTimeMs());
            assertEquals(BIT_32_START + 1, result.currentLapTimeMs());
            assertEquals(BIT_16_START, result.sector1TimeMsPart());
            assertEquals(BIT_16_START + 1, result.sector2TimeMsPart());
            assertEquals(FLOAT_START, result.lapDistance());
            assertEquals(FLOAT_START + 1, result.totalDistance());
            assertEquals(FLOAT_START + 2, result.safetyCarDelta());
            assertEquals(BIT_8_START, result.carPosition());
            assertEquals(BIT_8_START + 1, result.currentLapNum());
            assertEquals(BIT_8_START + 2, result.pitStatus());
            assertEquals(BIT_8_START + 3, result.numPitStops());
            assertEquals(BIT_8_START + 4, result.sector());
            assertEquals(BIT_8_START + 5, result.currentLapInvalid());
            assertEquals(BIT_8_START + 6, result.penalties());
            assertEquals(BIT_8_START + 7, result.warnings());
            assertEquals(BIT_8_START + 8, result.numUnservedDriveThroughPens());
            assertEquals(BIT_8_START + 9, result.numUnservedStopGoPens());
            assertEquals(BIT_8_START + 10, result.gridPosition());
            assertEquals(BIT_8_START + 11, result.driverStatus());
            assertEquals(BIT_8_START + 12, result.resultStatus());
            assertEquals(BIT_8_START + 13, result.pitLaneTimeActive());
            assertEquals(BIT_16_START + 2, result.pitLaneTimerInLaneInMs());
            assertEquals(BIT_16_START + 3, result.pitStopTimerInMS());
            assertEquals(BIT_8_START + 14, result.pitStopShouldServePen());

            assertEquals(0, result.sector1TimeMinutesPart());
            assertEquals(0, result.sector2TimeMinutesPart());
            assertEquals(0, result.deltaCarInFrontMsPart());
            assertEquals(0, result.deltaCarInFrontMinutesPart());
            assertEquals(0, result.deltaRaceLeaderMsPart());
            assertEquals(0, result.deltaRaceLeaderMinutesPart());
            assertEquals(0, result.totalWarnings());
            assertEquals(0, result.cornerCuttingWarnings());
            assertEquals(0, result.speedTrapFastestSpeed());
            assertEquals(0, result.speedTrapFastestLap());
            lapData2020(result);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2023)
    @DisplayName("Builds the Lap Data for 2023.")
    void testBuild_lapData2023(int packetFormat) {
        int bitMask8Count = 19;
        int bitMask16Count = 6;
        int bitMask32Count = 2;
        int floatCount = 4;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = LapDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_32_START, result.lastLapTimeMs());
            assertEquals(BIT_32_START + 1, result.currentLapTimeMs());
            assertEquals(BIT_16_START, result.sector1TimeMsPart());
            assertEquals(BIT_8_START, result.sector1TimeMinutesPart());
            assertEquals(BIT_16_START + 1, result.sector2TimeMsPart());
            assertEquals(BIT_8_START + 1, result.sector2TimeMinutesPart());
            assertEquals(BIT_16_START + 2, result.deltaCarInFrontMsPart());
            assertEquals(BIT_16_START + 3, result.deltaRaceLeaderMsPart());
            assertEquals(FLOAT_START, result.lapDistance());
            assertEquals(FLOAT_START + 1, result.totalDistance());
            assertEquals(FLOAT_START + 2, result.safetyCarDelta());
            assertEquals(BIT_8_START + 2, result.carPosition());
            assertEquals(BIT_8_START + 3, result.currentLapNum());
            assertEquals(BIT_8_START + 4, result.pitStatus());
            assertEquals(BIT_8_START + 5, result.numPitStops());
            assertEquals(BIT_8_START + 6, result.sector());
            assertEquals(BIT_8_START + 7, result.currentLapInvalid());
            assertEquals(BIT_8_START + 8, result.penalties());
            assertEquals(BIT_8_START + 9, result.totalWarnings());
            assertEquals(BIT_8_START + 10, result.cornerCuttingWarnings());
            assertEquals(BIT_8_START + 11, result.numUnservedDriveThroughPens());
            assertEquals(BIT_8_START + 12, result.numUnservedStopGoPens());
            assertEquals(BIT_8_START + 13, result.gridPosition());
            assertEquals(BIT_8_START + 14, result.driverStatus());
            assertEquals(BIT_8_START + 15, result.resultStatus());
            assertEquals(BIT_8_START + 16, result.pitLaneTimeActive());
            assertEquals(BIT_16_START + 4, result.pitLaneTimerInLaneInMs());
            assertEquals(BIT_16_START + 5, result.pitStopTimerInMS());
            assertEquals(BIT_8_START + 17, result.pitStopShouldServePen());
            assertEquals(FLOAT_START + 3, result.speedTrapFastestSpeed());
            assertEquals(BIT_8_START + 18, result.speedTrapFastestLap());


            assertEquals(0, result.deltaCarInFrontMinutesPart());
            assertEquals(0, result.deltaRaceLeaderMinutesPart());
            assertEquals(0, result.warnings());
            lapData2020(result);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Constants.YEAR_2024, Constants.YEAR_2025})
    @DisplayName("Builds the Lap Data for 2024 to Present.")
    void testBuild_lapData2024ToPresent(int packetFormat) {
        int bitMask8Count = 21;
        int bitMask16Count = 6;
        int bitMask32Count = 2;
        int floatCount = 4;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = LapDataFactory.build(packetFormat, mockByteBuffer);
            assertNotNull(result);
            assertEquals(BIT_32_START, result.lastLapTimeMs());
            assertEquals(BIT_32_START + 1, result.currentLapTimeMs());
            assertEquals(BIT_16_START, result.sector1TimeMsPart());
            assertEquals(BIT_8_START, result.sector1TimeMinutesPart());
            assertEquals(BIT_16_START + 1, result.sector2TimeMsPart());
            assertEquals(BIT_8_START + 1, result.sector2TimeMinutesPart());
            assertEquals(BIT_16_START + 2, result.deltaCarInFrontMsPart());
            assertEquals(BIT_8_START + 2, result.deltaCarInFrontMinutesPart());
            assertEquals(BIT_16_START + 3, result.deltaRaceLeaderMsPart());
            assertEquals(BIT_8_START + 3, result.deltaRaceLeaderMinutesPart());
            assertEquals(FLOAT_START, result.lapDistance());
            assertEquals(FLOAT_START + 1, result.totalDistance());
            assertEquals(FLOAT_START + 2, result.safetyCarDelta());
            assertEquals(BIT_8_START + 4, result.carPosition());
            assertEquals(BIT_8_START + 5, result.currentLapNum());
            assertEquals(BIT_8_START + 6, result.pitStatus());
            assertEquals(BIT_8_START + 7, result.numPitStops());
            assertEquals(BIT_8_START + 8, result.sector());
            assertEquals(BIT_8_START + 9, result.currentLapInvalid());
            assertEquals(BIT_8_START + 10, result.penalties());
            assertEquals(BIT_8_START + 11, result.totalWarnings());
            assertEquals(BIT_8_START + 12, result.cornerCuttingWarnings());
            assertEquals(BIT_8_START + 13, result.numUnservedDriveThroughPens());
            assertEquals(BIT_8_START + 14, result.numUnservedStopGoPens());
            assertEquals(BIT_8_START + 15, result.gridPosition());
            assertEquals(BIT_8_START + 16, result.driverStatus());
            assertEquals(BIT_8_START + 17, result.resultStatus());
            assertEquals(BIT_8_START + 18, result.pitLaneTimeActive());
            assertEquals(BIT_16_START + 4, result.pitLaneTimerInLaneInMs());
            assertEquals(BIT_16_START + 5, result.pitStopTimerInMS());
            assertEquals(BIT_8_START + 19, result.pitStopShouldServePen());
            assertEquals(FLOAT_START + 3, result.speedTrapFastestSpeed());
            assertEquals(BIT_8_START + 20, result.speedTrapFastestLap());

            assertEquals(0, result.warnings());
            lapData2020(result);
        }
    }

    private void lapData2020(LapData result) {
        assertEquals(0, result.lastLapTime20());
        assertEquals(0, result.currentLapTime20());
        assertEquals(0, result.bestLapTime());
        assertEquals(0, result.bestLapNum());
        assertEquals(0, result.bestLapSector1InMS());
        assertEquals(0, result.bestLapSector2InMS());
        assertEquals(0, result.bestLapSector3InMS());
        assertEquals(0, result.bestOverallSector1InMS());
        assertEquals(0, result.bestOverallSector1LapNum());
        assertEquals(0, result.bestOverallSector2InMS());
        assertEquals(0, result.bestOverallSector2LapNum());
        assertEquals(0, result.bestOverallSector3InMS());
        assertEquals(0, result.bestOverallSector3LapNum());
    }
}

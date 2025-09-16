package f1.data.parse.packets;

import f1.data.utils.BitMaskUtils;
import f1.data.utils.constants.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class LapDataFactoryTest extends AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("supportedYears2019")
    @DisplayName("Builds the Lap Data for 2019.")
    void testBuild_lapData2019(int packetFormat) {
        int bitMask8Count = 9;
        int floatCount = 8;
        int bitMask8Value = BIT_8_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = new LapDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(floatValue++, result.lastLapTime20());
            assertEquals(floatValue++, result.currentLapTime20());
            assertEquals(floatValue++, result.bestLapTime());
            assertEquals(floatValue++, result.sector1Time());
            assertEquals(floatValue++, result.sector2Time());
            assertEquals(floatValue++, result.lapDistance());
            assertEquals(floatValue++, result.totalDistance());
            assertEquals(floatValue++, result.safetyCarDelta());
            assertEquals(bitMask8Value++, result.carPosition());
            assertEquals(bitMask8Value++, result.currentLapNum());
            assertEquals(bitMask8Value++, result.pitStatus());
            assertEquals(bitMask8Value++, result.sector());
            assertEquals(bitMask8Value++, result.currentLapInvalid());
            assertEquals(bitMask8Value++, result.penalties());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.driverStatus());
            assertEquals(bitMask8Value++, result.resultStatus());

            assertEquals(0, result.sector1TimeMsPart());
            assertEquals(0, result.sector2TimeMsPart());
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
    @MethodSource("supportedYears2020")
    @DisplayName("Builds the Lap Data for 2020.")
    void testBuild_lapData2020(int packetFormat) {
        int bitMask8Count = 13;
        int bitMask16Count = 8;
        int floatCount = 6;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = new LapDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(floatValue++, result.lastLapTime20());
            assertEquals(floatValue++, result.currentLapTime20());
            assertEquals(bitMask16Value++, result.sector1TimeMsPart());
            assertEquals(bitMask16Value++, result.sector2TimeMsPart());
            assertEquals(floatValue++, result.bestLapTime());
            assertEquals(bitMask8Value++, result.bestLapNum());
            assertEquals(bitMask16Value++, result.bestLapSector1InMS());
            assertEquals(bitMask16Value++, result.bestLapSector2InMS());
            assertEquals(bitMask16Value++, result.bestLapSector3InMS());
            assertEquals(bitMask16Value++, result.bestOverallSector1InMS());
            assertEquals(bitMask8Value++, result.bestOverallSector1LapNum());
            assertEquals(bitMask16Value++, result.bestOverallSector2InMS());
            assertEquals(bitMask8Value++, result.bestOverallSector2LapNum());
            assertEquals(bitMask16Value++, result.bestOverallSector3InMS());
            assertEquals(bitMask8Value++, result.bestOverallSector3LapNum());
            assertEquals(floatValue++, result.lapDistance());
            assertEquals(floatValue++, result.totalDistance());
            assertEquals(floatValue++, result.safetyCarDelta());
            assertEquals(bitMask8Value++, result.carPosition());
            assertEquals(bitMask8Value++, result.currentLapNum());
            assertEquals(bitMask8Value++, result.pitStatus());
            assertEquals(bitMask8Value++, result.sector());
            assertEquals(bitMask8Value++, result.currentLapInvalid());
            assertEquals(bitMask8Value++, result.penalties());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.driverStatus());
            assertEquals(bitMask8Value++, result.resultStatus());

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
            assertEquals(0, result.sector1Time());
            assertEquals(0, result.sector2Time());
        }
    }

    @ParameterizedTest
    @MethodSource("supportedYears2021To2022")
    @DisplayName("Builds the Lap Data for 2021 and 2022.")
    void testBuild_lapData2021And2022(int packetFormat) {
        int bitMask8Count = 15;
        int bitMask16Count = 4;
        int bitMask32Count = 2;
        int floatCount = 3;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int bitMask32Value = BIT_32_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = new LapDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask32Value++, result.lastLapTimeMs());
            assertEquals(bitMask32Value++, result.currentLapTimeMs());
            assertEquals(bitMask16Value++, result.sector1TimeMsPart());
            assertEquals(bitMask16Value++, result.sector2TimeMsPart());
            assertEquals(floatValue++, result.lapDistance());
            assertEquals(floatValue++, result.totalDistance());
            assertEquals(floatValue++, result.safetyCarDelta());
            assertEquals(bitMask8Value++, result.carPosition());
            assertEquals(bitMask8Value++, result.currentLapNum());
            assertEquals(bitMask8Value++, result.pitStatus());
            assertEquals(bitMask8Value++, result.numPitStops());
            assertEquals(bitMask8Value++, result.sector());
            assertEquals(bitMask8Value++, result.currentLapInvalid());
            assertEquals(bitMask8Value++, result.penalties());
            assertEquals(bitMask8Value++, result.warnings());
            assertEquals(bitMask8Value++, result.numUnservedDriveThroughPens());
            assertEquals(bitMask8Value++, result.numUnservedStopGoPens());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.driverStatus());
            assertEquals(bitMask8Value++, result.resultStatus());
            assertEquals(bitMask8Value++, result.pitLaneTimeActive());
            assertEquals(bitMask16Value++, result.pitLaneTimerInLaneInMs());
            assertEquals(bitMask16Value++, result.pitStopTimerInMS());
            assertEquals(bitMask8Value++, result.pitStopShouldServePen());

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
            assertEquals(0, result.sector1Time());
            assertEquals(0, result.sector2Time());
            lapData2020(result);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = Constants.YEAR_2023)
    @DisplayName("Builds the Lap Data for 2023.")
    void testBuild_lapData2023(int packetFormat) {
        int bitMask8Count = 18;
        int bitMask16Count = 6;
        int bitMask32Count = 2;
        int floatCount = 3;
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int bitMask32Value = BIT_32_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = new LapDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask32Value++, result.lastLapTimeMs());
            assertEquals(bitMask32Value++, result.currentLapTimeMs());
            assertEquals(bitMask16Value++, result.sector1TimeMsPart());
            assertEquals(bitMask8Value++, result.sector1TimeMinutesPart());
            assertEquals(bitMask16Value++, result.sector2TimeMsPart());
            assertEquals(bitMask8Value++, result.sector2TimeMinutesPart());
            assertEquals(bitMask16Value++, result.deltaCarInFrontMsPart());
            assertEquals(bitMask16Value++, result.deltaRaceLeaderMsPart());
            assertEquals(floatValue++, result.lapDistance());
            assertEquals(floatValue++, result.totalDistance());
            assertEquals(floatValue++, result.safetyCarDelta());
            assertEquals(bitMask8Value++, result.carPosition());
            assertEquals(bitMask8Value++, result.currentLapNum());
            assertEquals(bitMask8Value++, result.pitStatus());
            assertEquals(bitMask8Value++, result.numPitStops());
            assertEquals(bitMask8Value++, result.sector());
            assertEquals(bitMask8Value++, result.currentLapInvalid());
            assertEquals(bitMask8Value++, result.penalties());
            assertEquals(bitMask8Value++, result.totalWarnings());
            assertEquals(bitMask8Value++, result.cornerCuttingWarnings());
            assertEquals(bitMask8Value++, result.numUnservedDriveThroughPens());
            assertEquals(bitMask8Value++, result.numUnservedStopGoPens());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.driverStatus());
            assertEquals(bitMask8Value++, result.resultStatus());
            assertEquals(bitMask8Value++, result.pitLaneTimeActive());
            assertEquals(bitMask16Value++, result.pitLaneTimerInLaneInMs());
            assertEquals(bitMask16Value++, result.pitStopTimerInMS());
            assertEquals(bitMask8Value++, result.pitStopShouldServePen());

            assertEquals(0, result.deltaCarInFrontMinutesPart());
            assertEquals(0, result.deltaRaceLeaderMinutesPart());
            assertEquals(0, result.speedTrapFastestSpeed());
            assertEquals(0, result.speedTrapFastestLap());
            assertEquals(0, result.warnings());
            assertEquals(0, result.sector1Time());
            assertEquals(0, result.sector2Time());
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
        int bitMask8Value = BIT_8_START;
        int bitMask16Value = BIT_16_START;
        int bitMask32Value = BIT_32_START;
        int floatValue = FLOAT_START;
        try (MockedStatic<BitMaskUtils> bitMaskUtils = mockStatic(BitMaskUtils.class)) {
            FactoryTestHelper.mockBitMask8(bitMaskUtils, bitMask8Count);
            FactoryTestHelper.mockBitMask16(bitMaskUtils, bitMask16Count);
            FactoryTestHelper.mockBitMask32(bitMaskUtils, bitMask32Count);
            FactoryTestHelper.mockFloatValues(mockByteBuffer, floatCount);
            LapData result = new LapDataFactory(packetFormat).build(mockByteBuffer);
            assertNotNull(result);
            assertEquals(bitMask32Value++, result.lastLapTimeMs());
            assertEquals(bitMask32Value++, result.currentLapTimeMs());
            assertEquals(bitMask16Value++, result.sector1TimeMsPart());
            assertEquals(bitMask8Value++, result.sector1TimeMinutesPart());
            assertEquals(bitMask16Value++, result.sector2TimeMsPart());
            assertEquals(bitMask8Value++, result.sector2TimeMinutesPart());
            assertEquals(bitMask16Value++, result.deltaCarInFrontMsPart());
            assertEquals(bitMask8Value++, result.deltaCarInFrontMinutesPart());
            assertEquals(bitMask16Value++, result.deltaRaceLeaderMsPart());
            assertEquals(bitMask8Value++, result.deltaRaceLeaderMinutesPart());
            assertEquals(floatValue++, result.lapDistance());
            assertEquals(floatValue++, result.totalDistance());
            assertEquals(floatValue++, result.safetyCarDelta());
            assertEquals(bitMask8Value++, result.carPosition());
            assertEquals(bitMask8Value++, result.currentLapNum());
            assertEquals(bitMask8Value++, result.pitStatus());
            assertEquals(bitMask8Value++, result.numPitStops());
            assertEquals(bitMask8Value++, result.sector());
            assertEquals(bitMask8Value++, result.currentLapInvalid());
            assertEquals(bitMask8Value++, result.penalties());
            assertEquals(bitMask8Value++, result.totalWarnings());
            assertEquals(bitMask8Value++, result.cornerCuttingWarnings());
            assertEquals(bitMask8Value++, result.numUnservedDriveThroughPens());
            assertEquals(bitMask8Value++, result.numUnservedStopGoPens());
            assertEquals(bitMask8Value++, result.gridPosition());
            assertEquals(bitMask8Value++, result.driverStatus());
            assertEquals(bitMask8Value++, result.resultStatus());
            assertEquals(bitMask8Value++, result.pitLaneTimeActive());
            assertEquals(bitMask16Value++, result.pitLaneTimerInLaneInMs());
            assertEquals(bitMask16Value++, result.pitStopTimerInMS());
            assertEquals(bitMask8Value++, result.pitStopShouldServePen());
            assertEquals(floatValue++, result.speedTrapFastestSpeed());
            assertEquals(bitMask8Value++, result.speedTrapFastestLap());

            assertEquals(0, result.warnings());
            assertEquals(0, result.sector1Time());
            assertEquals(0, result.sector2Time());
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

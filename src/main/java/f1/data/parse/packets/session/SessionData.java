package f1.data.parse.packets.session;

import f1.data.enums.FormulaEnum;
import f1.data.enums.SessionTypeEnum;
import f1.data.enums.TrackEnum;
import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

/**
 * - F1 2019 Length: 149 bytes
 * - F1 2020 Length: 227 bytes
 * - F1 2021 Length: 601 bytes
 * - F1 2022 Length: 608 bytes
 * - F1 2023 Length: 615 bytes
 * - F1 2024/2025 Length: 724 bytes
 * <p>
 * The values must be read from a ByteBuffer configured for Little Endian byte order.
 * <p>
 * PacketSessionData
 * -------------------
 * Member Name                    | Data Type | Size (bytes) | First Appeared | Notes
 * -------------------------------|-----------|--------------|----------------|----------------------------------------------------------------------------------
 * m_header                       | struct    | ---          |                | Header
 * - m_weather                    | uint8     | 1            | 2019           | Weather - 0 = clear, 1 = light cloud, 2 = overcast, 3 = light rain, 4 = heavy rain, 5 = storm
 * - m_trackTemperature           | int8      | 1            | 2019           | Track temp. in degrees celsius
 * - m_airTemperature             | int8      | 1            | 2019           | Air temp. in degrees celsius
 * - m_totalLaps                  | uint8     | 1            | 2019           | Total number of laps in this race
 * - m_trackLength                | uint16    | 2            | 2019           | Track length in metres
 * - m_sessionType                | uint8     | 1            | 2019           | 0 = unknown, see appendix
 * - m_trackId                    | int8      | 1            | 2019           | -1 for unknown, see appendix
 * - m_formula                    | uint8     | 1            | 2019           | Formula, 0 = F1 Modern, 1 = F1 Classic, 2 = F2, 3 = F1 Generic, 4 = Beta, 6 = Esports, 8 = F1 World, 9 = F1 Elimination
 * - m_sessionTimeLeft            | uint16    | 2            | 2019           | Time left in session in seconds
 * - m_sessionDuration            | uint16    | 2            | 2019           | Session duration in seconds
 * - m_pitSpeedLimit              | uint8     | 1            | 2019           | Pit speed limit in kilometres per hour
 * - m_gamePaused                 | uint8     | 1            | 2019           | Whether the game is paused – network game only
 * - m_isSpectating               | uint8     | 1            | 2019           | Whether the player is spectating
 * - m_spectatorCarIndex          | uint8     | 1            | 2019           | Index of the car being spectated
 * - m_sliProNativeSupport        | uint8     | 1            | 2019           | SLI Pro support, 0 = inactive, 1 = active
 * - m_numMarshalZones            | uint8     | 1            | 2019           | Number of marshal zones to follow
 * - m_marshalZones[21]           | struct    | 105          | 2019           | List of marshal zones – max 21
 * - m_safetyCarStatus            | uint8     | 1            | 2019           | 0 = no safety car, 1 = full, 2 = virtual, 3 = formation lap
 * - m_networkGame                | uint8     | 1            | 2019           | 0 = offline, 1 = online
 * - m_numWeatherForecastSamples  | uint8     | 1            | 2020           | Number of weather samples to follow
 * - m_weatherForecastSamples[64] | struct    | 512          | 2020           | Array of weather forecast samples
 * - m_forecastAccuracy           | uint8     | 1            | 2021           | 0 = Perfect, 1 = Approximate
 * - m_aiDifficulty               | uint8     | 1            | 2021           | AI Difficulty rating – 0-110
 * - m_seasonLinkIdentifier       | uint32    | 4            | 2021           | Identifier for season - persists across saves
 * - m_weekendLinkIdentifier      | uint32    | 4            | 2021           | Identifier for weekend - persists across saves
 * - m_sessionLinkIdentifier      | uint32    | 4            | 2021           | Identifier for session - persists across saves
 * - m_pitStopWindowIdealLap      | uint8     | 1            | 2021           | Ideal lap to pit on for current strategy (player)
 * - m_pitStopWindowLatestLap     | uint8     | 1            | 2021           | Latest lap to pit on for current strategy (player)
 * - m_pitStopRejoinPosition      | uint8     | 1            | 2021           | Predicted position to rejoin at (player)
 * - m_steeringAssist             | uint8     | 1            | 2021           | 0 = off, 1 = on
 * - m_brakingAssist              | uint8     | 1            | 2021           | 0 = off, 1 = low, 2 = medium, 3 = high
 * - m_gearboxAssist              | uint8     | 1            | 2021           | 1 = manual, 2 = manual & suggested gear, 3 = auto
 * - m_pitAssist                  | uint8     | 1            | 2021           | 0 = off, 1 = on
 * - m_pitReleaseAssist           | uint8     | 1            | 2021           | 0 = off, 1 = on
 * - m_ERSAssist                  | uint8     | 1            | 2021           | 0 = off, 1 = on
 * - m_DRSAssist                  | uint8     | 1            | 2021           | 0 = off, 1 = on
 * - m_dynamicRacingLine          | uint8     | 1            | 2021           | 0 = off, 1 = corners only, 2 = full
 * - m_dynamicRacingLineType      | uint8     | 1            | 2021           | 0 = 2D, 1 = 3D
 * - m_gameMode                   | uint8     | 1            | 2022           | Game mode id - see appendix
 * - m_ruleSet                    | uint8     | 1            | 2022           | Ruleset - see appendix
 * - m_timeOfDay                  | uint32    | 4            | 2022           | Local time of day - minutes since midnight
 * - m_sessionLength              | uint8     | 1            | 2022           | 0 = None, 2 = Very Short, 3 = Short, 4 = Medium, 5 = Medium Long, 6 = Long, 7 = Full
 * - m_speedUnitsLeadPlayer       | uint8     | 1            | 2023           | 0 = MPH, 1 = KPH
 * - m_temperatureUnitsLeadPlayer | uint8     | 1            | 2023           | 0 = Celsius, 1 = Fahrenheit
 * - m_speedUnitsSecondaryPlayer  | uint8     | 1            | 2023           | 0 = MPH, 1 = KPH
 * - m_temperatureUnitsSecondaryPlayer | uint8| 1            | 2023           | 0 = Celsius, 1 = Fahrenheit
 * - m_numSafetyCarPeriods        | uint8     | 1            | 2023           | Number of safety cars called during session
 * - m_numVirtualSafetyCarPeriods | uint8     | 1            | 2023           | Number of virtual safety cars called
 * - m_numRedFlagPeriods          | uint8     | 1            | 2023           | Number of red flags called during session
 * - m_equalCarPerformance        | uint8     | 1            | 2024           | 0 = Off, 1 = On
 * - m_recoveryMode               | uint8     | 1            | 2024           | 0 = None, 1 = Flashbacks, 2 = Auto-recovery
 * - m_flashbackLimit             | uint8     | 1            | 2024           | 0 = Low, 1 = Medium, 2 = High, 3 = Unlimited
 * - m_surfaceType                | uint8     | 1            | 2024           | 0 = Simplified, 1 = Realistic
 * - m_lowFuelMode                | uint8     | 1            | 2024           | 0 = Easy, 1 = Hard
 * - m_raceStarts                 | uint8     | 1            | 2024           | 0 = Manual, 1 = Assisted
 * - m_tyreTemperature            | uint8     | 1            | 2024           | 0 = Surface only, 1 = Surface & Carcass
 * - m_pitLaneTyreSim             | uint8     | 1            | 2024           | 0 = On, 1 = Off
 * - m_carDamage                  | uint8     | 1            | 2024           | 0 = Off, 1 = Reduced, 2 = Standard, 3 = Simulation
 * - m_carDamageRate              | uint8     | 1            | 2024           | 0 = Reduced, 1 = Standard, 2 = Simulation
 * - m_collisions                 | uint8     | 1            | 2024           | 0 = Off, 1 = Player-to-Player Off, 2 = On
 * - m_collisionsOffForFirstLapOnly | uint8   | 1            | 2024           | 0 = Disabled, 1 = Enabled
 * - m_mpUnsafePitRelease         | uint8     | 1            | 2024           | 0 = On, 1 = Off (Multiplayer)
 * - m_mpOffForGriefing           | uint8     | 1            | 2024           | 0 = Disabled, 1 = Enabled (Multiplayer)
 * - m_cornerCuttingStringency    | uint8     | 1            | 2024           | 0 = Regular, 1 = Strict
 * - m_parcFermeRules             | uint8     | 1            | 2024           | 0 = Off, 1 = On
 * - m_pitStopExperience          | uint8     | 1            | 2024           | 0 = Automatic, 1 = Broadcast, 2 = Immersive
 * - m_safetyCar                  | uint8     | 1            | 2024           | 0 = Off, 1 = Reduced, 2 = Standard, 3 = Increased
 * - m_safetyCarExperience        | uint8     | 1            | 2024           | 0 = Broadcast, 1 = Immersive
 * - m_formationLap               | uint8     | 1            | 2024           | 0 = Off, 1 = On
 * - m_formationLapExperience     | uint8     | 1            | 2024           | 0 = Broadcast, 1 = Immersive
 * - m_redFlags                   | uint8     | 1            | 2024           | 0 = Off, 1 = Reduced, 2 = Standard, 3 = Increased
 * - m_affectsLicenceLevelSolo    | uint8     | 1            | 2024           | 0 = Off, 1 = On
 * - m_affectsLicenceLevelMP      | uint8     | 1            | 2024           | 0 = Off, 1 = On
 * - m_numSessionsInWeekend       | uint8     | 1            | 2024           | Number of session in following array
 * - m_weekendStructure[12]       | uint8     | 12           | 2024           | List of session types to show weekend structure - see appendix for types
 * - m_sector2LapDistanceStart    | float     | 4            | 2024           | Distance in m around track where sector 2 starts
 * - m_sector3LapDistanceStart    | float     | 4            | 2024           | Distance in m around track where sector 3 starts
 */

public record SessionData(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                          int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                          int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                          int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                          int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                          WeatherForecastSampleData[] weatherForecastSamples, int forecastAccuracy, int aiDifficulty,
                          long seasonLinkIdentifier, long weekendLinkIdentifier, long sessionLinkIdentifier,
                          int pitStopWindowIdealLap, int pitStopWindowLatestLap, int pitStopRejoinPosition,
                          AssistData assistData, int gameMode, int ruleSet, long timeOfDay, int sessionLength,
                          int speedUnitsLeadPlayer, int tempUnitsLeadPlayer, int speedUnitsSecondaryPlayer,
                          int tempUnitsSecondaryPlayer, int numSafetyCarPeriods, int numVirtualSafetyCarPeriods,
                          int numRedFlagPeriods, GameModeData gameModeData, float sector2LapDistanceStart,
                          float sector3LapDistanceStart) {

    private static final int WEATHER_FORECAST_20_SIZE = 20;
    private static final int WEATHER_FORECAST_21_TO_23_SIZE = 56;
    private static final int WEATHER_FORECAST_24_NEWER_SIZE = 64;

    public String buildSessionName() {
        String formula = FormulaEnum.fromValue(this.formula).name();
        String track = TrackEnum.fromId(this.trackId).name();
        String session = SessionTypeEnum.fromId(this.sessionType).name();
        return formula + " " + session + " at " + track;
    }

    //Used to build the MarshalZone objects
    private static MarshalZoneData[] buildMarshalZones(int packetFormat, ByteBuffer byteBuffer) {
        return MarshalZoneDataFactory.build(packetFormat, byteBuffer);
    }

    //Used to build the WeatherForecastSamples array for F1 2020. It was a much smaller array back then.
    private static WeatherForecastSampleData[] buildWeatherForecastSamples20(ByteBuffer byteBuffer) {
        WeatherForecastSampleData[] results = new WeatherForecastSampleData[WEATHER_FORECAST_20_SIZE];
        for (int i = 0; i < results.length; i++) {
            WeatherForecastSampleData.WeatherForecastSampleData20 w20 = new WeatherForecastSampleData.WeatherForecastSampleData20(byteBuffer);
            results[i] = new WeatherForecastSampleData(w20.sessionType(), w20.timeOffset(), w20.weather(), w20.trackTemperature(), 0, w20.airTemperature(), 0, 0);
        }
        return results;
    }

    //Used to build the WeatherForecastSamples array for F1 2021 and beyond. The object is the same, but the size changed in 2024
    private static WeatherForecastSampleData[] buildWeatherForecastSamples21(ByteBuffer byteBuffer, int size) {
        WeatherForecastSampleData[] results = new WeatherForecastSampleData[size];
        for (int i = 0; i < results.length; i++) {
            WeatherForecastSampleData.WeatherForecastSampleData21 w21 = new WeatherForecastSampleData.WeatherForecastSampleData21(byteBuffer);
            results[i] = new WeatherForecastSampleData(w21.sessionType(), w21.timeOffset(), w21.weather(), w21.trackTemperature(), w21.trackTemperatureChange(), w21.airTemperature(), w21.airTemperatureChange(), w21.rainPercentage());
        }
        return results;
    }

    public record SessionData19(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame) {
        public SessionData19(int packetFormat, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildMarshalZones(packetFormat, byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()));
        }
    }

    public record SessionData20(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                                WeatherForecastSampleData[] weatherForecastSamples) {
        public SessionData20(int packetFormat, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildMarshalZones(packetFormat, byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildWeatherForecastSamples20(byteBuffer));
        }
    }

    public record SessionData21(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                                WeatherForecastSampleData[] weatherForecastSamples, int forecastAccuracy,
                                int aiDifficulty, long seasonLinkIdentifier, long weekendLinkIdentifier,
                                long sessionLinkIdentifier, int pitStopWindowIdealLap, int pitStopWindowLatestLap,
                                int pitStopRejoinPosition, AssistData assistData) {
        public SessionData21(int packetFormat, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildMarshalZones(packetFormat, byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildWeatherForecastSamples21(byteBuffer, WEATHER_FORECAST_21_TO_23_SIZE), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), new AssistData(byteBuffer));
        }
    }

    public record SessionData22(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                                WeatherForecastSampleData[] weatherForecastSamples, int forecastAccuracy,
                                int aiDifficulty, long seasonLinkIdentifier, long weekendLinkIdentifier,
                                long sessionLinkIdentifier, int pitStopWindowIdealLap, int pitStopWindowLatestLap,
                                int pitStopRejoinPosition, AssistData assistData, int gameMode, int ruleSet,
                                long timeOfDay, int sessionLength) {
        public SessionData22(int packetFormat, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildMarshalZones(packetFormat, byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildWeatherForecastSamples21(byteBuffer, WEATHER_FORECAST_21_TO_23_SIZE), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), new AssistData(byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask8(byteBuffer.get()));
        }
    }

    public record SessionData23(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                                WeatherForecastSampleData[] weatherForecastSamples, int forecastAccuracy,
                                int aiDifficulty, long seasonLinkIdentifier, long weekendLinkIdentifier,
                                long sessionLinkIdentifier, int pitStopWindowIdealLap, int pitStopWindowLatestLap,
                                int pitStopRejoinPosition, AssistData assistData, int gameMode, int ruleSet,
                                long timeOfDay, int sessionLength, int speedUnitsLeadPlayer, int tempUnitsLeadPlayer,
                                int speedUnitsSecondaryPlayer, int tempUnitsSecondaryPlayer, int numSafetyCarPeriods,
                                int numVirtualSafetyCarPeriods, int numRedFlagPeriods) {
        public SessionData23(int packetFormat, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildMarshalZones(packetFormat, byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildWeatherForecastSamples21(byteBuffer, WEATHER_FORECAST_21_TO_23_SIZE), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), new AssistData(byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()));
        }
    }

    public record SessionData24(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                                WeatherForecastSampleData[] weatherForecastSamples, int forecastAccuracy,
                                int aiDifficulty, long seasonLinkIdentifier, long weekendLinkIdentifier,
                                long sessionLinkIdentifier, int pitStopWindowIdealLap, int pitStopWindowLatestLap,
                                int pitStopRejoinPosition, AssistData assistData, int gameMode, int ruleSet,
                                long timeOfDay, int sessionLength, int speedUnitsLeadPlayer, int tempUnitsLeadPlayer,
                                int speedUnitsSecondaryPlayer, int tempUnitsSecondaryPlayer, int numSafetyCarPeriods,
                                int numVirtualSafetyCarPeriods, int numRedFlagPeriods, GameModeData gameModeData,
                                float sector2LapDistanceStart, float sector3LapDistanceStart) {
        public SessionData24(int packetFormat, ByteBuffer byteBuffer) {
            this(BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), byteBuffer.get(), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask16(byteBuffer.getShort()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildMarshalZones(packetFormat, byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), buildWeatherForecastSamples21(byteBuffer, WEATHER_FORECAST_24_NEWER_SIZE), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), new AssistData(byteBuffer), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask32(byteBuffer.getInt()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), BitMaskUtils.bitMask8(byteBuffer.get()), new GameModeData(byteBuffer), byteBuffer.getFloat(), byteBuffer.getFloat());
        }
    }
}

package f1.data.packets.session;

/**
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
 * - m_weather                    | uint8     | 1            | 2020           | Weather - 0 = clear, 1 = light cloud, 2 = overcast, 3 = light rain, 4 = heavy rain, 5 = storm
 * - m_trackTemperature           | int8      | 1            | 2020           | Track temp. in degrees celsius
 * - m_airTemperature             | int8      | 1            | 2020           | Air temp. in degrees celsius
 * - m_totalLaps                  | uint8     | 1            | 2020           | Total number of laps in this race
 * - m_trackLength                | uint16    | 2            | 2020           | Track length in metres
 * - m_sessionType                | uint8     | 1            | 2020           | 0 = unknown, see appendix
 * - m_trackId                    | int8      | 1            | 2020           | -1 for unknown, see appendix
 * - m_formula                    | uint8     | 1            | 2020           | Formula, 0 = F1 Modern, 1 = F1 Classic, 2 = F2, 3 = F1 Generic, 4 = Beta, 6 = Esports, 8 = F1 World, 9 = F1 Elimination
 * - m_sessionTimeLeft            | uint16    | 2            | 2020           | Time left in session in seconds
 * - m_sessionDuration            | uint16    | 2            | 2020           | Session duration in seconds
 * - m_pitSpeedLimit              | uint8     | 1            | 2020           | Pit speed limit in kilometres per hour
 * - m_gamePaused                 | uint8     | 1            | 2020           | Whether the game is paused – network game only
 * - m_isSpectating               | uint8     | 1            | 2020           | Whether the player is spectating
 * - m_spectatorCarIndex          | uint8     | 1            | 2020           | Index of the car being spectated
 * - m_sliProNativeSupport        | uint8     | 1            | 2020           | SLI Pro support, 0 = inactive, 1 = active
 * - m_numMarshalZones            | uint8     | 1            | 2020           | Number of marshal zones to follow
 * - m_marshalZones[21]           | struct    | 105          | 2020           | List of marshal zones – max 21
 * - m_safetyCarStatus            | uint8     | 1            | 2020           | 0 = no safety car, 1 = full, 2 = virtual, 3 = formation lap
 * - m_networkGame                | uint8     | 1            | 2020           | 0 = offline, 1 = online
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

    public record SessionData20(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                                WeatherForecastSampleData[] weatherForecastSamples) {

    }

    public record SessionData21(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                                WeatherForecastSampleData[] weatherForecastSamples, int forecastAccuracy, int aiDifficulty,
                                long seasonLinkIdentifier, long weekendLinkIdentifier, long sessionLinkIdentifier,
                                int pitStopWindowIdealLap, int pitStopWindowLatestLap, int pitStopRejoinPosition,
                                AssistData assistData) {

    }

    public record SessionData22(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
                                int sessionType, int trackId, int formula, int sessionTimeLeft, int sessionDuration,
                                int pitSpeedLimit, int gamePaused, int isSpectating, int spectatorCarIndex,
                                int sliProNativeSupport, int numMarshalZones, MarshalZoneData[] marshalZones,
                                int safetyCarStatus, int networkGame, int numWeatherForecastSamples,
                                WeatherForecastSampleData[] weatherForecastSamples, int forecastAccuracy, int aiDifficulty,
                                long seasonLinkIdentifier, long weekendLinkIdentifier, long sessionLinkIdentifier,
                                int pitStopWindowIdealLap, int pitStopWindowLatestLap, int pitStopRejoinPosition,
                                AssistData assistData, int gameMode, int ruleSet, long timeOfDay, int sessionLength) {

    }

    public record SessionData23(int weather, int trackTemperature, int airTemperature, int totalLaps, int trackLength,
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
                                int numRedFlagPeriods) {

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

    }
}

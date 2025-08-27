package f1.data.parse.packets.session;

import f1.data.utils.constants.Constants;

import java.nio.ByteBuffer;

public class SessionDataFactory {

    public static SessionData build(int packetFormat, ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case Constants.YEAR_2020:
                SessionData.SessionData20 s20 = new SessionData.SessionData20(byteBuffer);
                yield new SessionData(s20.weather(), s20.trackTemperature(), s20.airTemperature(), s20.totalLaps(), s20.trackLength(), s20.sessionType(), s20.trackId(), s20.formula(), s20.sessionTimeLeft(), s20.sessionDuration(), s20.pitSpeedLimit(), s20.gamePaused(), s20.isSpectating(), s20.spectatorCarIndex(), s20.sliProNativeSupport(), s20.numMarshalZones(), s20.marshalZones(), s20.safetyCarStatus(), s20.networkGame(), s20.numWeatherForecastSamples(), s20.weatherForecastSamples(), 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0);
            case Constants.YEAR_2021:
                SessionData.SessionData21 s21 = new SessionData.SessionData21(byteBuffer);
                yield new SessionData(s21.weather(), s21.trackTemperature(), s21.airTemperature(), s21.totalLaps(), s21.trackLength(), s21.sessionType(), s21.trackId(), s21.formula(), s21.sessionTimeLeft(), s21.sessionDuration(), s21.pitSpeedLimit(), s21.gamePaused(), s21.isSpectating(), s21.spectatorCarIndex(), s21.sliProNativeSupport(), s21.numMarshalZones(), s21.marshalZones(), s21.safetyCarStatus(), s21.networkGame(), s21.numWeatherForecastSamples(), s21.weatherForecastSamples(), s21.forecastAccuracy(), s21.aiDifficulty(), s21.seasonLinkIdentifier(), s21.weekendLinkIdentifier(), s21.sessionLinkIdentifier(), s21.pitStopWindowIdealLap(), s21.pitStopWindowLatestLap(), s21.pitStopRejoinPosition(), s21.assistData(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0);
            case Constants.YEAR_2022:
                SessionData.SessionData22 s22 = new SessionData.SessionData22(byteBuffer);
                yield new SessionData(s22.weather(), s22.trackTemperature(), s22.airTemperature(), s22.totalLaps(), s22.trackLength(), s22.sessionType(), s22.trackId(), s22.formula(), s22.sessionTimeLeft(), s22.sessionDuration(), s22.pitSpeedLimit(), s22.gamePaused(), s22.isSpectating(), s22.spectatorCarIndex(), s22.sliProNativeSupport(), s22.numMarshalZones(), s22.marshalZones(), s22.safetyCarStatus(), s22.networkGame(), s22.numWeatherForecastSamples(), s22.weatherForecastSamples(), s22.forecastAccuracy(), s22.aiDifficulty(), s22.seasonLinkIdentifier(), s22.weekendLinkIdentifier(), s22.sessionLinkIdentifier(), s22.pitStopWindowIdealLap(), s22.pitStopWindowLatestLap(), s22.pitStopRejoinPosition(), s22.assistData(), s22.gameMode(), s22.ruleSet(), s22.timeOfDay(), s22.sessionLength(), 0, 0, 0, 0, 0, 0, 0, null, 0, 0);
            case Constants.YEAR_2023:
                SessionData.SessionData23 s23 = new SessionData.SessionData23(byteBuffer);
                yield new SessionData(s23.weather(), s23.trackTemperature(), s23.airTemperature(), s23.totalLaps(), s23.trackLength(), s23.sessionType(), s23.trackId(), s23.formula(), s23.sessionTimeLeft(), s23.sessionDuration(), s23.pitSpeedLimit(), s23.gamePaused(), s23.isSpectating(), s23.spectatorCarIndex(), s23.sliProNativeSupport(), s23.numMarshalZones(), s23.marshalZones(), s23.safetyCarStatus(), s23.networkGame(), s23.numWeatherForecastSamples(), s23.weatherForecastSamples(), s23.forecastAccuracy(), s23.aiDifficulty(), s23.seasonLinkIdentifier(), s23.weekendLinkIdentifier(), s23.sessionLinkIdentifier(), s23.pitStopWindowIdealLap(), s23.pitStopWindowLatestLap(), s23.pitStopRejoinPosition(), s23.assistData(), s23.gameMode(), s23.ruleSet(), s23.timeOfDay(), s23.sessionLength(), s23.speedUnitsLeadPlayer(), s23.tempUnitsLeadPlayer(), s23.speedUnitsSecondaryPlayer(), s23.tempUnitsSecondaryPlayer(), s23.numSafetyCarPeriods(), s23.numVirtualSafetyCarPeriods(), s23.numRedFlagPeriods(), null, 0, 0);
            case Constants.YEAR_2024, Constants.YEAR_2025:
                SessionData.SessionData24 s24 = new SessionData.SessionData24(byteBuffer);
                yield new SessionData(s24.weather(), s24.trackTemperature(), s24.airTemperature(), s24.totalLaps(), s24.trackLength(), s24.sessionType(), s24.trackId(), s24.formula(), s24.sessionTimeLeft(), s24.sessionDuration(), s24.pitSpeedLimit(), s24.gamePaused(), s24.isSpectating(), s24.spectatorCarIndex(), s24.sliProNativeSupport(), s24.numMarshalZones(), s24.marshalZones(), s24.safetyCarStatus(), s24.networkGame(), s24.numWeatherForecastSamples(), s24.weatherForecastSamples(), s24.forecastAccuracy(), s24.aiDifficulty(), s24.seasonLinkIdentifier(), s24.weekendLinkIdentifier(), s24.sessionLinkIdentifier(), s24.pitStopWindowIdealLap(), s24.pitStopWindowLatestLap(), s24.pitStopRejoinPosition(), s24.assistData(), s24.gameMode(), s24.ruleSet(), s24.timeOfDay(), s24.sessionLength(), s24.speedUnitsLeadPlayer(), s24.tempUnitsLeadPlayer(), s24.speedUnitsSecondaryPlayer(), s24.tempUnitsSecondaryPlayer(), s24.numSafetyCarPeriods(), s24.numVirtualSafetyCarPeriods(), s24.numRedFlagPeriods(), s24.gameModeData(), s24.sector2LapDistanceStart(), s24.sector3LapDistanceStart());
            default:
                throw new IllegalStateException("Games Packet Format did not match an accepted format (2020 - 2025)");
        };
    }
}

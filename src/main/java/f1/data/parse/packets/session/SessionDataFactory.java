package f1.data.parse.packets.session;

import f1.data.enums.SupportedYearsEnum;
import f1.data.parse.packets.DataFactory;

import java.nio.ByteBuffer;

public class SessionDataFactory implements DataFactory<SessionData> {

    private final SupportedYearsEnum packetFormat;

    public SessionDataFactory(int packetFormat) {
        this.packetFormat = SupportedYearsEnum.fromYear(packetFormat);
    }

    public SessionData build(ByteBuffer byteBuffer) {
        return switch (packetFormat) {
            case F1_2019 -> buildData(new SessionData.SessionData19(this.packetFormat.getYear(), byteBuffer));
            case F1_2020 -> buildData(new SessionData.SessionData20(this.packetFormat.getYear(), byteBuffer));
            case F1_2021 -> buildData(new SessionData.SessionData21(this.packetFormat.getYear(), byteBuffer));
            case F1_2022 -> buildData(new SessionData.SessionData22(this.packetFormat.getYear(), byteBuffer));
            case F1_2023 -> buildData(new SessionData.SessionData23(this.packetFormat.getYear(), byteBuffer));
            case F1_2024, F1_2025 -> buildData(new SessionData.SessionData24(this.packetFormat.getYear(), byteBuffer));
        };
    }

    private SessionData buildData(SessionData.SessionData19 s19) {
        return new SessionData(s19.weather(), s19.trackTemperature(), s19.airTemperature(), s19.totalLaps(), s19.trackLength(), s19.sessionType(), s19.trackId(), s19.formula(), s19.sessionTimeLeft(), s19.sessionDuration(), s19.pitSpeedLimit(), s19.gamePaused(), s19.isSpectating(), s19.spectatorCarIndex(), s19.sliProNativeSupport(), s19.numMarshalZones(), s19.marshalZones(), s19.safetyCarStatus(), s19.networkGame(), 0, null, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0);
    }

    private SessionData buildData(SessionData.SessionData20 s20) {
        return new SessionData(s20.weather(), s20.trackTemperature(), s20.airTemperature(), s20.totalLaps(), s20.trackLength(), s20.sessionType(), s20.trackId(), s20.formula(), s20.sessionTimeLeft(), s20.sessionDuration(), s20.pitSpeedLimit(), s20.gamePaused(), s20.isSpectating(), s20.spectatorCarIndex(), s20.sliProNativeSupport(), s20.numMarshalZones(), s20.marshalZones(), s20.safetyCarStatus(), s20.networkGame(), s20.numWeatherForecastSamples(), s20.weatherForecastSamples(), 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0);
    }

    private SessionData buildData(SessionData.SessionData21 s21) {
        return new SessionData(s21.weather(), s21.trackTemperature(), s21.airTemperature(), s21.totalLaps(), s21.trackLength(), s21.sessionType(), s21.trackId(), s21.formula(), s21.sessionTimeLeft(), s21.sessionDuration(), s21.pitSpeedLimit(), s21.gamePaused(), s21.isSpectating(), s21.spectatorCarIndex(), s21.sliProNativeSupport(), s21.numMarshalZones(), s21.marshalZones(), s21.safetyCarStatus(), s21.networkGame(), s21.numWeatherForecastSamples(), s21.weatherForecastSamples(), s21.forecastAccuracy(), s21.aiDifficulty(), s21.seasonLinkIdentifier(), s21.weekendLinkIdentifier(), s21.sessionLinkIdentifier(), s21.pitStopWindowIdealLap(), s21.pitStopWindowLatestLap(), s21.pitStopRejoinPosition(), s21.assistData(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0);
    }

    private SessionData buildData(SessionData.SessionData22 s22) {
        return new SessionData(s22.weather(), s22.trackTemperature(), s22.airTemperature(), s22.totalLaps(), s22.trackLength(), s22.sessionType(), s22.trackId(), s22.formula(), s22.sessionTimeLeft(), s22.sessionDuration(), s22.pitSpeedLimit(), s22.gamePaused(), s22.isSpectating(), s22.spectatorCarIndex(), s22.sliProNativeSupport(), s22.numMarshalZones(), s22.marshalZones(), s22.safetyCarStatus(), s22.networkGame(), s22.numWeatherForecastSamples(), s22.weatherForecastSamples(), s22.forecastAccuracy(), s22.aiDifficulty(), s22.seasonLinkIdentifier(), s22.weekendLinkIdentifier(), s22.sessionLinkIdentifier(), s22.pitStopWindowIdealLap(), s22.pitStopWindowLatestLap(), s22.pitStopRejoinPosition(), s22.assistData(), s22.gameMode(), s22.ruleSet(), s22.timeOfDay(), s22.sessionLength(), 0, 0, 0, 0, 0, 0, 0, null, 0, 0);
    }

    private SessionData buildData(SessionData.SessionData23 s23) {
        return new SessionData(s23.weather(), s23.trackTemperature(), s23.airTemperature(), s23.totalLaps(), s23.trackLength(), s23.sessionType(), s23.trackId(), s23.formula(), s23.sessionTimeLeft(), s23.sessionDuration(), s23.pitSpeedLimit(), s23.gamePaused(), s23.isSpectating(), s23.spectatorCarIndex(), s23.sliProNativeSupport(), s23.numMarshalZones(), s23.marshalZones(), s23.safetyCarStatus(), s23.networkGame(), s23.numWeatherForecastSamples(), s23.weatherForecastSamples(), s23.forecastAccuracy(), s23.aiDifficulty(), s23.seasonLinkIdentifier(), s23.weekendLinkIdentifier(), s23.sessionLinkIdentifier(), s23.pitStopWindowIdealLap(), s23.pitStopWindowLatestLap(), s23.pitStopRejoinPosition(), s23.assistData(), s23.gameMode(), s23.ruleSet(), s23.timeOfDay(), s23.sessionLength(), s23.speedUnitsLeadPlayer(), s23.tempUnitsLeadPlayer(), s23.speedUnitsSecondaryPlayer(), s23.tempUnitsSecondaryPlayer(), s23.numSafetyCarPeriods(), s23.numVirtualSafetyCarPeriods(), s23.numRedFlagPeriods(), null, 0, 0);
    }

    private SessionData buildData(SessionData.SessionData24 s24) {
        return new SessionData(s24.weather(), s24.trackTemperature(), s24.airTemperature(), s24.totalLaps(), s24.trackLength(), s24.sessionType(), s24.trackId(), s24.formula(), s24.sessionTimeLeft(), s24.sessionDuration(), s24.pitSpeedLimit(), s24.gamePaused(), s24.isSpectating(), s24.spectatorCarIndex(), s24.sliProNativeSupport(), s24.numMarshalZones(), s24.marshalZones(), s24.safetyCarStatus(), s24.networkGame(), s24.numWeatherForecastSamples(), s24.weatherForecastSamples(), s24.forecastAccuracy(), s24.aiDifficulty(), s24.seasonLinkIdentifier(), s24.weekendLinkIdentifier(), s24.sessionLinkIdentifier(), s24.pitStopWindowIdealLap(), s24.pitStopWindowLatestLap(), s24.pitStopRejoinPosition(), s24.assistData(), s24.gameMode(), s24.ruleSet(), s24.timeOfDay(), s24.sessionLength(), s24.speedUnitsLeadPlayer(), s24.tempUnitsLeadPlayer(), s24.speedUnitsSecondaryPlayer(), s24.tempUnitsSecondaryPlayer(), s24.numSafetyCarPeriods(), s24.numVirtualSafetyCarPeriods(), s24.numRedFlagPeriods(), s24.gameModeData(), s24.sector2LapDistanceStart(), s24.sector3LapDistanceStart());
    }
}

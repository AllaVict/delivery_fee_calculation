package com.deliveryfeecalculation.config;

import com.deliveryfeecalculation.domain.enums.WeatherPhenomenon;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import java.time.LocalDateTime;
import static com.deliveryfeecalculation.domain.enums.City.TALLINN;
import static com.deliveryfeecalculation.domain.enums.WeatherPhenomenon.HAIL;
import static com.deliveryfeecalculation.domain.enums.WeatherPhenomenon.RAIN;

public class WeatherConditionFactory {
    private static final long WEATHER_CONDITION_ID = 101L;

    private static final Integer  AIR_TEMPERATURE= 10;
    private static final Integer  WIND_SPEED= 10;

    private static final LocalDateTime OBSERVATION_TIME = LocalDateTime.of(2024, 3, 23, 20, 24);

    private WeatherConditionFactory() {
    }

    public static WeatherCondition createWeatherConditionWithTime(LocalDateTime observationTime) {
        final WeatherCondition createdWeatherCondition = new WeatherCondition();
        createdWeatherCondition.setId(WEATHER_CONDITION_ID);
        createdWeatherCondition.setStationName(TALLINN);
        createdWeatherCondition.setAirTemperature(AIR_TEMPERATURE);
        createdWeatherCondition.setWindSpeed(WIND_SPEED);
        createdWeatherCondition.setObservationTime(observationTime);
        return createdWeatherCondition;
    }

    public static WeatherCondition createWeatherCondition() {
        final WeatherCondition createdWeatherCondition = new WeatherCondition();
        createdWeatherCondition.setId(WEATHER_CONDITION_ID);
        createdWeatherCondition.setStationName(TALLINN);
        createdWeatherCondition.setAirTemperature(AIR_TEMPERATURE);
        createdWeatherCondition.setWindSpeed(WIND_SPEED);
        createdWeatherCondition.setWeatherPhenomenon(RAIN);
        createdWeatherCondition.setObservationTime(OBSERVATION_TIME);
        return createdWeatherCondition;
    }
    public static WeatherCondition createWeatherConditionWithHail() {
        final WeatherCondition createdWeatherCondition = new WeatherCondition();
        createdWeatherCondition.setId(WEATHER_CONDITION_ID);
        createdWeatherCondition.setStationName(TALLINN);
        createdWeatherCondition.setAirTemperature(AIR_TEMPERATURE);
        createdWeatherCondition.setWindSpeed(WIND_SPEED);
        createdWeatherCondition.setWeatherPhenomenon(HAIL);
        createdWeatherCondition.setObservationTime(OBSERVATION_TIME);
        return createdWeatherCondition;
    }

    public static WeatherCondition createWeatherConditionWithParameters(final Integer airTemperature,
                                                                        final Integer windSpeed,
                                                                        final WeatherPhenomenon weatherPhenomenon) {
        final WeatherCondition createdWeatherCondition = new WeatherCondition();
        createdWeatherCondition.setId(WEATHER_CONDITION_ID);
        createdWeatherCondition.setStationName(TALLINN);
        createdWeatherCondition.setAirTemperature(airTemperature);
        createdWeatherCondition.setWindSpeed(windSpeed);
        createdWeatherCondition.setWeatherPhenomenon(weatherPhenomenon);
        createdWeatherCondition.setObservationTime(OBSERVATION_TIME);
        return createdWeatherCondition;
    }

}

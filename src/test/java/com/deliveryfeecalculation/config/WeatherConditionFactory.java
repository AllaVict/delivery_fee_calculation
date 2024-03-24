package com.deliveryfeecalculation.config;

import com.deliveryfeecalculation.domain.model.WeatherCondition;
import java.time.LocalDateTime;
import static com.deliveryfeecalculation.domain.enums.City.TALLINN;

public class WeatherConditionFactory {
    private static final long WEATHER_CONDITION_ID = 101L;

    private static final Integer  AIR_TEMPERATURE= 10;
    private static final Integer  WIND_SPEED= 10;

    private static final LocalDateTime OBSERVATION_TIME = LocalDateTime.of(2024, 3, 23, 20, 24);


    private WeatherConditionFactory() {
    }
    public static WeatherCondition createWeatherCondition() {
        final WeatherCondition createdWeatherCondition = new WeatherCondition();
        createdWeatherCondition.setId(WEATHER_CONDITION_ID);
        createdWeatherCondition.setStationName(TALLINN);
        createdWeatherCondition.setAirTemperature(AIR_TEMPERATURE);
        createdWeatherCondition.setWindSpeed(WIND_SPEED);
        createdWeatherCondition.setObservationTime(OBSERVATION_TIME);
        return createdWeatherCondition;
    }

}

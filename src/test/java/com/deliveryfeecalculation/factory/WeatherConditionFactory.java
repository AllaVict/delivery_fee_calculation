package com.deliveryfeecalculation.factory;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.model.WeatherCondition;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.deliveryfeecalculation.domain.enums.City.TALLINN;

public class WeatherConditionFactory {
    private static final long WEATHER_CONDITION_ID = 101L;

    private static final Double AIR_TEMPERATURE = 10.00;
    private static final Double WIND_SPEED = 10.00;

    private static final LocalDateTime OBSERVATION_TIME = LocalDateTime.of(2024, 3, 23, 20, 24);

    private WeatherConditionFactory() {
    }

    public static List<WeatherCondition> createWeatherConditionList() {
        return Arrays.asList(
                createWeatherConditionWithParameters(10.0, 4.0, "SUNNY", City.TALLINN),
                createWeatherConditionWithParameters(10.0, 4.0, "SUNNY", City.TARTU),
                createWeatherConditionWithParameters(10.0, 4.0, "SUNNY", City.TARTU));
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
        createdWeatherCondition.setWeatherPhenomenon("RAIN");
        createdWeatherCondition.setObservationTime(OBSERVATION_TIME);
        return createdWeatherCondition;
    }

    public static WeatherCondition createWeatherConditionWithHail() {
        final WeatherCondition createdWeatherCondition = new WeatherCondition();
        createdWeatherCondition.setId(WEATHER_CONDITION_ID);
        createdWeatherCondition.setStationName(TALLINN);
        createdWeatherCondition.setAirTemperature(AIR_TEMPERATURE);
        createdWeatherCondition.setWindSpeed(WIND_SPEED);
        createdWeatherCondition.setWeatherPhenomenon("HAIL");
        createdWeatherCondition.setObservationTime(OBSERVATION_TIME);
        return createdWeatherCondition;
    }

    public static WeatherCondition createWeatherConditionWithParameters(final Double airTemperature,
                                                                        final Double windSpeed,
                                                                        final String weatherPhenomenon,
                                                                        final City city) {
        final WeatherCondition createdWeatherCondition = new WeatherCondition();
        createdWeatherCondition.setId(WEATHER_CONDITION_ID);
        createdWeatherCondition.setStationName(city);
        createdWeatherCondition.setAirTemperature(airTemperature);
        createdWeatherCondition.setWindSpeed(windSpeed);
        createdWeatherCondition.setWeatherPhenomenon(weatherPhenomenon);
        createdWeatherCondition.setObservationTime(OBSERVATION_TIME);
        return createdWeatherCondition;
    }


}

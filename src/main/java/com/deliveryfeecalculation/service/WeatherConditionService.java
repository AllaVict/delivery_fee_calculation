package com.deliveryfeecalculation.service;

import com.deliveryfeecalculation.domain.model.Station;
import com.deliveryfeecalculation.domain.model.WeatherCondition;

import java.util.List;

public interface WeatherConditionService {
    List<WeatherCondition> saveWeatherCondition(List<Station> stationList);

}


package com.deliveryfeecalculation.service;

import com.deliveryfeecalculation.domain.model.Station;

import java.util.List;

public interface WeatherDataImportingService {

    List<Station> importWeatherDataFromIlmateenistus();

}

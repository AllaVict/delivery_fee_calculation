package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.model.Station;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.service.WeatherConditionService;
import com.deliveryfeecalculation.service.WeatherDataImportingService;
import com.deliveryfeecalculation.service.WeatherDataProcessScheduledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Scheduled service for regularly importing and saving weather data.
 *
 * @author Alla Borodina
 */
@Service
@EnableScheduling
public class WeatherDataProcessScheduledServiceImpl implements WeatherDataProcessScheduledService {
    private final WeatherConditionService weatherConditionService;
    private final WeatherDataImportingService weatherDataImportingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataProcessScheduledServiceImpl.class);

    public WeatherDataProcessScheduledServiceImpl(final WeatherConditionService weatherConditionService,
                                                  final WeatherDataImportingService weatherDataImportingService) {
        this.weatherConditionService = weatherConditionService;
        this.weatherDataImportingService = weatherDataImportingService;
    }


    /**
     * Scheduled task that runs periodically to import weather data from an external source and save it.
     * This method is invoked automatically according to the configured fixed rate.
     */
    @Scheduled(fixedRate = 3600000)
    public void saveImportingWeatherDataScheduled() {

        final List<Station> stationList = weatherDataImportingService.importWeatherDataFromIlmateenistus();
        final List<WeatherCondition> weatherConditionList = weatherConditionService.saveWeatherCondition(stationList);

        LOGGER.debug("In WeatherDataProcessScheduledServiceImpl save importing weather data for cities : {} ", weatherConditionList.stream().map(s -> s.getStationName()).toList());

    }

}

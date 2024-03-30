package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.model.Station;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.exception.ServiceException;
import com.deliveryfeecalculation.repository.WeatherConditionRepository;
import com.deliveryfeecalculation.service.WeatherConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.deliveryfeecalculation.constants.Constants.StationNames.*;

/**
 * Service for managing weather conditions.
 *
 * @author Alla Borodina
 */
@Service
public class WeatherConditionServiceImpl implements WeatherConditionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherConditionServiceImpl.class);

    private final WeatherConditionRepository weatherConditionRepository;

    /**
     * Constructs a new WeatherConditionServiceImpl with specified repository.
     *
     * @param weatherConditionRepository repository for accessing weather condition data.
     */
    public WeatherConditionServiceImpl(final WeatherConditionRepository weatherConditionRepository) {
        this.weatherConditionRepository = weatherConditionRepository;
    }

    /**
     * Saves a list of weather conditions derived from a list of stations.
     * Each station's data is converted into a weather condition before being saved.
     *
     * @param stationList the list of stations to be converted and saved as weather conditions
     * @return a list of saved weather conditions
     * @throws ServiceException if an error occurs during the saving process
     */
    @Override
    public List<WeatherCondition> saveWeatherCondition(final List<Station> stationList) {
        try {
            List<WeatherCondition> weatherConditions =
                    stationList.stream()
                            .map(this::convertStationToWeatherCondition)
                            .collect(Collectors.toList());
            List<WeatherCondition> savedList = weatherConditionRepository.saveAll(weatherConditions);

            LOGGER.info("In WeatherDataFetchingService Weather conditions saved list : {}", savedList);

            return savedList;
        } catch (Exception e) {
            LOGGER.error("Error saving weather conditions: ", e);
            throw new ServiceException("Unable to save weather conditions", e);
        }
    }

    private WeatherCondition convertStationToWeatherCondition(final Station station) {
        WeatherCondition weatherCondition = new WeatherCondition();
        if (station.getName().equalsIgnoreCase(TALLINN)) weatherCondition.setStationName(City.TALLINN);
        if (station.getName().equalsIgnoreCase(TARTU)) weatherCondition.setStationName(City.TARTU);
        if (station.getName().equalsIgnoreCase(PARNU)) weatherCondition.setStationName(City.PÄRNU);

        weatherCondition.setAirTemperature(station.getAirtemperature());
        weatherCondition.setWindSpeed(station.getWindspeed());
        if (station.getPhenomenon().isEmpty()) {
            if (station.getSunshineduration() >= 300) {
                weatherCondition.setWeatherPhenomenon("CLEARLY");
            } else {
                weatherCondition.setWeatherPhenomenon("CLOUDY");
            }
        } else {
            weatherCondition.setWeatherPhenomenon(station.getPhenomenon());
        }
        weatherCondition.setObservationTime(LocalDateTime.now());

        return weatherCondition;
    }
}


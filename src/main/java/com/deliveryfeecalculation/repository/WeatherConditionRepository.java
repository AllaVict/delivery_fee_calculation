package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Spring Data repository for managing {@link WeatherCondition} entities.
 */
@Repository
public interface WeatherConditionRepository extends JpaRepository<WeatherCondition,Long> {

    /**
     * Finds all {@link WeatherCondition} entries for a given station name (city).
     * This method is typically used to retrieve weather conditions recorded at a specific location.
     *
     * @param stationName the name of the station (city) for which weather conditions are sought
     * @return a list of {@link WeatherCondition} objects matching the given station name
     */
    List<WeatherCondition> findAllByStationName(City stationName);

}

package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    /**
     * Finds the first {@link WeatherCondition} entry after retrieving all entries for a given city,
     * sorted according to the specified sort order. This method is useful for finding the most recent
     * or relevant weather condition entry for a city based on a specific sorting criteria.
     *
     * @param city the city for which the weather condition is sought
     * @param sort the {@link Sort} instance specifying the order in which to sort the results
     * @return an {@link Optional} containing the first {@link WeatherCondition} according to the sort order,
     * or an empty Optional if no entries are found
     */
    Optional<WeatherCondition> findFirstAfterFindAllByStationName(City city, Sort sort);

}

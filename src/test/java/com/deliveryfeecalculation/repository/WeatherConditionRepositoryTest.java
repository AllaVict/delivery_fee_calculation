package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.config.WeatherConditionFactory;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.Optional;

import static com.deliveryfeecalculation.domain.enums.City.TALLINN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WeatherConditionRepositoryTest {


    @Autowired
    private WeatherConditionRepository weatherConditionRepository;

    private static final long WEATHER_CONDITION_ID = 101L;
    private WeatherCondition weatherCondition;

    @BeforeEach
    public void setUp() {
        weatherCondition = WeatherConditionFactory.createWeatherCondition();
    }

    @Nested
    @DisplayName("When Find foundWeatherCondition by Id")
    class FindWeatherConditionByIdTests {

        @Test
        void testFindWeatherConditionById_shouldReturnExistingWeatherConditionWithGivenId() {
            WeatherCondition existingWeatherCondition = weatherConditionRepository.save(weatherCondition);
            Optional<WeatherCondition> foundWeatherCondition = weatherConditionRepository.findById(existingWeatherCondition.getId());
            assertEquals(existingWeatherCondition.getId(), foundWeatherCondition.get().getId());
            assertTrue(foundWeatherCondition.isPresent());

        }

        @Test
        void testFindWeatherConditionById_shouldReturnNoWeatherConditionWithGivenId() {
            Optional<WeatherCondition> foundBaseFee = weatherConditionRepository.findById(WEATHER_CONDITION_ID);

            assertFalse(foundBaseFee.isPresent());
            assertThat(foundBaseFee).isEmpty();
        }
    }

    @Nested
    @DisplayName("When Save WeatherCondition")
    class SaveWeatherConditionTests {

        @Test
        void testSaveWeatherCondition_shouldReturnWeatherConditionSuccessfully() {
            WeatherCondition savedWeatherCondition = weatherConditionRepository.save(weatherCondition);

            assertNotNull(savedWeatherCondition);
            assertNotNull(savedWeatherCondition);
            assertThat(savedWeatherCondition.getId()).isNotNull();
            assertThat(savedWeatherCondition.getWeatherPhenomenon()).isEqualTo(weatherCondition.getWeatherPhenomenon());
            assertEquals(weatherCondition.getWindSpeed(), savedWeatherCondition.getWindSpeed());
        }

        @Test
        void testSaveWeatherCondition_shouldThrowException() {
            assertThrows(InvalidDataAccessApiUsageException.class,
                    () -> weatherConditionRepository.save(null));
        }

    }

    @Nested
    @DisplayName("When Find All WeatherCondition")
    class FindALlWeatherConditionTests {

        @Test
        void testFindAllWeatherCondition_shouldReturnWeatherConditionListSuccessfully() {
            weatherConditionRepository.deleteAll();
            weatherConditionRepository.save(weatherCondition);
            List<WeatherCondition> weatherConditionList = weatherConditionRepository.findAll();

            assertEquals(weatherConditionList.size(), 1);
            assertFalse(weatherConditionList.isEmpty());
        }

        @Test
        void testFindAllWeatherCondition_shouldReturnEmptyWeatherConditionList() {
            weatherConditionRepository.deleteAll();
            List<WeatherCondition> weatherConditionList = weatherConditionRepository.findAll();

            assertEquals(weatherConditionList.size(), 0);
            assertTrue(weatherConditionList.isEmpty());
        }
    }

    @Nested
    @DisplayName("When find WeatherCondition By Station Name")
    class FindWeatherConditionByStationName {
        @Test
        void testFindUWeatherConditionByStationName_shouldReturnWeatherConditionSuccessfully() {
            weatherConditionRepository.deleteAll();
            weatherConditionRepository.save(weatherCondition);
            List<WeatherCondition> allByStationName = weatherConditionRepository.findAllByStationName(TALLINN);

            assertFalse(allByStationName.isEmpty());
            assertEquals(allByStationName.size(), 1);
        }

        @Test
        void testFindWeatherConditionByStationName_shouldReturnNoWeatherCondition() {
            weatherConditionRepository.deleteAll();
            List<WeatherCondition> allByStationName = weatherConditionRepository.findAllByStationName(TALLINN);

            assertTrue(allByStationName.isEmpty());
            assertThat(allByStationName).isEmpty();
        }

    }

    @Nested
    @DisplayName("When Delete WeatherCondition By Id")
    class DeleteWeatherConditionByIdTests {
        @Test
        void testDeleteWeatherConditionById_shouldDeleteWeatherConditionByIdSuccessfully() {
            WeatherCondition baseFeeForDelete = weatherConditionRepository.save(weatherCondition);

            weatherConditionRepository.deleteById(baseFeeForDelete.getId());
            Optional<WeatherCondition> deletedWeatherCondition = weatherConditionRepository.findById(baseFeeForDelete.getId());
            assertFalse(deletedWeatherCondition.isPresent());
            assertThat(deletedWeatherCondition).isEmpty();
        }
    }

}

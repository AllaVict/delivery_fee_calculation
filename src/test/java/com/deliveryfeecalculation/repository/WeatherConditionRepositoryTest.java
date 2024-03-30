package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.factory.WeatherConditionFactory;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
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

    private WeatherCondition weatherConditionOne;

    private WeatherCondition weatherConditionTwo;
    private static final long WEATHER_CONDITION_ID = 101L;


    private static final LocalDateTime OBSERVATION_TIME_2024 = LocalDateTime.of(2024, 3, 23, 20, 24);

    private static final LocalDateTime OBSERVATION_TIME_2020 = LocalDateTime.of(2020, 3, 23, 20, 24);

    @BeforeEach
    public void setUp() {
        weatherConditionOne = WeatherConditionFactory.createWeatherConditionWithTime(OBSERVATION_TIME_2024);
        weatherConditionTwo = WeatherConditionFactory.createWeatherConditionWithTime(OBSERVATION_TIME_2020);
    }

    @Nested
    @DisplayName("When Find foundWeatherCondition by Id")
    class FindWeatherConditionByIdTests {

        @Test
        void testFindWeatherConditionById_shouldReturnExistingWeatherConditionWithGivenId() {
            WeatherCondition existingWeatherCondition = weatherConditionRepository.save(weatherConditionOne);
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
            WeatherCondition savedWeatherCondition = weatherConditionRepository.save(weatherConditionOne);

            assertNotNull(savedWeatherCondition);
            assertNotNull(savedWeatherCondition);
            assertThat(savedWeatherCondition.getId()).isNotNull();
            assertThat(savedWeatherCondition.getWeatherPhenomenon()).isEqualTo(weatherConditionOne.getWeatherPhenomenon());
            assertEquals(weatherConditionOne.getWindSpeed(), savedWeatherCondition.getWindSpeed());
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
            weatherConditionRepository.save(weatherConditionOne);
            List<WeatherCondition> weatherConditionList = weatherConditionRepository.findAll();

            assertEquals( 1, weatherConditionList.size());
            assertFalse(weatherConditionList.isEmpty());
        }

        @Test
        void testFindAllWeatherCondition_shouldReturnEmptyWeatherConditionList() {
            weatherConditionRepository.deleteAll();
            List<WeatherCondition> weatherConditionList = weatherConditionRepository.findAll();

            assertEquals(0, weatherConditionList.size());
            assertTrue(weatherConditionList.isEmpty());
        }
    }

    @Nested
    @DisplayName("When Delete WeatherCondition By Id")
    class DeleteWeatherConditionByIdTests {
        @Test
        void testDeleteWeatherConditionById_shouldDeleteWeatherConditionByIdSuccessfully() {
            WeatherCondition baseFeeForDelete = weatherConditionRepository.save(weatherConditionOne);

            weatherConditionRepository.deleteById(baseFeeForDelete.getId());
            Optional<WeatherCondition> deletedWeatherCondition = weatherConditionRepository.findById(baseFeeForDelete.getId());
            assertFalse(deletedWeatherCondition.isPresent());
            assertThat(deletedWeatherCondition).isEmpty();
        }
    }

    @Nested
    @DisplayName("When find WeatherCondition By Station Name")
    class FindWeatherConditionByStationName {
        @Test
        void testFindUWeatherConditionByStationName_shouldReturnWeatherConditionSuccessfully() {
            weatherConditionRepository.deleteAll();
            weatherConditionRepository.save(weatherConditionOne);
            List<WeatherCondition> allByStationName = weatherConditionRepository.findAllByStationName(TALLINN);

            assertFalse(allByStationName.isEmpty());
            assertEquals(1, allByStationName.size());
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
    @DisplayName("When find WeatherCondition By Station Name")
    class FindFirstAfterFindAllByStationName {
        @Test
        void testFindFirstAfterFindAllByStationName_shouldReturnWeatherConditionSuccessfully() {
            weatherConditionRepository.deleteAll();
            weatherConditionRepository.save(weatherConditionOne);
            weatherConditionRepository.save(weatherConditionTwo);
            Optional<WeatherCondition> allByStationName = weatherConditionRepository
                    .findFirstAfterFindAllByStationName(TALLINN, Sort.by(Sort.Direction.DESC, "observationTime"));

            assertFalse(allByStationName.isEmpty());
            assertEquals(TALLINN, allByStationName.get().getStationName());
        }

        @Test
        void testFindFirstAfterFindAllByStationName_shouldReturnNoWeatherCondition() {
            weatherConditionRepository.deleteAll();
            Optional<WeatherCondition> allByStationName = weatherConditionRepository
                    .findFirstAfterFindAllByStationName(TALLINN, Sort.by(Sort.Direction.DESC, "observationTime"));

            assertTrue(allByStationName.isEmpty());
            assertThat(allByStationName).isEmpty();
        }

    }
}

package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.ExtraFee;
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

import static com.deliveryfeecalculation.factory.ExtraFeeFactory.*;
import static com.deliveryfeecalculation.domain.enums.VehicleType.BIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExtraFeeRepositoryTest {

    @Autowired
    private ExtraFeeRepository extraFeeRepository;

    private static final long EXTRA_FEE_ID = 101L;

    private List<ExtraFee> extraFeeList;

    private ExtraFee extraFee;

    @BeforeEach
    public void setUp() {
        extraFeeList = createExtraFeeList();
        extraFee = createExtraFeeWithData();
    }

    @Nested
    @DisplayName("When Find ExtraFee By City, VehicleType, Status")
    class FindAllByNameAndVehicleTypeAndStatusTests {
        @Test
        void testFindAllByNameAndVehicleTypeAndStatus_shouldReturnExtraFeeSuccessfully() {
            List<ExtraFee> foundExtraFee = extraFeeRepository
                    .findAllByNameAndVehicleTypeAndStatus("air temperature", BIKE, Status.CURRENT);

            assertNotNull(foundExtraFee);
            assertEquals("air temperature", foundExtraFee.get(0).getName());
            assertEquals(BIKE, foundExtraFee.get(0).getVehicleType());
            assertEquals(Status.CURRENT, foundExtraFee.get(0).getStatus());
        }

        @Test
        void testFindAllByNameAndVehicleTypeAndStatus_shouldReturnNoExtraFee() {
            extraFeeRepository.deleteAll();
            List<ExtraFee> foundExtraFee = extraFeeRepository
                    .findAllByNameAndVehicleTypeAndStatus("air temperature", BIKE, Status.CURRENT);

            assertTrue(foundExtraFee.isEmpty());
            assertEquals(0, foundExtraFee.size());
        }

    }

    @Nested
    @DisplayName("When Find ExtraFee By VehicleType,WeatherPhenomenon, Status")
    class FindByVehicleTypeAndAndWeatherPhenomenonAndStatusTests {
        @Test
        void testFindByVehicleTypeAndAndWeatherPhenomenonAndStatus_shouldReturnExtraFeeListSuccessfully() {
            Optional<ExtraFee> foundExtraFee = extraFeeRepository
                    .findByVehicleTypeAndWeatherPhenomenonAndStatus(BIKE, "RAIN", Status.CURRENT);

            assertNotNull(foundExtraFee);
            assertEquals("weather phenomenon", foundExtraFee.get().getName());
            assertEquals(BIKE, foundExtraFee.get().getVehicleType());
            assertEquals(Status.CURRENT, foundExtraFee.get().getStatus());
        }

        @Test
        void testFindByVehicleTypeAndAndWeatherPhenomenonAndStatus_shouldReturnNoExtraFee() {
            extraFeeRepository.deleteAll();
            Optional<ExtraFee> foundExtraFee = extraFeeRepository
                    .findByVehicleTypeAndWeatherPhenomenonAndStatus(BIKE, "RAIN", Status.CURRENT);

            assertTrue(foundExtraFee.isEmpty());
        }

    }

    @Nested
    @DisplayName("When Find ExtraFee by Id")
    class FindExtraFeeByIdTests {

        @Test
        void testFindExtraFeeById_shouldReturnExistingExtraFeeWithGivenId() {
            ExtraFee existingExtraFee = extraFeeRepository.save(extraFee);
            Optional<ExtraFee> foundExtraFee = extraFeeRepository.findById(existingExtraFee.getId());
            assertEquals(existingExtraFee.getId(), foundExtraFee.get().getId());
            assertTrue(foundExtraFee.isPresent());

        }

        @Test
        void testFindExtraFeeById_shouldReturnNoExtraFeeWithGivenId() {
            Optional<ExtraFee> foundExtraFee = extraFeeRepository.findById(EXTRA_FEE_ID);

            assertFalse(foundExtraFee.isPresent());
            assertThat(foundExtraFee).isEmpty();

        }
    }

    @Nested
    @DisplayName("When Find All ExtraFee")
    class FindALlExtraFeeTests {

        @Test
        void testFindAllExtraFee_shouldReturnExtraFeeListSuccessfully() {
            extraFeeRepository.deleteAll();
            extraFeeRepository.save(extraFee);
            List<ExtraFee> extraFeeList = extraFeeRepository.findAll();

            assertEquals(1, extraFeeList.size());
            assertFalse(extraFeeList.isEmpty());
        }

        @Test
        void testFindAllExtraFee_shouldReturnEmptyExtraFeeList() {
            extraFeeRepository.deleteAll();
            List<ExtraFee> extraFeeList = extraFeeRepository.findAll();

            assertEquals(0, extraFeeList.size());
            assertTrue(extraFeeList.isEmpty());
        }
    }

    @Nested
    @DisplayName("When Save ExtraFee")
    class SaveExtraFeeTests {

        @Test
        void testSaveExtraFee_shouldSaveExtraFeeSuccessfully() {
            ExtraFee savedExtraFee = extraFeeRepository.save(extraFee);

            assertNotNull(savedExtraFee);
            assertNotNull(savedExtraFee);
            assertThat(savedExtraFee.getId()).isNotNull();
            assertThat(savedExtraFee.getFee()).isEqualTo(extraFee.getFee());
            assertEquals(extraFee.getName(), savedExtraFee.getName());
        }

        @Test
        void testSaveExtraFee_shouldThrowException() {
            assertThrows(InvalidDataAccessApiUsageException.class,
                    () -> extraFeeRepository.save(null));
        }

    }

    @Nested
    @DisplayName("When Delete ExtraFee By Id")
    class DeleteExtraFeeByIdTests {
        @Test
        void testDeleteExtraFeeById_shouldDeleteExtraFeeByIdSuccessfully() {
            ExtraFee extraFeeForDelete = extraFeeRepository.save(extraFee);

            extraFeeRepository.deleteById(extraFeeForDelete.getId());
            Optional<ExtraFee> deletedExtraFee = extraFeeRepository.findById(extraFeeForDelete.getId());
            assertFalse(deletedExtraFee.isPresent());
            assertThat(deletedExtraFee).isEmpty();
        }

    }

}
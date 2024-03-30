package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.factory.BaseFeeFactory;
import com.deliveryfeecalculation.domain.model.BaseFee;
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
import static com.deliveryfeecalculation.domain.enums.VehicleType.BIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BaseFeeRepositoryTest {

    @Autowired
    private BaseFeeRepository baseFeeRepository;

    private static final long BASE_FEE_ID = 101L;

    private BaseFee baseFee;

    @BeforeEach
    public void setUp() {
        baseFee = BaseFeeFactory.createBaseFee();
    }

    @Nested
    @DisplayName("When Find BaseFee By City and VehicleType")
    class FindBaseFeeByCityAndVehicleTypeTests {
        @Test
        void testFindBaseFeeByCityAndVehicleType_shouldReturnBaseFeeSuccessfully() {
            Optional<BaseFee> foundBaseFee = baseFeeRepository.findBaseFeeByCityAndVehicleType(TALLINN, BIKE);

            assertTrue(foundBaseFee.isPresent());
            assertEquals(TALLINN, foundBaseFee.get().getCity());
            assertEquals(BIKE, foundBaseFee.get().getVehicleType());
        }

        @Test
        void testFindBaseFeeByCityAndVehicleType_shouldReturnNoBaseFee() {
            baseFeeRepository.deleteAll();
            Optional<BaseFee> foundBaseFee = baseFeeRepository.findBaseFeeByCityAndVehicleType(TALLINN, BIKE);

            assertFalse(foundBaseFee.isPresent());
            assertThat(foundBaseFee).isEmpty();
        }

    }

    @Nested
    @DisplayName("When Find BaseFee by Id")
    class FindBaseFeeByIdTests {

        @Test
        void testFindBaseFeeById_shouldReturnExistingBaseFeeWithGivenId() {
            BaseFee existingBaseFee = baseFeeRepository.save(baseFee);
            Optional<BaseFee> foundBaseFee = baseFeeRepository.findById(existingBaseFee.getId());
            assertEquals(existingBaseFee.getId(), foundBaseFee.get().getId());
            assertTrue(foundBaseFee.isPresent());

        }

        @Test
        void testFindBaseFeeById_shouldReturnNoBaseFeeWithGivenId() {
            Optional<BaseFee> foundBaseFee = baseFeeRepository.findById(BASE_FEE_ID);

            assertFalse(foundBaseFee.isPresent());
            assertThat(foundBaseFee).isEmpty();
        }
    }

    @Nested
    @DisplayName("When Find All BaseFee")
    class FindALlBaseFeeTests {

        @Test
        void testFindAllBaseFee_shouldReturnBaseFeeListSuccessfully() {
            baseFeeRepository.deleteAll();
            baseFeeRepository.save(baseFee);
            List<BaseFee> baseFeeList = baseFeeRepository.findAll();

            assertEquals(1, baseFeeList.size());
            assertFalse(baseFeeList.isEmpty());
        }

        @Test
        void testFindAllBaseFee_shouldReturnEmptyBaseFeeList() {
            baseFeeRepository.deleteAll();
            List<BaseFee> baseFeeList = baseFeeRepository.findAll();

            assertEquals(baseFeeList.size(), 0);
            assertTrue(baseFeeList.isEmpty());
        }
    }

    @Nested
    @DisplayName("When Save BaseFee")
    class SaveBaseFeeTests {

        @Test
        void testSaveBaseFee_shouldSaveBaseFeeSuccessfully() {
            BaseFee savedBaseFee = baseFeeRepository.save(baseFee);

            assertNotNull(savedBaseFee);
            assertNotNull(savedBaseFee);
            assertThat(savedBaseFee.getId()).isNotNull();
            assertThat(savedBaseFee.getFee()).isEqualTo(baseFee.getFee());
            assertEquals(baseFee.getCity(), savedBaseFee.getCity());
        }

        @Test
        void testSaveBaseFee_shouldThrowException() {
            assertThrows(InvalidDataAccessApiUsageException.class,
                    () -> baseFeeRepository.save(null));
        }

    }

    @Nested
    @DisplayName("When Delete BaseFee By Id")
    class DeleteBaseFeeByIdTests {
        @Test
        void testDeleteBaseFeeById_shouldDeleteBaseFeeByIdSuccessfully() {
            BaseFee baseFeeForDelete = baseFeeRepository.save(baseFee);

            baseFeeRepository.deleteById(baseFeeForDelete.getId());
            Optional<BaseFee> deletedBaseFee = baseFeeRepository.findById(baseFeeForDelete.getId());
            assertFalse(deletedBaseFee.isPresent());
            assertThat(deletedBaseFee).isEmpty();
        }

    }

}
package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.BaseFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for managing {@link BaseFee} entities.
 *
 * @author Alla Borodina
 */
@Repository
public interface BaseFeeRepository extends JpaRepository<BaseFee, Long> {

    /**
     * Finds a {@link BaseFee} based on the provided city and vehicle type.
     * This method is used to retrieve the base fee applicable for a specific combination of city and vehicle type.
     *
     * @param city        the city for which the base fee needs to be found
     * @param vehicleType the vehicle type for which the base fee needs to be found
     * @return an {@link Optional} containing the {@link BaseFee} if found, or an empty Optional if not found
     */
    Optional<BaseFee> findBaseFeeByCityAndVehicleType(City city, VehicleType vehicleType);

}

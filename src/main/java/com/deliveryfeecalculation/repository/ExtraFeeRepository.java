package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link ExtraFee} entities.
 *
 * @author Alla Borodina
 */
@Repository
public interface ExtraFeeRepository extends JpaRepository<ExtraFee,Long> {

    /**
     * Finds all ExtraFee entities matching the given name, vehicle type, and status.
     *
     * @param name The name of the ExtraFee.
     * @param vehicleType The type of vehicle associated with the ExtraFee.
     * @param status The status of the ExtraFee.
     * @return A list of matching ExtraFee entities.
     */
    List<ExtraFee> findAllByNameAndVehicleTypeAndStatus(String name, VehicleType vehicleType, Status status);

    /**
     * Finds an ExtraFee entity matching the given vehicle type, weather phenomenon, and status.
     *
     * @param vehicleType The vehicle type of the ExtraFee.
     * @param weatherPhenomenon The weather phenomenon associated with the ExtraFee.
     * @param status The status of the ExtraFee.
     * @return An {@link Optional} containing the found ExtraFee or an empty Optional if not found.
     */
    Optional<ExtraFee> findByVehicleTypeAndWeatherPhenomenonAndStatus(VehicleType vehicleType, String weatherPhenomenon, Status status);

}

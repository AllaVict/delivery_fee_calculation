package com.deliveryfeecalculation.repository;

import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtraFeeRepository extends JpaRepository<ExtraFee,Long> {
    List<ExtraFee> findAllByNameAndVehicleTypeAndStatus(String name, VehicleType vehicleType, Status status);
    Optional<ExtraFee> findByVehicleTypeAndWeatherPhenomenonAndStatus(VehicleType vehicleType, String weatherPhenomenon, Status status);
    Optional<ExtraFee> findAllByNameAndWeatherPhenomenonAndStatus(String name, String weatherPhenomenon, Status status);

}

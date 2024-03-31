package com.deliveryfeecalculation.factory;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.ExtraFee;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.deliveryfeecalculation.domain.enums.VehicleType.BIKE;
import static com.deliveryfeecalculation.domain.enums.VehicleType.SCOOTER;

public class ExtraFeeFactory {

    private static TypeConverter<ExtraFee, ExtraFeeDTO> extraFeeToExtraFeeDTO;
    private static final LocalDateTime CREATED_DATE = LocalDateTime.of(2024, 3, 23, 20, 24);

    private ExtraFeeFactory() {
    }

    public static ExtraFee createExtraFeeWithData() {
        return createExtraFee(101L, "air temperature", SCOOTER, 1.00, -10.0, null,
                null, false, Status.CURRENT, CREATED_DATE);
    }

    public static ExtraFeeDTO createExtraFeeDtoWithData() {
        return createExtraFeeDTO(101L, "air temperature", SCOOTER, 1.00, -10.0, null,
                null, false, Status.CURRENT, CREATED_DATE);
    }

    public static List<ExtraFee> createWindExtraFeeList() {
        final List<ExtraFee> extraFeeList = Arrays.asList(
                createExtraFee(104L, "wind speed", BIKE, 0.50, 10.0, 20.0, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(105L, "wind speed", BIKE, null, null, 20.0, null, true, Status.CURRENT, CREATED_DATE)
        );
        return extraFeeList;
    }

    public static List<ExtraFee> createAirExtraFeeList() {
        final List<ExtraFee> extraFeeList = Arrays.asList(
                createExtraFee(100L, "air temperature", SCOOTER, 1.00, -10.0, null, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(101L, "air temperature", BIKE, 1.00, -10.0, null, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(102L, "air temperature", SCOOTER, 0.50, -10.0, 0.0, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(103L, "air temperature", BIKE, 0.50, -10.0, 0.0, null, false, Status.CURRENT, CREATED_DATE)
        );
        return extraFeeList;
    }

    public static List<ExtraFee> createPhenomenonExtraFeeList() {
        final List<ExtraFee> extraFeeList = Arrays.asList(
                createExtraFee(106L, "weather phenomenon", SCOOTER, 1.00, null, null, "SNOW", false, Status.CURRENT, CREATED_DATE),
                createExtraFee(107L, "weather phenomenon", SCOOTER, 0.50, null, null, "RAIN", false, Status.CURRENT, CREATED_DATE),
                createExtraFee(108L, "weather phenomenon", SCOOTER, null, null, null, "HAIL", true, Status.CURRENT, CREATED_DATE),
                createExtraFee(109L, "weather phenomenon", BIKE, 1.00, null, null, "SNOW", false, Status.CURRENT, CREATED_DATE),
                createExtraFee(110L, "weather phenomenon", BIKE, 0.50, null, null, "RAIN", false, Status.CURRENT, CREATED_DATE),
                createExtraFee(111L, "weather phenomenon", BIKE, null, null, null, "HAIL", true, Status.CURRENT, CREATED_DATE)
        );
        return extraFeeList;
    }

    public static List<ExtraFee> createExtraFeeList() {
        final List<ExtraFee> extraFeeList = Arrays.asList(
                createExtraFee(100L, "air temperature", SCOOTER, 1.00, -10.0, null, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(101L, "air temperature", BIKE, 1.00, -10.0, null, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(102L, "air temperature", SCOOTER, 0.50, -10.0, 0.0, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(103L, "air temperature", BIKE, 0.50, -10.0, 0.0, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(104L, "wind speed", BIKE, 0.50, 10.0, 20.0, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFee(105L, "wind speed", BIKE, null, null, 20.0, null, true, Status.CURRENT, CREATED_DATE),
                createExtraFee(106L, "weather phenomenon", SCOOTER, 1.00, null, null, "SNOW", false, Status.CURRENT, CREATED_DATE),
                createExtraFee(107L, "weather phenomenon", SCOOTER, 0.50, null, null, "RAIN", false, Status.CURRENT, CREATED_DATE),
                createExtraFee(108L, "weather phenomenon", SCOOTER, null, null, null, "HAIL", true, Status.CURRENT, CREATED_DATE),
                createExtraFee(109L, "weather phenomenon", BIKE, 1.00, null, null, "SNOW", false, Status.CURRENT, CREATED_DATE),
                createExtraFee(110L, "weather phenomenon", BIKE, 0.50, null, null, "RAIN", false, Status.CURRENT, CREATED_DATE),
                createExtraFee(111L, "weather phenomenon", BIKE, null, null, null, "HAIL", true, Status.CURRENT, CREATED_DATE)
        );
        return extraFeeList;
    }

    public static List<ExtraFeeDTO> createExtraFeeDTOList() {
        final List<ExtraFeeDTO> extraFeeList = Arrays.asList(
                createExtraFeeDTO(100L, "air temperature", SCOOTER, 1.00, -10.0, null, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(101L, "air temperature", BIKE, 1.00, -10.0, null, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(102L, "air temperature", SCOOTER, 0.50, -10.0, 0.0, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(103L, "air temperature", BIKE, 0.50, -10.0, 0.0, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(104L, "wind speed", BIKE, 0.50, 10.0, 20.0, null, false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(105L, "wind speed", BIKE, null, null, 20.0, null, true, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(106L, "weather phenomenon", SCOOTER, 1.00, null, null, "SNOW", false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(107L, "weather phenomenon", SCOOTER, 0.50, null, null, "RAIN", false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(108L, "weather phenomenon", SCOOTER, null, null, null, "HAIL", true, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(109L, "weather phenomenon", BIKE, 1.00, null, null, "SNOW", false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(110L, "weather phenomenon", BIKE, 0.50, null, null, "RAIN", false, Status.CURRENT, CREATED_DATE),
                createExtraFeeDTO(111L, "weather phenomenon", BIKE, null, null, null, "HAIL", true, Status.CURRENT, CREATED_DATE)
        );
        return extraFeeList;
    }

    public static ExtraFee createExtraFee(
            final Long id,
            final String name,
            final VehicleType vehicleType,
            final Double fee,
            final Double lowerLimit,
            final Double upperLimit,
            final String weatherPhenomenon,
            final Boolean isForbidden,
            final Status status,
            final LocalDateTime createdDate) {
        ExtraFee extraFee = new ExtraFee();
        extraFee.setId(id);
        extraFee.setName(name);
        extraFee.setVehicleType(vehicleType);
        extraFee.setFee(fee);
        extraFee.setLowerLimit(lowerLimit);
        extraFee.setUpperLimit(upperLimit);
        extraFee.setWeatherPhenomenon(weatherPhenomenon);
        extraFee.setForbidden(isForbidden);
        extraFee.setStatus(status);
        extraFee.setCreatedDate(createdDate);
        return extraFee;
    }

    public static ExtraFeeDTO createExtraFeeDTO(
            final Long id,
            final String name,
            final VehicleType vehicleType,
            final Double fee,
            final Double lowerLimit,
            final Double upperLimit,
            final String weatherPhenomenon,
            final Boolean isForbidden,
            final Status status,
            final LocalDateTime createdDate) {
        ExtraFeeDTO extraFeeDTO = new ExtraFeeDTO();
        extraFeeDTO.setId(id);
        extraFeeDTO.setName(name);
        extraFeeDTO.setVehicleType(vehicleType);
        extraFeeDTO.setFee(fee);
        extraFeeDTO.setLowerLimit(lowerLimit);
        extraFeeDTO.setUpperLimit(upperLimit);
        extraFeeDTO.setWeatherPhenomenon(weatherPhenomenon);
        extraFeeDTO.setForbidden(isForbidden);
        extraFeeDTO.setStatus(status);
        extraFeeDTO.setCreatedDate(createdDate);
        return extraFeeDTO;
    }

}

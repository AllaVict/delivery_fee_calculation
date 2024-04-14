package com.deliveryfeecalculation.factory;

import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.ExtraFee;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.deliveryfeecalculation.domain.enums.VehicleType.BIKE;
import static com.deliveryfeecalculation.domain.enums.VehicleType.SCOOTER;

public class BaseFeeFactory {

    private static final long BASE_FEE_ID = 101L;

    private static final Double BASE_FEE = 0.00;

    private static final LocalDateTime CREATED_DATE = LocalDateTime.of(2024, 3, 23, 20, 24);

    private BaseFeeFactory() {
    }

    public static BaseFee createBaseFee() {
        final BaseFee createdBaseFee = new BaseFee();
        createdBaseFee.setId(BASE_FEE_ID);
        createdBaseFee.setCity(City.TALLINN);
        createdBaseFee.setVehicleType(VehicleType.BIKE);
        createdBaseFee.setFee(BASE_FEE);
        createdBaseFee.setCreatedDate(CREATED_DATE);
        return createdBaseFee;
    }

    public static BaseFeeDTO createBaseFeeDto() {
        final BaseFeeDTO createdBaseFeeDto = new BaseFeeDTO();
        createdBaseFeeDto.setId(BASE_FEE_ID);
        createdBaseFeeDto.setCity(City.TALLINN);
        createdBaseFeeDto.setVehicleType(VehicleType.BIKE);
        createdBaseFeeDto.setFee(BASE_FEE);
        createdBaseFeeDto.setCreatedDate(CREATED_DATE);
        return createdBaseFeeDto;
    }

    public static List<BaseFee> createBaseFeeList() {
        final List<BaseFee> baseFeeList = Arrays.asList(
                createBaseFee(),
                createBaseFee()
        );
        return baseFeeList;
    }

    public static List<BaseFeeDTO> createBaseFeeDtoList() {
        final List<BaseFeeDTO> baseFeeList = Arrays.asList(
                createBaseFeeDto(),
                createBaseFeeDto()
        );
        return baseFeeList;
    }

}

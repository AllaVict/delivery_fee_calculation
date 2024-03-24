package com.deliveryfeecalculation.config;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.BaseFee;

import java.time.LocalDateTime;

public class BaseFeeFactory {

    private static final long BASE_FEE_ID = 101L;

    private static final Double BASE_FEE = 3.00;

    private static final LocalDateTime CREATED_DATE = LocalDateTime.of(2024, 03, 23, 20, 24);

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

}

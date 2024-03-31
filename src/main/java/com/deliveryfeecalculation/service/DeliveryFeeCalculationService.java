package com.deliveryfeecalculation.service;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.Response;

public interface DeliveryFeeCalculationService {
    Response deliveryFeeCalculate(City city, VehicleType vehicleType);

}

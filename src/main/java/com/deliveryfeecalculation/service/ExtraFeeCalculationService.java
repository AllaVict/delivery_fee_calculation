package com.deliveryfeecalculation.service;

import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.domain.model.WeatherCondition;

public interface ExtraFeeCalculationService {

    Response extraFeesCalculate(WeatherCondition weatherCondition, VehicleType vehicleType);
}

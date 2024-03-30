package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.deliveryfeecalculation.factory.WeatherConditionFactory.*;
import static com.deliveryfeecalculation.constants.Constants.Messages.DELIVERY_FEE_CALCULATION;
import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static com.deliveryfeecalculation.domain.enums.VehicleType.BIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExtraFeeCalculationServiceImplTest {

    @InjectMocks
    private ExtraFeeCalculationServiceImpl extraFeeCalculationService;

    private Response response;
    private WeatherCondition weatherCondition;

    @Test
    void testExtraFeeCalculation_ReturnExtraFeeSuccessfully() {
        weatherCondition = createWeatherConditionWithParameters(-15.0, 15.0, "SNOW", City.TALLINN);
        response = new Response(DELIVERY_FEE_CALCULATION, 2.5);

        Response result = extraFeeCalculationService.extraFeesCalculate(weatherCondition, BIKE);

        assertNotNull(result);
        assertEquals(response, result);
        assertThat(result.getFee()).isEqualTo(response.getFee());
    }

    @Test
    void testExtraFeeCalculation_ReturnResponseWithVehicleTypeIsForbidden() {
        weatherCondition = createWeatherConditionWithHail();
        response = new Response(VEHICLE_FORBIDDEN, 0.0);
        Response result = extraFeeCalculationService.extraFeesCalculate(weatherCondition, BIKE);

        assertNotNull(result);
        assertEquals(response, result);
        assertThat(result.getFee()).isEqualTo(response.getFee());
    }

    @Test
    void testExtraFeeCalculation_shouldThrowException() {
        weatherCondition = new WeatherCondition();
        response = new Response();
        assertThrows(NullPointerException.class,
                () -> extraFeeCalculationService.extraFeesCalculate(weatherCondition, BIKE));
    }

}
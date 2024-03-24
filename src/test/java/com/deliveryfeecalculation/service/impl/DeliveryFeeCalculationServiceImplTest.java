package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.config.WeatherConditionFactory;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.repository.BaseFeeRepository;
import com.deliveryfeecalculation.repository.WeatherConditionRepository;
import com.deliveryfeecalculation.service.ExtraFeeCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static com.deliveryfeecalculation.config.BaseFeeFactory.createBaseFee;
import static com.deliveryfeecalculation.config.WeatherConditionFactory.createWeatherConditionWithHail;
import static com.deliveryfeecalculation.constants.Constants.Messages.DELIVERY_FEE_CALCULATION;
import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static com.deliveryfeecalculation.domain.enums.City.TALLINN;
import static com.deliveryfeecalculation.domain.enums.VehicleType.BIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryFeeCalculationServiceImplTest {

    @Mock
    private BaseFeeRepository baseFeeRepository;

    @Mock
    private WeatherConditionRepository weatherConditionRepository;

    @Mock
    private ExtraFeeCalculationService extraFeeCalculationService;

    @InjectMocks
    private DeliveryFeeCalculationServiceImpl deliveryFeeCalculationService;

    private Response response;
    private WeatherCondition weatherCondition;
    private BaseFee baseFee;

    @BeforeEach
    public void setUp() {
        response =new Response(DELIVERY_FEE_CALCULATION, 5.5);
        weatherCondition = WeatherConditionFactory.createWeatherCondition();
        baseFee = createBaseFee();
    }

    @Test
    void testDeliveryFeeCalculate_ReturnResponseWithDeliveryFeeSuccessfully() {
        Sort sort =Sort.by(Sort.Direction.DESC, "observationTime");
        when(weatherConditionRepository.findFirstAfterFindAllByStationName(TALLINN, sort))
                .thenReturn(Optional.of(weatherCondition));
        when(baseFeeRepository.findBaseFeeByCityAndVehicleType(TALLINN, BIKE))
                .thenReturn(Optional.ofNullable(baseFee));
        when(extraFeeCalculationService.extraFeesCalculate(weatherCondition, BIKE)).thenReturn(response);

        Response result = deliveryFeeCalculationService.deliveryFeeCalculate(TALLINN, BIKE);

        assertNotNull(result);
        assertEquals(response, result);
        assertThat(result.getFee()).isEqualTo(response.getFee());

    }

    @Test
    void testExtraFeeCalculation_ReturnResponseWithVehicleTypeIsForbidden() {
        weatherCondition = createWeatherConditionWithHail();
        response =new Response(VEHICLE_FORBIDDEN, 0.0);
        Sort sort =Sort.by(Sort.Direction.DESC, "observationTime");
        when(weatherConditionRepository.findFirstAfterFindAllByStationName(TALLINN, sort))
                .thenReturn(Optional.of(weatherCondition));
        when(baseFeeRepository.findBaseFeeByCityAndVehicleType(TALLINN, BIKE))
                .thenReturn(Optional.ofNullable(baseFee));
        when(extraFeeCalculationService.extraFeesCalculate(weatherCondition, BIKE)).thenReturn(response);

        Response result = deliveryFeeCalculationService.deliveryFeeCalculate(TALLINN, BIKE);

        assertNotNull(result);
        assertEquals(response, result);
        assertThat(result.getFee()).isEqualTo(response.getFee());
     }

}
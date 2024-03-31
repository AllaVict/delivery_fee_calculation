package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.repository.ExtraFeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.deliveryfeecalculation.factory.ExtraFeeFactory.*;
import static com.deliveryfeecalculation.factory.ExtraFeeFactory.createExtraFee;
import static com.deliveryfeecalculation.factory.WeatherConditionFactory.*;
import static com.deliveryfeecalculation.constants.Constants.Messages.DELIVERY_FEE_CALCULATION;
import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static com.deliveryfeecalculation.domain.enums.VehicleType.BIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtraFeeCalculationServiceImplTest {

    @Mock
    private ExtraFeeRepository extraFeeRepository;
    @InjectMocks
    private ExtraFeeCalculationServiceImpl extraFeeCalculationService;

    private Response response;
    private WeatherCondition weatherCondition;

    private List<ExtraFee> extraFeeList;
    private List<ExtraFee> windExtraFeeList;

    private ExtraFee extraFee;
    private static final LocalDateTime CREATED_DATE = LocalDateTime.of(2024, 3, 23, 20, 24);


    @BeforeEach
    public void setUp() {
        extraFeeList = createExtraFeeList();
        extraFee = createExtraFeeWithData();
        windExtraFeeList = createWindExtraFeeList();
    }

    @Test
    void testExtraFeeCalculation_shouldThrowException() {
        weatherCondition = new WeatherCondition();
        response = new Response();
        assertThrows(NullPointerException.class,
                () -> extraFeeCalculationService.extraFeesCalculate(weatherCondition, BIKE));
    }
    @ParameterizedTest()
    @MethodSource("arguments")
    void testExtraFeesCalculate_WithVariousParameters_ReturnsCorrectExtraFeeResponse(Response response,
                                                                                     WeatherCondition weatherCondition,
                                                                                     ExtraFee phenomenonExtraFee) {
        when(extraFeeRepository.findByVehicleTypeAndWeatherPhenomenonAndStatus(BIKE,
                weatherCondition.getWeatherPhenomenon(), Status.CURRENT))
                .thenReturn(Optional.ofNullable(phenomenonExtraFee));
        when(extraFeeRepository.findAllByNameAndVehicleTypeAndStatus(
                "wind speed", BIKE, Status.CURRENT)).thenReturn(createWindExtraFeeList());
        when(extraFeeRepository.findAllByNameAndVehicleTypeAndStatus(
                "air temperature", BIKE, Status.CURRENT)).thenReturn(createAirExtraFeeList());

        Response result = extraFeeCalculationService.extraFeesCalculate(weatherCondition, BIKE);

        assertNotNull(result);
        assertEquals(response, result);
        assertThat(result.getFee()).isEqualTo(response.getFee());
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of(new Response(VEHICLE_FORBIDDEN, 0.0),
                        createWeatherConditionWithHail(),
                        createExtraFee(111L, "weather phenomenon", BIKE, 0.00, null, null,"HAIL", true, Status.CURRENT, CREATED_DATE)),
                Arguments.of(new Response(VEHICLE_FORBIDDEN, 0.0),
                        createWeatherConditionWithParameters(0.0, 25.0, "CLOUDY", City.TALLINN),
                        createExtraFee(111L, "weather phenomenon", BIKE, 0.00, null, null,"CLOUDY", true, Status.CURRENT, CREATED_DATE)),
                Arguments.of(new Response(DELIVERY_FEE_CALCULATION, 2.5),
                        createWeatherConditionWithParameters(-11.0, 15.0, "SNOW", City.TALLINN),
                        createExtraFee(110L, "weather phenomenon", BIKE, 1.00, null, null, "SNOW",
                                false, Status.CURRENT, CREATED_DATE)),
                Arguments.of(new Response(DELIVERY_FEE_CALCULATION, 1.5),
                        createWeatherConditionWithParameters(0.0, 15.0, "RAIN", City.TALLINN),
                        createExtraFee(110L, "weather phenomenon", BIKE, 0.50, null, null, "RAIN",
                                false, Status.CURRENT, CREATED_DATE))
        );
    }

}
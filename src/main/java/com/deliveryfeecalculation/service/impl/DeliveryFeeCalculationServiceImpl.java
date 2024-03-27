package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.repository.BaseFeeRepository;
import com.deliveryfeecalculation.repository.WeatherConditionRepository;
import com.deliveryfeecalculation.service.DeliveryFeeCalculationService;
import com.deliveryfeecalculation.service.ExtraFeeCalculationService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static com.deliveryfeecalculation.constants.Constants.Messages.DELIVERY_FEE_CALCULATION;

@Service
public class DeliveryFeeCalculationServiceImpl implements DeliveryFeeCalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryFeeCalculationServiceImpl.class);
    private final BaseFeeRepository baseFeeRepository;

    private final WeatherConditionRepository weatherConditionRepository;

    private final ExtraFeeCalculationService extraFeeCalculationService;

    public DeliveryFeeCalculationServiceImpl(final BaseFeeRepository baseFeeRepository,
                                             final WeatherConditionRepository weatherConditionRepository,
                                             final ExtraFeeCalculationService extraFeeCalculationService) {
        this.baseFeeRepository = baseFeeRepository;
        this.weatherConditionRepository = weatherConditionRepository;
        this.extraFeeCalculationService = extraFeeCalculationService;
    }

    /**
     * Performs the delivery fee calculation based on the provided city and vehicle type. The method fetches the latest
     * weather condition for the city, calculates the base fee, and any extra fees applicable due to weather conditions
     * or vehicle type restrictions.
     *
     * @param city the city for which the delivery fee is being calculated
     * @param vehicleType the type of vehicle used for the delivery
     * @return a {@link Response} containing the calculated delivery fee and a message detailing the calculation.
     * @throws EntityNotFoundException if no weather condition or base fee can be found for the provided city and vehicle type.
     */
    @Override
    public Response deliveryFeeCalculate(final City city, final VehicleType vehicleType) {

       final WeatherCondition weatherCondition = weatherConditionRepository
               .findFirstAfterFindAllByStationName(city, Sort.by(Sort.Direction.DESC, "observationTime"))
                       .orElseThrow(() -> new EntityNotFoundException("Weather condition not found for city: " + city));

       final Double baseFee = baseFeeRepository
                .findBaseFeeByCityAndVehicleType(city, vehicleType)
                .orElseThrow(() -> new EntityNotFoundException("Base fee not found for city: " + city + " and vehicle type: " + vehicleType))
                .getFee();

        final Response extraFeeResponse = extraFeeCalculationService.extraFeesCalculate(weatherCondition, vehicleType);

        if (extraFeeResponse.getMessage().equals(VEHICLE_FORBIDDEN)) {
            LOGGER.debug("In deliveryFeeCalculate deliveryFee is calculated with response {} ", extraFeeResponse);
            return extraFeeResponse;
        }

        final Response response =new Response(DELIVERY_FEE_CALCULATION, baseFee + extraFeeResponse.getFee());

        LOGGER.debug("In deliveryFeeCalculate deliveryFee is calculated with response {} ", response);

        return response;
    }

}

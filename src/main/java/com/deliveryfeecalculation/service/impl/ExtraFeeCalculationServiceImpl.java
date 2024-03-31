package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.repository.ExtraFeeRepository;
import com.deliveryfeecalculation.service.ExtraFeeCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static com.deliveryfeecalculation.constants.Constants.Messages.DELIVERY_FEE_CALCULATION;

/**
 * Service implementation for calculating additional fees for delivery based on various weather conditions and vehicle types.
 *
 * @author Alla Borodina
 */
@Service
public class ExtraFeeCalculationServiceImpl implements ExtraFeeCalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraFeeCalculationServiceImpl.class);

    private final ExtraFeeRepository extraFeeRepository;

    public ExtraFeeCalculationServiceImpl(final ExtraFeeRepository extraFeeRepository) {
        this.extraFeeRepository = extraFeeRepository;
    }

    /**
     * Calculates extra fees for a specific weather condition attribute (air temperature or wind speed) based on the vehicle type.
     * This method looks up the applicable extra fees from the repository based on the name of the extra fee (e.g., "air temperature" or "wind speed"),
     * the current weather condition, and the vehicle type. It then determines whether the vehicle is forbidden under the specific conditions
     * or calculates the additional charges accordingly.
     *
     * @param weatherCondition the current weather conditions affecting the delivery
     * @param vehicleType      the type of vehicle being used for the delivery
     * @return a {@link Response} object containing either the calculated extra fee or a message indicating that the vehicle type is forbidden.
     */
    public Response extraFeesCalculate(final WeatherCondition weatherCondition, final VehicleType vehicleType) {

        final Response airTemperatureResponse = determineAirOrWindExtraFee("air temperature", weatherCondition, vehicleType);
        final Response windSpeedResponse = determineAirOrWindExtraFee("wind speed", weatherCondition, vehicleType);
        final Response weatherPhenomenonResponse = determinePhenomenonExtraFee(weatherCondition, vehicleType);

        if (airTemperatureResponse.getMessage().equals(VEHICLE_FORBIDDEN) || windSpeedResponse.getMessage().equals(VEHICLE_FORBIDDEN)
                || weatherPhenomenonResponse.getMessage().equals(VEHICLE_FORBIDDEN)) {
            return new Response(VEHICLE_FORBIDDEN, 0.0);
        }

        final Double extraFees = airTemperatureResponse.getFee() + windSpeedResponse.getFee() + weatherPhenomenonResponse.getFee();

        return new Response(DELIVERY_FEE_CALCULATION, extraFees);

    }

    /**
     * Calculates the extra fee based on the weather phenomenon affecting the delivery.
     * It queries the repository to find any applicable extra fees for the given weather phenomenon
     * and vehicle type. If a matching extra fee is found, it determines whether the vehicle is
     * forbidden under these specific weather conditions or calculates the additional charge.
     *
     * @param weatherCondition The current weather conditions affecting the delivery.
     * @param vehicleType      The type of vehicle being used for delivery.
     * @return A {@link Response} object containing either the calculated extra fee for the weather phenomenon
     * or a message indicating that the vehicle type is forbidden under these conditions.
     */
    private Response determinePhenomenonExtraFee(final WeatherCondition weatherCondition, final VehicleType vehicleType) {

        final Response defaultResponse = new Response(DELIVERY_FEE_CALCULATION, 0.0);

        try {
            if (!weatherCondition.getWeatherPhenomenon().isEmpty()) {
                final ExtraFee weatherPhenomenonExtraFee = extraFeeRepository
                        .findByVehicleTypeAndWeatherPhenomenonAndStatus(vehicleType,
                                weatherCondition.getWeatherPhenomenon(), Status.CURRENT)
                        .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for weatherPhenomenon" +
                                " and for Vehicle Type: " + vehicleType));

                return weatherPhenomenonExtraFee.getForbidden()
                        ? new Response(VEHICLE_FORBIDDEN, 0.00)
                        : new Response(DELIVERY_FEE_CALCULATION, weatherPhenomenonExtraFee.getFee());
            }
        } catch (ResourceNotFoundException exception) {

            LOGGER.error("ResourceNotFoundException: " + exception.getMessage());
        }

        return defaultResponse;
    }

    /**
     * Retrieves and calculates extra fees based on a specific weather condition attribute
     * (e.g., "air temperature" or "wind speed") and the vehicle type. This method utilizes the
     * extraFeeRepository to find all matching extra fees and then determines the applicable fee
     * based on the current value of the weather condition attribute.
     *
     * @param nameExtraFee     The name of the extra fee attribute to calculate (e.g., "air temperature" or "wind speed").
     * @param weatherCondition The current weather conditions affecting the delivery.
     * @param vehicleType      The type of vehicle being used for the delivery.
     * @return A {@link Response} object containing the calculated extra fee based on the specified weather condition attribute
     * or a message indicating that the vehicle type is forbidden, if applicable.
     */
    private Response determineAirOrWindExtraFee(final String nameExtraFee,
                                                final WeatherCondition weatherCondition,
                                                final VehicleType vehicleType) {

        Response defaultResponse = new Response(DELIVERY_FEE_CALCULATION, 0.00);
        Double current = 0.0;

        if (nameExtraFee.equals("wind speed")) current = weatherCondition.getWindSpeed();
        if (nameExtraFee.equals("air temperature")) current = weatherCondition.getAirTemperature();

        final List<ExtraFee> extraFees = extraFeeRepository.findAllByNameAndVehicleTypeAndStatus(nameExtraFee,
                vehicleType, Status.CURRENT);

        for (ExtraFee extraFee : extraFees) {

            if (extraFee.getLowerLimit() != null && extraFee.getUpperLimit() != null &&
                    (current <= extraFee.getUpperLimit() && current >= extraFee.getLowerLimit())) {

                return extraFee.getForbidden()
                        ? new Response(VEHICLE_FORBIDDEN, 0.00)
                        : new Response(DELIVERY_FEE_CALCULATION, extraFee.getFee());

            }

            if (extraFee.getLowerLimit() == null && extraFee.getUpperLimit() != null &&
                    (current > extraFee.getUpperLimit())) {
                return extraFee.getForbidden()
                        ? new Response(VEHICLE_FORBIDDEN, 0.00)
                        : new Response(DELIVERY_FEE_CALCULATION, extraFee.getFee());

            }

            if (extraFee.getLowerLimit() != null && extraFee.getUpperLimit() == null &&
                    (current < extraFee.getLowerLimit())) {
                return extraFee.getForbidden()
                        ? new Response(VEHICLE_FORBIDDEN, 0.00)
                        : new Response(DELIVERY_FEE_CALCULATION, extraFee.getFee());

            }
        }

        return defaultResponse;
    }
}

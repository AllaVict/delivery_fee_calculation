package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.enums.WeatherPhenomenon;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.service.ExtraFeeCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static com.deliveryfeecalculation.constants.Constants.Messages.DELIVERY_FEE_CALCULATION;

@Service
public class ExtraFeeCalculationServiceImpl implements ExtraFeeCalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraFeeCalculationServiceImpl.class);

    /**
     * Calculates extra fees based on weather conditions and vehicle type. The calculation considers
     * air temperature, wind speed, and weather phenomena to determine any additional charges or if
     * the vehicle type is forbidden under the current conditions.
     *
     * @param weatherCondition the current weather conditions
     * @param vehicleType the vehicle type being used for delivery
     * @return a {@link Response} with the total extra fees calculated or a message indicating the vehicle type is forbidden.
     */
    @Override
    public Response extraFeesCalculate(final WeatherCondition weatherCondition, final VehicleType vehicleType) {

        Response airTemperatureResponse = airTemperatureExtraFeeCalculation(weatherCondition, vehicleType);
        Response windSpeedResponse = windSpeedExtraFeeCalculation(weatherCondition, vehicleType);
        Response weatherPhenomenonResponse = weatherPhenomenonExtraFeeCalculation(weatherCondition, vehicleType);

        if (airTemperatureResponse.getMessage().equals(VEHICLE_FORBIDDEN)  || windSpeedResponse.getMessage().equals(VEHICLE_FORBIDDEN)
                || weatherPhenomenonResponse.getMessage().equals(VEHICLE_FORBIDDEN)) {

            final Response response = new Response(VEHICLE_FORBIDDEN, 0.0);
            LOGGER.debug("In extraFeeCalculation extraFee is calculated with response {} ", response);
            return response;
        }

        final Double extraFees = airTemperatureResponse.getFee() + windSpeedResponse.getFee() + weatherPhenomenonResponse.getFee();
        final Response response = new Response(DELIVERY_FEE_CALCULATION, extraFees);
        LOGGER.debug("In extraFeeCalculation extraFee is calculated with response {} ", response);

        return response;
    }

    private Response airTemperatureExtraFeeCalculation(final WeatherCondition weatherCondition, final VehicleType vehicleType) {

        if (vehicleType.equals(VehicleType.SCOOTER)  || vehicleType.equals(VehicleType.BIKE)) {
            Integer airTemperature = weatherCondition.getAirTemperature();
            if (airTemperature < -10) {
                return new Response(DELIVERY_FEE_CALCULATION, 1.0);
            } else if (airTemperature >= -10 && airTemperature <= 0) {
                return new Response(DELIVERY_FEE_CALCULATION, 0.5);
            }
        }
        return new Response(DELIVERY_FEE_CALCULATION, 0.0);
    }

    private Response windSpeedExtraFeeCalculation(final WeatherCondition weatherCondition, final VehicleType vehicleType) {

        if (vehicleType.equals(VehicleType.BIKE)) {
            Integer windSpeed = weatherCondition.getWindSpeed();
            if (windSpeed > 20) {
                return new Response(VEHICLE_FORBIDDEN, 0.0);
            } else if (windSpeed >= 10 && windSpeed <= 20) {
                return new Response(DELIVERY_FEE_CALCULATION, 0.5);
            }
        }
        return new Response(DELIVERY_FEE_CALCULATION, 0.0);
    }

    private Response weatherPhenomenonExtraFeeCalculation(final WeatherCondition weatherCondition, final VehicleType vehicleType) {
        if (vehicleType.equals(VehicleType.SCOOTER)  || vehicleType.equals(VehicleType.BIKE)) {
            WeatherPhenomenon weatherPhenomenon = weatherCondition.getWeatherPhenomenon();
            if ("SNOW".equalsIgnoreCase(weatherPhenomenon.name()) || "SLEET".equalsIgnoreCase(weatherPhenomenon.name())) {
                return new Response(DELIVERY_FEE_CALCULATION, 1.0);
            } else if ("RAIN".equalsIgnoreCase(weatherPhenomenon.name())) {
                return new Response(DELIVERY_FEE_CALCULATION, 0.5);
            } else if ("GLAZE".equalsIgnoreCase(weatherPhenomenon.name()) || "HAIL".equalsIgnoreCase(weatherPhenomenon.name()) || "THUNDER".equalsIgnoreCase(weatherPhenomenon.name())) {
                return new Response(VEHICLE_FORBIDDEN, 0.0);
            }
        }
        return new Response(DELIVERY_FEE_CALCULATION, 0.0);
    }

}

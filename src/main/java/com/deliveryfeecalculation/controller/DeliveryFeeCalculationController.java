package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.Request;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.service.DeliveryFeeCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static com.deliveryfeecalculation.constants.Constants.Endpoints.FEE_URL;

/**
 * Controller for handling delivery fee calculation requests.
 * This controller provides endpoints to calculate delivery fees based on specified city and vehicle type.
 *
 * @author Alla Borodina
 */
@RequestMapping(FEE_URL)
@RestController
@Tag(name = "DeliveryFee Calculation Controller", description = "DeliveryFee Calculation API")
public class DeliveryFeeCalculationController {

    private final DeliveryFeeCalculationService deliveryFeeCalculationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryFeeCalculationController.class);

    /**
     * Constructs a new DeliveryFeeCalculationController with the specified delivery fee calculation service.
     *
     * @param deliveryFeeCalculationService the service used for calculating delivery fees.
     */
    public DeliveryFeeCalculationController(final DeliveryFeeCalculationService deliveryFeeCalculationService) {
        this.deliveryFeeCalculationService = deliveryFeeCalculationService;
    }

    /**
     * Calculates the delivery fee based on the provided request details. The calculation takes into
     * account the city and vehicle type specified in the request. Logs the request and the calculation
     * result for debugging purposes.
     *
     * @param request the request containing the city and vehicle type for which the delivery fee is calculated
     * @return a {@link ResponseEntity} containing the calculation result or an error message. Returns the delivery
     * fee in a {@link Response} object wrapped in a ResponseEntity with an HTTP status of OK.
     */
    @GetMapping()
    @Operation(summary = "Calculate an DeliveryFee by given a city and a vehicle type")
    public ResponseEntity<?> getDeliveryFee(@RequestBody final Request request) {
        try {
            if (request == null || request.getCity() == null || request.getVehicleType() == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill all fields: city, vehicleType");

            final Response response = deliveryFeeCalculationService
                    .deliveryFeeCalculate(City.valueOf(request.getCity()), VehicleType.valueOf(request.getVehicleType()));

            LOGGER.debug("In deliveryFeeCalculate received GET deliveryFee calculate successfully with request {} ", request);

            if (response.getMessage().equals(VEHICLE_FORBIDDEN))
                return ResponseEntity.status(HttpStatus.OK).body(response.getMessage());

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (IllegalArgumentException e) {
            LOGGER.error("Error in delivery fee calculation: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Resource not found: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}

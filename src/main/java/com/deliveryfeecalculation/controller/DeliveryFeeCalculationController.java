package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.Request;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.service.DeliveryFeeCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static com.deliveryfeecalculation.constants.Constants.Endpoints.FEE_URL;

@RequestMapping(FEE_URL)
@RestController
public class DeliveryFeeCalculationController {

    private final DeliveryFeeCalculationService deliveryFeeCalculationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryFeeCalculationController.class);

    public DeliveryFeeCalculationController(final DeliveryFeeCalculationService deliveryFeeCalculationService) {
       this.deliveryFeeCalculationService = deliveryFeeCalculationService;
   }

    /**
     * Calculates the delivery fee based on the provided request details. The calculation takes into
     * account the city and vehicle type specified in the request. Logs the request and the calculation
     * result for debugging purposes.
     *
     * @param request the request containing the city and vehicle type for which the delivery fee is calculated
     * @return a {@link ResponseEntity} containing the calculation result. Returns the delivery fee in a {@link Response} object
     *         wrapped in a ResponseEntity with an HTTP status of OK.
     */
    @GetMapping()
    public ResponseEntity<?> getDeliveryFee(@RequestBody final Request request) {
        if (request == null || request.getCity()==null || request.getVehicleType()==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill all fields: city, vehicleType");

        final Response response = deliveryFeeCalculationService
                .deliveryFeeCalculate(City.valueOf(request.getCity()), VehicleType.valueOf(request.getVehicleType()));

        LOGGER.debug("In deliveryFeeCalculate received GET deliveryFee calculate successfully with request {} ", request);

        if(response.getMessage().equals(VEHICLE_FORBIDDEN))
            return ResponseEntity.status(HttpStatus.OK).body(response.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

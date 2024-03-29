package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.Request;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.service.impl.DeliveryFeeCalculationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static com.deliveryfeecalculation.factory.RequestFactory.createRequest;
import static com.deliveryfeecalculation.constants.Constants.Messages.VEHICLE_FORBIDDEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeliveryFeeCalculationController")
class DeliveryFeeCalculationControllerTest {

    @Mock
    private DeliveryFeeCalculationServiceImpl deliveryFeeCalculationService;

    @InjectMocks
    private DeliveryFeeCalculationController deliveryFeeCalculationController;

    private Response response;

    private Request request;

    @BeforeEach
    public void setUp() {
        request= createRequest();
    }

    @Nested
    @DisplayName("When Delivery Fee Calculate")
    class DeliveryFeeCalculateTests {
        @Test
        void testDeliveryFeeCalculate_status200ShouldReturnResponse() {
            response = new Response("Delivery fee calculation: ", 5.00);
            when(deliveryFeeCalculationService.deliveryFeeCalculate(City.TALLINN,VehicleType.BIKE))
                    .thenReturn(response);

            ResponseEntity<?> responseEntity = deliveryFeeCalculationController.getDeliveryFee(request);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(response, responseEntity.getBody());
        }
        @Test
        void testDeliveryFeeCalculate_status200ShouldReturnResponseWithVehicleTypeIsForbidden() {
            response = new Response(VEHICLE_FORBIDDEN, 0.00);
            when(deliveryFeeCalculationService.deliveryFeeCalculate(any(City.class),any(VehicleType.class)))
                    .thenReturn(response);

            ResponseEntity<?> responseEntity = deliveryFeeCalculationController.getDeliveryFee(request);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        void testDeliveryFeeCalculate_statusCode400WhenInvalidRequested() {
            request= null;

            ResponseEntity<?> responseEntity = deliveryFeeCalculationController.getDeliveryFee(request);

            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        }
    }

}

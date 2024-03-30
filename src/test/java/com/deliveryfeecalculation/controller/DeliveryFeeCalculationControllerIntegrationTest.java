package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.Request;
import com.deliveryfeecalculation.domain.model.Response;
import com.deliveryfeecalculation.service.DeliveryFeeCalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.deliveryfeecalculation.factory.RequestFactory.createRequest;
import static com.deliveryfeecalculation.constants.Constants.Endpoints.FEE_URL;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeliveryFeeCalculationController.class)
class DeliveryFeeCalculationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private DeliveryFeeCalculationService deliveryFeeCalculationService;

    private Request request;

    private Response response;

    @BeforeEach
    public void setUp() {
        request = createRequest();
    }

    @Test
    void testDeliveryFeeCalculate_ShouldReturnResponseWithOkStatus() throws Exception {
        response = new Response("Delivery fee calculation: ", 5.00);

        given(deliveryFeeCalculationService.deliveryFeeCalculate(any(City.class), any(VehicleType.class)))
                .willReturn(response);

        mockMvc.perform(get(FEE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"city\": \"TALLINN\", \"vehicleType\": \"BIKE\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fee").value(5.0));

        verify(deliveryFeeCalculationService, times(1)).deliveryFeeCalculate(any(City.class), any(VehicleType.class));
    }

    @Test
    void testDeliveryFeeCalculate_InvalidData() throws Exception {
        response = new Response("Delivery fee calculation: ", 5.00);

        given(deliveryFeeCalculationService.deliveryFeeCalculate(any(City.class), any(VehicleType.class)))
                .willReturn(response);
        mockMvc.perform(get(FEE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeliveryFeeCalculate_statusCode404WhenInvalidRequested() throws Exception {

        mockMvc.perform(get("/invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
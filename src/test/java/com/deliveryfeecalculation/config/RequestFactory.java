package com.deliveryfeecalculation.config;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.Request;

public class RequestFactory {

    private RequestFactory() {
    }

    public static Request createRequest() {
        final Request createdRequest = new Request();
        createdRequest.setCity("TALLINN");
        createdRequest.setVehicleType("BIKE");
        return createdRequest;
    }

}

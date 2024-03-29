package com.deliveryfeecalculation.factory;

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

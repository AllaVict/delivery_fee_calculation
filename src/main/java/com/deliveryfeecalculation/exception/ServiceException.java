package com.deliveryfeecalculation.exception;

public class ServiceException extends DeliveryFeeApplicationException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}


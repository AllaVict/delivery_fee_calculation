package com.deliveryfeecalculation.exception;


public class FeeCreationException extends DeliveryFeeApplicationException {
    public FeeCreationException(String message) {
        super(message);
    }

    public FeeCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}


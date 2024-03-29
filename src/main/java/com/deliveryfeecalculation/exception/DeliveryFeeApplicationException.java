package com.deliveryfeecalculation.exception;

public abstract class DeliveryFeeApplicationException extends RuntimeException  {
    DeliveryFeeApplicationException(final String message) {
        super(message);
    }

    DeliveryFeeApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    DeliveryFeeApplicationException(final Throwable cause) {
        super(cause);
    }
}


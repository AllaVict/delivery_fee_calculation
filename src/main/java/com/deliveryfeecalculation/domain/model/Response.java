package com.deliveryfeecalculation.domain.model;

import java.util.Objects;

public class Response {
    private String message;
    private Double fee;

    public Response() {
    }

    public Response(String message, Double fee) {
        this.message = message;
        this.fee = fee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(message, response.message) && Objects.equals(fee, response.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, fee);
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", deliveryFee=" + fee +
                '}';
    }
}

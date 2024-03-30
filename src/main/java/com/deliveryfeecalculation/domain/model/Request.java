package com.deliveryfeecalculation.domain.model;

import java.util.Objects;

public class Request {
    private String city;
    private String vehicleType;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return city == request.city && vehicleType == request.vehicleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, vehicleType);
    }

    @Override
    public String toString() {
        return "Request{" +
                "city=" + city +
                ", vehicleType=" + vehicleType +
                '}';
    }
}

package com.deliveryfeecalculation.domain.dto;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.Objects;

public class BaseFeeDTO {

    private Long id;

    private City city;

    private VehicleType vehicleType;

    private Double fee;

    private Status status;

    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseFeeDTO that = (BaseFeeDTO) o;
        return Objects.equals(id, that.id) && city == that.city && vehicleType == that.vehicleType && Objects.equals(fee, that.fee) && status == that.status && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, vehicleType, fee, status, createdDate);
    }

    @Override
    public String toString() {
        return "BaseFeeDTO{" +
                "id=" + id +
                ", city=" + city +
                ", vehicleType=" + vehicleType +
                ", fee=" + fee +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }
}

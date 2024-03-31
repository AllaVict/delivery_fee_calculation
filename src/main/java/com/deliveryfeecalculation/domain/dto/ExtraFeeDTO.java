package com.deliveryfeecalculation.domain.dto;

import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.Objects;

public class ExtraFeeDTO {

    private Long id;

    private String name;

    private VehicleType vehicleType;

    private Double fee;

    private Double lowerLimit;

    private Double upperLimit;

    private String weatherPhenomenon;


    private Boolean isForbidden;

    private Status status;

    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    public Boolean getForbidden() {
        return isForbidden;
    }

    public void setForbidden(Boolean forbidden) {
        isForbidden = forbidden;
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
        ExtraFeeDTO that = (ExtraFeeDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && vehicleType == that.vehicleType && Objects.equals(fee, that.fee) && Objects.equals(lowerLimit, that.lowerLimit) && Objects.equals(upperLimit, that.upperLimit) && Objects.equals(weatherPhenomenon, that.weatherPhenomenon) && Objects.equals(isForbidden, that.isForbidden) && status == that.status && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vehicleType, fee, lowerLimit, upperLimit, weatherPhenomenon, isForbidden, status, createdDate);
    }

    @Override
    public String toString() {
        return "ExtraFeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vehicleType=" + vehicleType +
                ", fee=" + fee +
                ", lowerLimit=" + lowerLimit +
                ", upperLimit=" + upperLimit +
                ", weatherPhenomenon='" + weatherPhenomenon + '\'' +
                ", isForbidden=" + isForbidden +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }
}

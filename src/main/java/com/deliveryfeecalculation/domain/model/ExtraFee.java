package com.deliveryfeecalculation.domain.model;

import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "extra_fees")
public class ExtraFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "extra_fee")
    private Double fee;

    @Column(name = "lower_limit")
    private Double lowerLimit;

    @Column(name = "upper_limit")
    private Double upperLimit;

    @Column(name = "weather_phenomenon")
    private String weatherPhenomenon;

    @Column(name = "is_forbidden")
    private Boolean isForbidden;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_date")
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
        ExtraFee extraFee1 = (ExtraFee) o;
        return Objects.equals(id, extraFee1.id) && Objects.equals(name, extraFee1.name) && vehicleType == extraFee1.vehicleType && Objects.equals(fee, extraFee1.fee) && Objects.equals(lowerLimit, extraFee1.lowerLimit) && Objects.equals(upperLimit, extraFee1.upperLimit) && weatherPhenomenon == extraFee1.weatherPhenomenon && Objects.equals(isForbidden, extraFee1.isForbidden) && status == extraFee1.status && Objects.equals(createdDate, extraFee1.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vehicleType, fee, lowerLimit, upperLimit, weatherPhenomenon, isForbidden, status, createdDate);
    }

    @Override
    public String toString() {
        return "ExtraFee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vehicleType=" + vehicleType +
                ", extraFee=" + fee +
                ", lowerLimit=" + lowerLimit +
                ", upperLimit=" + upperLimit +
                ", weatherPhenomenon=" + weatherPhenomenon +
                ", isForbidden=" + isForbidden +
                ", status=" + status +
                ", createdData=" + createdDate +
                '}';
    }
}

package com.deliveryfeecalculation.domain.model;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.enums.VehicleType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "base_fees")
public class BaseFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "city")
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "base_fee")
    private Double fee;

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
        BaseFee baseFee1 = (BaseFee) o;
        return Objects.equals(id, baseFee1.id) && city == baseFee1.city && vehicleType == baseFee1.vehicleType && Objects.equals(fee, baseFee1.fee) && status == baseFee1.status && Objects.equals(createdDate, baseFee1.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, vehicleType, fee, status, createdDate);
    }

    @Override
    public String toString() {
        return "BaseFee{" +
                "id=" + id +
                ", city=" + city +
                ", vehicleType=" + vehicleType +
                ", baseFee=" + fee +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }
}

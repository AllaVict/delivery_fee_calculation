package com.deliveryfeecalculation.domain.model;

import com.deliveryfeecalculation.domain.enums.City;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "weather_conditions")
public class WeatherCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "station_name")
    private City stationName;

    @Column(name = "air_temperature")
    private Double airTemperature;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "weather_phenomenon")
    private String weatherPhenomenon;

    @Column(name = "observation_time")
    private LocalDateTime observationTime;

    public WeatherCondition(final City stationName,
                            final Double airTemperature,
                            final Double windSpeed,
                            final String weatherPhenomenon,
                            final LocalDateTime observationTime) {
        this.stationName = stationName;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
        this.observationTime = observationTime;
    }

    public WeatherCondition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getStationName() {
        return stationName;
    }

    public void setStationName(City city) {
        this.stationName = city;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    public LocalDateTime getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(LocalDateTime observationTime) {
        this.observationTime = observationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherCondition that = (WeatherCondition) o;
        return Objects.equals(id, that.id) && Objects.equals(stationName, that.stationName) && Objects.equals(airTemperature, that.airTemperature) && Objects.equals(windSpeed, that.windSpeed) && weatherPhenomenon == that.weatherPhenomenon && Objects.equals(observationTime, that.observationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stationName, airTemperature, windSpeed, weatherPhenomenon, observationTime);
    }

    @Override
    public String toString() {
        return "WeatherCondition{" +
                "id=" + id +
                ", city='" + stationName + '\'' +
                ", airTemperature=" + airTemperature +
                ", windSpeed=" + windSpeed +
                ", weatherPhenomenon=" + weatherPhenomenon +
                ", observationTime=" + observationTime +
                '}';
    }
}

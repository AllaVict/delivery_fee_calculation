package com.deliveryfeecalculation.domain.model;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.enums.WeatherPhenomenon;
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
    City stationName;

    @Column(name = "air_temperature")
    Integer airTemperature;

    @Column(name = "wind_speed")
    Integer windSpeed;

    @Enumerated(EnumType.STRING)
    @Column(name = "weather_phenomenon")
    WeatherPhenomenon weatherPhenomenon;

    @Column(name = "observation_time")
    LocalDateTime observationTime;

    public WeatherCondition(final City stationName,
                            final Integer airTemperature,
                            final Integer windSpeed,
                            final WeatherPhenomenon weatherPhenomenon,
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

    public Integer getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Integer airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public WeatherPhenomenon getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    public void setWeatherPhenomenon(WeatherPhenomenon weatherPhenomenon) {
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

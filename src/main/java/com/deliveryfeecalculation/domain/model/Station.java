package com.deliveryfeecalculation.domain.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Station {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "wmocode")
    private String wmocode;
    @XmlElement(name = "longitude")
    private Double longitude;
    @XmlElement(name = "latitude")
    private Double latitude;
    @XmlElement(name = "phenomenon")
    private String phenomenon;
    @XmlElement(name = "visibility")
    private Double visibility;
    @XmlElement(name = "precipitations")
    private Double precipitations;
    @XmlElement(name = "airpressure")
    private Double airpressure;
    @XmlElement(name = "relativehumidity")
    private Double relativehumidity;
    @XmlElement(name = "airtemperature")
    private Double airtemperature;
    @XmlElement(name = "winddirection")
    private Double winddirection;
    @XmlElement(name = "windspeed")
    private Double windspeed;
    @XmlElement(name = "windspeedmax")
    private Double windspeedmax;
    @XmlElement(name = "waterlevel")
    private Double waterlevel;
    @XmlElement(name = "waterlevel_eh2000")
    private Double waterlevel_eh2000;
    @XmlElement(name = "watertemperature")
    private Double watertemperature;
    @XmlElement(name = "uvindex")
    private Double uvindex;
    @XmlElement(name = "sunshineduration")
    private Double sunshineduration;
    @XmlElement(name = "globalradiation")
    private Double globalradiation;

    public Station() {
    }

    public Station(final String name,
                   final String wmocode,
                   final Double longitude,
                   final Double latitude,
                   final String phenomenon,
                   final Double visibility,
                   final Double precipitations,
                   final Double airpressure,
                   final Double relativehumidity,
                   final Double airtemperature,
                   final Double winddirection,
                   final Double windspeed,
                   final Double windspeedmax,
                   final Double waterlevel,
                   final Double waterlevel_eh2000,
                   final Double watertemperature,
                   final Double uvindex,
                   final Double sunshineduration,
                   final Double globalradiation) {
        this.name = name;
        this.wmocode = wmocode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phenomenon = phenomenon;
        this.visibility = visibility;
        this.precipitations = precipitations;
        this.airpressure = airpressure;
        this.relativehumidity = relativehumidity;
        this.airtemperature = airtemperature;
        this.winddirection = winddirection;
        this.windspeed = windspeed;
        this.windspeedmax = windspeedmax;
        this.waterlevel = waterlevel;
        this.waterlevel_eh2000 = waterlevel_eh2000;
        this.watertemperature = watertemperature;
        this.uvindex = uvindex;
        this.sunshineduration = sunshineduration;
        this.globalradiation = globalradiation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWmocode() {
        return wmocode;
    }

    public void setWmocode(String wmocode) {
        this.wmocode = wmocode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public Double getPrecipitations() {
        return precipitations;
    }

    public void setPrecipitations(Double precipitations) {
        this.precipitations = precipitations;
    }

    public Double getAirpressure() {
        return airpressure;
    }

    public void setAirpressure(Double airpressure) {
        this.airpressure = airpressure;
    }

    public Double getRelativehumidity() {
        return relativehumidity;
    }

    public void setRelativehumidity(Double relativehumidity) {
        this.relativehumidity = relativehumidity;
    }

    public Double getAirtemperature() {
        return airtemperature;
    }

    public void setAirtemperature(Double airtemperature) {
        this.airtemperature = airtemperature;
    }

    public Double getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(Double winddirection) {
        this.winddirection = winddirection;
    }

    public Double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(Double windspeed) {
        this.windspeed = windspeed;
    }

    public Double getWindspeedmax() {
        return windspeedmax;
    }

    public void setWindspeedmax(Double windspeedmax) {
        this.windspeedmax = windspeedmax;
    }

    public Double getWaterlevel() {
        return waterlevel;
    }

    public void setWaterlevel(Double waterlevel) {
        this.waterlevel = waterlevel;
    }

    public Double getWaterlevel_eh2000() {
        return waterlevel_eh2000;
    }

    public void setWaterlevel_eh2000(Double waterlevel_eh2000) {
        this.waterlevel_eh2000 = waterlevel_eh2000;
    }

    public Double getWatertemperature() {
        return watertemperature;
    }

    public void setWatertemperature(Double watertemperature) {
        this.watertemperature = watertemperature;
    }

    public Double getUvindex() {
        return uvindex;
    }

    public void setUvindex(Double uvindex) {
        this.uvindex = uvindex;
    }

    public Double getSunshineduration() {
        return sunshineduration;
    }

    public void setSunshineduration(Double sunshineduration) {
        this.sunshineduration = sunshineduration;
    }

    public Double getGlobalradiation() {
        return globalradiation;
    }

    public void setGlobalradiation(Double globalradiation) {
        this.globalradiation = globalradiation;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", wmocode='" + wmocode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", phenomenon='" + phenomenon + '\'' +
                ", visibility=" + visibility +
                ", precipitations=" + precipitations +
                ", airpressure=" + airpressure +
                ", relativehumidity=" + relativehumidity +
                ", airtemperature=" + airtemperature +
                ", winddirection=" + winddirection +
                ", windspeed=" + windspeed +
                ", windspeedmax=" + windspeedmax +
                ", waterlevel=" + waterlevel +
                ", waterlevel_eh2000=" + waterlevel_eh2000 +
                ", watertemperature=" + watertemperature +
                ", uvindex=" + uvindex +
                ", sunshineduration=" + sunshineduration +
                ", globalradiation=" + globalradiation +
                '}';
    }
}
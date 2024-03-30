package com.deliveryfeecalculation.domain.model;

import com.deliveryfeecalculation.utils.UnixTimestampAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "observations")
public class Stations {
    @XmlAttribute(name = "timestamp")
    @XmlJavaTypeAdapter(UnixTimestampAdapter.class)
    private LocalDateTime timestamp;
    @XmlElement(name = "station")
    private List<Station> stations = new ArrayList();

    public Stations() {
    }

    public Stations(LocalDateTime timestamp, List<Station> stations) {
        this.timestamp = timestamp;
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp_gmt) {
        this.timestamp = timestamp_gmt;
    }

    @Override
    public String toString() {
        return "Stations{" +
                "timestamp='" + timestamp + '\'' +
                ", stations=" + stations +
                '}';
    }
}
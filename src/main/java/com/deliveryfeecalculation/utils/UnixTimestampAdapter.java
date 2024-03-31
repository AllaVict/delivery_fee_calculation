package com.deliveryfeecalculation.utils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UnixTimestampAdapter extends XmlAdapter<String, LocalDateTime> {

    /**
     * Converts a Unix timestamp (String) to a {@link LocalDateTime} object.
     * This method interprets the input string as the number of seconds since
     * the Unix epoch (1970-01-01T00:00:00Z) and converts it to a {@link LocalDateTime}
     * instance in UTC.
     *
     * @param v the Unix timestamp as a string. Can be {@code null}.
     * @return a {@link LocalDateTime} representing the timestamp in UTC,
     *         or {@code null} if the input string is {@code null}.
     */
    @Override
    public LocalDateTime unmarshal(String v) {
        if (v == null) return null;
        long epochSecond = Long.parseLong(v);
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneOffset.UTC);
    }

    /**
     * Converts a {@link LocalDateTime} object to a Unix timestamp string.
     * This method converts the {@link LocalDateTime} instance to the number of seconds
     * since the Unix epoch (1970-01-01T00:00:00Z) as a string. The {@link LocalDateTime}
     * is assumed to be in UTC.
     *
     * @param v the {@link LocalDateTime} instance to convert. Can be {@code null}.
     * @return a string representing the Unix timestamp, or {@code null} if the input
     *         {@link LocalDateTime} is {@code null}.
     */
    @Override
    public String marshal(LocalDateTime v) {
        if (v == null) return null;
        return String.valueOf(v.toInstant(ZoneOffset.UTC).getEpochSecond());
    }

}
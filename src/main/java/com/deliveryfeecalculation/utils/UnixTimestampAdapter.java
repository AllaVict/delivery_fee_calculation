package com.deliveryfeecalculation.utils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UnixTimestampAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        if (v == null) return null;
        long epochSecond = Long.parseLong(v);
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneOffset.UTC);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        if (v == null) return null;
        return String.valueOf(v.toInstant(ZoneOffset.UTC).getEpochSecond());
    }
}
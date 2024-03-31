package com.deliveryfeecalculation.factory;

import com.deliveryfeecalculation.domain.model.Station;

import java.util.Arrays;
import java.util.List;

public class StationFactory {

    public static List<Station> createStationList() {
        return Arrays.asList(createTallinnStation(), createTartuStation(), createParnuStation());
    }

    public static Station createTallinnStation() {
        return new Station(
                "Tallinn-Harku",
                "26038",
                24.602891666624284,
                59.398122222355134,
                "",
                35.0,
                0.0,
                1000.5,
                49.0,
                10.7,
                168.0,
                4.4,
                6.8,
                null,
                null,
                null,
                2.0,
                59.0,
                126.0);
    }

    public static Station createTartuStation() {
        return new Station(
                "Tartu-Tõravere",
                "26242",
                26.46130555576748,
                58.264072222179834,
                "",
                35.0,
                0.0,
                1002.6,
                50.0,
                10.0,
                126.0,
                4.3,
                6.6,
                null,
                null,
                null,
                null,
                200.0,
                269.0);
    }

    public static Station createParnuStation() {
        return new Station(
                "Pärnu",
                "41803",
                24.485197221899487,
                58.38456666634923,
                "",
                30.0,
                0.0,
                1000.2,
                49.0,
                10.8,
                113.0,
                5.5,
                7.3,
                null,
                -3.0,
                4.0,
                1.2,
                190.0,
                271.0);
    }
}

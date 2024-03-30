package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.model.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.deliveryfeecalculation.factory.StationFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WeatherDataImportingServiceImlTest {

    @Autowired
    private WeatherDataImportingServiceIml weatherDataImportingServiceIml;

    private List<Station> stations;

    @BeforeEach
    public void setUp() {
        stations = createStationList();
    }

    @Test
    void testImportWeatherDataFromIlmateenistus_shouldReturnStationList() {
        List<Station> result = weatherDataImportingServiceIml.importWeatherDataFromIlmateenistus();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertThat(result.get(0).getName()).isEqualTo(stations.get(0).getName());
        assertThat(result.get(1).getName()).isEqualTo(stations.get(1).getName());
        assertThat(result.get(2).getName()).isEqualTo(stations.get(2).getName());
        assertDoesNotThrow(() -> weatherDataImportingServiceIml.importWeatherDataFromIlmateenistus());
    }

}





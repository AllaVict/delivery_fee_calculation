package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.model.Station;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

import static com.deliveryfeecalculation.factory.StationFactory.*;
import static com.deliveryfeecalculation.factory.WeatherConditionFactory.createWeatherConditionList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WeatherDataImportingServiceImlTest {

    private WebClient.Builder webClientMock;
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    private WebClient.ResponseSpec responseSpecMock;

    @Autowired
    private WeatherDataImportingServiceIml weatherDataImportingServiceIml;

    private List<Station> stations;

    @BeforeEach
    public void setUp() {
        webClientMock = mock(WebClient.Builder.class);
        responseSpecMock = mock(WebClient.ResponseSpec.class);
        requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);

        MockitoAnnotations.openMocks(this);
        stations = createStationList();
    }

    @Test
    void testImportWeatherDataFromIlmateenistus_shouldReturnStationList() {
        List<Station> result = weatherDataImportingServiceIml.importWeatherDataFromIlmateenistus();

        assertNotNull(result);
        assertEquals(result.size(), 3);
        assertThat(result.get(0).getName()).isEqualTo(stations.get(0).getName());
        assertThat(result.get(1).getName()).isEqualTo(stations.get(1).getName());
        assertThat(result.get(2).getName()).isEqualTo(stations.get(2).getName());
        assertDoesNotThrow(() -> weatherDataImportingServiceIml.importWeatherDataFromIlmateenistus());
    }

}





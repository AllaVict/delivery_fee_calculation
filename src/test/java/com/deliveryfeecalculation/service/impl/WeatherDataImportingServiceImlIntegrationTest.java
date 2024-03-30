package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.model.Station;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.util.List;

import static com.deliveryfeecalculation.factory.StationFactory.*;
import static com.deliveryfeecalculation.constants.Constants.Endpoints.URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WeatherDataImportingServiceImlIntegrationTest {

    private MockWebServer mockWebServer;

    private WebClient.Builder webClient;

    private WeatherDataImportingServiceIml weatherDataImportingServiceIml;

    private List<Station> stations;

    void startServer() throws IOException {
        this.mockWebServer = new MockWebServer();
        mockWebServer.start();
        this.webClient = WebClient
                .builder()
                .baseUrl(this.mockWebServer.url(URL).toString())
                .build().mutate();
    }

    @BeforeEach
    public void setUp() throws IOException {
        startServer();
        stations = createStationList();
        weatherDataImportingServiceIml = new WeatherDataImportingServiceIml(webClient);
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

    @AfterEach
    void shutdown() throws IOException {
        if (mockWebServer != null) {
            this.mockWebServer.shutdown();
        }
    }

}

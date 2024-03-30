package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.model.Station;
import com.deliveryfeecalculation.domain.model.Stations;
import com.deliveryfeecalculation.exception.ServiceException;
import com.deliveryfeecalculation.service.WeatherDataImportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.stream.Collectors;

import static com.deliveryfeecalculation.constants.Constants.Endpoints.URL;
import static com.deliveryfeecalculation.constants.Constants.StationNames.*;

/**
 * Service for importing weather data from an external source.
 *
 * @author Alla Borodina
 */
@Service
public class WeatherDataImportingServiceIml implements WeatherDataImportingService {

    private final WebClient webClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataImportingServiceIml.class);

    public WeatherDataImportingServiceIml(final WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder.baseUrl(URL)
                .defaultHeaders(headers -> {
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
                    headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE);
                })
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer ->
                                configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder()))
                        .build())
                .build();
    }

    /**
     * Imports weather data from an external service and filters the results based on predefined criteria.
     *
     * @return a list of filtered station data
     * @throws ServiceException if there is an error during the data fetching process or if the external service is unreachable
     */
    @Override
    public List<Station> importWeatherDataFromIlmateenistus() {
        try {
            List<Station> filteredStationList =
                    webClient.get().uri(URL)
                            .accept(MediaType.APPLICATION_XML)
                            .retrieve()
                            .bodyToMono(Stations.class)
                            .map(Stations::getStations)
                            .blockOptional()
                            .orElseThrow(() -> new ServiceException("Failed to fetch weather data"))
                            .stream()
                            .filter(station -> station.getName().equalsIgnoreCase(TALLINN)
                                    || station.getName().equalsIgnoreCase(TARTU)
                                    || station.getName().equalsIgnoreCase(PARNU))
                            .collect(Collectors.toList());

            LOGGER.debug("In importWeatherDataFromIlmateenistus import station list: {} ", filteredStationList);
            return filteredStationList;

        } catch (WebClientResponseException e) {
            LOGGER.error("WebClient response error: ", e);
            throw new ServiceException("Error calling external weather data service", e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error importing weather data: ", e);
            throw new ServiceException("Unexpected error during weather data import", e);
        }
    }

}


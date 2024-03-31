package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.model.Station;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.deliveryfeecalculation.factory.StationFactory.createStationList;
import static com.deliveryfeecalculation.factory.WeatherConditionFactory.createWeatherConditionList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherDataProcessScheduledServiceImplTest {

    @Mock
    private WeatherDataImportingServiceIml weatherDataImportingService;

    @Mock
    private WeatherConditionServiceImpl weatherConditionService;

    @InjectMocks
    private WeatherDataProcessScheduledServiceImpl weatherDataProcessScheduledService;

    private List<Station> stations;

    private List<WeatherCondition> weatherConditionList;

    @BeforeEach
    public void setUp() {
        stations = createStationList();
        weatherConditionList = createWeatherConditionList();
    }

    @Test
    void testSaveWeatherCondition() {
        when(weatherDataImportingService.importWeatherDataFromIlmateenistus()).thenReturn(stations);
        when(weatherConditionService.saveWeatherCondition(stations)).thenReturn(weatherConditionList);

        weatherDataProcessScheduledService.saveImportingWeatherDataScheduled();

        verify(weatherDataImportingService, times(1)).importWeatherDataFromIlmateenistus();
        verify(weatherConditionService, times(1)).saveWeatherCondition(stations);
    }

    @Test
    void testSaveImportingWeatherDataScheduled_withImportError() {
        stations = null;
        when(weatherDataImportingService.importWeatherDataFromIlmateenistus())
                .thenThrow(new ServiceException("Failed to fetch weather data"));

        assertThrows(ServiceException.class,
                () -> weatherDataProcessScheduledService.saveImportingWeatherDataScheduled());

        verify(weatherConditionService, never()).saveWeatherCondition(any());
        verify(weatherDataImportingService, times(1)).importWeatherDataFromIlmateenistus();

    }

    @Test
    void testSaveImportingWeatherDataScheduled_withSaveError() {
        when(weatherDataImportingService.importWeatherDataFromIlmateenistus()).thenReturn(stations);
        when(weatherConditionService.saveWeatherCondition(stations))
                .thenThrow(new ServiceException("Failed to fetch weather data"));

        assertThrows(ServiceException.class,
                () -> weatherDataProcessScheduledService.saveImportingWeatherDataScheduled());

        verify(weatherDataImportingService, times(1)).importWeatherDataFromIlmateenistus();
        verify(weatherConditionService, times(1)).saveWeatherCondition(stations);

    }

}
package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.domain.enums.City;
import com.deliveryfeecalculation.domain.model.Station;
import com.deliveryfeecalculation.domain.model.WeatherCondition;
import com.deliveryfeecalculation.exception.ServiceException;
import com.deliveryfeecalculation.repository.WeatherConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.deliveryfeecalculation.factory.StationFactory.*;
import static com.deliveryfeecalculation.factory.WeatherConditionFactory.createWeatherConditionList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherConditionServiceImplTest {

    @Mock
    private WeatherConditionRepository weatherConditionRepository;

    @InjectMocks
    private WeatherConditionServiceImpl weatherConditionService;

    private List<Station> stations;
    private List<WeatherCondition> weatherConditionList;

    @BeforeEach
    public void setUp() {
        stations = createStationList();
        weatherConditionList = createWeatherConditionList();
    }

    @Test
    void testSaveWeatherCondition() {
        when(weatherConditionRepository.saveAll(any())).thenReturn(weatherConditionList);

        List<WeatherCondition> savedConditions = weatherConditionService.saveWeatherCondition(stations);

        assertNotNull(savedConditions);
        assertEquals(3, savedConditions.size());
        assertEquals(City.TALLINN, savedConditions.get(0).getStationName());
        verify(weatherConditionRepository).saveAll(any());
    }

    @Test
    void testSaveWeatherCondition_withException() {
        when(weatherConditionRepository.saveAll(any())).thenThrow(new ServiceException("Unable to save weather conditions"));

        Exception exception = assertThrows(ServiceException.class, () -> {
            weatherConditionService.saveWeatherCondition(stations);
        });

        assertEquals("Unable to save weather conditions", exception.getMessage());
        verify(weatherConditionRepository).saveAll(any());
    }
}
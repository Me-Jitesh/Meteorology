package com.jitesh.meteorology;


import com.jitesh.meteorology.entities.Pincode;
import com.jitesh.meteorology.entities.Weather;
import com.jitesh.meteorology.repositories.PincodeRepository;
import com.jitesh.meteorology.repositories.WeatherRepository;
import com.jitesh.meteorology.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class MeteorologyApplicationTests {


    @Autowired
    private WeatherService weatherService;

    @MockBean
    private PincodeRepository pincodeRepository;

    @MockBean
    private WeatherRepository weatherRepository;


    @Test
    public void shouldFetchWeatherFromAPIIfNotAvailableInDB() {

        Long id = 112L;
        String pincode = "495677";
        String main = "Clear";
        String description = "clear sky";
        Double pressure = 1014.2;
        Double feelsLike = 28.21;
        Integer visibility = 10000;
        Double windSpeed = 0.6;
        Double windDirectionInDeg = 162.0;
        Double expectedTemp = 29.09;
        Double expectedHumid = 39.0;
        Pincode mockPincode = new Pincode(id, pincode, 18.5204, 73.8567);
        LocalDate date = LocalDate.of(2024, 11, 05);

        Mockito.when(weatherRepository.findByPincodeAndDate(eq(mockPincode), eq(date)))
                .thenReturn(Optional.empty());

        Mockito.when(pincodeRepository.findByPincode(eq(pincode)))
                .thenReturn(Optional.of(mockPincode));

        Weather mockWeather = new Weather(id, mockPincode, date, main, description, expectedTemp, expectedHumid, pressure, feelsLike, visibility, windSpeed, windDirectionInDeg);
        Mockito.when(weatherRepository.save(any(Weather.class)))
                .thenReturn(mockWeather);

        Weather response = weatherService.getWeather(pincode, date);

        assertEquals("Verify Temp : ", expectedTemp, response.getTemperature());
        assertEquals("Verify Humid : ", expectedHumid, response.getHumidity());
        Mockito.verify(weatherRepository, Mockito.times(1)).findByPincodeAndDate(eq(mockPincode), eq(date));
        Mockito.verify(pincodeRepository, Mockito.times(1)).findByPincode(eq(pincode));
        Mockito.verify(weatherRepository, Mockito.times(1)).save(any(Weather.class));
    }
}

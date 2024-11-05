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
        Pincode mockPincode = new Pincode(id, pincode, 18.5204, 73.8567);
        LocalDate date = LocalDate.of(2024, 11, 05);

        Mockito.when(weatherRepository.findByPincodeAndDate(eq(mockPincode), eq(date)))
                .thenReturn(Optional.empty());

        Mockito.when(pincodeRepository.findByPincode(eq(pincode)))
                .thenReturn(Optional.of(mockPincode));

        Weather mockWeather = new Weather(id, mockPincode, date, 34.5, 50.0);
        Mockito.when(weatherRepository.save(any(Weather.class)))
                .thenReturn(mockWeather);

        Weather response = weatherService.getWeather(pincode, date);

        assertEquals("Verify Temp : ", 34.5, response.getTemperature());
        assertEquals("Verify Humid : ", 50.0, response.getHumidity());
        Mockito.verify(weatherRepository, Mockito.times(1)).findByPincodeAndDate(eq(mockPincode), eq(date));
        Mockito.verify(pincodeRepository, Mockito.times(1)).findByPincode(eq(pincode));
        Mockito.verify(weatherRepository, Mockito.times(1)).save(any(Weather.class));
    }
}

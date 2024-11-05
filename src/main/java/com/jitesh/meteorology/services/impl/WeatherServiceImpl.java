package com.jitesh.meteorology.services.impl;

import com.jitesh.meteorology.dto.GeocodeResponse;
import com.jitesh.meteorology.dto.WeatherResponse;
import com.jitesh.meteorology.entities.Pincode;
import com.jitesh.meteorology.entities.Weather;
import com.jitesh.meteorology.repositories.PincodeRepository;
import com.jitesh.meteorology.repositories.WeatherRepository;
import com.jitesh.meteorology.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private PincodeRepository pincodeRepository;
    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String API_KEY;
    @Value("${openweather.api.geocode.url}")
    private String GEOCODE_URL;
    @Value("${openweather.api.weather.url}")
    private String WEATHER_URL;

    @Override
    public Weather getWeather(String pincode, LocalDate date) {
        Pincode pincodeEntity = pincodeRepository.findByPincode(pincode)
                .orElseGet(() -> fetchAndSavePincodeDetails(pincode));

        return weatherRepository.findByPincodeAndDate(pincodeEntity, date)
                .orElseGet(() -> fetchAndSaveWeatherDetails(pincodeEntity, date));
    }

    private Pincode fetchAndSavePincodeDetails(String pincode) {
        String geocodeUrl = String.format(GEOCODE_URL, pincode, API_KEY);
        GeocodeResponse geocodeResponse = restTemplate.getForObject(geocodeUrl, GeocodeResponse.class);

        if (geocodeResponse == null || geocodeResponse.getLat() == null || geocodeResponse.getLon() == null) {
            throw new RuntimeException("Invalid response from geocoding API for pincode: " + pincode);
        }

        Pincode pincodeEntity = new Pincode();
        pincodeEntity.setPincode(pincode);
        pincodeEntity.setLatitude(geocodeResponse.getLat());
        pincodeEntity.setLongitude(geocodeResponse.getLon());
        return pincodeRepository.save(pincodeEntity);
    }

    private Weather fetchAndSaveWeatherDetails(Pincode pincode, LocalDate date) {
        String weatherUrl = String.format(WEATHER_URL, pincode.getLatitude(), pincode.getLongitude(), API_KEY);
        WeatherResponse weatherResponse = restTemplate.getForObject(weatherUrl, WeatherResponse.class);

        System.out.println(weatherResponse);

        if (weatherResponse == null) {
            throw new RuntimeException("Invalid response from weather API for pincode : " + pincode.getPincode());
        }

        Weather weather = new Weather();
        weather.setPincode(pincode);
        weather.setDate(date);
        weather.setTemperature(weatherResponse.getMain().getTemp());
        weather.setHumidity(weatherResponse.getMain().getHumidity());
        return weatherRepository.save(weather);
    }
}

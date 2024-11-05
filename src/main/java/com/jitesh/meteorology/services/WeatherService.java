package com.jitesh.meteorology.services;

import com.jitesh.meteorology.entities.Weather;

import java.time.LocalDate;

public interface WeatherService {
    Weather getWeather(String pincode, LocalDate date);
}

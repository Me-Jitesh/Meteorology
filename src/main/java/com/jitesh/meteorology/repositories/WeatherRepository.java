package com.jitesh.meteorology.repositories;

import com.jitesh.meteorology.entities.Pincode;
import com.jitesh.meteorology.entities.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findByPincodeAndDate(Pincode pincode, LocalDate date);
}
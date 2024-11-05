package com.jitesh.meteorology.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pincode_id")
    private Pincode pincode;

    private LocalDate date;
    private String main;
    private String description;
    private Double temperature;
    private Double humidity;
    private Double pressure;
    private Double feelsLike;
    private Integer visibility;
    private Double windSpeed;
    private Double windDirectionInDeg;
}
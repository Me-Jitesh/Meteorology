package com.jitesh.meteorology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private Main main;
    private Weather[] weather;
    private Wind wind;
    private Integer visibility;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Main {
        private Double temp;
        private Double humidity;
        private Double pressure;
        private Double feels_like;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Weather {
        private String main;
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Wind {
        private Double speed;
        private Double deg;
    }

}

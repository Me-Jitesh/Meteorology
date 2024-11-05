package com.jitesh.meteorology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private Main main;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Main {
        private Double temp;
        private Double humidity;
    }
}

package com.jitesh.meteorology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeocodeResponse {
    private Double lat;
    private Double lon;
}

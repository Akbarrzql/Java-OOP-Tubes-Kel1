package com.tubes.pbo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateItineraryRequest {
    private Integer provinceId;
    private Integer totalDays;
    private String title;
    private Integer numberOfTravelers;
    private String travelDate;
}

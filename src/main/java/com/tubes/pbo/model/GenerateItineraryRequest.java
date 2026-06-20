package com.tubes.pbo.model;

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

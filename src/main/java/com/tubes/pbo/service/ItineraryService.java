package com.tubes.pbo.service;

import java.util.List;
import java.util.Optional;

import com.tubes.pbo.model.Itinerary;

public interface ItineraryService {
    List<Itinerary> getAllItinerary();
    Optional<Itinerary> getItineraryById(Integer id);
    Itinerary saveItinerary(Itinerary itinerary);
    Itinerary updateItinerary(Itinerary itinerary);
    void deleteItinerary(Integer id);
}
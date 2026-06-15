package com.tubes.pbo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.pbo.model.Itinerary;
import com.tubes.pbo.repository.ItineraryRepository;
import com.tubes.pbo.service.ItineraryService;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Override
    public List<Itinerary> getAllItinerary() {
        return itineraryRepository.findAll();
    }

    @Override
    public Optional<Itinerary> getItineraryById(Integer id) {
        return itineraryRepository.findById(id);
    }

    @Override
    public Itinerary saveItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public Itinerary updateItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public void deleteItinerary(Integer id) {
        itineraryRepository.deleteById(id);
    }
}
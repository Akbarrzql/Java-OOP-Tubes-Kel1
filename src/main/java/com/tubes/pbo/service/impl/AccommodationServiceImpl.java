package com.tubes.pbo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.pbo.model.Accommodation;
import com.tubes.pbo.repository.AccommodationRepository;
import com.tubes.pbo.service.AccommodationService;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Override
    public List<Accommodation> getAllAccommodation() {
        return accommodationRepository.findAll();
    }

    @Override
    public Optional<Accommodation> getAccommodationById(Integer id) {
        return accommodationRepository.findById(id);
    }

    @Override
    public Accommodation saveAccommodation(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation updateAccommodation(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public void deleteAccommodation(Integer id) {
        accommodationRepository.deleteById(id);
    }
}
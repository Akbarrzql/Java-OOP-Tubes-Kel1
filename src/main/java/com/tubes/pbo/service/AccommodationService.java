package com.tubes.pbo.service;

import java.util.List;
import java.util.Optional;

import com.tubes.pbo.model.Accommodation;

public interface AccommodationService {

    List<Accommodation> getAllAccommodation();

    Optional<Accommodation> getAccommodationById(Integer id);

    Accommodation saveAccommodation(Accommodation accommodation);

    Accommodation updateAccommodation(Accommodation accommodation);

    void deleteAccommodation(Integer id);
}
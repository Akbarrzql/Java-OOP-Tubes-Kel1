package com.tubes.pbo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tubes.pbo.model.ItineraryDayAccommodation;

@Repository
public interface ItineraryDayAccommodationRepository extends JpaRepository<ItineraryDayAccommodation, Integer> {
    List<ItineraryDayAccommodation> findByDayId(Integer dayId);
}


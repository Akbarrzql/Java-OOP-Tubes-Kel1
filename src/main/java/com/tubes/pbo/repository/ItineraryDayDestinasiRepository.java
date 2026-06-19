package com.tubes.pbo.repository;

import com.tubes.pbo.model.ItineraryDayDestinasi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryDayDestinasiRepository extends JpaRepository<ItineraryDayDestinasi, Integer> {
    
    // PAKAI EntityGraph agar Destinasi selalu di-load
    @EntityGraph(attributePaths = {"destinasi", "itineraryDay"})
    List<ItineraryDayDestinasi> findByItineraryDay_DayIdOrderByWaktuAsc(Integer dayId);
}
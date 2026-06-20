package com.tubes.pbo.repository;

import com.tubes.pbo.model.ItineraryDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItineraryDayRepository extends JpaRepository<ItineraryDay, Integer> {

    List<ItineraryDay> findByItinerary_ItineraryIdOrderByHariKeAsc(Integer itineraryId);

    @Query("SELECT d FROM ItineraryDay d WHERE d.itinerary.itineraryId = :itineraryId")
    List<ItineraryDay> findByItineraryId(@Param("itineraryId") Integer itineraryId);
}
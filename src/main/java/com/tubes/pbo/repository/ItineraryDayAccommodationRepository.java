package com.tubes.pbo.repository;

import com.tubes.pbo.model.ItineraryDayAccommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItineraryDayAccommodationRepository extends JpaRepository<ItineraryDayAccommodation, Integer> {
    List<ItineraryDayAccommodation> findByItineraryDay_DayId(Integer dayId);

    @Query("SELECT a FROM ItineraryDayAccommodation a WHERE a.itineraryDay.dayId = :dayId")
    List<ItineraryDayAccommodation> findByDayId(@Param("dayId") Integer dayId);
}
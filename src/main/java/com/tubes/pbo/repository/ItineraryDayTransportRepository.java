package com.tubes.pbo.repository;

import com.tubes.pbo.model.ItineraryDayTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItineraryDayTransportRepository extends JpaRepository<ItineraryDayTransport, Integer> {
    List<ItineraryDayTransport> findByItineraryDay_DayIdOrderByUrutanAsc(Integer dayId);

    @Query("SELECT t FROM ItineraryDayTransport t WHERE t.itineraryDay.dayId = :dayId")
    List<ItineraryDayTransport> findByDayId(@Param("dayId") Integer dayId);
}
package com.tubes.pbo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tubes.pbo.model.ItineraryDay;

@Repository
public interface ItineraryDayRepository extends JpaRepository<ItineraryDay, Integer> {
    List<ItineraryDay> findByItineraryId(Integer itineraryId);
    ItineraryDay findByItineraryIdAndHariKe(Integer itineraryId, Integer hariKe);
}


package com.tubes.pbo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tubes.pbo.model.ItineraryDayDestinasi;

@Repository
public interface ItineraryDayDestinasiRepository extends JpaRepository<ItineraryDayDestinasi, Integer> {
    List<ItineraryDayDestinasi> findByDayId(Integer dayId);
}


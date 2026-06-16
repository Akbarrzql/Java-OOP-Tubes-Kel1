package com.tubes.pbo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tubes.pbo.model.ItineraryDayTransport;

@Repository
public interface ItineraryDayTransportRepository extends JpaRepository<ItineraryDayTransport, Integer> {
    List<ItineraryDayTransport> findByDayId(Integer dayId);
}


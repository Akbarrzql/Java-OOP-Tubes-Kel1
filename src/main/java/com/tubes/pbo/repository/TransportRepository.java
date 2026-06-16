package com.tubes.pbo.repository;

import java.util.List;
import com.tubes.pbo.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {
    List<Transport> findByProvinsiId(Integer provinsiId);
}
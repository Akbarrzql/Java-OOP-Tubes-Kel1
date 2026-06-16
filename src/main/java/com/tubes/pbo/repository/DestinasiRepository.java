package com.tubes.pbo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tubes.pbo.model.Destinasi;

@Repository
public interface DestinasiRepository extends JpaRepository<Destinasi, Integer> {
    List<Destinasi> findByProvinsiId(Integer provinsiId);
}
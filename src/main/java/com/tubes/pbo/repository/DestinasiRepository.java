package com.tubes.pbo.repository;

import com.tubes.pbo.model.Destinasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinasiRepository extends JpaRepository<Destinasi, Integer> {
} 
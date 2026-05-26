package com.tubes.pbo.repository;

import com.tubes.pbo.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {
    // Semua fungsi database dasar (save, findAll, deleteById) langsung tersedia otomatis
}
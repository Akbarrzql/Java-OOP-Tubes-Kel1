package com.tubes.pbo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tubes.pbo.model.Traveler;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Integer> {
    Optional<Traveler> findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}


package com.tubes.pbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tubes.pbo.model.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
}
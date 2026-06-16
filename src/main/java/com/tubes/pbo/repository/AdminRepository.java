package com.tubes.pbo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tubes.pbo.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}


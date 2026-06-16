package com.tubes.pbo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tubes.pbo.model.Admin;
import com.tubes.pbo.model.Traveler;
import com.tubes.pbo.model.User;
import com.tubes.pbo.repository.AdminRepository;
import com.tubes.pbo.repository.TravelerRepository;
import com.tubes.pbo.repository.UserRepository;
import com.tubes.pbo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TravelerRepository travelerRepository;

    @Override
    @Transactional
    public User registerTraveler(String name, String email, String rawPassword) {
        String normalizedEmail = normalize(email);
        if (normalizedEmail.isBlank()) {
            throw new IllegalArgumentException("Email wajib diisi.");
        }
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email sudah terdaftar.");
        }

        User user = new User();
        user.setName(name == null ? "" : name.trim());
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(rawPassword));

        User savedUser = userRepository.save(user);

        Traveler traveler = new Traveler();
        traveler.setUserId(savedUser.getId());
        traveler.setBudget(0.0);
        traveler.setPreference(null);
        travelerRepository.save(traveler);

        return savedUser;
    }

    @Override
    public Optional<User> authenticate(String email, String rawPassword) {
        String normalizedEmail = normalize(email);
        if (normalizedEmail.isBlank()) {
            return Optional.empty();
        }

        return userRepository.findByEmail(normalizedEmail)
                .filter(user -> passwordMatches(rawPassword, user.getPassword()));
    }

    @Override
    public String determineRole(Integer userId) {
        if (userId == null) {
            return null;
        }

        if (adminRepository.existsByUserId(userId)) {
            return "ADMIN";
        }

        if (travelerRepository.existsByUserId(userId)) {
            return "TRAVELER";
        }

        return null;
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(normalize(email));
    }

    private boolean passwordMatches(String rawPassword, String storedPassword) {
        if (rawPassword == null || storedPassword == null) {
            return false;
        }

        if (passwordEncoder.matches(rawPassword, storedPassword)) {
            return true;
        }

        return rawPassword.equals(storedPassword);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}

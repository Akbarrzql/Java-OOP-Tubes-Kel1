package com.tubes.pbo.service;

import java.util.Optional;

import com.tubes.pbo.model.User;

public interface AuthService {
    User registerTraveler(String name, String email, String rawPassword);

    Optional<User> authenticate(String email, String rawPassword);

    String determineRole(Integer userId);

    boolean emailExists(String email);
    
    void resetPassword(String email, String newPassword);
}

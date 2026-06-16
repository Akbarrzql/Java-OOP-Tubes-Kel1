package com.tubes.pbo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tubes.pbo.model.User;
import com.tubes.pbo.repository.UserRepository;

@Service
public class ProfileService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired private UserRepository userRepository;

    @Transactional
    public void updateProfile(Integer userId, String name, String email) {
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Nama tidak boleh kosong.");
    }
    if (email == null || email.isBlank()) {
        throw new IllegalArgumentException("Email tidak boleh kosong.");
    }

    //validadasi gmail
    User existing = userRepository.findByEmail(email.trim()).orElse(null);
    if (existing != null && !existing.getId().equals(userId)) {
        throw new IllegalArgumentException("Email sudah digunakan akun lain.");
    }

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan."));

    user.setName(name.trim());
    user.setEmail(email.trim());
    userRepository.save(user);
    }

    @Transactional
    public void changePassword(Integer userId, String currentPassword,
                               String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("Password baru minimal 8 karakter.");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Konfirmasi password tidak cocok.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan."));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Password saat ini salah.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

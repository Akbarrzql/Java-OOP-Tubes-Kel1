package com.tubes.pbo.controller;

import com.tubes.pbo.model.Admin;
import com.tubes.pbo.model.User;
import com.tubes.pbo.repository.AdminRepository;
import com.tubes.pbo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/settings")
public class SettingsController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String settingsPage(Model model) {
        List<Admin> adminList = adminRepository.findAll();
        List<User> adminUserList = new ArrayList<>();
        for (Admin admin : adminList) {
            userRepository.findById(admin.getUserId()).ifPresent(adminUserList::add);
        }
        model.addAttribute("adminUserList", adminUserList);
        return "admin/settings/settings";
    }

    @PostMapping("/add")
    public String addAdmin(@RequestParam String email,
            RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User dengan email " + email + " tidak ditemukan.");
            return "redirect:/admin/settings";
        }

        User user = userOpt.get();
        if (adminRepository.existsByUserId(user.getId())) {
            redirectAttributes.addFlashAttribute("error", "User ini sudah menjadi admin.");
            return "redirect:/admin/settings";
        }

        Admin admin = new Admin();
        admin.setUserId(user.getId());
        admin.setAdminLevel(1);
        adminRepository.save(admin);

        redirectAttributes.addFlashAttribute("success", user.getName() + " berhasil ditambahkan sebagai admin.");
        return "redirect:/admin/settings";
    }

    @GetMapping("/remove/{userId}")
    public String removeAdmin(@PathVariable Integer userId,
            RedirectAttributes redirectAttributes) {
        Optional<Admin> adminOpt = adminRepository.findByUserId(userId);
        if (adminOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Admin tidak ditemukan.");
            return "redirect:/admin/settings";
        }

        adminRepository.delete(adminOpt.get());
        redirectAttributes.addFlashAttribute("success", "Hak akses admin berhasil dicabut.");
        return "redirect:/admin/settings";
    }
}
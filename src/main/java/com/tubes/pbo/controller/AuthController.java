package com.tubes.pbo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tubes.pbo.model.User;
import com.tubes.pbo.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Email dan password wajib diisi.");
            return "redirect:/login";
        }

        User user = authService.authenticate(email, password)
                .orElse(null);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Email atau password salah.");
            return "redirect:/login";
        }

        String role = authService.determineRole(user.getId());
        if (role == null) {
            redirectAttributes.addFlashAttribute("error", "Role akun tidak ditemukan. Silakan hubungi admin.");
            return "redirect:/login";
        }

        session.setAttribute("loggedInUserId", user.getId());
        session.setAttribute("loggedInUserName", user.getName());
        session.setAttribute("loggedInUserEmail", user.getEmail());
        session.setAttribute("userRole", role);

        redirectAttributes.addFlashAttribute("success", "Selamat datang kembali, " + user.getName() + "!");

        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        if (name == null || name.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Nama, email, dan password wajib diisi.");
            return "redirect:/register";
        }

        try {
            authService.registerTraveler(name, email, password);
            redirectAttributes.addFlashAttribute("success", "Registrasi berhasil. Silakan login dengan akun traveler Anda.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/register";
        }
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (email == null || email.isBlank() || newPassword == null || newPassword.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Email dan password baru wajib diisi.");
            return "redirect:/forgot-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Konfirmasi password tidak cocok.");
            return "redirect:/forgot-password";
        }

        try {
            authService.resetPassword(email, newPassword);
            redirectAttributes.addFlashAttribute("success", "Password berhasil diperbarui. Silakan login kembali.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

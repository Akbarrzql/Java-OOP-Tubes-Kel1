package com.tubes.pbo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tubes.pbo.model.User;
import com.tubes.pbo.repository.UserRepository;
import com.tubes.pbo.service.ProfileService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired private ProfileService profileService;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public String showProfile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        // Avatar initial dari huruf depan nama
        String initial = "";
        if (user.getName() != null && !user.getName().trim().isEmpty()) {
            initial = String.valueOf(user.getName().trim().charAt(0)).toUpperCase();
        }
        model.addAttribute("avatarInitial", initial);

        model.addAttribute("user", user);
        return "traveler/profile/profile";
    }

    @PostMapping("/update")
public String updateName(@RequestParam String name,
                         @RequestParam String email,  // ← tambah ini
                         HttpSession session,
                         RedirectAttributes redirectAttrs) {
    Integer userId = (Integer) session.getAttribute("loggedInUserId");
    if (userId == null) return "redirect:/login";

    try {
        profileService.updateProfile(userId, name, email);  // ← ganti method
        session.setAttribute("loggedInUserName", name.trim());
        session.setAttribute("loggedInUserEmail", email.trim());
        redirectAttrs.addFlashAttribute("successMessage", "Profil berhasil diperbarui.");
    } catch (IllegalArgumentException e) {
        redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/profile";
}

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 RedirectAttributes redirectAttrs) {
        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) return "redirect:/login";

        try {
            profileService.changePassword(userId, currentPassword, newPassword, confirmPassword);
            redirectAttrs.addFlashAttribute("successMessage", "Password berhasil diubah.");
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/profile";
    }
}
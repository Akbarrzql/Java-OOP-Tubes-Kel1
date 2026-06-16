package com.tubes.pbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

    /*
     * =========================
     * PUBLIC PAGE
     * =========================
     */

    @GetMapping("/")
    public String home() {
        return "home/index";
    }

    /*
     * =========================
     * AUTH PAGE
     * =========================
     */

    @GetMapping("/login")
    public String login(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }
        if ("TRAVELER".equals(role)) {
            return "redirect:/dashboard";
        }
        return "login/login";
    }

    @GetMapping("/register")
    public String register(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }
        if ("TRAVELER".equals(role)) {
            return "redirect:/dashboard";
        }
        return "register/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgotpassword/forgot-password";
    }

    /*
     * =========================
     * TRAVELER PAGE
     * =========================
     */

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if ("ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/admin/dashboard";
        }
        return "traveler/dashboard/dashboard";
    }

    @GetMapping("/itinerary/list")
    public String itineraryList() {
        return "traveler/itinerary/list";
    }

    @GetMapping("/explore")
    public String explore() {
        return "traveler/explore/index";
    }

    @GetMapping("/itinerary/{id}")
    public String itineraryDetail(@PathVariable Long id, Model model) {
        model.addAttribute("itineraryId", id);
        return "traveler/itinerary/detail-itinerary";
    }

    @GetMapping("/profile")
    public String profile() {
        return "traveler/profile/profile";
    }

    /*
     * =========================
     * ADMIN PAGE
     * =========================
     */

    // @GetMapping("/admin/dashboard")
    // public String adminDashboard() {
    //     return "admin/dashboard-admin";
    // }

    // @GetMapping("/admin/transport")
    // public String adminTransport() {
    //     return "admin/transport/transport";
    // }

    @GetMapping("/admin/profile")
    public String adminProfile() {
        return "admin/profile/profile";
    }

    @GetMapping("/admin/settings")
    public String adminSettings() {
        return "admin/settings/settings";
    }

    @GetMapping("/admin/destination")
    public String adminDestination() {
        return "admin/destination/destination";
    }

    // @GetMapping("/admin/accommodation")
    // public String adminAccommodation() {
    //     return "admin/accommodation/accommodation";
    // }    
}
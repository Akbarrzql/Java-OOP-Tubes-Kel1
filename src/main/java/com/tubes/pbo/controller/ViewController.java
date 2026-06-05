package com.tubes.pbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    /* =========================
       PUBLIC PAGE
    ========================= */

    @GetMapping("/")
    public String home() {
        return "home/index";
    }

    /* =========================
       AUTH PAGE
    ========================= */

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/register")
    public String register() {
        return "register/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgotpassword/forgot-password";
    }

    /* =========================
       TRAVELER PAGE
    ========================= */

    @GetMapping("/dashboard")
    public String dashboard() {
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

    /* =========================
       ADMIN PAGE
    ========================= */

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard-admin";
    }

    @GetMapping("/admin/transport")
    public String adminTransport() {
        return "admin/transport/transport";
    }

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

    @GetMapping("/admin/accommodation")
    public String adminAccommodation() {
        return "admin/accommodation/accommodation";
    }

    @GetMapping("/admin/province")
    public String adminProvince() {
        return "admin/province/province";
    }
}


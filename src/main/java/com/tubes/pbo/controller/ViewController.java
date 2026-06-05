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
       DASHBOARD
    ========================= */

    @GetMapping("/dashboard")
    public String dashboard() {
        return "traveler/dashboard/dashboard";
    }

    /* =========================
       ITINERARY
    ========================= */

    @GetMapping("/itinerary/list")
    public String itineraryList() {
        return "traveler/itinerary/list";
    }

    /* =========================
       EXPLORE
    ========================= */

    @GetMapping("/explore")
    public String explore() {
        return "traveler/explore/index";
    }

}